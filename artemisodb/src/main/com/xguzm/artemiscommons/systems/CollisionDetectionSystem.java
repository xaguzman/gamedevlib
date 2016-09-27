package com.xguzm.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalIteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.xguzm.artemiscommons.components.Collision;
import com.xguzm.artemiscommons.components.Velocity;
import com.xguzm.artemiscommons.events.Event;

/**
 * Triggers events when a collision happens between two entities.
 * Depends on {@link EventsSystem} and {@link SpatialHashSystem}
 * */
public class CollisionDetectionSystem extends IntervalIteratingSystem {

    @Wire EventsSystem events;
    @Wire SpatialHashSystem hashGrid;
    @Wire ComponentMapper<Collision> collisionMapper;
    @Wire ComponentMapper<Velocity> velMapper;

    private final int[] collisionEntities = new int[2];

    Array<CollisionPair> activeCollisions;
    Array<CollisionPair> tmp;
    Pool<CollisionPair> collisionPairPool;

    /** Creates the collision system with the update time set to 1/10 (process 10 times per second) */
    public CollisionDetectionSystem(){
        this(1f / 10f);
    }

    public CollisionDetectionSystem(float updateInterval){
        super(Aspect.all(Collision.class), updateInterval);
    }

    @Override
    protected void initialize() {
        activeCollisions = new Array<CollisionPair>();
        tmp = new Array<CollisionPair>();
        collisionPairPool = new Pool<CollisionPair>() {
            @Override
            protected CollisionPair newObject() {
                CollisionPair pair = new CollisionPair();
                activeCollisions.add(pair);
                return pair;
            }

            @Override
            public void free(CollisionPair object) {
                activeCollisions.removeValue(object, false);
                super.free(object);
            }
        };
    }

    @Override
    protected void removed(int entityId) {
        Entity e = world.getEntity(entityId);
        for(CollisionPair pair : getCollisionPairs(e.getId())){
            collisionPairPool.free(pair);
        }
    }

    private void checkCollision(int e, int potentialCollider){
        Collision eCol = collisionMapper.get(e);
        Collision potColliderCol = collisionMapper.get(potentialCollider);

        if (canCollide(eCol, potColliderCol) && collisionExists(eCol, potColliderCol)){
            CollisionPair collisionPair = getOrCreateCollisionPair(e, potentialCollider);
            if (!collisionPair.isColliding){

                //on collision enter
                Event collisionStartEvent = new Event();
                collisionStartEvent.type = Event.Types.COLLISION_STARTED;
                collisionEntities[0] = collisionPair.eId1;
                collisionEntities[1] = collisionPair.eId2;
                collisionStartEvent.sourceEntities = collisionEntities;
//                    Gdx.app.log("Collision started", "entities:" + collisionStartEvent.sourceEntities);

                events.broadCast(collisionStartEvent);
            }
            collisionPair.isColliding = true;
        }else{
            CollisionPair collisionPair = getCollisionPair(e, potentialCollider);

            if (collisionPair != null){
                //on collision end

                Event collisionEndEvent = new Event();
                collisionEndEvent.type = Event.Types.COLLISION_STARTED;
                collisionEntities[0] = collisionPair.eId1;
                collisionEntities[1] = collisionPair.eId2;
                collisionEndEvent.sourceEntities = collisionEntities;
//                    Gdx.app.log("Collision end", "entities:" + collisionEndEvent.sourceEntities);

                collisionPairPool.free(collisionPair);
                events.broadCast(collisionEndEvent);
            }
        }
    }

    private boolean canCollide(Collision collisionA, Collision collisionB){

        if (collisionA == null || collisionB == null)
            return false; //could happen when an entity is removed

        return (collisionA.categoryMaskBits & collisionB.categoryBits) != 0 &&
                (collisionA.categoryBits & collisionB.categoryMaskBits) != 0;
    }

    private boolean collisionExists(Collision c1, Collision c2) {
        return c1.bounds.overlaps(c2.bounds);
    }

    private CollisionPair getCollisionPair(int id1, int id2){
        for(CollisionPair pair : activeCollisions){
            if ((pair.eId1 == id1 && pair.eId2 == id2) || (pair.eId1 == id2 && pair.eId2 == id1))
                return pair;
        }
        return null;
    }

    private CollisionPair getOrCreateCollisionPair(int id1, int id2){
        CollisionPair pair = getCollisionPair(id1, id2);
        if (pair == null) {
            pair = collisionPairPool.obtain();
            pair.eId1 = id1;
            pair.eId2 = id2;
            activeCollisions.add(pair);
        }

        return pair;
    }

    public int getCollisionsCount(final int entityId){
        int count = 0;
        for(CollisionPair pair : activeCollisions)
            if (pair.eId1 == entityId || pair.eId2 == entityId)
                count++;

        return count;
    }

    public Array<CollisionPair> getCollisionPairs(final int entityId){
        tmp.clear();
        for(CollisionPair pair : activeCollisions){
            if (pair.eId1 == entityId || pair.eId2 == entityId)
                tmp.add(pair);
        }
        return tmp;
    }

    @Override
    protected void process(int entityId) {
        if (!velMapper.has(entityId)){
            return; //skip collision check for static objects
        }

        IntArray potentialColliders = hashGrid.getPotentialCollidersIds(entityId);

        for(int i = 0; i < potentialColliders.size; i++)
            checkCollision(entityId, potentialColliders.get(i));
    }


    class CollisionPair implements Pool.Poolable{
        public int eId1, eId2;
        boolean isColliding;

        @Override
        public void reset() {
            isColliding = false;
            eId1 = eId2 = 0;
        }

        @Override
        public boolean equals(Object obj) {
            CollisionPair pair = (CollisionPair)obj;

            return (pair.eId1 == eId1 && pair.eId2 == eId2) || (pair.eId1 == eId2 && pair.eId2 == eId1);
        }
    }
}

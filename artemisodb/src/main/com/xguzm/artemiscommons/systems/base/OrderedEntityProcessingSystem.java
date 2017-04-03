package com.xguzm.artemiscommons.systems.base;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

/**
 * Orders entities before processing them via the {@link #comparator}
 */
public abstract class OrderedEntityProcessingSystem extends BaseEntitySystem {

    private Comparator<Entity> comparator;
    private Array<Entity> entities;

    protected OrderedEntityProcessingSystem(Aspect.Builder aspect){
        super(aspect);
        entities = new Array<Entity>();
    }

    protected OrderedEntityProcessingSystem(Aspect.Builder aspect, Comparator<Entity> comparator){
        this(aspect);
        this.comparator = comparator;
    }

    protected void setComparator(Comparator<Entity> comparator){
        this.comparator = comparator;
    }

    @Override
    public void inserted(int entityId) {
        entities.add(world.getEntity(entityId));
    }

    @Override
    public void removed(int entityId) {
        entities.removeValue(world.getEntity(entityId), true);
    }

    @Override
    public void processSystem(){
        entities.sort(comparator);
        for (Entity e : entities)
            process(e);
    }

    public abstract void process(Entity e);
}

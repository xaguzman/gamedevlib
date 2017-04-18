package com.xguzm.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.xguzm.artemiscommons.components.transform.Position;
import com.xguzm.artemiscommons.components.Velocity;


/**
 * A system which will move an entity based on it's position and its velocity(scaled).
 *
 * The movement is not restricted by any means
 * Created by gdlxguzm on 5/20/2015.
 */
public class BasicMovementSystem extends EntityProcessingSystem {

    @Wire ComponentMapper<Velocity> vm;
    @Wire ComponentMapper<Position> pm;

    private Vector2 nextPosition = new Vector2();
    private Vector2 scaledVelocity = new Vector2();

    public BasicMovementSystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {
        Position position = pm.get(e);
        Velocity velocity = vm.get(e);

        scaledVelocity.set(velocity.xy);
        nextPosition.set(position.xy).add(scaledVelocity);

        position.prev.set(position.xy);
        position.xy.set(nextPosition);
    }
}

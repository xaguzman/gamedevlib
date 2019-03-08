package com.xaguzman.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.xaguzman.artemiscommons.components.Velocity;
import com.xaguzman.artemiscommons.components.transform.Position;


/**
 * A system which will move an entity based on it's position and its velocity(scaled).
 *
 * The movement is not restricted by any means
 * Created by gdlxguzm on 5/20/2015.
 */
public class BasicMovementSystem extends IteratingSystem {

    @Wire ComponentMapper<Velocity> vm;
    @Wire ComponentMapper<Position> pm;

    private Vector2 nextPosition = new Vector2();
    private Vector2 scaledVelocity = new Vector2();

    public BasicMovementSystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(int entityId) {
        Position position = pm.get(entityId);
        Velocity velocity = vm.get(entityId);

        scaledVelocity.set(velocity.xy).scl(getWorld().getDelta());
        nextPosition.set(position.xy).add(scaledVelocity);

        position.prev.set(position.xy);
        position.xy.set(nextPosition);
    }

}

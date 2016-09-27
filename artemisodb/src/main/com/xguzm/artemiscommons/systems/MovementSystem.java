package com.xguzm.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.xguzm.artemiscommons.components.Position;
import com.xguzm.artemiscommons.components.Velocity;


/**
 * Created by gdlxguzm on 5/20/2015.
 */
public class MovementSystem extends EntityProcessingSystem {

    @Wire ComponentMapper<Velocity> vm;
    @Wire ComponentMapper<Position> pm;

    private Vector2 nextPosition = new Vector2();

    public MovementSystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }



    @Override
    protected void process(Entity e) {
        Position pos = pm.get(e);
        Velocity vel = vm.get(e);

        nextPosition.set(pos.xy).add(vel.xy);
    }
}

package com.xguzm.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.xguzm.artemiscommons.components.Expiration;


/**
 * Deletes entities after their Expiration component timer has reached 0
 * Created by gdlxguzm on 6/6/2015.
 */
public class ExpirationSystem extends IteratingSystem {

    @Wire
    ComponentMapper<Expiration> em;

    public ExpirationSystem() {
        super(Aspect.all(Expiration.class));
    }

    @Override
    protected void process(int entityId) {
        Expiration expiration = em.get(entityId);

        expiration.timeLeft -= world.delta;

        if ( expiration.timeLeft <= 0)
            world.delete(entityId);
    }
}

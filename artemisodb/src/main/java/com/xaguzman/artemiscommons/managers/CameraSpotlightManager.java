package com.xaguzman.artemiscommons.managers;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.xaguzman.artemiscommons.components.CameraSpotlight;

/**
 * Makes sure there's never more than 1 worldCamera spotlight
 *
 * Created by gdlxguzm on 4/3/2017.
 */
public class CameraSpotlightManager extends Manager {

    private int currentSpotlightId = -1;
    @Wire ComponentMapper<CameraSpotlight> m;

    public CameraSpotlightManager(){}

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);

        suscribeToCameraSpotlight();
    }

    /**
     * Cause population of missing references.
     */
    private void suscribeToCameraSpotlight() {
        world.getSystem(AspectSubscriptionManager.class).get(Aspect.all(CameraSpotlight.class))
                .addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
                    @Override
                    public void inserted(IntBag entities) {
                        int[] ids = entities.getData();
                        if (currentSpotlightId != -1){
                            m.remove(currentSpotlightId);
                        }
                        currentSpotlightId = ids[ids.length - 1];
                    }

                    @Override
                    public void removed(IntBag entities) {
                    }
                });
    }
}

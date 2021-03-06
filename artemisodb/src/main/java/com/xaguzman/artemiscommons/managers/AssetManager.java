package com.xaguzman.artemiscommons.managers;

import com.artemis.*;
import com.artemis.annotations.SkipWire;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;

/**
 * Manages reference components to assets.
 *
 * - Adds asset reference component to entities with metadata component.
 * - Reloads asset reference if removed.
 * - Removes asset reference when metadata component is removed.
 *
 * @author Daan van Yperen
 *
 * @param <A> Identifying metadata component type.
 * @param <B> Asset reference component type (typically transient).
 */
@SkipWire
public abstract class AssetManager<A extends Component, B extends Component> extends Manager {

    protected final Class<A> metadataType;
    protected final Class<B> referenceType;

    protected ComponentMapper<A> mMetadataType;
    protected ComponentMapper<B> mReferenceType;

    /**
     * @param metadataType Identifying component type.
     * @param referenceType Asset reference component type (typically transient).
     */
    public AssetManager(Class<A> metadataType, Class<B> referenceType) {
        this.metadataType = metadataType;
        this.referenceType = referenceType;
    }

    /**
     * Setup new asset reference component by identifying component.
     *
     * @param entityId Altered entity id.
     * @param a Existing asset identifying component.
     * @param b New asset reference component to setup.
     */
    protected abstract void setup(int entityId, A a, B b);

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        mMetadataType = world.getMapper(metadataType);
        mReferenceType = world.getMapper(referenceType);

        listenForResolveJobs();
        listenForCleanupJobs();
    }

    /**
     * Cause cleanup of asset reference without asset metadata.
     */
    private void listenForCleanupJobs() {
        world.getSystem(AspectSubscriptionManager.class).get(Aspect.all(referenceType).exclude(metadataType))
                .addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
                    @Override
                    public void inserted(IntBag entities) {
                        int[] ids = entities.getData();
                        for (int i = 0, s = entities.size(); s > i; i++) {
                            remove(ids[i]);
                        }
                    }



                    @Override
                    public void removed(IntBag entities) {
                    }
                });
    }

    /**
     * Cause population of missing references.
     */
    private void listenForResolveJobs() {
        world.getSystem(AspectSubscriptionManager.class).get(Aspect.all(metadataType).exclude(referenceType))
                .addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
                    @Override
                    public void inserted(IntBag entities) {
                        int[] ids = entities.getData();
                        for (int i = 0, s = entities.size(); s > i; i++) {
                            create(ids[i]);
                        }
                    }

                    @Override
                    public void removed(IntBag entities) {
                    }
                });
    }

    protected void create(int entityId) {
        setup(entityId, mMetadataType.get(entityId), mReferenceType.create(entityId));
    }

    protected void remove(int entityId) {
        Entity entity = world.getEntity(entityId);
        if (entity != null) {
            mReferenceType.remove(entity);
        }
    }
}

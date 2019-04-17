package com.xaguzman.artemiscommons.managers;

import com.artemis.*;
import com.artemis.annotations.SkipWire;
import com.artemis.utils.IntBag;

@SkipWire
public abstract class DualAssetManager<A extends Component, B extends Component, C extends Component> extends Manager {

    protected final Class<A> metadataType1;
    protected final Class<B> metadataType2;
    protected final Class<C> referenceType;

    protected ComponentMapper<A> mMetadataType1;
    protected ComponentMapper<B> mMetadataType2;
    protected ComponentMapper<C> mReferenceType;

    public DualAssetManager(Class<A> metadataType1, Class<B> metadataType2, Class<C> referenceType) {
        this.metadataType1 = metadataType1;
        this.metadataType2 = metadataType2;
        this.referenceType = referenceType;
    }

    protected abstract void setupMeta1(int entityId, A a, C c);

    protected abstract void setupMeta2(int entityId, B b, C c);

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);

        mMetadataType1 = world.getMapper(metadataType1);
        mMetadataType2 = world.getMapper(metadataType2);
        mReferenceType = world.getMapper(referenceType);

        listenForResolveJobs();
        listenForCleanupJobs();
    }

    /**
     * Cause cleanup of asset reference without asset metadata.
     */
    private void listenForCleanupJobs() {
        world.getSystem(AspectSubscriptionManager.class).get(Aspect.all(referenceType).exclude(metadataType1, metadataType2))
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
        world.getSystem(AspectSubscriptionManager.class).get(Aspect.one(metadataType1, metadataType2).exclude(referenceType))
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
        if (mMetadataType1.has(entityId)){
            setupMeta1(entityId, mMetadataType1.get(entityId), mReferenceType.create(entityId));
        }else{
            setupMeta2(entityId, mMetadataType2.get(entityId), mReferenceType.create(entityId));
        }

    }

    protected void remove(int entityId) {
        Entity entity = world.getEntity(entityId);
        if (entity != null) {
            mReferenceType.remove(entity);
        }
    }
}

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

    private Comparator<Integer> comparator;
    private Array<Integer> entities;

    protected OrderedEntityProcessingSystem(Aspect.Builder aspect){
        super(aspect);
        entities = new Array<Integer>();
    }

    protected OrderedEntityProcessingSystem(Aspect.Builder aspect, Comparator<Integer> comparator){
        this(aspect);
        this.comparator = comparator;
    }

    protected void setComparator(Comparator<Integer> comparator){
        this.comparator = comparator;
    }

    @Override
    public void inserted(int entityId) {
        entities.add(entityId);
    }

    @Override
    public void removed(int entityId) {
        entities.removeValue(entityId, true);
    }

    @Override
    public void processSystem(){
        entities.sort(comparator);
        for (int entityId : entities)
            process(entityId);
    }

    public abstract void process(int entityId);
}

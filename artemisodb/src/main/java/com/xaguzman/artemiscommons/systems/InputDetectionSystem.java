package com.xaguzman.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.xaguzman.artemiscommons.components.Input;
import com.xaguzman.gdxcommons.input.InputConfiguration;

/** Updates the entities input. Detects current and previous frame's input */
public class InputDetectionSystem extends BaseEntitySystem {

    @Wire ComponentMapper<Input> inputMapper;

    int previousActions, currentActions;

    InputConfiguration inputConfiguration;

    public InputDetectionSystem() {
        this(new InputConfiguration());
    }

    public InputDetectionSystem(InputConfiguration inputCfg){
        super(Aspect.all(Input.class));
        this.inputConfiguration = inputCfg;
    }

    public void setInputConfiguration(InputConfiguration cfg){
        inputConfiguration = cfg;
    }

    public boolean isActionActive(int action){
        return (currentActions & action) != 0;
    }

    public boolean isAnyActionActive(int... actions){
        for (int action : actions){
            if ( (currentActions & action) != 0)
                return true;
        }
        return false;
    }

    public boolean justDidAction(int action){
        return isActionActive(action) &&  ((previousActions & action) == 0);
    }

    public void addAction(int action){
        currentActions |= action;
    }

    @Override
    protected void processSystem() {
        previousActions = currentActions;
        currentActions = update();

        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();
        for (int i = 0, s = actives.size(); s > i; i++) {
            process(ids[i]);
        }
    }

    protected void process(int entityId) {
        Input input = inputMapper.get(entityId);
        input.previousActions = previousActions;
        input.currentActions = currentActions;
    }

    private int update() {
        int actions = 0;

        IntMap.Keys actionsIterator = inputConfiguration.keyboardMapping.keys();

        while(actionsIterator.hasNext){
            int action = actionsIterator.next();
            IntArray triggers = inputConfiguration.keyboardMapping.get(action);
            for( int idx = 0; idx < triggers.size;  idx++){
                int trigger = triggers.get(idx);
                if (Gdx.input.isKeyPressed(trigger))
                    actions |= action;
            }
        }

        return actions;
    }
}

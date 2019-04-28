package com.xaguzman.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.xaguzman.artemiscommons.components.Input;
import com.xaguzman.gdxcommons.input.InputConfiguration;

/** Updates the entities input. Detects current and previous frame's input */
public class InputDetectionSystem extends IteratingSystem {

    @Wire ComponentMapper<Input> inputMapper;

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

    @Override
    protected void process(int entityId) {
        Input input = inputMapper.get(entityId);
        input.previousActions = input.currentActions;
        input.currentActions = update(input);
    }

    public int update(Input input) {
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

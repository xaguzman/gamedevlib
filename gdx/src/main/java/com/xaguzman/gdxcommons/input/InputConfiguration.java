package com.xaguzman.gdxcommons.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;

public class InputConfiguration {
    public final IntMap<IntArray> keyboardMapping = new IntMap<IntArray>();

    public InputConfiguration(){
        setKeyboardMapping(getDefault());
    }

    public InputConfiguration(IntMap<IntArray> keyboardMapping){
        setKeyboardMapping(keyboardMapping);
    }

    public void setKeyboardMapping(IntMap<IntArray> keyboardMapping){
        this.keyboardMapping.putAll(keyboardMapping);
    }

    private IntMap<IntArray> getDefault(){
        IntMap<IntArray> keyboard = new IntMap<IntArray>();

        keyboard.put(InputAction.PRESS_LEFT, new IntArray(new int[] { Input.Keys.LEFT }));
        keyboard.put(InputAction.PRESS_RIGHT, new IntArray(new int[] { Input.Keys.RIGHT }));
        keyboard.put(InputAction.PRESS_UP, new IntArray(new int[] { Input.Keys.UP }));
        keyboard.put(InputAction.PRESS_DOWN, new IntArray(new int[] { Input.Keys.DOWN }));
        return keyboard;
    }
}

package com.xaguzman.gdxcommons.input;

import com.badlogic.gdx.math.MathUtils;

/** Some sample actions possible within the game. **/
public final class InputAction {
    public static final short NONE = 0;
    public static final short PRESS_RIGHT = 1;
    public static final short PRESS_LEFT = 1 << 1;
    public static final short PRESS_UP = 1 << 2;
    public static final short PRESS_DOWN = 1 << 3;
    public static final short PRESS_FIRE = 1 << 4;
    public static final short PRESS_JUMP = 1 << 5;

}

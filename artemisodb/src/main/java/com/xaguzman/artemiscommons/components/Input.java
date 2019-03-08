package com.xaguzman.artemiscommons.components;

import com.artemis.PooledComponent;

/** Used to store user input and puts it in an int. Also stores user's actions on previous frame */
public class Input extends PooledComponent {
    public int previousActions;
    public int currentActions;

    public Input(){ }

    @Override
    protected void reset() {

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
}

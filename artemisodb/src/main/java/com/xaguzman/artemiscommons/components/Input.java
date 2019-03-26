package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/** Used to store user input and puts it in an int. Also stores user's actions on previous frame */
@PooledWeaver
public class Input extends Component {
    public int previousActions;
    public int currentActions;

    public Input(){ }

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

package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/**
 * Created by Xavier Guzman on 6/6/2015.
 */
@PooledWeaver
public class Expiration extends Component {
    public float timeLeft;

    public Expiration() {}

    public Expiration(float time){
        this.timeLeft = time;
    }

}

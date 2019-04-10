package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.artemis.annotations.PooledWeaver;

/**
 * Created by Xavier Guzman on 4/3/2017.
 */
@PooledWeaver
public class Label extends Component {
    public String fontName;
    public String text;

}

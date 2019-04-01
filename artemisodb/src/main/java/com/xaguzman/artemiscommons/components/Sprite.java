package com.xaguzman.artemiscommons.components;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 *
 * Created by xavier  on 4/3/2017.
 */
@PooledWeaver
public class Sprite extends Component {
    /** The name or id of this sprite. If not set, a {@link AnimatedSprite } should be set so the asset can be retrieved  */
    public String name;

    /**  The layer to draw the sprite, lower values go at the back */
    public int layer;

}

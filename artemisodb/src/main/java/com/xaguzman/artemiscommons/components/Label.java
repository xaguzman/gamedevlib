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

    /**
     * If true, an {@link #atlasName} must be provided.
     *
     * The atlas will be attemped to be retrieved from the sprite assets system, and then from the atlas the requested
     * sprite will be requested for the font ({@link #fontAtlasTextureName} is the name of the texture to extract)
     */
    public boolean isTextureInFont;

    public String atlasName;

    /**
     * The name of the texture region to use from the given {@link #atlasName}. If not provided, {@link #fontName} will be used instead
     */
    public String fontAtlasTextureName;

    public String text;
    /**
     * 0 = left
     * 0.5 = center
     * 1 = right
     */
    public float align = 0;
}

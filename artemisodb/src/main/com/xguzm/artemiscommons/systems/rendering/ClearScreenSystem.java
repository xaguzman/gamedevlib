package com.xguzm.artemiscommons.systems.rendering;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * Clears the screen with the desired {@link #color}
 *
 * Created by gdlxguzm on 4/3/2017.
 */
public class ClearScreenSystem extends BaseSystem {

    /** The {@link Color} to clear the the screen with.
     * Can't be assigned, change with color.set(r,g,b,a).
     *
     * */
    public final Color color = Color.WHITE.cpy();

    public ClearScreenSystem(){
        this(Color.BLACK);
    }

    public ClearScreenSystem(Color c){
        this.color.set(c);
    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}

package com.xguzm.gdxcommons;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Utility to move a camera around to be able to explore the map without slowing moving an entity.
 * Created by Xavier Guzman
 */
public class PanZoomCameraInput extends InputAdapter {
    OrthographicCamera cam;
    Vector3 lastTouch, tmp;

    public PanZoomCameraInput(OrthographicCamera cam){
        this.cam = cam;
        lastTouch = new Vector3();
        tmp = new Vector3();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastTouch.set(screenX, screenY, 0);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tmp.set(screenX, screenY, 0).sub(lastTouch).scl(-1, 1, 0).scl(cam.zoom);
        cam.translate(tmp);
        lastTouch.set(screenX, screenY, 0);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        cam.zoom *= amount > 0 ? 1.05f : 0.95f;

        return false;
    }



}

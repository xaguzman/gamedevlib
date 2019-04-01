package com.xaguzman.artemiscommons.systems;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.xaguzman.artemiscommons.managers.CameraManager;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;

public class PanZoomCameraSystem extends PassiveSystem implements InputProcessor {

    Vector3 lastTouch = new Vector3(), tmp =  new Vector3();
    OrthographicCamera cam;
    @Wire CameraManager cameraManager;
    float translation;

    public PanZoomCameraSystem(){
        this(1f);
    }

    public PanZoomCameraSystem(float translationSpeed){
        this.translation = translationSpeed;
    }

    @Override
    protected void initialize() {
        super.initialize();
        cam = cameraManager.get();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastTouch.set(screenX, screenY, 0);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tmp.set(screenX, screenY, 0).sub(lastTouch).scl(-1, 1, 0).scl(cam.zoom);

        cam.translate(tmp.scl( translation) );

        lastTouch.set(screenX, screenY, 0);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        cam.zoom *= amount > 0 ? 1.05f : 0.95f;

        return false;
    }
}

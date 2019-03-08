package com.xaguzman.artemiscommons.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ObjectMap;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;

/**
 * Stores all the needed cameras for the world to use
 *
 * Created by xavier on 4/3/2017.
 */
public class CameraSystem extends PassiveSystem {

    private ObjectMap<String, OrthographicCamera> cameras;
    protected OrthographicCamera worldCamera, guiCamera;

    public void add(OrthographicCamera camera, String id){
        cameras.put(id, camera);
    }

    /**
     * Creates the default worldCamera with Gdx.graphics.getWidth() and Gdx.graphics.getHeight(), having the y axis pointing up
     */
    public void create(){
        create(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    /**
     * Creates the guiCamera worldCamera with Gdx.graphics.getWidth() and Gdx.graphics.getHeight(), having the y axis pointing up
     */
    public void createGui(){
        createGui(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    /**
     * Creates the worldCamera with the given width and height, having the y axis pointing up
     * @param width viewport width
     * @param height viewport height
     */
    public void create(int width, int height){
        create(width, height, false);
    }

    /**
     * Creates the worldCamera with the given width and height, having the y axis pointing up
     * @param width viewport width
     * @param height viewport height
     */
    public void create(int width, int height, boolean ydown){
        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(ydown, width, height);
    }

    /**
     * Creates the guiCamera with the given width and height, having the y axis pointing up
     * @param width viewport width
     * @param height viewport height
     */
    public void createGui(int width, int height){
        create(width, height, false);
    }

    /**
     * Creates the guiCamera with the given width and height, having the y axis pointing up
     * @param width viewport width
     * @param height viewport height
     */
    public void createGui(int width, int height, boolean ydown){
        guiCamera = new OrthographicCamera();
        guiCamera.setToOrtho(ydown, width, height);
    }

    /**
     * Creates a camera with the given name, width and height, with the y axis pointing up
     * @param width viewport width
     * @param height viewport height
     */
    public void create(String id, int width, int height){
        create(id, width, height, false);
    }

    /**
     * Creates a camera with the given name and settings
     * @param width viewport width
     * @param height viewport height
     * @param ydown wether the incrementing y goes down or up the screen
     */
    public void create(String id, int width, int height, boolean ydown){
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(ydown, width, height);

        if (cameras == null){
            cameras = new ObjectMap<String, OrthographicCamera>();
        }
        cameras.put(id, camera);
    }

    /** Retrieve the camera registered with the given id. Null if the id is not found */
    public OrthographicCamera get(String id){
        if (cameras == null)
            return null;

        return cameras.get(id, null);
    }

    /** Retrieve the worldCamera */
    public OrthographicCamera get(){
        return worldCamera;
    }

    /** Retrieve the guiCamera */
    public OrthographicCamera getGuiCamera(){
        return guiCamera;
    }


}
package com.xaguzman.artemiscommons;

import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.xaguzman.artemiscommons.managers.CameraManager;
import com.xaguzman.artemiscommons.managers.CameraSpotlightManager;
import com.xaguzman.artemiscommons.managers.SpriteAssetManager;
import com.xaguzman.artemiscommons.systems.CollisionDetectionSystem;
import com.xaguzman.artemiscommons.systems.EventsSystem;
import com.xaguzman.artemiscommons.systems.InputDetectionSystem;
import com.xaguzman.artemiscommons.systems.SpatialHashgridSystem;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAssetSystem;

public class WorldUtils {

    private WorldConfigurationBuilder builder = new WorldConfigurationBuilder();

    /**
     * Bulds a new world using:
     * 1 camera called "default" with worldwidht and worldheight viewport.
     * 1 camera called gui with Gdx.graphics.getWidth() and Gdx.graphics.getHeight() as viewport
     * Sprite asset system
     * Sprite asset manager
     * Group manager
     * Tag manager
     * InputDetection (default configuration, set the configuration manually before initializing the world)
     * Spatial hashgrid for collision detection
     * Collision detection
     * Event system
     */
    public WorldConfigurationBuilder newWorld(int worldWidth, int worldHeight, int spatialWorldWidth, int spatialWorldHeight, int spatialCellSize){
        return newWorld(
                worldWidth, worldHeight,
                spatialWorldWidth, spatialWorldHeight, spatialCellSize,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public WorldConfigurationBuilder newWorld(int worldWidth, int worldHeight, int spatialWorldWidth, int spatialWorldHeight, int spatialCellSize, int screenWidth, int screenHeight){

        CameraManager cm = new CameraManager();
        cm.create(worldWidth, worldHeight);
        cm.createGui(screenWidth, screenHeight);

        builder = new WorldConfigurationBuilder()
                .with(new SpriteAssetSystem())
                .with(new SpriteAssetManager())
                .with(new GroupManager())
                .with(new TagManager())
                .with(new CameraManager())
                .with(new CameraSpotlightManager())
                .with(new InputDetectionSystem())
                .with(new SpatialHashgridSystem(spatialWorldWidth, spatialWorldHeight, spatialCellSize))
                .with(new CollisionDetectionSystem())
                .with(new EventsSystem());

        return builder;
    }

}

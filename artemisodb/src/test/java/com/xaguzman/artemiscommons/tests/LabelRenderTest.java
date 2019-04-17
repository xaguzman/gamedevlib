package com.xaguzman.artemiscommons.tests;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xaguzman.artemiscommons.components.Label;
import com.xaguzman.artemiscommons.components.transform.Position;
import com.xaguzman.artemiscommons.managers.CameraManager;
import com.xaguzman.artemiscommons.managers.FontAssetManager;
import com.xaguzman.artemiscommons.systems.rendering.LabelRenderingSystem;

public class LabelRenderTest{

    public static void main(String[] args){
        new LwjglApplication(new DefaultFontRenderingTest());
    }

    static class DefaultFontRenderingTest extends ApplicationAdapter{
        boolean loaded = false;
        private World world;
        float accum = 0;

        SpriteBatch b;

        @Override
        public void create() {
            b = new SpriteBatch();

            // world init
            WorldConfiguration config = new WorldConfigurationBuilder()
                .with(new CameraManager())
                .with(new FontAssetManager())
//                .with(new SpriteAssetSystem())
                .with(new LabelRenderingSystem())
                .build();

            config.register(b);
            world = new World(config);
            world.getSystem(CameraManager.class).create();

            // entity init
            int entityId = world.create();
            Position pos1 = world.getMapper(Position.class).create(entityId);
            Label label = world.getMapper(Label.class).create(entityId);

            pos1.set(100, 100);
            label.text = "Hello World with default font";

            loaded = true;
        }

        @Override
        public void render() {
            world.getSystem(CameraManager.class).get().update();
            world.setDelta(Gdx.graphics.getDeltaTime());
            world.process();
            accum += world.delta;

            if (accum > 10){
                Gdx.app.exit();
            }
        }
    }
}

package com.xaguzman.gdxcommons.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TitleBarWindow;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gdlxguzm on 9/27/2016.
 */
public class TitleBarWindowTest {

    public static void main (String[] args){
        System.out.println("\nRunning default title window test");
        TitleBaRWindowApp app = new TitleBaRWindowApp();
        new LwjglApplication(app);
    }

    @Test
    public void loadDefaultTitleWindow() {
        System.out.println("\nRunning default title window test");
        TitleBaRWindowApp app = new TitleBaRWindowApp();

        try{
            new LwjglApplication(app);
            while (!app.loaded){}
        }catch(Exception ex){
            fail(ex.getMessage());
        }
    }

    static class TitleBaRWindowApp extends ApplicationAdapter {
        boolean loaded = false;
        Stage stage;
        Skin skin;

        @Override
        public void create() {
            stage = new Stage();

            skin = new Skin(Gdx.files.classpath("com/badlogic/gdx/scenes/scene2d/ui/uiskin.json"));
            TitleBarWindow titledWindow = new TitleBarWindow("A title", skin );

            titledWindow.setPosition(50, 50);

            stage.addActor(titledWindow);
        }

        @Override
        public void render() {
            stage.act();
            stage.draw();

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                loaded = true;
                Gdx.app.exit();
            }
        }

        @Override
        public void resize(int width, int height) {

        }

        @Override
        public void dispose() {
            skin.dispose();
            stage.dispose();
        }
    }
}


package com.xaguzman.gdxcommons.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.RateMyAppDialog;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Xavier Guzman on 3/31/2017.
 */
public class RateMyAppDialogTest {

    public static void main (String[] args){
        System.out.println("\nRunning default rate my app dialog test");
        RateMyAppDialogApp app = new RateMyAppDialogApp();
        new LwjglApplication(app);
    }

    @Test
    public void loadDefaultTitleWindow() {
        System.out.println("\nRunning default rate my app dialog test");
        RateMyAppDialogApp app = new RateMyAppDialogApp();

        try{
            new LwjglApplication(app);
            while (!app.loaded){}
        }catch(Exception ex){
            fail(ex.getMessage());
        }
    }

    static class RateMyAppDialogApp extends ApplicationAdapter {
        boolean loaded = false;
        Stage stage;

        @Override
        public void create() {
            stage = new Stage();
            RateMyAppDialog rater = new RateMyAppDialog();
            rater.setAppTitle("My cool game");
            rater.setAppName("org.xguzm.mathgame");
            rater.setDaysUntilPrompt(0); // 0 is only usefult for testing!
            rater.setLaunchesUntilPrompt(3); // this is the default

            Gdx.input.setInputProcessor(stage);
        }

        @Override
        public void render() {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            stage.dispose();
        }
    }
}

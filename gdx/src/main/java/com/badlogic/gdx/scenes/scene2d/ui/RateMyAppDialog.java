package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by gdlxguzm on 3/31/2017.
 */
public class RateMyAppDialog extends Dialog {

    private String appTitle;// = "Duels";
    private String appName;// = "com.xguzm.duels";

    private int daysUntilPrompt = 3;
    private int launchesUntilPrompt = 3;
    private ImageTextButton rateButton, laterButton, noThanksButton;
    private boolean created;

    public RateMyAppDialog(){
        this("Please rate us");
    }

    public RateMyAppDialog(String title){
        this(title, new Skin(Gdx.files.classpath("com/badlogic/gdx/scenes/scene2d/ui/uiskin.json")));
    }

    public RateMyAppDialog(String title, Skin skin) {
        super(title, skin);
    }

    public RateMyAppDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public RateMyAppDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    private void createDialog() {
        text("If you enjoy using " + appTitle + ", please take a moment to rate it. Thanks for your support!").row();

        rateButton = new ImageTextButton("Rate " + appTitle, this.getSkin());
        laterButton = new ImageTextButton("Remind me later", this.getSkin());
        noThanksButton = new ImageTextButton("No, thanks", this.getSkin());

        rateButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop){
                    Gdx.net.openURI("https://play.google.com/store/apps/details?id=" + appName);
                }else if (Gdx.app.getType() == Application.ApplicationType.Android){
                    Gdx.net.openURI("market://details?id=" + appName);
                }
                noThanksButton.getClickListener().clicked(event, x, y);
            }
        });

        laterButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RateMyAppDialog.this.setVisible(false);
            }
        });

        noThanksButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RateMyAppDialog.this.setVisible(false);
                Preferences prefs = Gdx.app.getPreferences(appTitle + "_apprater");
                prefs.putBoolean("dont_show_again", true);
                prefs.flush();
            }
        });

        button(rateButton);
        button(laterButton);
        button(noThanksButton);

        created = true;

    }

    @Override
    public Dialog show(Stage stage) {
        if (created)
            return super.show(stage);

        return this;
    }

    @Override
    public Dialog show(Stage stage, Action action) {
        if (created)
            return super.show(stage, action);

        return this;
    }

    /**
     * Launch this method to show the app rater dialog if the conditions are met.
     *
     * Before calling this method, you should call {@link #setAppTitle(String)} and {@link #setAppName(String)}.
     * Two aditional calls are {@link #setDaysUntilPrompt(int)} and {@link #setLaunchesUntilPrompt(int)}
     * @param stage
     */
    public void check(Stage stage){
        Preferences prefs = Gdx.app.getPreferences(appTitle + "_apprater");
        if (prefs.getBoolean("dont_show_again", false))
        {
            return;
        }

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        prefs.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            prefs.putLong("date_firstlaunch", date_firstLaunch);
        }
        prefs.flush();

        // Wait at least n days before opening
        if (launch_count >= launchesUntilPrompt) {
            if (System.currentTimeMillis() >= date_firstLaunch + (daysUntilPrompt * 24 * 60 * 60 * 1000)) {
                createDialog();
                show(stage);
            }
        }


    }

    public int getDaysUntilPrompt() {
        return daysUntilPrompt;
    }

    public void setDaysUntilPrompt(int daysUntilPrompt) {
        this.daysUntilPrompt = daysUntilPrompt;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public int getLaunchesUntilPrompt() {
        return launchesUntilPrompt;
    }

    public void setLaunchesUntilPrompt(int launchesUntilPrompt) {
        this.launchesUntilPrompt = launchesUntilPrompt;
    }
}

package com.xaguzman.artemiscommons.systems.rendering;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.xaguzman.artemiscommons.components.BitmapFontAsset;
import com.xaguzman.artemiscommons.components.Invisible;
import com.xaguzman.artemiscommons.components.Label;
import com.xaguzman.artemiscommons.components.Tint;
import com.xaguzman.artemiscommons.components.transform.Position;
import com.xaguzman.artemiscommons.managers.CameraManager;

public class LabelRenderingSystem extends IteratingSystem {

    private ComponentMapper<Position> posMapper;
    private ComponentMapper<Label> labelMapper;
    private ComponentMapper<Tint> tintMapper;
    private ComponentMapper<BitmapFontAsset> fontMapper;
    private CameraManager cameraManager;
    private GlyphLayout glyphLayout = new GlyphLayout();

    private String injectedSpriteBatchName;
    private SpriteBatch batch;
    private String cameraName= "default";

    public LabelRenderingSystem(){
        this(new SpriteBatch());
    }

    public LabelRenderingSystem(SpriteBatch batch){
        super(Aspect.all(Position.class, Label.class, BitmapFontAsset.class).exclude(Invisible.class));
        this.batch = batch;
    }

    public LabelRenderingSystem(String injectedSpriteBatchName){
        super(Aspect.all(Position.class, Label.class, BitmapFontAsset.class));
        this.injectedSpriteBatchName = injectedSpriteBatchName;
    }

    public LabelRenderingSystem withCamera(String cameraName){
        this.cameraName = cameraName;
        return this;
    }

    @Override
    protected void initialize() {
        if (batch == null){
            if (injectedSpriteBatchName != null){
                batch = world.getRegistered(injectedSpriteBatchName);
            }else{
                batch = world.getRegistered(SpriteBatch.class);
            }
        }
    }

    @Override
    protected void begin() {
        OrthographicCamera cam = cameraManager.get(cameraName);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        Label lbl = labelMapper.get(entityId);

        if (lbl.text == null || lbl.text.length() == 0)
            return;

        Position pos = posMapper.get(entityId);
        BitmapFontAsset asset = fontMapper.get(entityId);
        Tint tint = tintMapper.getSafe(entityId, Tint.WHITE);

        float x = pos.x() * lbl.positionScale;
        float y = pos.y() * lbl.positionScale;

        batch.setColor(tint.color);

        switch( MathUtils.floor(lbl.align * 2f) ){
            case 0:
                //left
                asset.font.draw(batch, lbl.text, x, y);
                break;
            case 1:
                //center
                glyphLayout.setText(asset.font, lbl.text);
                asset.font.draw(batch, lbl.text, x - (glyphLayout.width * 0.5f), y);
                break;
            case 2:
                //right
                glyphLayout.setText(asset.font, lbl.text);
                asset.font.draw(batch, lbl.text, x - glyphLayout.width, y);
                break;
        }
    }

    @Override
    protected void end() {
        batch.end();
    }
}

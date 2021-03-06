package com.xaguzman.artemiscommons.systems.rendering;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xaguzman.artemiscommons.components.Invisible;
import com.xaguzman.artemiscommons.components.Sprite;
import com.xaguzman.artemiscommons.components.SpriteAsset;
import com.xaguzman.artemiscommons.components.Tint;
import com.xaguzman.artemiscommons.components.transform.Origin;
import com.xaguzman.artemiscommons.components.transform.Position;
import com.xaguzman.artemiscommons.components.transform.Rotation;
import com.xaguzman.artemiscommons.components.transform.Size;
import com.xaguzman.artemiscommons.systems.base.OrderedEntityProcessingSystem;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAssetSystem;
import com.xaguzman.artemiscommons.managers.CameraManager;

import java.util.Comparator;

/**
 * Renders the entities with sprites, using the world worldCamera from {@link CameraManager}
 * Created by Xavier on 4/3/2017.
 */
@Wire(failOnNull = false)
public class SpriteAssetRenderingSystem extends OrderedEntityProcessingSystem {

    private SpriteBatch batch;
    @Wire SpriteAssetSystem spriteAssets;
    @Wire CameraManager cameraManager;
    @Wire ComponentMapper<Position> posMapper;
    @Wire ComponentMapper<SpriteAsset> spriteAssetMapper;
    @Wire ComponentMapper<Size> sizeMapper;
    @Wire ComponentMapper<Origin> originMapper;
    @Wire ComponentMapper<Tint> tintMapper;
    @Wire ComponentMapper<Rotation> rotationMapper;

    private String injectedSpriteBatchName;
    private String cameraName = "default";

    private final Rectangle viewport = new Rectangle(), spriteBounds = new Rectangle();


    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer eId1, Integer eId2) {
            SpriteAsset img1 = spriteAssetMapper.get(eId1);
            SpriteAsset img2 = spriteAssetMapper.get(eId2);
            int layerDif = img1.layer - img2.layer;
            if (layerDif != 0)
                return layerDif;

            Position pos1 = posMapper.get(eId1);
            Position pos2 = posMapper.get(eId2);
            float dif = pos1.y() - pos2.y();
            if (dif < 0)
                return 1;
            if (dif > 0)
                return -1;
            return 0;
        }
    };

    public SpriteAssetRenderingSystem(){
        this(new SpriteBatch());
    }

    public SpriteAssetRenderingSystem(String injectedSpriteBatchName){
        super(Aspect.all(Position.class, SpriteAsset.class, Size.class).exclude(Invisible.class));
        setComparator(comparator);
        this.injectedSpriteBatchName = injectedSpriteBatchName;
    }

    @SuppressWarnings("unchecked")
    public SpriteAssetRenderingSystem(SpriteBatch batch) {
        super(Aspect.all(Position.class, SpriteAsset.class, Size.class).exclude(Invisible.class));
        setComparator(comparator);
        this.batch = batch;
    }

    /**
     * Set the camera to use to render the sprites with. Camera must exist in the camera manager before rendering
     * @param cameraName
     * @return
     */
    public SpriteAssetRenderingSystem withCamera(String cameraName){
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
        viewport.setSize(cam.viewportWidth * cam.zoom, cam.viewportHeight * cam.zoom);
        viewport.setPosition(cam.position.x - (viewport.width * 0.5f), cam.position.y - (viewport.height * 0.5f));

//        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
    }

    @Override
    public void process(int entityId) {
        Position ePos = posMapper.get(entityId);
        Size eSize = sizeMapper.get(entityId);

        spriteBounds.setPosition(ePos.xy);
        spriteBounds.setSize(eSize.xy.x, eSize.xy.y);

        if (!viewport.overlaps(spriteBounds)){
            return;
        }

        SpriteAsset spr = spriteAssetMapper.get(entityId);
        Origin eOrigin = originMapper.getSafe(entityId, Origin.CENTER);
        Tint eTint = tintMapper.getSafe(entityId, Tint.WHITE);
        Rotation eRot = rotationMapper.getSafe(entityId, Rotation.NONE);

        com.badlogic.gdx.graphics.g2d.Sprite asset = spr.asset;

        asset.setColor(eTint.color);
        asset.setOrigin(eOrigin.x(), eOrigin.y());
        asset.setRotation(eRot.angle);

        float xPos = ePos.x() + (eSize.x() * spr.offsetX);
        float yPos = ePos.y() + (eSize.y() * spr.offsetY);
        asset.setBounds(xPos, yPos, eSize.x(), eSize.y());

        asset.draw(batch);
    }

    @Override
    protected void end() {
        batch.end();
    }
}

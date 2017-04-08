package com.xguzm.artemiscommons.systems.rendering;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xguzm.artemiscommons.components.Invisible;
import com.xguzm.artemiscommons.components.Sprite;
import com.xguzm.artemiscommons.components.Tint;
import com.xguzm.artemiscommons.components.transform.Origin;
import com.xguzm.artemiscommons.components.transform.Position;
import com.xguzm.artemiscommons.components.transform.Rotation;
import com.xguzm.artemiscommons.components.transform.Size;
import com.xguzm.artemiscommons.systems.CameraSystem;
import com.xguzm.artemiscommons.systems.base.OrderedEntityProcessingSystem;
import com.xguzm.artemiscommons.systems.sprite.SpriteAssetSystem;

import java.util.Comparator;

/**
 * Renders the entities with sprites, using the world worldCamera from {@link com.xguzm.artemiscommons.systems.CameraSystem}
 * Created by Xavier on 4/3/2017.
 */
public class SpriteAssetRenderingSystem extends OrderedEntityProcessingSystem {

    private final SpriteBatch batch;
    @Wire SpriteAssetSystem spriteAssets;
    @Wire CameraSystem cameraSystem;
    @Wire ComponentMapper<Position> posMapper;
    @Wire ComponentMapper<Sprite> spriteMapper;
    @Wire ComponentMapper<Size> sizeMapper;
    @Wire ComponentMapper<Origin> originMapper;
    @Wire ComponentMapper<Tint> tintMapper;
    @Wire ComponentMapper<Rotation> rotationMapper;

    private final Rectangle viewport = new Rectangle(), spriteBounds = new Rectangle();


    Comparator<Integer> comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer eId1, Integer eId2) {
            Sprite img1 = spriteMapper.get(eId1);
            Sprite img2 = spriteMapper.get(eId2);
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

    @SuppressWarnings("unchecked")
    public SpriteAssetRenderingSystem(SpriteBatch batch) {
        super(Aspect.all(Position.class, Sprite.class, Size.class).exclude(Invisible.class));
        setComparator(comparator);
        this.batch = batch;
    }

    @Override
    protected void begin() {
        OrthographicCamera cam = cameraSystem.get();
        viewport.setSize(cam.viewportWidth * cam.zoom, cam.viewportHeight * cam.zoom);
        viewport.setPosition(cam.position.x - (viewport.width * 0.5f), cam.position.y - (viewport.height * 0.5f));

        cam.update();
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

        Sprite spr = spriteMapper.get(entityId);
        Origin eOrigin = originMapper.getSafe(entityId, Origin.DEFAULT);
        Tint eTint = tintMapper.getSafe(entityId, Tint.WHITE);
        Rotation eRot = rotationMapper.getSafe(entityId, Rotation.NONE);

        com.badlogic.gdx.graphics.g2d.Sprite asset = spr.asset;

        asset.setSize(eSize.xy.x, eSize.xy.y);
        asset.setColor(eTint.color);
        asset.setOrigin(eOrigin.xy.x, eOrigin.xy.y);
        asset.setRotation(eRot.angle);

        asset.draw(batch);
    }

    @Override
    protected void end() {
        batch.end();
    }
}

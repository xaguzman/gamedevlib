package com.xguzm.artemiscommons.systems.rendering;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.math.Rectangle;
import com.xguzm.artemiscommons.components.Sprite;
import com.xguzm.artemiscommons.components.SpriteAsset;
import com.xguzm.artemiscommons.components.transform.Position;
import com.xguzm.artemiscommons.components.transform.Size;
import com.xguzm.artemiscommons.systems.SpriteAssetsSystem;
import com.xguzm.artemiscommons.systems.base.OrderedEntityProcessingSystem;

import java.util.Comparator;

/**
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAssetRenderingSystem extends OrderedEntityProcessingSystem {

    @Wire SpriteAssetsSystem spriteAssets;
    @Wire ComponentMapper<Position> posMapper;
    @Wire ComponentMapper<Sprite> imgMapper;
//    @Wire ComponentMapper<Facing> facingMapper;
    @Wire ComponentMapper<Size> sizeMapper;
    //public final Rectangle viewPort;

    Comparator<Entity> comparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity o1, Entity o2) {
            Sprite img1 = imgMapper.get(o1);
            Sprite img2 = imgMapper.get(o2);
            int layerDif = img1.layer - img2.layer;
            if (layerDif != 0)
                return layerDif;

            Position pos1 = posMapper.get(o1);
            Position pos2 = posMapper.get(o2);
            float dif = pos1.y() - pos2.y();
            if (dif < 0)
                return 1;
            if (dif > 0)
                return -1;
            return 0;
        }
    };

    protected SpriteAssetRenderingSystem(Aspect.Builder aspect) {
        super(Aspect.all(Position.class, Sprite.class, Size.class));
        setComparator(comparator);
    }

    @Override
    public void process(Entity e) {

    }
}

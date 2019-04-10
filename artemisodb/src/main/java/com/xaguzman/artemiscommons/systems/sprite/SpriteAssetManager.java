package com.xaguzman.artemiscommons.systems.sprite;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.xaguzman.artemiscommons.components.Sprite;
import com.xaguzman.artemiscommons.components.SpriteAsset;
import com.xaguzman.artemiscommons.components.transform.Size;
import com.xaguzman.artemiscommons.managers.AssetManager;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;

import static com.artemis.Aspect.all;

/**
 * Retrieves a libgdx sprite from the {@link SpriteAssetSystem} IF the Sprite component has an Id / name
 * Created by Xavier Guzman on 4/3/2017.
 */
public class SpriteAssetManager extends AssetManager<Sprite, SpriteAsset> {

    @Wire SpriteAssetSystem spriteAssets;
//    ComponentMapper<Size> sizeMapper;


    public SpriteAssetManager() {
        super(Sprite.class, SpriteAsset.class);
    }

    @Override
    protected void setup(int entityId, Sprite sprite, SpriteAsset spriteAsset) {
        spriteAsset.asset = spriteAssets.getSprite(sprite.name);
    }
}

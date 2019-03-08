package com.xaguzman.artemiscommons.systems.sprite;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.xaguzman.artemiscommons.components.Sprite;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;

import static com.artemis.Aspect.all;

/**
 * Retrieves a libgdx sprite from the {@link SpriteAssetSystem} IF the Sprite component has an Id / name
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAssetManager extends PassiveSystem{

    @Wire SpriteAssetSystem spriteAssets;
    @Wire ComponentMapper<Sprite> spriteMapper;

    @Override
    protected void initialize() {
        world.getAspectSubscriptionManager()
                .get(all(Sprite.class))
                .addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
                    @Override
                    public void inserted(IntBag entities) {
                        int[] ids = entities.getData();
                        for(int i =0; i < ids.length; i++){
                            setSpriteAsset(ids[i]);
                        }
                    }

                    @Override
                    public void removed(IntBag entities) {
                    }
                });
    }

    private void setSpriteAsset(int entityId) {
        Sprite sprite = spriteMapper.get(entityId);

        if (!isNullOrEmpty(sprite.name) && sprite.asset == null){
            sprite.asset = spriteAssets.getSprite(sprite.name);
        }
    }

    private boolean isNullOrEmpty(String value){
        if (value == null)
            return true;
        return value.trim().length() == 0;
    }
}

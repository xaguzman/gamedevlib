package com.xaguzman.artemiscommons.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xaguzman.artemiscommons.components.BitmapFontAsset;
import com.xaguzman.artemiscommons.components.Label;
import com.xaguzman.artemiscommons.systems.sprite.SpriteAssetSystem;

public class FontAssetManager extends AssetManager<Label, BitmapFontAsset> {

    public FontAssetManager() {
        super(Label.class, BitmapFontAsset.class);
    }

    @Override
    protected void setup(int entityId, Label label, BitmapFontAsset bitmapFontAsset) {
        BitmapFont f;
        if (label.isTextureInFont){
            String fontTextureName = label.fontAtlasTextureName;
            if (fontTextureName == null || fontTextureName.length() == 0){
                fontTextureName = label.fontName;
            }

            SpriteAssetSystem spriteAssets = world.getSystem(SpriteAssetSystem.class);
            TextureRegion fontRegion = spriteAssets.getAtlas(label.atlasName).findRegion(fontTextureName);
            f = new BitmapFont(Gdx.files.internal(label.fontName + ".fnt"), fontRegion);
        }else{
            if (label.fontName != null && label.fontName.length() > 0)
                f = new BitmapFont(Gdx.files.internal(label.fontName + ".fnt"));
            else
               f = new BitmapFont();
        }

        bitmapFontAsset.font = f;
    }
}

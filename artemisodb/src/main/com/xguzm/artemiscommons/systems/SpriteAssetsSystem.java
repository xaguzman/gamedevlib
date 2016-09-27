package com.xguzm.artemiscommons.systems;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;


/**
 * System to store sprites and sprite animations and access them easily from other systems.
 * If you are using {@link Animation}s you are probably storing the animation metadata in some file, override
 * the {@link #loadAnimations(String)} to generate your animations dictionary, which stores animations by name
 */
public class SpriteAssetsSystem extends BaseSystem {

    private ObjectMap<String, Animation> animations;
    private ObjectMap<String, Sprite> sprites;
    private TextureAtlas atlas;
    private boolean ownsAtlas = false;

    public SpriteAssetsSystem(String atlasInternalFile){
        atlas = new TextureAtlas(atlasInternalFile);
        ownsAtlas = true;
    }

    public SpriteAssetsSystem(TextureAtlas spritesAtlas){
        atlas = spritesAtlas;
    }

    @Override
    protected void initialize() {
        animations = new ObjectMap<String, Animation>();
        sprites = new ObjectMap<String, Sprite>();

        for(TextureAtlas.AtlasRegion region : atlas.getRegions()){
            if (region.index == -1) {
                Sprite spr = atlas.createSprite(region.name);
                sprites.put(region.name, spr);
            }
        }

        animations.putAll(loadAnimations(null));
    }

    public Sprite getAnimationSprite(String animationName, float time, Animation.PlayMode mode){
        if( animations.containsKey(animationName) ){
            Animation anim = animations.get(animationName);
            anim.setPlayMode(mode);
            return (Sprite)anim.getKeyFrame(time);

        }
        return null;
    }

    public Sprite getSprite(String spriteName){
        return sprites.get(spriteName);
    }

    public ObjectMap<String, Animation> loadAnimations(String animationsFilePath){
        return new ObjectMap<String, Animation>();
    }
//    private void loadAnimations(FileHandle xmlFile){
//        XmlReader reader = new XmlReader();
//
//        try {
//            XmlReader.Element root = reader.parse(xmlFile);
//            for(XmlReader.Element animDesc : root.getChildrenByName("animation")){
//
//                String name = animDesc.getAttribute("name", null);
//                boolean flipX = Boolean.parseBoolean(animDesc.getAttribute("flipX", "false"));
//                boolean flipY = Boolean.parseBoolean(animDesc.getAttribute("flipY", "false"));
//                float animationDuration = Float.parseFloat(animDesc.getAttribute("animationDuration", "0"));
//                float frameDuration = Float.parseFloat(animDesc.getAttribute("frameDuration", "0"));
//
//                XmlReader.Element regions = animDesc.getChildByName("regions");
//                String regionNames = regions.getAttribute("name");
//
//                validateAnimationData(name, frameDuration, animationDuration);
//                Array<Sprite> sprites = atlas.createSprites(regionNames);
//
//                for(Sprite spr : sprites){
//                    spr.setFlip(flipX, flipY);
//                }
//
//                if (frameDuration == 0){
//                    frameDuration = animationDuration / sprites.size;
//                }
//
//                Animation animation = new Animation(frameDuration, sprites);
//                animations.put(name, animation);
//            }
//        }catch(IOException e){
//            throw new GdxRuntimeException("Unable to read file " + xmlFile.name(), e);
//        }
//    }

//    private void validateAnimationData(String name, float frameDuration, float animationDuration){
//        if (name == null)
//            throw new GdxRuntimeException("Animation name has to be specified in your xml file");
//        if (animationDuration == 0 && frameDuration == 0)
//            throw new GdxRuntimeException("Animation " + name + " needs one of either, animationDuration or frameDuration");
//    }

    @Override
    protected void processSystem() { }

    public TextureAtlas getTextureAtlas(){
        return atlas;
    }

    @Override
    protected void dispose() {
        if (ownsAtlas )
            atlas.dispose();
    }
}

package com.xguzm.artemiscommons.systems.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.xguzm.artemiscommons.systems.base.PassiveSystem;

/**
 * Stores information about static sprites, bitmapfonts and animations procedent from a TextureAtlas
 *
 *
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAssetSystem extends PassiveSystem {

    private ObjectMap<String, Animation> animations;
    private ObjectMap<String, Sprite> sprites;
    private ObjectMap<String, TextureAtlas> atlasses;
    private ObjectMap<String, Boolean> ownsAtlasses;
    private TextureAtlas defaultAtlas;
    private boolean ownsAtlas;

    @Override
    protected void initialize() {
        animations = new ObjectMap<String, Animation>();
        sprites = new ObjectMap<String, Sprite>();
        atlasses = new ObjectMap<String, TextureAtlas>();
        ownsAtlasses = new ObjectMap<String, Boolean>();
    }

    /** adds the default atlas, which will be disposed along with this system*/
    public void add(TextureAtlas atlas){
        add(atlas, true);
    }

    /** adds the default atlas*/
    public void add(TextureAtlas atlas, boolean ownsAtlas){
        this.ownsAtlas = ownsAtlas;
        this.defaultAtlas = atlas;

        for(TextureAtlas.AtlasRegion region : atlas.getRegions()){
            if (region.index == -1) {
                Sprite spr = atlas.createSprite(region.name);
                sprites.put(region.name, spr);
                Gdx.app.debug("Sprite Assets System", "Loaded sprite " + region.name);
            }
        }
    }

    /**
     * will read all the static sprites comming from the given texture atlas.
     *
     * The provided texture atlas will be disposed once this system is disposed
     * TO READ ANIMATIONS YOU NEED TO CALL {@link #load(String, String, float)}
     */
    public void add(TextureAtlas atlas, String id){
        add(atlas, id, true);
    }

    /**
     * <p>
     * Reads all the static sprites comming from the given texture atlas.
     *</p>
     * This system will store and dispose the texture atlas if ownAtlas is true
     * TO READ ANIMATIONS YOU NEED TO CALL {@link #load(String, String, float)}
     */
    public void add(TextureAtlas atlas, String id, boolean ownAtlas){
        ownsAtlasses.put(id, ownAtlas);

        for(TextureAtlas.AtlasRegion region : atlas.getRegions()){
            if (region.index == -1) {
                Sprite spr = atlas.createSprite(region.name);
                sprites.put(region.name, spr);
                Gdx.app.debug("Sprite Assets System", "Loaded sprite " + region.name);
            }
        }
    }

    /**
     *  Loads an animation from the default atlas
     * */
    public Animation load(String id, float frameDuration){
        Array<Sprite> sprites = defaultAtlas.createSprites(id);

        Animation animation = new Animation(frameDuration, sprites);
        animations.put(id, animation);

        return animation;
    }

    /**
     *  Loads an animation from a previously registered atlas
     * */
    public Animation load(String id, String atlasId, float frameDuration){
        Array<Sprite> sprites = atlasses.get(atlasId).createSprites(id);

        Animation animation = new Animation(frameDuration, sprites);
        animations.put(id, animation);

        return animation;
    }

    /**
     * Register an animation from spritesheet (retrieved by calling {@link #spriteSheet(String, int, int)})
     * <p>
     *     No padding / margin between each frame is supported at the moment
     * </p>
     * After registering this, the animation will be available through {@link #getAnimation(String)} by providing the the given animationId.
     *
     */
    public Animation create(String animationId, TextureRegion[] frames, float frameDuration){
        Animation newAnim = new Animation(frameDuration, frames);
        animations.put(animationId, newAnim);

        return newAnim;
    }

    /**
     * <p>Creates a spritesheet from the given regionId with the width,height dimensions. </p>
     * <p>After retrieving the Spritesheet, the animations stored in it can be
     * registered in this system by calling {@link #create(String, TextureRegion[], float)}
     * </p>
     *
     * <p>
     * After registering this, the animation will be available
     * through {@link #getAnimation(String)} by providing the the given animationId.
     * <b>The textureRegion with the given id must already be part of the registered atlases</b>
     * </p>
     *
     * The animations in the spritesheet are assumed to be 1 row per animation. No padding / margin between each frame is
     * supported at the moment
     */
    public TextureRegion[][] spriteSheet(String regionId, int width, int height){
        if (!sprites.containsKey(regionId))
            return null;

        return sprites.get(regionId).split(width, height);
    }


    public Animation getAnimation(String animationName) {
        return animations.get(animationName, null);
    }

    public Sprite getSprite(String spriteName) {
        return sprites.get(spriteName, null);
    }

    public void removeSprite(String spriteName){
        sprites.remove(spriteName);
    }

    @Override
    protected void dispose() {
        for(String atlasId : atlasses.keys())
            if (ownsAtlasses.get(atlasId) == true){
                atlasses.get(atlasId).dispose();
            }
        atlasses.clear();
        ownsAtlasses.clear();

        if (ownsAtlas)
            defaultAtlas.dispose();
    }
}

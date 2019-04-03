package com.xaguzman.artemiscommons.systems.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.xaguzman.artemiscommons.systems.base.PassiveSystem;

/**
 * Stores information about static sprites, bitmapfonts and animations procedent from a TextureAtlas
 *
 *
 * Created by gdlxguzm on 4/3/2017.
 */
public class SpriteAssetSystem extends PassiveSystem {

    private ObjectMap<String, Animation<Sprite>> animations;
    private ObjectMap<String, Sprite> sprites;
    private ObjectMap<String, TextureAtlas> atlasses;
    private ObjectMap<String, Boolean> ownsAtlasses;
    private ObjectMap<String, Texture> textures;
    private ObjectMap<String, Boolean> ownsTextures;
    private TextureAtlas defaultAtlas;
    private boolean ownsAtlas;

    @Override
    protected void initialize() {
        animations = new ObjectMap<String, Animation<Sprite>>();
        sprites = new ObjectMap<String, Sprite>();
        atlasses = new ObjectMap<String, TextureAtlas>();
        ownsAtlasses = new ObjectMap<String, Boolean>();
        textures = new ObjectMap<String, Texture>();
        ownsTextures = new ObjectMap<String, Boolean>();
    }

    /** adds the default atlas, which will be disposed along with this system*/
    public void addAtlas(TextureAtlas atlas){
        addAtlas(atlas, true);
    }

    /** adds the default atlas*/
    public void addAtlas(TextureAtlas atlas, boolean ownsAtlas){
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
     * Allows to create a duplicate of spriteName and register it under the "alias" key.
     * Useful to store transformed sprites
     * @param spriteName
     * @param alias
     */
    public Sprite createSprite(String spriteName, String alias){
        Sprite spr= defaultAtlas.createSprite(spriteName);
        sprites.put(alias, spr);
        return spr;
    }

    /**
     * Allows to create a duplicate of spriteName and register it under the "alias" key.
     * Useful to store transformed sprites
     * @param spriteName
     * @param alias
     */
    public Sprite createSprite(String spriteName, String alias, String atlasId){
        Sprite spr= atlasses.get(atlasId).createSprite(spriteName);
        sprites.put(alias, spr);
        return spr;
    }

    /**
     * will read all the static sprites comming from the given texture atlas.
     *
     * The provided texture atlas will be disposed once this system is disposed
     * TO READ ANIMATIONS YOU NEED TO CALL {@link #createAnimation(String, float)}
     */
    public void addAtlas(TextureAtlas atlas, String id){
        addAtlas(atlas, id, true);
    }

    /**
     * <p>
     * Reads all the static sprites comming from the given texture atlas.
     *</p>
     * This system will store and dispose the texture atlas if ownAtlas is true
     * TO READ ANIMATIONS YOU NEED TO CALL {@link #createAnimation(String, String, float)}
     */
    public void addAtlas(TextureAtlas atlas, String id, boolean ownAtlas){
        ownsAtlasses.put(id, ownAtlas);

        for(TextureAtlas.AtlasRegion region : atlas.getRegions()){
            if (region.index == -1) {
                Sprite spr = atlas.createSprite(region.name);
                sprites.put(region.name, spr);
                Gdx.app.debug("Sprite Assets System", "Loaded sprite " + region.name);
            }
        }
    }

    public void addTexture(Texture texture, String textureId){
        addTexture(texture, textureId, true);
    }

    public void addTexture(Texture texture, String textureId, boolean ownsTexture){
        textures.put(textureId, texture);
        ownsTextures.put(textureId, ownsTexture);
    }

    /**
     *  Loads an animation from the default atlas. The sprites are retrieved from the atlas and the animation will keep the id as the alias
     * */
    public Animation<Sprite> createAnimation(String id, float frameDuration){
        Array<Sprite> sprites = defaultAtlas.createSprites(id);

        Animation<Sprite> animation = new Animation<Sprite>(frameDuration, sprites);
        animations.put(id, animation);

        return animation;
    }

    /**
     *  Loads an animation from a previously registered atlas. The sprites are retrieved from the atlas and the animation will keep the id as the alias / key
     * */
    public Animation<Sprite> createAnimation(String id, String atlasId, float frameDuration){
        Array<Sprite> sprites = atlasses.get(atlasId).createSprites(id);

        Animation<Sprite> animation = new Animation<Sprite>(frameDuration, sprites);
        animations.put(id, animation);

        return animation;
    }

    /**
     *  Loads an animation from the default atlas. The sprites are retrieved from the atlas and the animation will be stored with the alias as the key
     * */
    public Animation<Sprite> createAnimationAlias(String id, String alias, float frameDuration){
        Array<Sprite> sprites = defaultAtlas.createSprites(id);

        Animation<Sprite> animation = new Animation<Sprite>(frameDuration, sprites);
        animations.put(alias, animation);

        return animation;
    }

    /**
     *  Loads an animation from the default atlas. The sprites are retrieved from the atlas and the animation will be stored with the alias as the key
     * */
    public Animation<Sprite> createAnimationAlias(String id, String alias, String atlasId, float frameDuration){
        Array<Sprite> sprites = atlasses.get(atlasId).createSprites(id);

        Animation<Sprite> animation = new Animation<Sprite>(frameDuration, sprites);
        animations.put(alias, animation);

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
    public Animation<Sprite> createAnimation(String animationId, TextureRegion[] frames, float frameDuration){
        Array<Sprite> sprites = new Array<Sprite>();
        for(TextureRegion frame : frames){
            sprites.add(new Sprite(frame));
        }

        Animation<Sprite> newAnim = new Animation<Sprite>(frameDuration, sprites);
        animations.put(animationId, newAnim);

        return newAnim;
    }

    /**
     * Adds an animation to the asset system
     * */

    public void loadAnimation(Animation animation, String animationId){
        animations.put(animationId, animation);
    }

    /**
     * <p>Creates a spritesheet from the given regionId with the width,height dimensions. </p>
     * <p>After retrieving the Spritesheet, the animations stored in it can be
     * registered in this system by calling {@link #createAnimation(String, TextureRegion[], float)}
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




    /**
     * <p>Creates a spritesheet from the given textureId with the width,height dimensions. </p>
     * <p>After retrieving the Spritesheet, the animations stored in it can be
     * registered in this system by calling {@link #createAnimation(String, TextureRegion[], float)}
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
    public TextureRegion[][] spriteSheetT(String textureId, int width, int height){
        if (!textures.containsKey(textureId))
            return null;

        return TextureRegion.split(textures.get(textureId), width, height);
    }


    public Animation<Sprite> getAnimation(String animationName) {
        return animations.get(animationName, null);
    }

    public Sprite getAnimationFrame(String animationName, float time, boolean looping){
        if( animations.containsKey(animationName) ){
            Animation<Sprite> anim = animations.get(animationName);
            return anim.getKeyFrame(time, looping);

        }
        return null;
    }

    public Sprite getSprite(String spriteName) {
        return sprites.get(spriteName, null);
    }

    public void removeSprite(String spriteName){
        sprites.remove(spriteName);
    }

    /**
     *
     * @return The default atlas
     */
    public TextureAtlas getAtlas(){
        return defaultAtlas;
    }

    /**
     *
     * @return The atlas identified by the supplied id. "default" will return the default atlas
     */
    public TextureAtlas getAtlas(String atlasId){
        if (atlasId.equals("default"))
            return defaultAtlas;
        return atlasses.get(atlasId);
    }

    @Override
    protected void dispose() {
        for(String atlasId : atlasses.keys())
            if (ownsAtlasses.get(atlasId) == true){
                atlasses.get(atlasId).dispose();
            }
        atlasses.clear();
        ownsAtlasses.clear();

        for(String tId : textures.keys())
            if (ownsTextures.get(tId) == true){
                textures.get(tId).dispose();
            }
        atlasses.clear();
        ownsAtlasses.clear();

        if (ownsAtlas)
            defaultAtlas.dispose();
    }
}

package com.xaguzman.gdxcommons.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Xavier Guzman on 9/27/2016.
 */
public class ClassPathResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve(String fileName) {
        return Gdx.files.classpath(fileName);
    }
}

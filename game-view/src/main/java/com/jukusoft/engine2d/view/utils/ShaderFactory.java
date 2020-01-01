package com.jukusoft.engine2d.view.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.io.IOException;

/**
 * Created by Justin on 09.02.2017.
 */
public class ShaderFactory {

    public static ShaderProgram createShader (final FileHandle vertexShaderPath, final FileHandle fragmentShaderPath)
        throws IOException {
        //read shader programs to string
        final String vertexShader = vertexShaderPath.readString("UTF-8");
        final String fragmentShader = fragmentShaderPath.readString("UTF-8");

        return createShader(vertexShader, fragmentShader);
    }

    public static ShaderProgram createShader (final String vertexShader, final String fragmentShader)
            throws IOException {
        //create new shader
        ShaderProgram shaderProgram = new ShaderProgram(vertexShader, fragmentShader);

        //check, if water shader was compiled
        if (!shaderProgram.isCompiled()) {
            //log debug information
            System.out.println("vertex shader:\n\n" + vertexShader);

            System.out.println("\n\nfragment shader:\n" + fragmentShader + "\n");

            throw new RuntimeException("Could not compile shader: " + shaderProgram.getLog());
        }

        return shaderProgram;
    }

}

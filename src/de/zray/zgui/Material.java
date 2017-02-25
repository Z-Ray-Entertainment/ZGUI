/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.nio.FloatBuffer;
import java.util.Stack;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class Material{
    public static Stack<Material> dump = new Stack<>();
    
    public static void pushMaterial(Material mat){
        dump.push(mat);
    }
    
    public static Material popMaterial(){
        return dump.pop();
    }
    
    public static final int NO_TEXTURE = -2, LOAD_TEXTURE = -1;
    public static enum MaterialMode{GL_COLOR, GL_MATERIAL, AUTO};
    
    private float r = 1, g = 1, b = 1, a = 0;
    private String texture;
    private TextureManager textManager;
    private int intTexture = NO_TEXTURE;
    private FloatBuffer floatBuffer;
    private MaterialMode matMode = MaterialMode.AUTO;
    
    public Material(){
        init(null, 1, 1, 1, 0, null, MaterialMode.AUTO);
    }
    
    public Material(float red, float green, float blue, float alpha){
        init(null, red, green, blue, alpha, null, MaterialMode.AUTO);
    }
    
    public Material(float red, float green, float blue, float alpha, MaterialMode matMode){
        init(null, red, green, blue, alpha, null, matMode);
    }
    
    public Material(TextureManager textureManager, String texture){
        init(textureManager, 1, 1, 1, 0, texture, MaterialMode.AUTO);
    }
    
    public Material(TextureManager textureManager, String texture, MaterialMode matMode){
        init(textureManager, 1, 1, 1, 0, texture, matMode);
    }
    
    public Material(TextureManager textureManager){
        init(textureManager, 1, 1, 1, 0, null, MaterialMode.AUTO);
    }
    
    public Material(TextureManager textureManager, float red, float green, float blue, float alpha){
        init(textureManager, red, green, blue, alpha, null, MaterialMode.AUTO);
    }
    
    public Material(TextureManager textManager, float red, float green, float blue, float alpha, String texture){
        init(textManager, red, green, blue, alpha, texture, MaterialMode.AUTO);
    }
    
    public Material(TextureManager textManager, float red, float green, float blue, float alpha, String texture, MaterialMode matMode){
        init(textManager, red, green, blue, alpha, texture, matMode);
    }
    
    public void setMaterialMode(MaterialMode matMode){
        this.matMode = matMode;
    }
    
    private void init(TextureManager textManager, float red, float green, float blue, float alpha, String texture, MaterialMode matMode){
        r = red;
        g = green;
        b = blue;
        a = alpha;
        this.matMode = matMode;
        this.texture = texture;
        this.textManager = textManager;
        if(texture != null){
            intTexture = LOAD_TEXTURE;
        }
        floatBuffer = BufferUtils.createFloatBuffer(4);
        floatBuffer.put(new float[]{r, g, b, a});
        floatBuffer.flip();
    }
    
    public void setIntTexture(int texture){
        if(intTexture != texture && textManager != null){
            this.intTexture = texture;
        }
    }
    
    public void setTexture(String file){
        this.texture = file;
        if(!texture.isEmpty() && textManager != null){
            intTexture = LOAD_TEXTURE;
        }
    }
    
    public void applyMaterial(){
        switch(intTexture){
            case NO_TEXTURE :
                switch(matMode){
                    case AUTO :
                    case GL_COLOR :
                        glColor4f(r, g, b, a);
                        break;
                    case GL_MATERIAL :
                        glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, floatBuffer);
                        break;
                }
                glDisable(GL_TEXTURE);
                glDisable(GL_TEXTURE_2D);
                glDisable(GL_BLEND);
                break;
            case LOAD_TEXTURE :
                intTexture = textManager.getTexture(texture);
            default:
                switch(matMode){
                    case GL_COLOR :
                        glColor4f(r, g, b, a);
                        break;
                    case AUTO:
                    case GL_MATERIAL :
                        glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, floatBuffer);
                        break;
                }
                glEnable(GL_TEXTURE);
                glEnable(GL_TEXTURE_2D);
                glEnable (GL_BLEND);
                glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                textManager.getTexture(intTexture).bind();
                break;
        }
    }
    
    public void setColor(float red, float green, float blue, float alpha){
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
        floatBuffer = null;
        floatBuffer = BufferUtils.createFloatBuffer(4);
        floatBuffer.put(new float[]{r, g, b, a});
        floatBuffer.flip();
    }
}

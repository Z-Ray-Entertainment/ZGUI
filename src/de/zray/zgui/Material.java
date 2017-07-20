/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.nio.FloatBuffer;
import java.util.Stack;

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
    
    public void setMaterialMode(MaterialMode matMode){
        this.matMode = matMode;
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
    
    public void setColor(float red, float green, float blue, float alpha){
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
        floatBuffer = null;
        //floatBuffer = BufferUtils.createFloatBuffer(4);
        floatBuffer.put(new float[]{r, g, b, a});
        floatBuffer.flip();
    }
}

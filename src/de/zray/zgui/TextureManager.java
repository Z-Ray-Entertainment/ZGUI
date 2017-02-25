/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author vortex
 */
public class TextureManager{
    private static TextureManager tm;
    
    public static TextureManager get(){
        if(tm == null){
            tm = new TextureManager();
        }
        return tm;
    }
    
    List<Texture> textures = new ArrayList<>();
    List<String> files = new ArrayList<>();
    
    public int getTexture(String file){
        for(int i = 0; i < files.size(); i++){
            if(files.get(i).equals(file)){
                return i;
            }
        }
        return loadTexture(file);
    }
    
    public Texture getTexture(int i){
        return textures.get(i);
    }
    
    public int loadTexture(String file){
        try{
            System.out.println(Constants.name+": Loading texture: "+file);
            Texture tmp = TextureLoader.getTexture(file.substring(file.length()-3), ResourceLoader.getResourceAsStream(file));
            textures.add(tmp);
            files.add(file);
            return textures.size()-1;
        }
        catch(RuntimeException | IOException e){
            System.out.println(Constants.name+": failed loading texture: "+file+" Exception:"+e.getLocalizedMessage());
        }
        return -1;
    }
}

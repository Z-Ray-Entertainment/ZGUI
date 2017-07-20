/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    
    List<String> files = new ArrayList<>();
    
    public int getTexture(String file){
        for(int i = 0; i < files.size(); i++){
            if(files.get(i).equals(file)){
                return i;
            }
        }
        return -1;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import org.lwjgl.opengl.Display;

/**
 *
 * @author vortex
 */
public class Hitbox {
    /*
    ------->x
    |
    |
    |
    y
    */
    Orientation ori;
    
    public Hitbox(Orientation ori){
        this.ori = ori;
    }
    
    public boolean hit(float x, float y){
        if(x >= ori.posX*Display.getWidth()-ori.halfWidth*ori.scaleX && x <= ori.posX*Display.getWidth()+ori.halfWidth*ori.scaleX){
            if(y >= ori.posY*Display.getHeight()-ori.halfHeight*ori.scaleY && y <= ori.posY*Display.getHeight()+ori.halfHeight*ori.scaleY){
                return true;
            }
        }
        return false;
    }
    
    
}

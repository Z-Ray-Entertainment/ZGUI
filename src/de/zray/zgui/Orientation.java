/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import de.zray.zgui.exceptions.InvalidRangeException;

/**
 *
 * @author vortex
 */
public class Orientation {
    public float halfWidth, halfHeight, posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ;
    
    public Orientation(float halfWidth, float halfHeight, float posX, float posY, float posZ) throws InvalidRangeException{
        init(halfWidth, halfHeight, posX, posY, posZ, 0, 0, 0, 1, 1, 1);
    }
    
    private void init(float halfWidth, float halfHeight, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) throws InvalidRangeException{
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        
        if(posX > 1 || posY > 1 || posX < 0 || posY < 0){
            throw new InvalidRangeException("Invalid range, posX and posY must be between 1 and 0");
        }
    }
}

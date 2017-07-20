/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.util.UUID;

/**
 *
 * @author vortex
 */
public abstract class GUIItem{
    public static enum GUIAnimation{GUI_OPEN, GUI_CLOSE, HOVER, CLICK, IDLE, RELEASE};
    public UUID uuid = UUID.randomUUID();
    
    private Hitbox hitbox;
    private Orientation ori;
    private int displayList = -1, textSize = 12, action = -1;
    private Material material;
    private boolean closeable = false, disabled = false;
    private GUI parrentGUI;
    private GUIText text;
    private GUIAnimation anim = GUIAnimation.IDLE;
    
    public UUID getUUID(){
        return uuid;
    }
    
    public GUIItem(Orientation ori, GUI parrent){
        this.parrentGUI = parrent;
        this.ori = ori;
        hitbox = new Hitbox(ori);
    }
    
    public GUI getParrentGUI(){
        return parrentGUI;
    }
    
    public void setDisabled(boolean b){
        this.disabled = b;
    }
    
    public boolean isDisabled(){
        return disabled;
    }
    
    public GUIText getGUIText(){
        return text;
    }
    
    public void setText(GUIText text){
        this.text = text;
    }
    
    public boolean intersect(int pointerX, int pointerY){
        return hitbox.hit(pointerX, pointerY);
    }
    
    public void setClosable(boolean b){
        closeable = b;
    }
    
    public boolean isClosable(){
        return closeable;
    }
    
    public void setOrientation(Orientation ori){
        this.ori = ori;
    }
    
    public Orientation getOrientation(){
        return ori;
    }
    
    public void setMaterial(Material mat){
        this.material = mat;
    }
    
    public Material getMaterial(){
        return material;
    }

    public GUIAnimation getAnimation(){
        return this.anim;
    }
    
    public void setAnimation(GUIAnimation anim){
        this.anim = anim;
    }
    
    //Actions
    public abstract void idleAction();
    public abstract void onMouseHoverAction();
    public abstract void onMouseClickAction(int mouseButton);
    public abstract void onMousePressedAction(int mouseButton);
    public abstract void onMouseReleaseAction();
    public abstract void onOpenGUIAction();
    public abstract void onCloseGUIAction();
    public abstract void onKeyTipedAction(int key);
    public abstract void onKeyPressedAction(int key);
    
    public abstract void update(double delta);
}

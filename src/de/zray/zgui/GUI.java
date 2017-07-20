/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public abstract class GUI{
    List<GUIItem> items = new LinkedList<>(), hoveredItems = new LinkedList<>();
    List<GUIAction> actions = new LinkedList<>();
    UUID uuid;
    private boolean standAlone = false, firstInit = true, hide = false, timerAndPressedInited = false;
    private GUIItemInput activeInput = null;
    private long threshold = 1, mouseTime[], keyTimes[]; //ms whcih need to be passed befor an event ist noticed as pressed
    TextureManager textureManager;
    
    public GUI(TextureManager texManager){
        this.textureManager = texManager;
        init();
    }
    
    public TextureManager getTextureManager(){
        return textureManager;
    }
    
    public void addItem(GUIItem item){
        items.add(item);
    }
    
    public int addGUIAction(GUIAction action){
        actions.add(action);
        return actions.size()-1;
    }
    
    public void removeGUIAction(int id){
        if(id == actions.size()-1){}
    }
    
    public void clearActiveInput(){
        activeInput = null;
    }
    
    public void setActiveInput(GUIItemInput input){
        this.activeInput.clearActive();
        this.activeInput = input;
    }
    
    public GUIItemInput getActiveInput(){
        return activeInput;
    }
    
    public void callAction(int id){
        if(actions.get(id) != null){
            actions.get(id).execute();
        }
    }
    
    private void init(){
        uuid = UUID.randomUUID();
    }
    
    public UUID getUUID(){
        return uuid;
    }
    
    public void toogleShow(){
        hide = !hide;
    }
    
    public void hide(){
        hide = true;
    }
    
    public void show(){
        hide = false;
    }
    
    /**
     * @param delta
     * Delta represents the seconds past since the last update 1 = 1 second.
     */
    public void update(double delta){
        for(GUIItem tmp : items){
            if(firstInit){
                tmp.onOpenGUIAction();
            }
            tmp.update(delta);
        }
        firstInit = false;
    }
    
    /**
     * Returns true if all GUIItems are done with their close action.
     * @return 
     */
    public boolean closeGUI(){
        boolean done = true;
        for(GUIItem tmp : items){
            tmp.onCloseGUIAction();
            if(!tmp.isClosable()){
                done = false;
            }
        }
        return done;
    }
 
    public void resetTimers(){
        
    }
    
    private void addItemsToHovered(GUIItem item){
        boolean found = false;
        for(GUIItem tmp : hoveredItems){
            if(tmp.getUUID().equals(item.getUUID())){
                found = true;
            }
        }
        if(!found){
            hoveredItems.add(item);
        }
    }
}
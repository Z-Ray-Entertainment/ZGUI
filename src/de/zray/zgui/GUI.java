/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.PixelFormat;

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
    
    public void standAlone(){
        if(!Display.isCreated()){
            initDispaly();
        }
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
    
    private void initDispaly(){
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
        
        try {
                Display.setDisplayMode(new DisplayMode(Settings.windowWidth, Settings.windowHeight));
                Display.setTitle(Constants.name);
                Display.create(pixelFormat, contextAtrributes);
                Display.setResizable(true);
        } catch (LWJGLException e) {
                e.printStackTrace();
                System.exit(-1);
        }
        glEnable(GL_TEXTURE);
        glClearColor(0.1f, 0.1f, 0.1f, 0f);
        standAlone = true;
    }
    
    public void callAction(int id){
        if(actions.get(id) != null){
            actions.get(id).execute();
        }
    }
    
    public float getAspectRatio(){
	float ratio = ( (float) Display.getWidth() / (float) Display.getHeight());
	return ratio;
    }
    
    private void init(){
        uuid = UUID.randomUUID();
    }
    
    private void initInputTimersAndPress(){
        keyTimes = new long[Keyboard.getKeyCount()];
        mouseTime = new long[Mouse.getButtonCount()];
        timerAndPressedInited = true;
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
    
    public void render(){
        if(!timerAndPressedInited){
            initInputTimersAndPress();
        }
        if(!hide){
            glMatrixMode(GL_PROJECTION);
            glPushMatrix();
            glLoadIdentity();
            glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 100);
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();
            
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE);
            glColor3f(1, 1, 1);
            if(standAlone){
                glClearColor(1f, 0.25f, 0f, 0f);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            }
            
            for(GUIItem tmp : items){
                glPushMatrix();
                tmp.render();
                glPopMatrix();
            }
            glPopMatrix();
        }
        
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        
        if(standAlone){
            Display.update();
            if(Display.isCloseRequested()){
                System.exit(0);
            }
        }
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
 
    public void pollInputs(long delta){
        if(!hide){
            pollMouseInput(delta);
            pollKeyboardInputs(delta);
        }
    }
    
    public void keyPressed(int key, long delta){
        for(GUIItem tmp : items){
            if(!tmp.isDisabled()){
                if(Keyboard.isKeyDown(key)){
                    if(keyTimes[key] >= threshold){
                        tmp.onKeyPressedAction(key);
                        tmp.setAnimation(GUIItem.GUIAnimation.CLICK);
                    }
                    else{
                        keyTimes[key] += delta;
                    }
                }
                else{
                    keyTimes[key] = 0;
                }
            }
        }
    }
    
    public void keyTiped(int key){
        for(GUIItem tmp : items){
            if(!tmp.isDisabled()){
                if(Keyboard.getEventKeyState()){
                    tmp.onKeyTipedAction(key);
                    tmp.setAnimation(GUIItem.GUIAnimation.CLICK);
                }
            }
        }
    }
    
    public void resetTimers(){
        
    }
    
    private void pollKeyboardInputs(long delta){
        while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                if(activeInput != null){
                    activeInput.onKeyTipedAction(Keyboard.getEventKey());
                }
                else{
                    keyTiped(Keyboard.getEventKey());
                }
            }
        }
        
        for(int i = 0; i < Keyboard.getKeyCount(); i++){
            keyPressed(i, delta);
        }
    }
    
    public void mouseClicked(int button){
        int mousePos[] = getMousePos();
        if(Mouse.getEventButtonState()){
            for(GUIItem tmp : items){
                if(!tmp.isDisabled()){
                    if(tmp.intersect(mousePos[0], mousePos[1])){
                        tmp.onMouseClickAction(button);
                    }
                }
            }
        }
    }
    
    public void mouseReleased(){
        int mousePos[] = getMousePos();
        for(GUIItem tmp : hoveredItems){
            if(!tmp.intersect(mousePos[0], mousePos[1])){
                tmp.onMouseReleaseAction();
            }
        }
    }
    
    public void mouseHovered(){
        int mousePos[] = getMousePos();
        for(GUIItem tmp : items){
            if(tmp.intersect(mousePos[0], mousePos[1])){
                tmp.onMouseHoverAction();
                addItemsToHovered(tmp);
            }
        }
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
    
    public void mousePressed(int button, long delta){
        if(button >= 0){
            int mousePos[] = getMousePos();
            for(GUIItem tmp : items){
                if(!tmp.isDisabled()){
                    if(Mouse.isButtonDown(button)){
                        if(tmp.intersect( mousePos[0], mousePos[1])){
                            if(mouseTime[button] >= threshold){
                                tmp.onMouseHoverAction();
                                addItemsToHovered(tmp);
                                tmp.onMousePressedAction(button);
                            }
                            else{
                                mouseTime[button] += delta;
                            }
                        }
                    }
                    else{
                        mouseTime[button] = 0;
                    }
                }
            }
        }
    }
    
    private int[] getMousePos(){
        return new int[]{Mouse.getX(), Display.getHeight()-Mouse.getY()};
    }
    
    private void pollMouseInput(long delta){
         while(Mouse.next()){
             mouseClicked(Mouse.getEventButton());
        }
        for(int i = 0; i < Mouse.getButtonCount(); i++){
            mousePressed(i, delta);
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

/**
 *
 * @author Vortex Acherontic
 */
public abstract class GUIItemInput extends GUIItem{
    public static enum ActiveMode {BY_HOVER, BY_CLICK, NO};
    private ActiveMode activeMode = ActiveMode.NO;
    public GUIItemInput(Orientation ori, GUI parrent) {
        super(ori, parrent);
    }

    @Override
    public void idleAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMouseHoverAction() {
        activeMode = ActiveMode.BY_HOVER;
        GUIItemInput active = getParrentGUI().getActiveInput();
        if(active == null || active.activeMode == ActiveMode.BY_HOVER){
            getParrentGUI().setActiveInput(this);
        }
    }

    @Override
    public void onMouseClickAction(int mouseButton) {
        setActiveByClick();
    }

    @Override
    public void onMousePressedAction(int mouseButton) {
        setActiveByClick();
    }

    @Override
    public void onMouseReleaseAction() {
        if(activeMode == ActiveMode.BY_HOVER){
            activeMode = ActiveMode.NO;
        }
    }
    
    public void clearActive(){
        activeMode = ActiveMode.NO;
    }
    
    private void setActiveByClick(){
        activeMode = ActiveMode.BY_CLICK;
        getParrentGUI().setActiveInput(this);
    }
}

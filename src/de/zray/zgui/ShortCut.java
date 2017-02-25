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
public class ShortCut {
    private GUI parrentGUI;
    private int keyCombo[], action = -1, comboCount = 0;
    
    public ShortCut(GUI parrentGUI, int keyCombo[], int action){
        this.keyCombo = keyCombo;
        this.parrentGUI = parrentGUI;
        this.action = action;
    }
    
    /**
     * Returns the number of left keys until complete combo
     * @param key is the number of the pressed key
     * @return 
     */
    public int isMyCombo(int key){
        if(keyCombo[comboCount] == key){
            comboCount++;
        }
        else{
            comboCount = 0;
        }
        return keyCombo.length-comboCount;
    }
    
    public void callAction(){
        parrentGUI.callAction(action);
    }
}

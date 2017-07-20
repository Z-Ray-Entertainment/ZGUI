/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author vortex
 */
public class GUIText{
    public enum TextAllign{CENTER, LEFT, RIGHT};
    
    private Font awtFont;
    private boolean textInited = false;
    private float size = 12;
    private String ttfFont = "DejaVuSans.ttf", text;
    private final Orientation ori;
    private TextAllign allign = TextAllign.LEFT; 

    public GUIText(String text, Orientation ori, float size, String font){
        this.ori = ori;
        this.text = text;
        this.size = size;
        if(!font.isEmpty()){
            this.ttfFont = font;
        }
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public void setAllignment(TextAllign allign){
        this.allign = allign;
    }
}

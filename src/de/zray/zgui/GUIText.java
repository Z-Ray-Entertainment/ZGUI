/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.zgui;

import java.awt.Font;
import java.io.InputStream;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author vortex
 */
public class GUIText{
    public enum TextAllign{CENTER, LEFT, RIGHT};
    
    private Font awtFont;
    private TrueTypeFont font;
    private boolean textInited = false;
    private float size = 12;
    private String ttfFont = "DejaVuSans.ttf", text;
    private final Orientation ori;
    private TextAllign allign = TextAllign.LEFT; 
    private Color textColor = Color.black;
    
    public GUIText(String text, Orientation ori, float size, String font, Color textColor){
        this.ori = ori;
        this.text = text;
        this.size = size;
        this.textColor = textColor;
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
    
    private void initText(){
        if(!textInited){
            try {
                InputStream inputStream = ResourceLoader.getResourceAsStream(ttfFont);
                awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                awtFont = awtFont.deriveFont(size);
                font = new TrueTypeFont(awtFont, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
            textInited = true;
        }
    }
    
    public TrueTypeFont getFont(){
        return font;
    }
    
    public Color getColor(){
        return textColor;
    }
    
    public void setColor(Color color){
        this.textColor = color;
    }
    
    public void drawText(){
        initText();
        glPushMatrix();
        glEnable(GL_BLEND);
        switch(allign){
            case CENTER :
                font.drawString(-size/4f*text.length(), -size/2f, text, textColor);
                break;
            case LEFT :
                font.drawString(0, -size/2, text, textColor);
                break;
            case RIGHT :
                font.drawString(0, -size/2, text, textColor);
                break;
        }
        glDisable(GL_BLEND);
        glPopMatrix();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

/**
 *
 * @author 12sdauncey
 */
public abstract class RocketPart{
    
    protected int price;
    protected int mass;
    protected Image icon;
    protected Image bottomPartIcon;
    protected boolean isBottomPart;
    
    public abstract boolean isEngine();
    
    public void drawPart(Graphics g, int x, int y,JFrame jf){
        if(isBottomPart){
            g.drawImage(bottomPartIcon, x, y, jf);
        }else{
            g.drawImage(icon, x, y, jf);
        }
    }
    
    public int getHeight(JFrame jf){
        if(isBottomPart){
            return bottomPartIcon.getHeight(jf);
        }else{
            return icon.getHeight(jf);
        }
    }
}

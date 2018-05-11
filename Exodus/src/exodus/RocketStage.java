/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author 12sdauncey
 */
public class RocketStage {
    
    ArrayList<RocketPart> stageParts = new ArrayList();
    
    public void drawStage(Graphics g, int x,int y,JFrame jf){
        for(RocketPart part : stageParts){
            int totalHeight = 0;
            part.drawPart(g,x,y-totalHeight,jf);
            totalHeight += part.getHeight(jf);
        }
    }
    
    public int fuelMass;
    public int stageMass;
    public int specificImpulse;
    public int Thrust;
    
    public int getHeight(JFrame jf){
        int height = 0;
        for(RocketPart part : stageParts){
            height += part.getHeight(jf);
        }
        return height;
    }
}

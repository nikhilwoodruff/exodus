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
public class Rocket {
    
    protected int price;
    protected int stages;
    
    ArrayList<RocketStage> rocketStages = new ArrayList();
    public void fly(Graphics g){}
    public void drawRocket(Graphics g, int x, int y,JFrame jf){
        for(RocketStage stage : rocketStages){
            int totalHeight = 0;
            stage.drawStage(g,x,y-totalHeight,jf);
            totalHeight += stage.getHeight(jf);
        }
    }
    
    
    
    public Rocket(){
        rocketStages.add(new RocketStage());
    }
}

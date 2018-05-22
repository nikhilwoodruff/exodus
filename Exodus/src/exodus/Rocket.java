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
    public static final int MAXPARTS = 5;
    ArrayList<RocketStage> rocketStages = new ArrayList();
    
    public void fly(Graphics g){}
    
    public void drawRocket(Graphics g, int x, int y,JFrame jf){
        for(RocketStage stage : rocketStages){
            int totalHeight = 0;
            stage.drawStage(g,x,y-totalHeight,jf);
            totalHeight += stage.getHeight(jf);
        }
    }
    
    public void addPart(int position,RocketPart part)throws PartAddingException{
        if(position > countParts()||countParts() >= MAXPARTS){
            throw new PartAddingException();
        }    
        int partCount = 0;
        boolean partNotAdded = true;
        for(int i = 0;i<rocketStages.size() && partNotAdded;i++){
            partCount += rocketStages.get(i).stageParts.size();
            if(partCount <= position){
                if(part.isEngine()){
                    if(partCount == position){
                        //lol
                    }
                }else{
                    rocketStages.get(i).addPart(partCount-position, part);
                }
            }
        }
    }
    
    public int countParts(){
        int parts = 0;
        for(int i = 0;i<rocketStages.size();i++){
            parts += rocketStages.get(i).stageParts.size();
        }
        return parts;
    }
    
    public Rocket(){
        rocketStages.add(new RocketStage());
    }
}

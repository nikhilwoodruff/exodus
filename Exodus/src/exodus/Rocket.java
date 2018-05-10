/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Graphics;

/**
 *
 * @author 12sdauncey
 */
public class Rocket {
    
    RocketPart[] parts;
    protected int price;
    protected int stages;
    
    public void fly(Graphics g){}
    public void drawRocket(){}
    public int countStages(){
        int stages = 1;
        this.stages = stages;
        return stages;
    }
}

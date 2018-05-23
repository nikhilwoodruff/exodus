/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import javax.swing.JComponent;

/**
 *
 * @author user
 */
public class Animation {
    
    public JComponent object;
    boolean global;
    int[] dLocation;
    int[] targetLocation;
    int[] startLocation;
    long startTime;
    float duration;
    
    public int[] calculateLocation()
    {
        if(targetLocation == null)
        {
            targetLocation = new int[] {startLocation[0] + dLocation[0], startLocation[1] + dLocation[1]};
        }
        float progress = (float) (System.currentTimeMillis() - startTime) / (duration * 1000);
        if(progress > 1)
        {
            progress = 1;
        }
        progress = easeInOut(progress, 6);
        int x;
        int y;
        x = (int) Math.round(startLocation[0] + (targetLocation[0] - startLocation[0]) * progress);
        y = (int) Math.round(startLocation[1] + (targetLocation[1] - startLocation[1]) * progress);
        
        return new int[] {x, y};
    }
    
    public static Animation globalAnimation(JComponent object, int targetX, int targetY, float duration)
    {
        Animation anim = new Animation();
        anim.global = true;
        anim.object = object;
        anim.startLocation = new int[] {object.getLocation().x, object.getLocation().y};
        anim.targetLocation = new int[] {targetX, targetY};
        anim.startTime = System.currentTimeMillis();
        anim.duration = duration;
        return anim;
    }
    
    public static Animation localAnimation(JComponent object, int dx, int dy, float duration)
    {
        Animation anim = new Animation();
        anim.global = false;
        anim.object = object;
        anim.startLocation = new int[] {object.getLocation().x, object.getLocation().y};
        anim.dLocation = new int[] {dx, dy};
        anim.startTime = System.currentTimeMillis();
        anim.duration = duration;
        return anim;
    }
    
    public static float easeInOut(float input, float arg)
    {
        return (float) ((float) Math.pow(input, arg) / (Math.pow(input, arg) + Math.pow(1 - input, arg)));
    }
    
}

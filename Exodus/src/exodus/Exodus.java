/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author 12nwoodruff
 */
public class Exodus extends JFrame implements MouseListener{

    /**
     * @param args the command line arguments
     */
    Point mouse;
    boolean mouseDown;
    
    Image dbImage;
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(),getHeight());
        drawFrame(dbImage.getGraphics());
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void drawFrame(Graphics g){
        mouse = MouseInfo.getPointerInfo().getLocation();
        
        if(mouseDown){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.BLACK);
        }
        g.drawLine(0, 0, mouse.x, mouse.y);
        
        repaint();
        //ISSUE: when moving the window, the mouse position becomes offset - fullscreen?
    }
    
    public Exodus(){
        ExodusData player = new ExodusData(0.5f);
        for(int i = 0; i < 50; i++)
        {
            player.nextYear();
            System.out.println(player.islands[0].gdpPerCapita);
        }
        setTitle("Exodus");
        setSize(1800,1000);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            try
            {
                //player.nextYear();
                //System.out.println(player.islands[0].money);
                //System.out.println(player.islands[0].crimeRate);
                //System.out.println(player.islands[0].gdpPerCapita);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.MILLISECONDS);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new Exodus();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}

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
    ExodusData game;
    Image dbImage;
    float zoomScale;
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(),getHeight());
        drawFrame(dbImage.getGraphics());
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void drawFrame(Graphics g){
        mouse = MouseInfo.getPointerInfo().getLocation();
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(game.getWorldTime()), 50, 850);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(Color.GREEN);
        g.fillRect(50, 125, 250, 350);
        g.fillRect(1100, 120, 350, 250);
        g.fillRect(550, 600, 500, 200);
        int menuHit = isIntersectingMenu(mouse.x, mouse.y);
        if(menuHit != -1)
        {
            if(menuHit == 1)
            {
                g.setColor(Color.GRAY);
                g.fillRect(0, 500, 300, 500);
                g.setColor(Color.RED);
                g.fillRect(1500, 500, 300, 500);
            }
            else
            {
                g.setColor(Color.RED);
                g.fillRect(0, 500, 300, 500);
                g.setColor(Color.GRAY);
                g.fillRect(1500, 500, 300, 500);
            }
        }
        else
        {
            g.setColor(Color.GRAY);
            g.fillRect(0, 500, 300, 500);
            g.fillRect(1500, 500, 300, 500);
        }
        repaint();
        //ISSUE: when moving the window, the mouse position becomes offset - fullscreen?
    }
    int isIntersectingMenu(int x, int y)
    {
        if(y >= 500)
        {
            if(x <= 300)
            {
                return 0;
            }
            else if(x >= 1500)
            {
                return 1;
            }
            return -1;
        }
        else
        {
            return -1;
        }
    }
    public Exodus(){
        game = new ExodusData(0.5f, 10);
        game.setYearLength(1);
        setTitle("Exodus");
        setSize(1800,1000);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        /*for(int i = 0; i < 250; i++) //Just for looking at game data
        {
            game.nextYear();
        }
        String csvFile = "";
        for(int i = 0; i < 250; i++)
        {
            for(int j = 0; j < 34; j++)
            {
                csvFile += game.history.get(i)[j] + ", ";
            }
            csvFile += "\n";
        }
        System.out.println(csvFile);*/
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            try
            {
                game.nextYear();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }, 0, (long) game.getYearLength(), TimeUnit.SECONDS);
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

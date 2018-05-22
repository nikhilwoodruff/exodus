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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static jdk.nashorn.internal.runtime.ScriptingFunctions.exec;

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
                g.fillRect(0, 580, 300, 500);
                g.setColor(Color.RED);
                g.fillRect(1620, 580, 300, 500);
            }
            else
            {
                g.setColor(Color.RED);
                g.fillRect(0, 580, 300, 500);
                g.setColor(Color.GRAY);
                g.fillRect(1620, 580, 300, 500);
            }
        }
        else
        {
            g.setColor(Color.GRAY);
            g.fillRect(0, 580, 300, 500);
            g.fillRect(1620, 580, 300, 500);
        }
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(game.getWorldTime()), 50, 850);
        g.drawString(String.valueOf(mouse.x) + ", " + String.valueOf(mouse.y), mouse.x, mouse.y);
        repaint();
        //ISSUE: when moving the window, the mouse position becomes offset - fullscreen?
    }
    int isIntersectingMenu(int x, int y)
    {
        if(y >= 580)
        {
            if(x <= 300)
            {
                return 0;
            }
            else if(x >= 1620)
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
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Exodus");
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
    
    /*public static void main(String[] args) {
        // TODO code application logic here
        JPanel panel = new JPanel();
        JButton button = new JButton("Click me");
        button.setLocation(new Point(500, 550));
        button.setVisible(true);
        button.setSize(1000, 500);
        panel.add(button);
        panel.revalidate();
        panel.setVisible(true);
        panel.setSize(1920, 1080);
        Exodus exodus = new Exodus();
        exodus.add(panel);
        exodus.revalidate();
    }*/

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusUI {
    /*public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }*/
    int islandSelected;
    public ExodusUI()
    {
        islandSelected = 0;
        JFrame jf = new JFrame();
        jf.getContentPane().setBackground(new Color(55, 130, 200));
        JLabel localMenu = createLabel(0, 600, 400, 480, Color.GRAY, null);
        JLabel worldMenu = createLabel(1520, 600, 400, 480, Color.GRAY, null);
        List<JLabel> islands = new ArrayList<JLabel>();
        JLabel island1 = createLabel(175, 150, 450, 450, Color.GREEN, null);
        JLabel island2 = createLabel(1100, 75, 450, 450, Color.GREEN, null);
        JLabel island3 = createLabel(800, 500, 450, 450, Color.GREEN, null);
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        JLabel localHeaders = createLabel(50, 650, 150, 250, null, "<html>Island:<br>Population:<br>Money:<br>GDP Per Capita:<br>Tax Rate:<br>Crime Rate:<br>Food Security:<br>Job Security:<br>Land Area:<br>Happiness:</html>");
        JLabel localText = createLabel(200, 650, 150, 250, null, null);
        JLabel worldHeaders = createLabel(1570, 700, 150, 250, null, "<html>Population:<br>Time:<br>Climate Change:</html>");
        JLabel worldText = createLabel(1720, 700, 150, 250, null, null);
        
        for(JLabel island : islands) //Event listeners for mouse hover, click
        {
            final int index = islands.indexOf(island);
            MouseListener ml = new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent e) {
                    islandSelected = index;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            };
            island.addMouseListener(ml);
        }
        
        JButton exit = createButton(1820, 0, 100, 50, "Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
            }
            
        });
        jf.add(exit);
        jf.add(worldText);
        jf.add(worldHeaders);
        jf.add(localHeaders);
        jf.add(localText);
        jf.add(localMenu);
        jf.add(worldMenu);
        jf.add(island1);
        jf.add(island2);
        jf.add(island3);
        
        jf.setSize(1920, 1080);
        jf.setLayout(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setUndecorated(true);
        jf.revalidate();
        jf.setVisible(true);
        
        ExodusData game = new ExodusData(0.5f, 2);
        ScheduledExecutorService gameClock = Executors.newSingleThreadScheduledExecutor();
        gameClock.scheduleAtFixedRate(() -> {
            try
            {
                if(game.history.size() > 0)
                {
                    String localTextSummary = "<html>" + islandSelected + "<br>";
                    for(int i = 0; i < 9; i++)
                    {
                        localTextSummary += game.history.get(game.history.size()-1)[4 + islandSelected * 10 + i] + "<br>";
                    }
                    localText.setText(localTextSummary + "</html>");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);
        
        ScheduledExecutorService dataSync = Executors.newSingleThreadScheduledExecutor();
        dataSync.scheduleAtFixedRate(() -> {
            try
            {
                game.nextYear();
                String worldTextSummary = "<html>";
                for(int i = 0; i < 4; i++)
                {
                    if(i != 2)
                    {
                        worldTextSummary += game.history.get(game.history.size()-1)[i] + "<br>";
                    }
                }
                worldText.setText(worldTextSummary + "</html>");
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }, 0, (long) game.getYearLength(), TimeUnit.SECONDS);
        
    }
    
    public JLabel createLabel(int x, int y, int width, int height, Object background, String text)
    {
        JLabel label = new JLabel();
        label.setText(text);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setVisible(true);
        label.setOpaque(true);
        if(background != null)
        {
            if(background.getClass() == Color.class)
            {
                label.setBackground((Color) background);
            }
            else if(background.getClass() == ImageIcon.class)
            {
                label.setIcon((ImageIcon) background);
            }
        }
        else
        {
            label.setOpaque(false);
        }
        return label;
    }
    
    public JButton createButton(int x, int y, int width, int height, String text)
    {
        JButton button = new JButton(text);
        button.setLocation(x, y);
        button.setSize(width, height);
        return button;
    }
    
    public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }
}

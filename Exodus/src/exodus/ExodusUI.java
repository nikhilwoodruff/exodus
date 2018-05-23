/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusUI {
    /*public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }*/
    int islandSelected;
    int screen;
    List<Animation> jobs = new ArrayList<Animation>();
    public ExodusUI()
    {
        screen = 0;
        islandSelected = 0;
        JFrame jf = new JFrame();
        jf.getContentPane().setBackground(new Color(55, 130, 200));
        JPanel world = new JPanel();
        world.setLayout(null);
        world.setSize(1920, 1080);
        world.setLocation(0, 0);
        JPanel hq = new JPanel();
        hq.setLayout(null);
        hq.setSize(1920, 1080);
        hq.setLocation(0, 1080);
        hq.setBackground(Color.red);
        JLabel localMenu = createLabel(0, 600, 400, 480, Color.GRAY, null, false);
        JLabel worldMenu = createLabel(1520, 600, 400, 480, Color.GRAY, null, false);
        List<JLabel> islands = new ArrayList<JLabel>();
        JLabel island1 = createLabel(175, 150, 450, 450, readImage("island1.png", 450, 450), null, true);
        JLabel island2 = createLabel(1100, 75, 450, 450, readImage("island2.png", 450, 450), null, true);
        JLabel island3 = createLabel(800, 500, 450, 450, readImage("island3.png", 450, 450), null, true);
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        JLabel localHeaders = createLabel(25, 650, 175, 400, null, "<html>Island:<br>Population:<br>Money:<br>GDP Per Capita:<br>Tax Rate:<br>Crime Rate:<br>Food Security:<br>Job Security:<br>Land Area:<br>Happiness:</html>", true);
        localHeaders.setFont(new Font("Courier New", Font.PLAIN, 12));
        JLabel localText = createLabel(200, 650, 175, 400, null, null, true);
        JLabel worldHeaders = createLabel(1545, 700, 175, 400, null, "<html>Population:<br>Time:<br>Climate Change:</html>", true);
        worldHeaders.setFont(new Font("Courier New", Font.PLAIN, 12));
        JLabel worldText = createLabel(1720, 700, 175, 400, null, null, true);
        
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
        JButton switchView = createButton(0, 0, 100, 50, "Switch");
        switchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(screen == 0)
                {
                    jobs.add(Animation.globalAnimation(world, 0, 1080, 1));
                    jobs.add(Animation.globalAnimation(hq, 0, 0, 1));
                    screen = 1;
                }
                else if(screen == 1)
                {
                    jobs.add(Animation.globalAnimation(hq, 0, 1080, 1));
                    jobs.add(Animation.globalAnimation(world, 0, 0, 1));
                    screen = 0;
                }
            }
            
        });
        JButton exit = createButton(1820, 0, 100, 50, "Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
            }
            
        });
        
        JLabel background = createLabel(0, 0, 1920, 1080, readImage("ocean.png", 1920, 1080), null, false);
        
        jf.add(exit);
        jf.add(switchView);
        jf.add(world);
        world.add(worldText);
        world.add(worldHeaders);
        world.add(localHeaders);
        world.add(localText);
        world.add(localMenu);
        world.add(worldMenu);
        world.add(island1);
        world.add(island2);
        world.add(island3);
        world.add(background);
        jf.add(hq);
        jf.setSize(1920, 1080);
        jf.setLayout(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setUndecorated(true);
        jf.revalidate();
        jf.setVisible(true);
        
        ExodusData game = new ExodusData(0.5f, 2);
        
        ScheduledExecutorService animation = Executors.newSingleThreadScheduledExecutor();
        animation.scheduleAtFixedRate(() -> {
            try
            {
                if(jobs.size() > 0)
                {
                    Animation nextInQueue = jobs.get(0);
                    int newX = nextInQueue.calculateLocation()[0];
                    int newY = nextInQueue.calculateLocation()[1];
                    nextInQueue.object.setLocation(newX, newY);
                    int[] currentLocation = new int[] {nextInQueue.object.getLocation().x, nextInQueue.object.getLocation().y};
                    if(currentLocation[0] == nextInQueue.targetLocation[0] && currentLocation[1] == nextInQueue.targetLocation[1])
                    {
                        jobs.remove(0);
                        if(jobs.size() > 0)
                        {
                            Animation next = jobs.get(0);
                            next.startTime = System.currentTimeMillis();
                            next.startLocation = new int[] {next.object.getLocation().x, next.object.getLocation().y};
                            jobs.set(0, next);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.NANOSECONDS);
        
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
    
    public ImageIcon readImage(String fileName, int width, int height) {
        //File must be in /Resources/!
        BufferedImage image = null;
        try {
            image = ImageIO.read((Exodus.class.getResourceAsStream("/Resources/" + fileName)));
        } catch (Exception e) {
//            System.out.println(e);
        }
        Image sImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(sImage);
    }
    
    public JLabel createLabel(int x, int y, int width, int height, Object background, String text, boolean transparent)
    {
        JLabel label = new JLabel();
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Courier New", Font.BOLD, 24));
        label.setText(text);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setVisible(true);
        label.setOpaque(!transparent);
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

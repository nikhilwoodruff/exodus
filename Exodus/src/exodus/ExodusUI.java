/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusUI {
    
    int islandSelected;
    int screen;
    boolean budgetOpen;
    List<Animation> jobs = new ArrayList<Animation>();
    public ExodusUI()
    {
        ExodusData game = new ExodusData(0.5f, 2);
        
        budgetOpen = false;
        screen = 0;
        islandSelected = 0;
        JFrame jf = new JFrame();
        jf.getContentPane().setBackground(new Color(55, 130, 200));
        JLabel worldBackground = createLabel(0, 0, 1920, 1080, readImage("stars.png", 1920, 1080), null, false);
        JPanel world = new JPanel();
        world.setLayout(null);
        world.setSize(1920, 1080);
        world.setLocation(0, 0);
        JPanel hq = new JPanel();
        hq.setLayout(null);
        hq.setSize(1920, 1080);
        hq.setLocation(0, 1080);
        JLabel hqBackground = createLabel(0, 0, 1920, 1080, readImage("hqBackground.png", 1920, 1080), null, false);
        JLabel localMenu = createLabel(0, 600, 400, 480, Color.GRAY, null, false);
        JLabel worldMenu = createLabel(1520, 600, 400, 480, Color.GRAY, null, false);
        List<JLabel> islands = new ArrayList<JLabel>();
        JLabel island1 = createLabel(175, 150, 450, 450, readImage("altIsland1.png", 450, 450), null, true);
        JLabel island2 = createLabel(1100, 75, 450, 450, readImage("island2.png", 450, 450), null, true);
        JLabel island3 = createLabel(800, 500, 450, 450, readImage("island3.png", 450, 450), null, true);
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        JLabel localText = createLabel(25, 650, 350, 150, null, null, false);
        JLabel worldText = createLabel(1545, 700, 350, 400, null, null, false);
        
        for(JLabel island : islands) //Event listeners for mouse hover, click
        {
            final int index = islands.indexOf(island);
            MouseListener ml = new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for(int i = 0; i < 3; i++)
                    {
                        islands.get(i).setIcon(readImage("island" + (i + 1) + ".png", 450, 450));
                    }
                    islandSelected = index;
                    if(!budgetOpen)
                    {
                        island.setIcon(readImage("altIsland" + (index + 1) + ".png", 450, 450));
                    }
                    HandleSound("click.wav");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                    if(!budgetOpen)
                    {
                        HandleSound("hover.wav");
                        island.setIcon(readImage("altIsland" + (index + 1) + ".png", 450, 450));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(index != islandSelected)
                    {
                        island.setIcon(readImage("island" + (index + 1) + ".png", 450, 450));
                    }
                }
            };
            island.addMouseListener(ml);
        }
        JButton switchView = createButton(0, 0, 100, 50, "Switch");
        
        JButton exit = createButton(1820, 0, 100, 50, "Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HandleSound("click.wav");
                jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
            }
            
        });
        
        JLabel background = createLabel(0, 0, 1920, 1080, new Color(134, 190, 255), null, false);
        JPanel editBudget = new JPanel();
        editBudget.setLayout(null);
        editBudget.setSize(500, 400);
        editBudget.setLocation(750, 1080);
        JSlider publicServices = new JSlider();
        publicServices.setLocation(250, 25);
        publicServices.setSize(200, 25);
        JSlider greenEnergy = new JSlider();
        greenEnergy.setLocation(250, 125);
        greenEnergy.setSize(200, 25);
        JSlider greenDefenses = new JSlider();
        greenDefenses.setLocation(250, 225);
        greenDefenses.setSize(200, 25);
        JSlider military = new JSlider();
        military.setLocation(250, 325);
        military.setSize(200, 25);
        JLabel[] sliderLabels = new JLabel[4];
        String[] sliderText = new String[] {"Public Services", "Greeen Energy", "Disaster Insurance", "Military Spending"};
        for(int i = 0; i < 4; i++)
        {
            sliderLabels[i] = createLabel(25, 25 + 100 * i, 200, 50, null, sliderText[i], true);
            sliderLabels[i].setFont(new Font("Courier New", Font.PLAIN, 15));
            editBudget.add(sliderLabels[i]);
        }
        editBudget.add(publicServices);
        editBudget.add(greenEnergy);
        editBudget.add(greenDefenses);
        editBudget.add(military);
        switchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(budgetOpen)
                {
                    jobs.add(Animation.globalAnimation(editBudget, 750, 1080, 1));
                    budgetOpen = false;
                }
                if(screen == 0)
                {
                    jobs.add(Animation.globalAnimation(world, 0, 1080, 0.5f));
                    jobs.add(Animation.globalAnimation(hq, 0, 0, 0.5f));
                    screen = 1;
                }
                else if(screen == 1)
                {
                    jobs.add(Animation.globalAnimation(hq, 0, 1080, 0.5f));
                    jobs.add(Animation.globalAnimation(world, 0, 0, 0.5f));
                    screen = 0;
                }
                HandleSound("click.wav");
            }
            
        });
        JLabel budgetBackground = createLabel(0, 0, 500, 250, Color.red, null, true);
        editBudget.add(budgetBackground);
        JButton editBudgetButton = createButton(25, 825, 100, 50, "Edit Budget");
        editBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(budgetOpen)
                {
                    jobs.add(Animation.globalAnimation(editBudget, 750, 1080, 1));
                    game.getIslands()[islandSelected].setBudget(new float[] {(float) publicServices.getValue() / 100,(float) greenEnergy.getValue() / 100,(float) greenDefenses.getValue() / 100,(float) military.getValue() / 100});
                    budgetOpen = false;
                }
                else
                {
                    publicServices.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[0] * 100));
                    greenEnergy.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[1] * 100));
                    greenDefenses.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[2] * 100));
                    military.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[3] * 100));
                    jobs.add(Animation.globalAnimation(editBudget, 750, 350, 1));
                    budgetOpen = true;
                }
                HandleSound("click.wav");
            }
            
        });
        world.add(editBudgetButton);
        JLabel rocketLabel = createLabel(50, 50, 400, 900, null, "Rocket", true);
        BufferedImage img = new BufferedImage(400, 900, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        Rocket rocket = new Rocket();
        rocket.drawRocket(g, 0, 0, jf);
        rocketLabel.setIcon(new ImageIcon(img));
        hq.add(rocketLabel);
        jf.add(editBudget);
        jf.add(exit);
        jf.add(switchView);
        jf.add(world);
        hq.add(hqBackground);
        world.add(worldText);
        world.add(localText);
        world.add(localMenu);
        world.add(worldMenu);
        world.add(island1);
        world.add(island2);
        world.add(island3);
        world.add(background);
        
        jf.add(hq);
        jf.add(worldBackground);
        jf.setSize(1920, 1080);
        jf.setLayout(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setUndecorated(true);
        jf.revalidate();
        jf.setVisible(true);
        
        
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
        ScheduledExecutorService animation = Executors.newSingleThreadScheduledExecutor();
        animation.scheduleAtFixedRate(() -> {
            try
            {
                if(jobs.size() >= 1)
                {
                    while(jobs.get(0) == null)
                    {
                        jobs.remove(0);
                    }
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
                
            }
        }, 0, 1, TimeUnit.NANOSECONDS);
        
        ScheduledExecutorService gameClock = Executors.newSingleThreadScheduledExecutor();
        gameClock.scheduleAtFixedRate(() -> {
            try
            {
                String local = "<html>";
                local += "<i>Island:</i> " + islandSelected;
                local += "<br><i>Population:</i> " + game.getIslands()[islandSelected].getPopulation();
                local += "<br><i>Money:</i> " + game.getIslands()[islandSelected].getMoney();
                local += "<br><i>GDP Per Capita:</i> " + game.getIslands()[islandSelected].getGdpPerCapita();
                local += "<br><i>Crime Rate:</i> " + game.getIslands()[islandSelected].getCrimeRate();
                local += "<br><i>Food Access:</i> " + game.getIslands()[islandSelected].getFoodSecurity();
                local += "<br><i>Energy Access:</i> " + game.getIslands()[islandSelected].getEnergySecurity();
                local += "<br><i>Employment Rate:</i> " + game.getIslands()[islandSelected].getJobSecurity();
                local += "<br><i>Happiness:</i> " + game.getIslands()[islandSelected].getHappiness();
                local += "<br><i>Land Area:</i> " + game.getIslands()[islandSelected].getLandArea() + "</html>";
                localText.setText(local);
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
            
        }
        Image sImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(sImage);
    }
    
    public JLabel createLabel(int x, int y, int width, int height, Object background, String text, boolean transparent)
    {
        JLabel label = new JLabel();
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Courier New", Font.PLAIN, 18));
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
    
    public void HandleSound(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Exodus.class.getResourceAsStream("/Resources/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }
}

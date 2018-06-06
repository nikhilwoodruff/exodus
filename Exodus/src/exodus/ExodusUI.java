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
import javax.swing.JProgressBar;
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
    boolean actionOpen;
    List<Animation> jobs = new ArrayList<Animation>();

    public ExodusUI() {
        ImageIcon[] islandImages = new ImageIcon[6];
        islandImages[0] = readImage("island1.png", 450, 450);
        islandImages[1] = readImage("island2.png", 450, 450);
        islandImages[2] = readImage("island3.png", 450, 450);
        islandImages[3] = readImage("altIsland1.png", 450, 450);
        islandImages[4] = readImage("altIsland2.png", 450, 450);
        islandImages[5] = readImage("altIsland3.png", 450, 450);
        ExodusData game = new ExodusData(0.5f, 2);
        actionOpen = false;
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
        JLabel island1 = createLabel(175, 150, 450, 450, islandImages[3], null, true);
        JLabel island2 = createLabel(1100, 75, 450, 450, islandImages[1], null, true);
        JLabel island3 = createLabel(800, 500, 450, 450, islandImages[2], null, true);
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        JLabel crimeLabel = createLabel(10, 730, 100, 30, null, "Crime Rate", true, 12);
        world.add(crimeLabel);
        JProgressBar crimeBar = new JProgressBar();
        crimeBar.setStringPainted(true);
        crimeBar.setLocation(125, 730);
        crimeBar.setSize(250, 15);
        world.add(crimeBar);
        JLabel foodLabel = createLabel(10, 760, 100, 30, null, "Energy Access", true, 12);
        world.add(foodLabel);
        JProgressBar foodBar = new JProgressBar();
        foodBar.setStringPainted(true);
        foodBar.setLocation(125, 760);
        foodBar.setSize(250, 15);
        world.add(foodBar);
        JLabel energyLabel = createLabel(10, 790, 100, 30, null, "Energy Access", true, 12);
        world.add(energyLabel);
        JProgressBar energyBar = new JProgressBar();
        energyBar.setStringPainted(true);
        energyBar.setLocation(125, 790);
        energyBar.setSize(250, 15);
        world.add(energyBar);
        JLabel employmentLabel = createLabel(10, 820, 100, 30, null, "Employment", true, 12);
        world.add(employmentLabel);
        JProgressBar employmentBar = new JProgressBar();
        employmentBar.setStringPainted(true);
        employmentBar.setLocation(125, 820);
        employmentBar.setSize(250, 15);
        world.add(employmentBar);
        JLabel happinessLabel = createLabel(10, 850, 100, 30, null, "Happiness", true, 12);
        world.add(happinessLabel);
        JProgressBar happinessBar = new JProgressBar();
        happinessBar.setStringPainted(true);
        happinessBar.setLocation(125, 850);
        happinessBar.setSize(250, 15);
        JLabel localText = createLabel(25, 650, 350, 400, null, null, false);
        JLabel worldText = createLabel(1545, 650, 350, 400, null, null, false);
        for (JLabel island : islands) //Event listeners for mouse hover, click
        {
            final int index = islands.indexOf(island);
            MouseListener ml = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HandleSound("click.wav");
                    if (!budgetOpen && !actionOpen) {
                        for (int i = 0; i < 3; i++) {
                            islands.get(i).setIcon(islandImages[i]);
                        }
                        islandSelected = index;
                        island.setIcon(islandImages[index + 3]);

                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    if (!budgetOpen && !actionOpen) {
                        HandleSound("hover.wav");
                        island.setIcon(islandImages[index + 3]);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!budgetOpen && !actionOpen) {
                        if (index != islandSelected) {
                            island.setIcon(islandImages[index]);
                        }
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
        String[] sliderText = new String[]{"Public Services", "Greeen Energy", "Disaster Insurance", "Military Spending"};
        for (int i = 0; i < 4; i++) {
            sliderLabels[i] = createLabel(25, 25 + 100 * i, 200, 50, null, sliderText[i], true);
            sliderLabels[i].setFont(new Font("Courier New", Font.PLAIN, 15));
            editBudget.add(sliderLabels[i]);
        }

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(null);
        actionPanel.setSize(500, 400);
        actionPanel.setLocation(750, 1080);
        JButton actionButton = createButton(150, 900, 100, 50, "Actions");
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (budgetOpen) {
                    budgetOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(editBudget, 750, 1080, 0.5f));
                    }

                }
                if (actionOpen) {
                    actionOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(actionPanel, 750, 1080, 0.5f));
                    }
                } else {
                    actionOpen = true;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(actionPanel, 750, 350, 0.5f));
                    }

                }
                HandleSound("click.wav");
            }

        });
        JButton movePopulation = createButton(0, 0, 100, 50, "Move Population");
        movePopulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HandleSound("click.wav");
            }

        });
        actionPanel.add(movePopulation);
        editBudget.add(publicServices);
        editBudget.add(greenEnergy);
        editBudget.add(greenDefenses);
        editBudget.add(military);
        switchView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (budgetOpen) {
                    budgetOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(editBudget, 750, 1080, 0.5f));
                    }

                }
                if (actionOpen) {
                    actionOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(actionPanel, 750, 1080, 0.5f));
                    }

                }
                if (screen == 0) {
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(world, 0, 1080, 0.5f));
                        jobs.add(Animation.globalAnimation(hq, 0, 0, 0.5f));
                    }
                    screen = 1;
                } else if (screen == 1) {
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(hq, 0, 1080, 0.5f));
                        jobs.add(Animation.globalAnimation(world, 0, 0, 0.5f));
                    }
                    screen = 0;
                }
                HandleSound("click.wav");
            }

        });
        JLabel budgetBackground = createLabel(0, 0, 500, 250, Color.red, null, true);
        editBudget.add(budgetBackground);
        JButton editBudgetButton = createButton(25, 900, 100, 50, "Edit Budget");
        editBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionOpen) {
                    actionOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(actionPanel, 750, 1080, 0.5f));
                    }

                }
                if (budgetOpen) {
                    budgetOpen = false;
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(editBudget, 750, 1080, 0.5f));
                    }
                    game.getIslands()[islandSelected].setBudget(new float[]{(float) publicServices.getValue() / 100, (float) greenEnergy.getValue() / 100, (float) greenDefenses.getValue() / 100, (float) military.getValue() / 100});

                } else {
                    budgetOpen = true;
                    publicServices.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[0] * 100));
                    greenEnergy.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[1] * 100));
                    greenDefenses.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[2] * 100));
                    military.setValue(Math.round(game.getIslands()[islandSelected].getBudget()[3] * 100));
                    if (jobs.size() < 3) {
                        jobs.add(Animation.globalAnimation(editBudget, 750, 350, 0.5f));
                    }

                }
                HandleSound("click.wav");
            }

        });
        world.add(editBudgetButton);
        world.add(actionButton);
        JLabel rocketLabel = createLabel(50, 50, 400, 900, null, "Rocket", true);
        BufferedImage img = new BufferedImage(400, 900, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        Rocket rocket = new Rocket();
        rocket.drawRocket(g, 0, 0, jf);
        rocketLabel.setIcon(new ImageIcon(img));
        hq.add(rocketLabel);
        jf.add(editBudget);
        jf.add(actionPanel);
        jf.add(exit);
        jf.add(switchView);
        jf.add(world);
        hq.add(hqBackground);
        world.add(worldText);
        world.add(happinessBar);
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
            try {
                if (jobs.size() >= 1) {
                    Animation nextInQueue = jobs.get(0);
                    int newX = nextInQueue.calculateLocation()[0];
                    int newY = nextInQueue.calculateLocation()[1];
                    nextInQueue.object.setLocation(newX, newY);

                    int[] currentLocation = new int[]{nextInQueue.object.getLocation().x, nextInQueue.object.getLocation().y};
                    if (currentLocation[0] == nextInQueue.targetLocation[0] && currentLocation[1] == nextInQueue.targetLocation[1]) {
                        jobs.remove(0);
                        if (jobs.size() > 0) {
                            Animation next = jobs.get(0);
                            next.startTime = System.currentTimeMillis();
                            next.startLocation = new int[]{next.object.getLocation().x, next.object.getLocation().y};
                            jobs.set(0, next);
                        }
                    }
                }
            } catch (Exception e) {

            }
        }, 0, 1, TimeUnit.NANOSECONDS);

        ScheduledExecutorService gameClock = Executors.newSingleThreadScheduledExecutor();
        gameClock.scheduleAtFixedRate(() -> {
            try {
                String local = "<html>";
                local += "<i>Island:</i> " + islandSelected;
                local += "<br><i>Population in millions:</i> " + game.getIslands()[islandSelected].getPopulation();
                local += "<br><i>Money in millions:</i> " + game.getIslands()[islandSelected].getMoney();
                local += "<br><i>GDP Per Capita in thousands:</i> " + game.getIslands()[islandSelected].getGdpPerCapita();
//                local += "<br><i>Crime Rate:</i> " + game.getIslands()[islandSelected].getCrimeRate();
//                local += "<br><i>Food Access:</i> " + game.getIslands()[islandSelected].getFoodSecurity();
//                local += "<br><i>Employment Rate:</i> " + game.getIslands()[islandSelected].getJobSecurity();
//                local += "<br><i>Happiness:</i> " + game.getIslands()[islandSelected].getHappiness();
//                local += "<br><i>EditBudgetOpen:</i> " + budgetOpen;
//                local += "<br><i>InvestOpen:</i> " + actionOpen;
//                local += "<br><i>Land Area in 1000km3:</i> " + game.getIslands()[islandSelected].getLandArea()/1000 + "</html>";
                localText.setText(local);
                localText.setSize(localText.getPreferredSize());
                happinessBar.setValue((int) Math.round(game.getIslands()[islandSelected].getHappiness() * 100));
                employmentBar.setValue((int) Math.round(game.getIslands()[islandSelected].getJobSecurity() * 100));
                energyBar.setValue((int) Math.round(game.getIslands()[islandSelected].getEnergySecurity() * 100));
                foodBar.setValue((int) Math.round(game.getIslands()[islandSelected].getFoodSecurity() * 100));
                crimeBar.setValue((int) Math.round(game.getIslands()[islandSelected].getCrimeRate() * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);

        ScheduledExecutorService dataSync = Executors.newSingleThreadScheduledExecutor();
        dataSync.scheduleAtFixedRate(() -> {
            try {
                if (!budgetOpen && !actionOpen && !(game.getWorldTime() > game.getWorldEndTime())) {
                    game.nextYear();
                }
                String worldTextSummary = "<html>";
                for (int i = 0; i < 4; i++) {
                    if (i != 2) {
                        worldTextSummary += game.history.get(game.history.size() - 1)[i] + "<br>";
                    }
                }
                worldText.setText(worldTextSummary + "</html>");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, (long) 800, TimeUnit.MILLISECONDS);

    }

    public ImageIcon readImage(String fileName, int width, int height) {
        //File must be in /Resources/!
        BufferedImage image = null;
        try {
            image = ImageIO.read((ExodusUI.class.getResourceAsStream("/Resources/" + fileName)));
        } catch (Exception e) {

        }
        Image sImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(sImage);
    }

    public JLabel createLabel(int x, int y, int width, int height, Object background, String text, boolean transparent) {
        JLabel label = new JLabel();
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Courier New", Font.PLAIN, 18));
        label.setText(text);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setVisible(true);
        label.setOpaque(!transparent);
        if (background != null) {
            if (background.getClass() == Color.class) {
                label.setBackground((Color) background);
            } else if (background.getClass() == ImageIcon.class) {
                label.setIcon((ImageIcon) background);
            }
        } else {
            label.setOpaque(false);
        }
        return label;
    }
    public JLabel createLabel(int x, int y, int width, int height, Object background, String text, boolean transparent, int fontSize) {
        JLabel label = new JLabel();
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("Courier New", Font.PLAIN, fontSize));
        label.setText(text);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setVisible(true);
        label.setOpaque(!transparent);
        if (background != null) {
            if (background.getClass() == Color.class) {
                label.setBackground((Color) background);
            } else if (background.getClass() == ImageIcon.class) {
                label.setIcon((ImageIcon) background);
            }
        } else {
            label.setOpaque(false);
        }
        return label;
    }

    public JButton createButton(int x, int y, int width, int height, String text) {
        JButton button = new JButton(text);
        button.setLocation(x, y);
        button.setSize(width, height);
        return button;
    }

    public void HandleSound(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(ExodusUI.class.getResourceAsStream("/Resources/" + fileName));
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusUI {
    /*public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }*/
    public ExodusUI()
    {
        JFrame jf = new JFrame();
        
        JLabel localMenu = createLabel(0, 500, 400, 580, Color.GRAY, null);
        JLabel worldMenu = createLabel(1520, 500, 400, 580, Color.GRAY, null);
        
//        jf.add(localMenu);
//        jf.add(worldMenu);
        jf.setSize(1920, 1080);
        jf.setLayout(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setUndecorated(true);
        jf.revalidate();
        jf.setVisible(true);
    }
    
    public JLabel createLabel(int x, int y, int width, int height, Object background, String text)
    {
        if(background.getClass() != Color.class && background.getClass() != ImageIcon.class)
        {
            return null;
        }
        JLabel label = new JLabel();
        label.setText(text);
        label.setLocation(x, y);
        label.setSize(width, height);
        if(background.getClass() == Color.class)
        {
            label.setBackground((Color) background);
        }
        else
        {
            label.setIcon((ImageIcon) background);
        }
        label.setVisible(true);
        label.setOpaque(true);
        return label;
    }
    
    public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }
}

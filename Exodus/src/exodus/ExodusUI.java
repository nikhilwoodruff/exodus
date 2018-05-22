/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exodus;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author 12nwoodruff
 */
public class ExodusUI {
    public static void main(String[] args) {
        ExodusUI ui = new ExodusUI();
    }
    public ExodusUI()
    {
        JFrame jf = new JFrame();
        JPanel jp = new JPanel();
        jf.add(jp);
        jf.revalidate();
        jf.setVisible(true);
        jf.setSize(1920, 1080);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton btn = new JButton("Click me");
        jp.add(btn);
    }
}

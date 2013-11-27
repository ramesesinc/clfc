/*
 * VerticalLabel.java
 *
 * Created on November 7, 2013, 12:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test.label;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author compaq
 */
public class VerticalLabel extends JPanel 
{
    private String text;
    
    public String getText() { return text; } 
    public void setText(String text) {
        this.text = text; 
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(-10.0, 0, 0); 
        g2.drawString("Vertical Label", 0, 0); 
        g2.dispose();
    }
}

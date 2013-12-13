/*
 * ImageUtil.java
 *
 * Created on December 9, 2013, 11:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test.util;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author compaq
 */
public final class ImageUtil 
{
    public static void showImage(byte[] data) {
        ImageIcon iicon = new ImageIcon(data); 
        JLabel lbl = new JLabel(); 
        lbl.setIcon(iicon);
        JOptionPane.showMessageDialog(null, lbl); 
    }
    
    
    private ImageUtil() {
    }
    
}

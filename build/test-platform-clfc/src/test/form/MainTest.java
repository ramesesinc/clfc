/*
 * MainTest.java
 *
 * Created on December 4, 2013, 12:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test.form;

import javax.swing.JDialog;

/**
 *
 * @author compaq
 */
public class MainTest {
    
    public static void main(String[] args) throws Exception 
    {
        JDialog d = new JDialog(); 
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);         
        d.setModal(true);
        d.setContentPane(new SamplePage2()); 
        d.pack();
        d.setVisible(true); 
    }
}

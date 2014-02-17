/*
 * NewEmptyJUnitTest.java
 * JUnit based test
 *
 * Created on September 9, 2013, 1:27 PM
 */

package test;

import javax.swing.JDialog;
import javax.swing.UIManager;
import junit.framework.*;

/**
 *
 * @author compaq
 */
public class NewEmptyJUnitTest extends TestCase {
    
    public NewEmptyJUnitTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
    }

    protected void tearDown() throws Exception {
    }
    
    public void test0() throws Exception {
//        JPanel pnl = new JPanel(new BorderLayout());
//        pnl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        
//        CTabbedPane ctab = new CTabbedPane();
//        pnl.add(ctab); 
//        
//        ctab.addTab("Tab#1", new TestPanel(ctab));
//        ctab.addTab("Tab#2", new TestPanel(ctab));
//        ctab.addTab("Tab#3", new TestPanel(ctab));
//        
        JDialog d = new JDialog();
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.setModal(true);        
        d.setContentPane(new ImageCanvas()); 
        d.setSize(640, 480); 
        d.setVisible(true); 
    }

    
}

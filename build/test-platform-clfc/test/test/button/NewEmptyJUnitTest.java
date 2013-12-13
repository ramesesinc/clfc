/*
 * NewEmptyJUnitTest.java
 * JUnit based test
 *
 * Created on September 9, 2013, 1:27 PM
 */

package test.button;

import javax.swing.JButton;
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
        
        JDialog d = new JDialog();
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.setModal(true);        
        d.setContentPane(new TestForm()); 
        d.setSize(640, 480); 
        d.pack();
        d.setVisible(true); 
    }


}

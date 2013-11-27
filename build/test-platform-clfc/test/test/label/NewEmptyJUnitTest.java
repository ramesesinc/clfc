/*
 * NewEmptyJUnitTest.java
 * JUnit based test
 *
 * Created on November 7, 2013, 12:55 PM
 */

package test.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
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
        System.out.println(UIManager.getSystemLookAndFeelClassName());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    protected void tearDown() throws Exception {
    }
    
    public void testMain() throws Exception {
        JPanel p = new JPanel();
        //p.setLayout(new BorderLayout());
        
        VerticalPanel vl = new VerticalPanel();
        vl.setText("This is a vertical label");
        vl.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5)); 
        vl.setFont(Font.decode("-bold-"));
        p.add(vl);
        
        JDialog d = new JDialog();
        d.setModal(true);
        d.setContentPane(p); 
        d.setSize(200,200);
        d.setVisible(true); 
    }

    private class VerticalPanel extends JPanel 
    {
        private JLabel lbl;
        private Border bin;
        private Dimension prefdim; 

        private JLabel getLabel() {
            if (lbl == null) {
                lbl = new JLabel();
            }
            return lbl;
        }
        
        private Border getBorderIn() {
            if (bin == null) {
                bin = BorderFactory.createEmptyBorder(1,3,1,3);
            } 
            return bin;
        }
        
        public String getText() { 
            return getLabel().getText(); 
        } 
        public void setText(String text) {
            getLabel().setText(text); 
        }

        public void setBorder(Border border) {
            Border bout = border;
            if (bout == null) bout = BorderFactory.createEmptyBorder(0,0,0,0);
                        
            Border bin = getBorderIn();
            Border compound = BorderFactory.createCompoundBorder(bout, bin);
            super.setBorder(bout); 
            getLabel().setBorder(compound); 
        }

        public Dimension getPreferredSize() {
            Dimension dim = prefdim; 
            if (prefdim == null) {
                dim = getLabel().getPreferredSize();
            }
            return new Dimension(dim.height, dim.width); 
        }
        public void setPreferredSize(Dimension prefdim) {
            super.setPreferredSize(prefdim);
            this.prefdim = prefdim; 
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Insets margin = getInsets();
            String text = getText();
            Graphics2D g2 = (Graphics2D) g.create();
            Font font = g2.getFont();
            FontRenderContext frc = g2.getFontRenderContext();
            Rectangle2D rect = font.getStringBounds(text, frc); 
            LineMetrics lm = font.getLineMetrics(text, frc); 
            int fwidth = (int) rect.getWidth();
            int fheight = (int) rect.getHeight();
            float lmh = lm.getAscent() + lm.getDescent(); 

            int pw = getWidth() - (margin.left + margin.right);
            int ph = getHeight() - (margin.top + margin.bottom);
            FontMetrics fm = g2.getFontMetrics();
            int twidth = fm.stringWidth(text); 
            int theight = fm.getHeight();
            int tx = (getWidth()-twidth)/2;
            int ty = (getHeight()-theight)/2;
            ty += (theight/2); 
            
            System.out.println("tx="+tx + ", ty="+ty);
            
            g2.rotate(-Math.PI/2, Math.max(pw/2, 0), Math.max(ph/2, 0)); 
            g2.drawString(text, tx-margin.bottom-margin.top, margin.left+ty); 
            g2.dispose();
        }
    }
}

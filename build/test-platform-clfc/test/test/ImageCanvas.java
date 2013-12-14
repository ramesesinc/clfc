/*
 * PhotoCanvas.java
 *
 * Created on December 6, 2013, 9:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test;

import com.rameses.rcp.support.ImageIconSupport;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author wflores
 */
public class ImageCanvas extends JLabel 
{
    private Dimension cropSize;
    private ImageIcon iicon;
    private BufferedImage bufferedImage;
    private Point dragPoint;
    private Rectangle rectOver;
    
    public ImageCanvas() {
        super("Image Canvas"); 
        setBorder(BorderFactory.createLineBorder(Color.BLUE)); 
        
        MouseSupport mouseSupport = new MouseSupport();
        addMouseListener(mouseSupport); 
        addMouseMotionListener(mouseSupport);
        
        cropSize = new Dimension(2, 2); 
        iicon = ImageIconSupport.getInstance().getIcon("test/splash.png"); 
    }
    
    private BufferedImage getBufferedImage() {
        if (bufferedImage == null) {
            Image image = (iicon == null? null: iicon.getImage()); 
            if (image == null) return null; 
            
            bufferedImage = new BufferedImage(iicon.getIconWidth(), iicon.getIconHeight(), BufferedImage.TYPE_INT_ARGB); 
            Graphics2D g2 = bufferedImage.createGraphics(); 
            g2.drawImage(image, 0, 0, null); 
        }
        return bufferedImage;
    }

    public void paint(Graphics g) {
        super.paint(g);         
    }

    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
        
        int width = getWidth();
        int height = getHeight();
        // gets the current clipping area
        Rectangle clip = g.getClipBounds();
        g.clearRect(0, 0, clip.width, clip.height); 
        
        // paint the image
        BufferedImage bi = getBufferedImage(); 
        if (bi != null) g.drawImage(bi, 0, 0, null); 

        Color oldColor = g.getColor();        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
        // sets a 80% translucent composite
        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.63f);
        Composite oldComposite = g2.getComposite();
        g2.setComposite(alpha);
        
        // fills the background
        g2.setColor(Color.BLACK);
        g2.fillRect(clip.x, clip.y, clip.width, clip.height);                
        g2.setComposite(oldComposite);
        g2.setColor(oldColor); 
        
        if (bi != null) {
            int cx = 0;
            int cy = 0;
            int cw = cropSize.width * 72;
            int ch = cropSize.height * 72;
            if (rectOver == null) {
                cx = Math.max((width-cw)/2, 0);
                cy = Math.max((height-ch)/2, 0);
                rectOver = new Rectangle(cx, cy, cw, ch); 
            } else {
                cx = rectOver.x;
                cy = rectOver.y;
            } 
            
            int iw = iicon.getIconWidth();
            int ih = iicon.getIconHeight();
            int bw = rectOver.width;
            int bh = rectOver.height;
            if (rectOver.x + rectOver.width > iw) { 
                bw = Math.max(iw - rectOver.x, 0);
            }
            if (rectOver.y + rectOver.height > ih) {
                bh = Math.max(ih - rectOver.y, 0);
            } 
            
            Graphics g3 = (Graphics2D) g2.create(cx, cy, cw, ch); 
            Image img3 = bi.getSubimage(rectOver.x, rectOver.y, bw, bh); 
            g3.drawImage(img3, 0, 0, null); 
            g3.setColor(Color.WHITE); 
            g3.drawRect(0, 0, cw-1, ch-1); 
            g3.dispose(); 
        } 
    }
    
    
    // <editor-fold defaultstate="collapsed" desc=" MouseSupport ">
    
    private class MouseSupport implements MouseListener, MouseMotionListener 
    {
        ImageCanvas root = ImageCanvas.this;
        
        private Point origPoint;
        private Point basePoint;
        
        public void mouseEntered(MouseEvent e) {}        
        public void mousePressed(MouseEvent e) {
            if (hasIntersect(e)) { 
                basePoint = e.getPoint();
                origPoint = new Point(rectOver.x, rectOver.y);
            } else {
                basePoint = null;
                origPoint = null;
            }
        }
        
        public void mouseReleased(MouseEvent e) {
            basePoint = null;
            origPoint = null;
        }
        
        public void mouseClicked(MouseEvent e) {} 
        public void mouseExited(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
        
        public void mouseDragged(MouseEvent e) {
            if (basePoint == null) return;
            
            int pw = root.getWidth();
            int ph = root.getHeight();
            int nx=0, ny=0;
            Point p = e.getPoint();
            if (p.x >= basePoint.x) { 
                nx = p.x-basePoint.x;  
                if (origPoint.x + nx  + rectOver.width < pw) { 
                    rectOver.x = origPoint.x + nx; 
                } 
            } else {
                nx = basePoint.x - p.x;
                rectOver.x = Math.max(origPoint.x - nx, 0); 
            }         
            
            if (p.y >= basePoint.y) {
                ny = p.y - basePoint.y; 
                if (origPoint.y + ny + rectOver.height < ph) { 
                    rectOver.y = origPoint.y + ny; 
                } 
            } else {
                ny = basePoint.y - p.y; 
                rectOver.y = Math.max(origPoint.y - ny, 0); 
            }
            root.repaint(); 
        }                 
        
        private boolean hasIntersect(MouseEvent e) {
            if (root.rectOver == null) return false;
            
            Point p = e.getPoint();
            return rectOver.contains(p); 
        }
    }

    // </editor-fold>    
    
}

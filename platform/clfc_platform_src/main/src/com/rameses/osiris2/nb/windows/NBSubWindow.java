package com.rameses.osiris2.nb.windows;

import com.rameses.osiris2.nb.*;
import com.rameses.platform.interfaces.ContentPane;
import com.rameses.platform.interfaces.SubWindow;
import com.rameses.platform.interfaces.SubWindowListener;
import com.rameses.platform.interfaces.ViewContext;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.openide.windows.TopComponent;

public class NBSubWindow extends TopComponent implements SubWindow, NBTopComponentSelector 
{    
    private NBPlatform nbPlatform;
    private NBMainWindow mainWindow;
    private String preferredID;
    private boolean closeable = true;
    private boolean bypassVerifyClose;
    
    private JLayeredPane contentPane;
    private JComponent glassPane;
    private Component content;
    private Component child;
    
    public NBSubWindow(NBPlatform nbPlatform, NBMainWindow mainWindow, String preferredID) {
        this.nbPlatform = nbPlatform;
        this.mainWindow = mainWindow;
        this.preferredID = preferredID;
        setLayout(new BorderLayout());
        
        contentPane = new JLayeredPane();
        contentPane.setLayout(new LayeredPaneLayout());
        
        glassPane = new WidgetGlassPane();
        contentPane.add(glassPane, JLayeredPane.MODAL_LAYER);
        
        super.addImpl(contentPane, null, -1);
        
        //attach events
        ActionListener closeAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) { closeWindow(); }
        };
        registerKeyboardAction(closeAction, KeyStroke.getKeyStroke("ctrl W"), JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        contentPane.addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                if (e.getChild() instanceof ContentPane.View) {
                    child = e.getChild();
                }
            }
            public void componentRemoved(ContainerEvent e) {
                if (child != null && child.equals(e.getChild())) {
                    child = null; 
                }
            }
        });        
    }
    
    protected void addImpl(Component comp, Object constraints, int index) {
        content = comp;
        contentPane.add(content);
    }
    
    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }
    
    public JComponent getGlassPane() {
        return glassPane;
    }
    
    protected String preferredID() { return preferredID; }
    
    public int getPersistenceType() { return PERSISTENCE_NEVER; }
    
    public void open() {
        super.open();
        
        if (getViewContext() == null) return;
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getViewContext().display();
            }
        });
    }
    
    public boolean canClose() {
        if (!closeable) return false;
        if (!bypassVerifyClose) {
            ViewContext vc = getViewContext();
            if (vc != null) return vc.close();
        }
        return true;
    }
    
    protected void componentClosed() {
        super.componentClosed();
        nbPlatform.unregisterWindow(preferredID);
    }
    
    public void forceClose() {
        bypassVerifyClose = true;
        close();
    }
    
    private ViewContext getViewContext() {
        Component c = content;
        if (c instanceof ViewContext) {
            ViewContext vc = (ViewContext) c;
            if (vc.getSubWindow() == null) {
                vc.setSubWindow(this);
            } 
            return vc;
        } 
        return null; 
    }
    
    // <editor-fold defaultstate="collapsed" desc=" NBTopComponentSelector implementation "> 
    
    private NBPlatform nbplatform;
    
    public void setNBPlatform(NBPlatform nbplatform) {
        this.nbplatform = nbplatform; 
    }
    
    public void showInfo() {
        if (child instanceof ContentPane.View) {
            ((ContentPane.View) child).showInfo(); 
        } 
    }
    
    protected void componentActivated() {
        super.componentActivated(); 
        if (nbplatform != null) nbplatform.setSelectedTopComponent(this);
    }

    protected void componentDeactivated() {         
        super.componentDeactivated(); 
        if (nbplatform != null) nbplatform.setSelectedTopComponent(null);
    }    
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc=" SubWindow implementation ">
    
    private SubWindowListener listener;
    
    public void setListener(SubWindowListener listener) {
        this.listener = listener;
    }
    
    public String getName() { return preferredID(); }     
    
    public String getTitle() { 
        return getDisplayName(); 
    } 
    public void setTitle(String title) {
        setDisplayName(title);
    } 
    
    public void closeWindow() {
        if (canClose()) {
            try {
                bypassVerifyClose = true;
                close();
            } catch(RuntimeException rex) {
                throw rex;
            } catch(Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            } finally {
                bypassVerifyClose = false;
            }
        }
    }
    
    public void update(Map attrs) {
        if (attrs == null || attrs.isEmpty()) return;
        
        Object oid = attrs.remove("id");
        if (oid != null) this.preferredID = oid.toString();
        
        Object otitle = attrs.remove("title");
        if (otitle != null) setTitle(otitle.toString());
    }    
    
    // </editor-fold>
    
}

/*
 * NBPopup.java
 *
 * Created on July 17, 2010, 3:07 PM
 * @author jaycverg
 */

package com.rameses.osiris2.nb.windows;

import com.rameses.osiris2.nb.*;
import com.rameses.platform.interfaces.SubWindow;
import com.rameses.platform.interfaces.SubWindowListener;
import com.rameses.platform.interfaces.ViewContext;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class NBPopup extends JDialog implements SubWindow  
{    
    private NBPlatform platform;
    private ViewContext viewContext;
    private JComponent source;
    
    private boolean canClose = true;
    private String id;
        
    public NBPopup(NBPlatform platform, Frame parent, String id) {
        super(parent);
        init(platform, id);
    }
    
    public NBPopup(NBPlatform platform, Dialog parent, String id) {
        super(parent);
        init(platform, id);
    }
    
    private void init(NBPlatform platform, String id) {
        this.platform = platform;
        this.id = id;
        addWindowListener(new NBPopupAdapter());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        EscAction esc = new EscAction();
        getRootPane().registerKeyboardAction(esc, esc.keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        if (contentPane instanceof ViewContext) {
            viewContext = (ViewContext) contentPane;
        }
    } 
    
    public JComponent getSource() { return source; } 
    public void setSource(JComponent source) { this.source = source; }
    
    public boolean isCanClose() { return canClose; }    
    public void setCanClose(boolean canClose) {
        this.canClose = canClose;
    }    
    
    // <editor-fold defaultstate="collapsed" desc=" SubWindow implementation ">
    
    public void setListener(SubWindowListener listener) {
    }
    
    public String getName() { return id; }     
    
    public String getTitle() { 
        return super.getTitle(); 
    } 
    public void setTitle(String title) {
        super.setTitle(title); 
    } 
    
    public void closeWindow() {
        if (!isCanClose()) return; 
        if (viewContext != null && !viewContext.close()) return;
        
        dispose();
        platform.unregisterWindow(id);        
        //notify others
        getContentPane().firePropertyChange("Window.close", 0L, 1L); 
        //
        JComponent source = getSource();
        if (source != null) source.firePropertyChange("Window.close", false, true);        
    }
    
    public void update(Map windowAttributes) {
        if (windowAttributes == null || windowAttributes.isEmpty()) return;

        Object otitle = windowAttributes.remove("title");
        if (otitle != null) setTitle(otitle.toString());
        
        Object oid = windowAttributes.remove("id");
        if (oid != null) {
            String newId = oid.toString();
            String oldId = getName();
            if (newId != null && oldId != null && !newId.equals(oldId)) {
                platform.unregisterWindow(oldId);
                this.id = newId;
                platform.registerWindow(newId, this); 
            }
        }
    }    
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc=" NBPopupAdapter ">
    
    private class NBPopupAdapter extends WindowAdapter 
    {
        NBPopup root = NBPopup.this;
        
        public void windowClosing(WindowEvent e) {
            closeWindow();
        }

        public void windowOpened(WindowEvent e) {
            if (viewContext != null) {
                viewContext.display();
                viewContext.setSubWindow(root);
            }
        }
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc=" EscAction ">
    
    private class EscAction implements ActionListener 
    {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        
        public void actionPerformed(ActionEvent e) {
            closeWindow();
        }
    }
    
    // </editor-fold>   
}

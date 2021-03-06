/*
 * TestTextPanePage.java
 *
 * Created on August 27, 2013, 11:05 AM
 */

package test.pane;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author  compaq
 */
public class TestHtmlPage extends javax.swing.JPanel {
    
    /** Creates new form TestTextPanePage */
    public TestHtmlPage() {
        initComponents();
        
        HTMLEditorKit kit = new HTMLEditorKit(); 
        StyleSheet css = kit.getStyleSheet(); 
        css.addRule("A {text-decoration:none;}");
        css.addRule("body {font-family:Tahoma;} ");
        css.addRule("a:hover {text-decoration:underline;} ");
        xHtmlView1.setEditorKit(kit);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        xHtmlView1 = new com.rameses.rcp.control.XHtmlView();

        xHtmlView1.setName("htmlview");
        jScrollPane1.setViewportView(xHtmlView1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private com.rameses.rcp.control.XHtmlView xHtmlView1;
    // End of variables declaration//GEN-END:variables
    
}

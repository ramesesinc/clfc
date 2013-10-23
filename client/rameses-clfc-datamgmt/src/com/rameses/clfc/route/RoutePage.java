/*
 * RoutePage.java
 *
 * Created on September 11, 2013, 10:38 AM
 */

package com.rameses.clfc.route;

import com.rameses.osiris2.themes.FormPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author  Rameses
 */
@StyleSheet
@Template(FormPage.class)
public class RoutePage extends javax.swing.JPanel 
{    
    public RoutePage() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xTextField2 = new com.rameses.rcp.control.XTextField();
        xTextField3 = new com.rameses.rcp.control.XTextField();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setFont(new java.awt.Font("Tahoma", 1, 11));
        xTitledBorder1.setTitle(" Route Information ");
        xFormPanel1.setBorder(xTitledBorder1);
        xFormPanel1.setPadding(new java.awt.Insets(10, 15, 10, 20));
        com.rameses.rcp.control.border.XLineBorder xLineBorder1 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder1.setLineColor(new java.awt.Color(150, 150, 150));
        xLabel1.setBorder(xLineBorder1);
        xLabel1.setCaption("Code");
        xLabel1.setExpression("#{entity.code}");
        xLabel1.setFontStyle("font-size:12;font-weight:bold;");
        xLabel1.setForeground(new java.awt.Color(50, 50, 50));
        xLabel1.setOpaque(true);
        xLabel1.setPreferredSize(new java.awt.Dimension(200, 20));
        xFormPanel1.add(xLabel1);

        xTextField2.setCaption("Description");
        xTextField2.setName("entity.description");
        xTextField2.setPreferredSize(new java.awt.Dimension(400, 20));
        xTextField2.setRequired(true);
        xFormPanel1.add(xTextField2);

        xTextField3.setCaption("Area");
        xTextField3.setName("entity.area");
        xTextField3.setPreferredSize(new java.awt.Dimension(400, 20));
        xTextField3.setRequired(true);
        xFormPanel1.add(xTextField3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XTextField xTextField2;
    private com.rameses.rcp.control.XTextField xTextField3;
    // End of variables declaration//GEN-END:variables
    
}

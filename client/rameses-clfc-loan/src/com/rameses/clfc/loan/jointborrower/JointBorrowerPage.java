/*
 * LoanAppPrincipalBorrowerPage.java
 *
 * Created on September 9, 2013, 12:05 PM
 */

package com.rameses.clfc.loan.jointborrower;

import com.rameses.osiris2.themes.OKCancelPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author  wflores
 */
@StyleSheet
@Template(OKCancelPage.class)
public class JointBorrowerPage extends javax.swing.JPanel {
    
    public JointBorrowerPage() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        xTabbedPane1 = new com.rameses.rcp.control.XTabbedPane();

        xTabbedPane1.setName("tabHandler");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(xTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
        );
    }// </editor-fold>
    
    
    // Variables declaration - do not modify
    private com.rameses.rcp.control.XTabbedPane xTabbedPane1;
    // End of variables declaration
    
}

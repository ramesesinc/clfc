/*
 * VoidRequestPage.java
 *
 * Created on December 27, 2013, 5:08 PM
 */

package com.rameses.clfc.loan.payment.voidrequest;

import com.rameses.osiris2.themes.FormPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author  louie
 */

@StyleSheet
@Template(FormPage.class)
public class PaymentVoidRequestPage extends javax.swing.JPanel {
    
    /** Creates new form VoidRequestPage */
    public PaymentVoidRequestPage() {
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
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xLabel4 = new com.rameses.rcp.control.XLabel();
        xLabel5 = new com.rameses.rcp.control.XLabel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xDecimalField1 = new com.rameses.rcp.control.XDecimalField();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("General Information");
        xFormPanel1.setBorder(xTitledBorder1);
        com.rameses.rcp.control.border.XLineBorder xLineBorder1 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder1.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel2.setBorder(xLineBorder1);
        xLabel2.setCaption("App. No.");
        xLabel2.setExpression("#{entity.loanapp.appno}");
        xLabel2.setPreferredSize(new java.awt.Dimension(120, 16));
        xFormPanel1.add(xLabel2);

        com.rameses.rcp.control.border.XLineBorder xLineBorder2 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder2.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel3.setBorder(xLineBorder2);
        xLabel3.setCaption("Borrower");
        xLabel3.setExpression("#{entity.borrower.name}");
        xLabel3.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel1.add(xLabel3);

        com.rameses.rcp.control.border.XLineBorder xLineBorder3 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder3.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel4.setBorder(xLineBorder3);
        xLabel4.setCaption("Address");
        xLabel4.setExpression("#{entity.borrower.address}");
        xLabel4.setPreferredSize(new java.awt.Dimension(0, 18));
        xFormPanel1.add(xLabel4);

        com.rameses.rcp.control.border.XLineBorder xLineBorder4 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder4.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel5.setBorder(xLineBorder4);
        xLabel5.setCaption("Route");
        xLabel5.setExpression("#{entity.route.description} - #{entity.route.area}");
        xLabel5.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel1.add(xLabel5);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle("Void Request Information");
        xFormPanel2.setBorder(xTitledBorder2);
        com.rameses.rcp.control.border.XLineBorder xLineBorder5 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder5.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel1.setBorder(xLineBorder5);
        xLabel1.setCaption("Requested By");
        xLabel1.setExpression("#{entity.collector.name}");
        xLabel1.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel2.add(xLabel1);

        xDecimalField1.setCaption("Amount");
        xDecimalField1.setName("entity.payamount");
        xDecimalField1.setReadonly(true);
        xFormPanel2.add(xDecimalField1);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 60));
        xTextArea1.setCaption("Reason");
        xTextArea1.setName("entity.reason");
        xTextArea1.setRequired(true);
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel2.add(jScrollPane1);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(0, 60));
        xTextArea2.setCaption("Remarks");
        xTextArea2.setName("entity.approvedremarks");
        xTextArea2.setRequired(true);
        jScrollPane2.setViewportView(xTextArea2);

        xFormPanel2.add(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xFormPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.rameses.rcp.control.XDecimalField xDecimalField1;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLabel xLabel4;
    private com.rameses.rcp.control.XLabel xLabel5;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    // End of variables declaration//GEN-END:variables
    
}

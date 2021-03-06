/*
 * OnlineCollectionPage.java
 *
 * Created on February 3, 2014, 11:40 AM
 */

package com.rameses.clfc.loan.payment;

import com.rameses.osiris2.themes.FormPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author  louie
 */

@StyleSheet
@Template(FormPage.class)
public class PaymentPage extends javax.swing.JPanel {
    
    /** Creates new form OnlineCollectionPage */
    public PaymentPage() {
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
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xLabel4 = new com.rameses.rcp.control.XLabel();
        jPanel1 = new javax.swing.JPanel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xLabel5 = new com.rameses.rcp.control.XLabel();
        xDecimalField7 = new com.rameses.rcp.control.XDecimalField();
        xDecimalField5 = new com.rameses.rcp.control.XDecimalField();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xDecimalField1 = new com.rameses.rcp.control.XDecimalField();
        xDecimalField2 = new com.rameses.rcp.control.XDecimalField();
        xDecimalField3 = new com.rameses.rcp.control.XDecimalField();
        xDecimalField4 = new com.rameses.rcp.control.XDecimalField();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xLabel6 = new com.rameses.rcp.control.XLabel();
        xDateField2 = new com.rameses.rcp.control.XDateField();
        xDecimalField6 = new com.rameses.rcp.control.XDecimalField();
        xTextField1 = new com.rameses.rcp.control.XTextField();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Borrower Information");
        xFormPanel1.setBorder(xTitledBorder1);
        com.rameses.rcp.control.border.XLineBorder xLineBorder1 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder1.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel1.setBorder(xLineBorder1);
        xLabel1.setCaption("App. No.");
        xLabel1.setExpression("#{entity.loanapp.appno}");
        xLabel1.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel1.add(xLabel1);

        com.rameses.rcp.control.border.XLineBorder xLineBorder2 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder2.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel2.setBorder(xLineBorder2);
        xLabel2.setCaption("Borrower");
        xLabel2.setExpression("#{entity.borrower.name}");
        xLabel2.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel1.add(xLabel2);

        com.rameses.rcp.control.border.XLineBorder xLineBorder3 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder3.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel3.setBorder(xLineBorder3);
        xLabel3.setCaption("Address");
        xLabel3.setExpression("#{entity.borrower.address}");
        xLabel3.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel1.add(xLabel3);

        com.rameses.rcp.control.border.XLineBorder xLineBorder4 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder4.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel4.setBorder(xLineBorder4);
        xLabel4.setCaption("Route");
        xLabel4.setExpression("#{entity.route.description} - #{entity.route.area}");
        xLabel4.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel1.add(xLabel4);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle("Ledger Information");
        jPanel1.setBorder(xTitledBorder2);

        xDateField1.setCaption("Maturity Date");
        xDateField1.setCaptionWidth(95);
        xDateField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDateField1.setEnabled(false);
        xDateField1.setName("bill.dtmatured");
        xDateField1.setOutputFormat("MMM-dd-yyyy");
        xDateField1.setPreferredSize(new java.awt.Dimension(0, 20));
        xDateField1.setReadonly(true);
        xFormPanel2.add(xDateField1);

        com.rameses.rcp.control.border.XLineBorder xLineBorder5 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder5.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel5.setBorder(xLineBorder5);
        xLabel5.setCaption("Term");
        xLabel5.setCaptionWidth(95);
        xLabel5.setExpression("#{bill.term}");
        xLabel5.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel2.add(xLabel5);

        xDecimalField7.setCaption("Payment Sched.");
        xDecimalField7.setCaptionWidth(95);
        xDecimalField7.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField7.setEnabled(false);
        xDecimalField7.setName("bill.dailydue");
        xDecimalField7.setPreferredSize(new java.awt.Dimension(0, 20));
        xFormPanel2.add(xDecimalField7);

        xDecimalField5.setCaption("Balance");
        xDecimalField5.setCaptionWidth(95);
        xDecimalField5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField5.setEnabled(false);
        xDecimalField5.setName("bill.balance");
        xDecimalField5.setPreferredSize(new java.awt.Dimension(0, 20));
        xDecimalField5.setReadonly(true);
        xFormPanel2.add(xDecimalField5);

        xDecimalField1.setCaption("Interest");
        xDecimalField1.setCaptionWidth(100);
        xDecimalField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField1.setEnabled(false);
        xDecimalField1.setName("bill.interest");
        xDecimalField1.setPreferredSize(new java.awt.Dimension(0, 20));
        xDecimalField1.setReadonly(true);
        xFormPanel3.add(xDecimalField1);

        xDecimalField2.setCaption("Overpayment");
        xDecimalField2.setCaptionWidth(100);
        xDecimalField2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField2.setEnabled(false);
        xDecimalField2.setName("bill.overpayment");
        xDecimalField2.setPreferredSize(new java.awt.Dimension(0, 20));
        xDecimalField2.setReadonly(true);
        xFormPanel3.add(xDecimalField2);

        xDecimalField3.setCaption("Overdue Penalty");
        xDecimalField3.setCaptionWidth(100);
        xDecimalField3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField3.setEnabled(false);
        xDecimalField3.setName("bill.overduepenalty");
        xDecimalField3.setPreferredSize(new java.awt.Dimension(0, 20));
        xDecimalField3.setReadonly(true);
        xFormPanel3.add(xDecimalField3);

        xDecimalField4.setCaption("Amount Due");
        xDecimalField4.setCaptionWidth(100);
        xDecimalField4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDecimalField4.setEnabled(false);
        xDecimalField4.setName("bill.amountdue");
        xDecimalField4.setPreferredSize(new java.awt.Dimension(0, 20));
        xDecimalField4.setReadonly(true);
        xFormPanel3.add(xDecimalField4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(xFormPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(xFormPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addContainerGap())
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder3 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder3.setTitle("Payment Information");
        xFormPanel4.setBorder(xTitledBorder3);
        com.rameses.rcp.control.border.XLineBorder xLineBorder6 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder6.setLineColor(new java.awt.Color(180, 180, 180));
        xLabel6.setBorder(xLineBorder6);
        xLabel6.setCaption("Ref. No.");
        xLabel6.setExpression("#{entity.refno}");
        xLabel6.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel4.add(xLabel6);

        xDateField2.setCaption("Txndate");
        xDateField2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        xDateField2.setEnabled(false);
        xDateField2.setName("entity.txndate");
        xDateField2.setOutputFormat("MMM-dd-yyyy");
        xDateField2.setPreferredSize(new java.awt.Dimension(150, 20));
        xDateField2.setReadonly(true);
        xFormPanel4.add(xDateField2);

        xDecimalField6.setCaption("Amount");
        xDecimalField6.setName("entity.amount");
        xDecimalField6.setPreferredSize(new java.awt.Dimension(150, 20));
        xDecimalField6.setRequired(true);
        xFormPanel4.add(xDecimalField6);

        xTextField1.setCaption("Paid By");
        xTextField1.setName("entity.paidby");
        xTextField1.setPreferredSize(new java.awt.Dimension(270, 20));
        xTextField1.setRequired(true);
        xFormPanel4.add(xTextField1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(xFormPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xFormPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XDecimalField xDecimalField1;
    private com.rameses.rcp.control.XDecimalField xDecimalField2;
    private com.rameses.rcp.control.XDecimalField xDecimalField3;
    private com.rameses.rcp.control.XDecimalField xDecimalField4;
    private com.rameses.rcp.control.XDecimalField xDecimalField5;
    private com.rameses.rcp.control.XDecimalField xDecimalField6;
    private com.rameses.rcp.control.XDecimalField xDecimalField7;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLabel xLabel4;
    private com.rameses.rcp.control.XLabel xLabel5;
    private com.rameses.rcp.control.XLabel xLabel6;
    private com.rameses.rcp.control.XTextField xTextField1;
    // End of variables declaration//GEN-END:variables
    
}

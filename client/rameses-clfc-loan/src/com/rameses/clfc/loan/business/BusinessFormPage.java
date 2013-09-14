/*
 * BussinessFormPage2.java
 *
 * Created on September 2, 2013, 10:48 AM
 */

package com.rameses.clfc.loan.business;

import com.rameses.osiris2.themes.OKCancelPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;
import java.math.BigDecimal;

/**
 *
 * @author  Rameses
 */
@StyleSheet
@Template(OKCancelPage.class)
public class BusinessFormPage extends javax.swing.JPanel {
    
    /** Creates new form BussinessFormPage2 */
    public BusinessFormPage() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xNumberField3 = new com.rameses.rcp.control.XNumberField();
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xTextField1 = new com.rameses.rcp.control.XTextField();
        xTextField2 = new com.rameses.rcp.control.XTextField();
        xFormPanel12 = new com.rameses.rcp.control.XFormPanel();
        xComboBox9 = new com.rameses.rcp.control.XComboBox();
        xFormPanel13 = new com.rameses.rcp.control.XFormPanel();
        xComboBox10 = new com.rameses.rcp.control.XComboBox();
        xFormPanel10 = new com.rameses.rcp.control.XFormPanel();
        xNumberField2 = new com.rameses.rcp.control.XNumberField();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        xTextArea4 = new com.rameses.rcp.control.XTextArea();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xFormPanel11 = new com.rameses.rcp.control.XFormPanel();
        xComboBox8 = new com.rameses.rcp.control.XComboBox();
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        xTextArea3 = new com.rameses.rcp.control.XTextArea();
        xTextField5 = new com.rameses.rcp.control.XTextField();

        jPanel1.setLayout(null);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Business Information");
        jPanel1.setBorder(xTitledBorder1);

        xFormPanel3.setPadding(new java.awt.Insets(4, 0, 0, 0));
        xComboBox1.setCaption("Kind of Business");
        xComboBox1.setCaptionWidth(125);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("value");
        xComboBox1.setItems("kindTypes");
        xComboBox1.setName("entity.kind");
        xComboBox1.setPreferredSize(new java.awt.Dimension(120, 19));
        xComboBox1.setRequired(true);
        xFormPanel3.add(xComboBox1);

        jPanel1.add(xFormPanel3);
        xFormPanel3.setBounds(10, 20, 280, 30);

        xFormPanel2.setPadding(new java.awt.Insets(4, 5, 0, 0));
        xNumberField3.setCaption("Stall Size/P.O. Size(in mtrs.)");
        xNumberField3.setCaptionWidth(160);
        xNumberField3.setFieldType(BigDecimal.class);
        xNumberField3.setName("entity.stallsize");
        xNumberField3.setPreferredSize(new java.awt.Dimension(0, 20));
        xNumberField3.setRequired(true);
        xFormPanel2.add(xNumberField3);

        jPanel1.add(xFormPanel2);
        xFormPanel2.setBounds(290, 20, 310, 30);

        xFormPanel1.setPadding(new java.awt.Insets(5, 0, 0, 0));
        xTextField1.setCaption("Firm/Trade Name");
        xTextField1.setCaptionWidth(125);
        xTextField1.setName("entity.tradename");
        xTextField1.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField1.setRequired(true);
        xFormPanel1.add(xTextField1);

        xTextField2.setCaption("Address");
        xTextField2.setCaptionWidth(125);
        xTextField2.setName("entity.address");
        xTextField2.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField2.setRequired(true);
        xFormPanel1.add(xTextField2);

        jPanel1.add(xFormPanel1);
        xFormPanel1.setBounds(10, 40, 590, 50);

        xFormPanel12.setPadding(new java.awt.Insets(7, 0, 0, 0));
        xComboBox9.setCaption("Occupancy");
        xComboBox9.setCaptionWidth(125);
        xComboBox9.setExpression("#{item.value}");
        xComboBox9.setItemKey("value");
        xComboBox9.setItems("occupancyTypes");
        xComboBox9.setName("entity.occupancy.type");
        xComboBox9.setPreferredSize(new java.awt.Dimension(120, 19));
        xComboBox9.setRequired(true);
        xFormPanel12.add(xComboBox9);

        jPanel1.add(xFormPanel12);
        xFormPanel12.setBounds(10, 80, 280, 30);

        xFormPanel13.setPadding(new java.awt.Insets(8, 0, 0, 0));
        xComboBox10.setCaption("Rent Type");
        xComboBox10.setCaptionWidth(125);
        xComboBox10.setDepends(new String[] {"entity.occupancy.type"});
        xComboBox10.setExpression("#{item.value}");
        xComboBox10.setItemKey("value");
        xComboBox10.setItems("rentTypes");
        xComboBox10.setName("entity.occupancy.renttype");
        xComboBox10.setPreferredSize(new java.awt.Dimension(120, 19));
        xFormPanel13.add(xComboBox10);

        jPanel1.add(xFormPanel13);
        xFormPanel13.setBounds(10, 100, 280, 30);

        xFormPanel10.setPadding(new java.awt.Insets(8, 5, 0, 0));
        xNumberField2.setCaption("Rent Amount");
        xNumberField2.setCaptionWidth(160);
        xNumberField2.setDepends(new String[] {"entity.occupancy.type"});
        xNumberField2.setFieldType(BigDecimal.class);
        xNumberField2.setName("entity.occupancy.rentamount");
        xNumberField2.setPattern("#,##0.00");
        xNumberField2.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel10.add(xNumberField2);

        jPanel1.add(xFormPanel10);
        xFormPanel10.setBounds(290, 100, 310, 30);

        xFormPanel8.setPadding(new java.awt.Insets(0, 0, 0, 0));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(0, 40));
        xTextArea4.setCaption("Remarks");
        xTextArea4.setCaptionWidth(125);
        xTextArea4.setName("entity.occupancy.remarks");
        xTextArea4.setPreferredSize(new java.awt.Dimension(100, 38));
        xTextArea4.setRequired(true);
        xTextArea4.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane4.setViewportView(xTextArea4);

        xFormPanel8.add(jScrollPane4);

        jPanel1.add(xFormPanel8);
        xFormPanel8.setBounds(10, 130, 590, 40);

        xFormPanel7.setCellspacing(40);
        xFormPanel7.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel7.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xDateField1.setCaption("Business Started");
        xDateField1.setCaptionWidth(125);
        xDateField1.setName("entity.dtstarted");
        xDateField1.setPreferredSize(new java.awt.Dimension(120, 19));
        xDateField1.setRequired(true);
        xFormPanel7.add(xDateField1);

        xTextField10.setCaption("Estimated Daily Sales");
        xTextField10.setCaptionWidth(160);
        xTextField10.setName("entity.avgsales");
        xTextField10.setPreferredSize(new java.awt.Dimension(120, 19));
        xTextField10.setRequired(true);
        xFormPanel7.add(xTextField10);

        jPanel1.add(xFormPanel7);
        xFormPanel7.setBounds(10, 170, 590, 30);

        xFormPanel11.setCellspacing(10);
        xFormPanel11.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel11.setPadding(new java.awt.Insets(5, 0, 0, 0));
        xComboBox8.setCaption("Ownership");
        xComboBox8.setCaptionWidth(125);
        xComboBox8.setExpression("#{item.value}");
        xComboBox8.setItemKey("value");
        xComboBox8.setItems("ownershipTypes");
        xComboBox8.setName("entity.ownership");
        xComboBox8.setPreferredSize(new java.awt.Dimension(150, 19));
        xComboBox8.setRequired(true);
        xFormPanel11.add(xComboBox8);

        xNumberField1.setCaption("Initial Invested");
        xNumberField1.setCaptionWidth(160);
        xNumberField1.setFieldType(BigDecimal.class);
        xNumberField1.setName("entity.capital");
        xNumberField1.setPattern("#,##0.00");
        xNumberField1.setPreferredSize(new java.awt.Dimension(130, 19));
        xNumberField1.setRequired(true);
        xFormPanel11.add(xNumberField1);

        jPanel1.add(xFormPanel11);
        xFormPanel11.setBounds(10, 190, 590, 30);

        xFormPanel5.setPadding(new java.awt.Insets(7, 0, 0, 0));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(0, 40));
        xTextArea3.setCaption("Business Hours");
        xTextArea3.setCaptionWidth(125);
        xTextArea3.setName("entity.officehours");
        xTextArea3.setPreferredSize(new java.awt.Dimension(100, 38));
        xTextArea3.setRequired(true);
        xTextArea3.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane3.setViewportView(xTextArea3);

        xFormPanel5.add(jScrollPane3);

        xTextField5.setCaption("Tel. No.");
        xTextField5.setCaptionWidth(125);
        xTextField5.setName("entity.contactno");
        xTextField5.setPreferredSize(new java.awt.Dimension(150, 19));
        xFormPanel5.add(xTextField5);

        jPanel1.add(xFormPanel5);
        xFormPanel5.setBounds(10, 210, 590, 80);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox10;
    private com.rameses.rcp.control.XComboBox xComboBox8;
    private com.rameses.rcp.control.XComboBox xComboBox9;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel10;
    private com.rameses.rcp.control.XFormPanel xFormPanel11;
    private com.rameses.rcp.control.XFormPanel xFormPanel12;
    private com.rameses.rcp.control.XFormPanel xFormPanel13;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
    private com.rameses.rcp.control.XFormPanel xFormPanel7;
    private com.rameses.rcp.control.XFormPanel xFormPanel8;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XNumberField xNumberField2;
    private com.rameses.rcp.control.XNumberField xNumberField3;
    private com.rameses.rcp.control.XTextArea xTextArea3;
    private com.rameses.rcp.control.XTextArea xTextArea4;
    private com.rameses.rcp.control.XTextField xTextField1;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField2;
    private com.rameses.rcp.control.XTextField xTextField5;
    // End of variables declaration//GEN-END:variables
    
}

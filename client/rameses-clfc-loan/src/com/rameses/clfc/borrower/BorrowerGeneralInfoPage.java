/*
 * BorrowerGeneralInfoPage.java
 *
 * Created on September 9, 2013, 9:25 PM
 */

package com.rameses.clfc.borrower;

import com.rameses.rcp.ui.annotations.StyleSheet;
import java.math.BigDecimal;

/**
 *
 * @author  wflores
 */
@StyleSheet
public class BorrowerGeneralInfoPage extends javax.swing.JPanel {
    
    public BorrowerGeneralInfoPage() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xLookupField1 = new com.rameses.rcp.control.XLookupField();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        xTextField9 = new com.rameses.rcp.control.XTextField();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xDateField2 = new com.rameses.rcp.control.XDateField();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel6 = new com.rameses.rcp.control.XFormPanel();
        xComboBox3 = new com.rameses.rcp.control.XComboBox();
        xDateField4 = new com.rameses.rcp.control.XDateField();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        xComboBox4 = new com.rameses.rcp.control.XComboBox();
        xNumberField2 = new com.rameses.rcp.control.XNumberField();
        jScrollPane3 = new javax.swing.JScrollPane();
        xTextArea3 = new com.rameses.rcp.control.XTextArea();
        xFormPanel9 = new com.rameses.rcp.control.XFormPanel();
        xTextField1 = new com.rameses.rcp.control.XTextField();

        com.rameses.rcp.control.border.XEtchedBorder xEtchedBorder1 = new com.rameses.rcp.control.border.XEtchedBorder();
        xEtchedBorder1.setHideLeft(true);
        xEtchedBorder1.setHideRight(true);
        xEtchedBorder1.setHideTop(true);
        xLabel1.setBorder(xEtchedBorder1);
        xLabel1.setFontStyle("font-weight:bold;");
        xLabel1.setForeground(new java.awt.Color(80, 80, 80));
        xLabel1.setText("Personal Information");

        xLookupField1.setCaption("Name");
        xLookupField1.setCaptionWidth(100);
        xLookupField1.setExpression("#{entity.name}");
        xLookupField1.setHandler("lookupBorrower");
        xLookupField1.setName("borrower");
        xLookupField1.setPreferredSize(new java.awt.Dimension(150, 20));
        xLookupField1.setRequired(true);
        xFormPanel1.add(xLookupField1);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(150, 70));
        xTextArea1.setBackground(new java.awt.Color(250, 250, 250));
        xTextArea1.setCaption("Address");
        xTextArea1.setCaptionWidth(100);
        xTextArea1.setDepends(new String[] {"borrower"});
        xTextArea1.setEnabled(false);
        xTextArea1.setName("entity.address");
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel1.add(jScrollPane1);

        xTextField9.setBackground(new java.awt.Color(250, 250, 250));
        xTextField9.setCaption("Marital Status");
        xTextField9.setCaptionWidth(100);
        xTextField9.setDepends(new String[] {"borrower"});
        xTextField9.setEnabled(false);
        xTextField9.setName("entity.civilstatus");
        xFormPanel8.add(xTextField9);

        xTextField10.setBackground(new java.awt.Color(250, 250, 250));
        xTextField10.setCaption("Citizenship");
        xTextField10.setCaptionWidth(100);
        xTextField10.setDepends(new String[] {"borrower"});
        xTextField10.setEnabled(false);
        xTextField10.setName("entity.citizenship");
        xFormPanel8.add(xTextField10);

        xTextField11.setBackground(new java.awt.Color(250, 250, 250));
        xTextField11.setCaption("Contact No.");
        xTextField11.setCaptionWidth(100);
        xTextField11.setDepends(new String[] {"borrower"});
        xTextField11.setEnabled(false);
        xTextField11.setName("entity.contactno");
        xFormPanel8.add(xTextField11);

        xDateField1.setBackground(new java.awt.Color(250, 250, 250));
        xDateField1.setCaption("Birth Date");
        xDateField1.setCaptionWidth(100);
        xDateField1.setDepends(new String[] {"borrower"});
        xDateField1.setEnabled(false);
        xDateField1.setName("entity.birthdate");
        xFormPanel8.add(xDateField1);

        com.rameses.rcp.control.border.XEtchedBorder xEtchedBorder2 = new com.rameses.rcp.control.border.XEtchedBorder();
        xEtchedBorder2.setHideLeft(true);
        xEtchedBorder2.setHideRight(true);
        xEtchedBorder2.setHideTop(true);
        xLabel2.setBorder(xEtchedBorder2);
        xLabel2.setFontStyle("font-weight:bold;");
        xLabel2.setForeground(new java.awt.Color(80, 80, 80));
        xLabel2.setText("Residency");

        xFormPanel2.setCaption("Residency");

        xFormPanel3.setCaption("Residency");
        xFormPanel3.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel3.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel3.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel3.setShowCaption(false);
        xComboBox1.setCaption("Type");
        xComboBox1.setCaptionWidth(100);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("value");
        xComboBox1.setItems("occupancyTypes");
        xComboBox1.setName("entity.residency.type");
        xComboBox1.setPreferredSize(new java.awt.Dimension(150, 19));
        xComboBox1.setRequired(true);
        xFormPanel3.add(xComboBox1);

        xDateField2.setBackground(new java.awt.Color(250, 250, 250));
        xDateField2.setCaption("Since");
        xDateField2.setCaptionWidth(100);
        xDateField2.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField2.setName("entity.residency.since");
        xFormPanel3.add(xDateField2);

        xFormPanel2.add(xFormPanel3);

        xFormPanel4.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel4.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel4.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel4.setShowCaption(false);
        xComboBox2.setCaption("Rent Type");
        xComboBox2.setCaptionWidth(100);
        xComboBox2.setDepends(new String[] {"entity.residency.type"});
        xComboBox2.setExpression("#{item.value}");
        xComboBox2.setItemKey("value");
        xComboBox2.setItems("rentTypes");
        xComboBox2.setName("entity.residency.renttype");
        xComboBox2.setPreferredSize(new java.awt.Dimension(150, 19));
        xFormPanel4.add(xComboBox2);

        xNumberField1.setCaption("Rent Amount");
        xNumberField1.setCaptionWidth(100);
        xNumberField1.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xNumberField1.setDepends(new String[] {"entity.residency.type"});
        xNumberField1.setFieldType(BigDecimal.class);
        xNumberField1.setHint("0.00");
        xNumberField1.setName("entity.residency.rentamount");
        xNumberField1.setPattern("#,##0.00");
        xFormPanel4.add(xNumberField1);

        xFormPanel2.add(xFormPanel4);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(367, 40));
        xTextArea2.setBackground(new java.awt.Color(250, 250, 250));
        xTextArea2.setCaption("Remarks");
        xTextArea2.setCaptionWidth(100);
        xTextArea2.setName("entity.residency.remarks");
        jScrollPane2.setViewportView(xTextArea2);

        xFormPanel2.add(jScrollPane2);

        com.rameses.rcp.control.border.XEtchedBorder xEtchedBorder3 = new com.rameses.rcp.control.border.XEtchedBorder();
        xEtchedBorder3.setHideLeft(true);
        xEtchedBorder3.setHideRight(true);
        xEtchedBorder3.setHideTop(true);
        xLabel3.setBorder(xEtchedBorder3);
        xLabel3.setFontStyle("font-weight:bold;");
        xLabel3.setForeground(new java.awt.Color(80, 80, 80));
        xLabel3.setText("Lot Occupancy");

        xFormPanel5.setCaption("Lot Occupancy");

        xFormPanel6.setCaption("Lot Occupancy");
        xFormPanel6.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel6.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel6.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel6.setShowCaption(false);
        xComboBox3.setCaption("Type");
        xComboBox3.setCaptionWidth(100);
        xComboBox3.setExpression("#{item.value}");
        xComboBox3.setItemKey("value");
        xComboBox3.setItems("occupancyTypes");
        xComboBox3.setName("entity.occupancy.type");
        xComboBox3.setPreferredSize(new java.awt.Dimension(150, 19));
        xComboBox3.setRequired(true);
        xFormPanel6.add(xComboBox3);

        xDateField4.setBackground(new java.awt.Color(250, 250, 250));
        xDateField4.setCaption("Since");
        xDateField4.setCaptionWidth(100);
        xDateField4.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField4.setName("entity.occupancy.since");
        xFormPanel6.add(xDateField4);

        xFormPanel5.add(xFormPanel6);

        xFormPanel7.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel7.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel7.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel7.setShowCaption(false);
        xComboBox4.setCaption("Rent Type");
        xComboBox4.setCaptionWidth(100);
        xComboBox4.setDepends(new String[] {"entity.occupancy.type"});
        xComboBox4.setExpression("#{item.value}");
        xComboBox4.setItemKey("value");
        xComboBox4.setItems("rentTypes");
        xComboBox4.setName("entity.occupancy.renttype");
        xComboBox4.setPreferredSize(new java.awt.Dimension(150, 19));
        xFormPanel7.add(xComboBox4);

        xNumberField2.setCaption("Rent Amount");
        xNumberField2.setCaptionWidth(100);
        xNumberField2.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xNumberField2.setDepends(new String[] {"entity.occupancy.type"});
        xNumberField2.setFieldType(BigDecimal.class);
        xNumberField2.setHint("0.00");
        xNumberField2.setName("entity.occupancy.rentamount");
        xNumberField2.setPattern("#,##0.00");
        xFormPanel7.add(xNumberField2);

        xFormPanel5.add(xFormPanel7);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(367, 40));
        xTextArea3.setBackground(new java.awt.Color(250, 250, 250));
        xTextArea3.setCaption("Remarks");
        xTextArea3.setCaptionWidth(100);
        xTextArea3.setName("entity.occupancy.remarks");
        jScrollPane3.setViewportView(xTextArea3);

        xFormPanel5.add(jScrollPane3);

        xTextField1.setCaption("Relationship to the Principal Borrower");
        xTextField1.setCaptionWidth(210);
        xTextField1.setDepends(new String[] {"borrower"});
        xTextField1.setName("entity.relation");
        xTextField1.setPreferredSize(new java.awt.Dimension(0, 20));
        xTextField1.setRequired(true);
        xFormPanel9.add(xTextField1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xFormPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xFormPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addComponent(xLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xFormPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                    .addComponent(xLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addComponent(xLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xFormPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xFormPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xFormPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XComboBox xComboBox3;
    private com.rameses.rcp.control.XComboBox xComboBox4;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XDateField xDateField4;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
    private com.rameses.rcp.control.XFormPanel xFormPanel6;
    private com.rameses.rcp.control.XFormPanel xFormPanel7;
    private com.rameses.rcp.control.XFormPanel xFormPanel8;
    private com.rameses.rcp.control.XFormPanel xFormPanel9;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLookupField xLookupField1;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XNumberField xNumberField2;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    private com.rameses.rcp.control.XTextArea xTextArea3;
    private com.rameses.rcp.control.XTextField xTextField1;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField11;
    private com.rameses.rcp.control.XTextField xTextField9;
    // End of variables declaration//GEN-END:variables
    
}

/*
 * BorrowerSpouseInfoPage.java
 *
 * Created on September 9, 2013, 9:25 PM
 */

package com.rameses.clfc.borrower;

import com.rameses.rcp.ui.annotations.StyleSheet;

/**
 *
 * @author  wflores
 */
@StyleSheet
public class BorrowerSpouseInfoPage extends javax.swing.JPanel {
    
    public BorrowerSpouseInfoPage() {
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
        xLookupField1 = new com.rameses.rcp.control.XLookupField();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xTextField5 = new com.rameses.rcp.control.XTextField();
        xDateField2 = new com.rameses.rcp.control.XDateField();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xTextField6 = new com.rameses.rcp.control.XTextField();
        xDateField3 = new com.rameses.rcp.control.XDateField();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel6 = new com.rameses.rcp.control.XFormPanel();
        xTextField7 = new com.rameses.rcp.control.XTextField();
        xDateField4 = new com.rameses.rcp.control.XDateField();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        xTextField8 = new com.rameses.rcp.control.XTextField();
        xDateField5 = new com.rameses.rcp.control.XDateField();
        jScrollPane3 = new javax.swing.JScrollPane();
        xTextArea3 = new com.rameses.rcp.control.XTextArea();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        xTextField9 = new com.rameses.rcp.control.XTextField();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xDateField1 = new com.rameses.rcp.control.XDateField();

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
        xTextArea1.setEnabled(false);
        xTextArea1.setName("entity.address");
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel1.add(jScrollPane1);

        com.rameses.rcp.control.border.XEtchedBorder xEtchedBorder1 = new com.rameses.rcp.control.border.XEtchedBorder();
        xEtchedBorder1.setHideLeft(true);
        xEtchedBorder1.setHideRight(true);
        xEtchedBorder1.setHideTop(true);
        xLabel1.setBorder(xEtchedBorder1);
        xLabel1.setFontStyle("font-weight:bold;");
        xLabel1.setForeground(new java.awt.Color(80, 80, 80));
        xLabel1.setText("Personal Information");

        com.rameses.rcp.control.border.XEtchedBorder xEtchedBorder2 = new com.rameses.rcp.control.border.XEtchedBorder();
        xEtchedBorder2.setHideLeft(true);
        xEtchedBorder2.setHideRight(true);
        xEtchedBorder2.setHideTop(true);
        xLabel2.setBorder(xEtchedBorder2);
        xLabel2.setFontStyle("font-weight:bold;");
        xLabel2.setForeground(new java.awt.Color(80, 80, 80));
        xLabel2.setText("Residency");

        xFormPanel3.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel3.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel3.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel3.setShowCaption(false);
        xTextField5.setBackground(new java.awt.Color(250, 250, 250));
        xTextField5.setCaption("Type");
        xTextField5.setCaptionWidth(100);
        xTextField5.setEnabled(false);
        xTextField5.setName("entity.residency.type");
        xTextField5.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel3.add(xTextField5);

        xDateField2.setBackground(new java.awt.Color(250, 250, 250));
        xDateField2.setCaption("Since");
        xDateField2.setCaptionWidth(100);
        xDateField2.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField2.setEnabled(false);
        xDateField2.setName("entity.residency.since");
        xFormPanel3.add(xDateField2);

        xFormPanel2.add(xFormPanel3);

        xFormPanel4.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel4.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel4.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel4.setShowCaption(false);
        xTextField6.setBackground(new java.awt.Color(250, 250, 250));
        xTextField6.setCaption("Rent Type");
        xTextField6.setCaptionWidth(100);
        xTextField6.setEnabled(false);
        xTextField6.setName("entity.residency.renttype");
        xTextField6.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel4.add(xTextField6);

        xDateField3.setBackground(new java.awt.Color(250, 250, 250));
        xDateField3.setCaption("Rent Amount");
        xDateField3.setCaptionWidth(100);
        xDateField3.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField3.setEnabled(false);
        xDateField3.setName("entity.residency.since");
        xFormPanel4.add(xDateField3);

        xFormPanel2.add(xFormPanel4);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(367, 40));
        xTextArea2.setBackground(new java.awt.Color(250, 250, 250));
        xTextArea2.setCaption("Remarks");
        xTextArea2.setCaptionWidth(100);
        xTextArea2.setEnabled(false);
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

        xFormPanel6.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel6.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel6.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel6.setShowCaption(false);
        xTextField7.setBackground(new java.awt.Color(250, 250, 250));
        xTextField7.setCaption("Type");
        xTextField7.setCaptionWidth(100);
        xTextField7.setEnabled(false);
        xTextField7.setName("entity.occupancy.type");
        xTextField7.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel6.add(xTextField7);

        xDateField4.setBackground(new java.awt.Color(250, 250, 250));
        xDateField4.setCaption("Since");
        xDateField4.setCaptionWidth(100);
        xDateField4.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField4.setEnabled(false);
        xDateField4.setName("entity.occupancy.since");
        xFormPanel6.add(xDateField4);

        xFormPanel5.add(xFormPanel6);

        xFormPanel7.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel7.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel7.setPreferredSize(new java.awt.Dimension(500, 20));
        xFormPanel7.setShowCaption(false);
        xTextField8.setBackground(new java.awt.Color(250, 250, 250));
        xTextField8.setCaption("Rent Type");
        xTextField8.setCaptionWidth(100);
        xTextField8.setEnabled(false);
        xTextField8.setName("entity.occupancy.renttype");
        xTextField8.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel7.add(xTextField8);

        xDateField5.setBackground(new java.awt.Color(250, 250, 250));
        xDateField5.setCaption("Rent Amount");
        xDateField5.setCaptionWidth(100);
        xDateField5.setCellPadding(new java.awt.Insets(0, 15, 0, 0));
        xDateField5.setEnabled(false);
        xDateField5.setName("entity.occupancy.since");
        xFormPanel7.add(xDateField5);

        xFormPanel5.add(xFormPanel7);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(367, 40));
        xTextArea3.setBackground(new java.awt.Color(250, 250, 250));
        xTextArea3.setCaption("Remarks");
        xTextArea3.setCaptionWidth(100);
        xTextArea3.setEnabled(false);
        xTextArea3.setName("entity.occupancy.remarks");
        jScrollPane3.setViewportView(xTextArea3);

        xFormPanel5.add(jScrollPane3);

        xTextField9.setBackground(new java.awt.Color(250, 250, 250));
        xTextField9.setCaption("Marital Status");
        xTextField9.setCaptionWidth(100);
        xTextField9.setEnabled(false);
        xTextField9.setName("entity.civilstatus");
        xFormPanel8.add(xTextField9);

        xTextField10.setBackground(new java.awt.Color(250, 250, 250));
        xTextField10.setCaption("Citizenship");
        xTextField10.setCaptionWidth(100);
        xTextField10.setEnabled(false);
        xTextField10.setName("entity.citizenship");
        xFormPanel8.add(xTextField10);

        xTextField11.setBackground(new java.awt.Color(250, 250, 250));
        xTextField11.setCaption("Contact No.");
        xTextField11.setCaptionWidth(100);
        xTextField11.setEnabled(false);
        xTextField11.setName("entity.contactno");
        xFormPanel8.add(xTextField11);

        xDateField1.setBackground(new java.awt.Color(250, 250, 250));
        xDateField1.setCaption("Birth Date");
        xDateField1.setCaptionWidth(100);
        xDateField1.setEnabled(false);
        xDateField1.setName("entity.birthdate");
        xFormPanel8.add(xDateField1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(xLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(xFormPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(xLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(xFormPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xFormPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
                    .addComponent(xLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
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
                .addContainerGap(132, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XDateField xDateField3;
    private com.rameses.rcp.control.XDateField xDateField4;
    private com.rameses.rcp.control.XDateField xDateField5;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
    private com.rameses.rcp.control.XFormPanel xFormPanel6;
    private com.rameses.rcp.control.XFormPanel xFormPanel7;
    private com.rameses.rcp.control.XFormPanel xFormPanel8;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLookupField xLookupField1;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    private com.rameses.rcp.control.XTextArea xTextArea3;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField11;
    private com.rameses.rcp.control.XTextField xTextField5;
    private com.rameses.rcp.control.XTextField xTextField6;
    private com.rameses.rcp.control.XTextField xTextField7;
    private com.rameses.rcp.control.XTextField xTextField8;
    private com.rameses.rcp.control.XTextField xTextField9;
    // End of variables declaration//GEN-END:variables
    
}

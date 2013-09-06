/*
 * CustomerPage.java
 *
 * Created on September 3, 2013, 4:39 PM
 */

package com.rameses.clfc.customer;

/**
 *
 * @author  compaq
 */
public class CustomerPage extends javax.swing.JPanel {
    
    /** Creates new form CustomerPage */
    public CustomerPage() {
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
        xFormPanel1 = new com.rameses.rcp.control.XFormPanel();
        xTextField1 = new com.rameses.rcp.control.XTextField();
        xTextField2 = new com.rameses.rcp.control.XTextField();
        xTextField3 = new com.rameses.rcp.control.XTextField();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xTextField5 = new com.rameses.rcp.control.XTextField();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xTextField12 = new com.rameses.rcp.control.XTextField();
        xTextField13 = new com.rameses.rcp.control.XTextField();
        jPanel3 = new javax.swing.JPanel();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel6 = new com.rameses.rcp.control.XFormPanel();
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xDateField2 = new com.rameses.rcp.control.XDateField();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        xComboBox3 = new com.rameses.rcp.control.XComboBox();
        xDecimalField1 = new com.rameses.rcp.control.XDecimalField();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        jPanel4 = new javax.swing.JPanel();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        xFormPanel9 = new com.rameses.rcp.control.XFormPanel();
        xComboBox4 = new com.rameses.rcp.control.XComboBox();
        xDateField3 = new com.rameses.rcp.control.XDateField();
        xFormPanel10 = new com.rameses.rcp.control.XFormPanel();
        xComboBox5 = new com.rameses.rcp.control.XComboBox();
        xDecimalField2 = new com.rameses.rcp.control.XDecimalField();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setPadding(new java.awt.Insets(25, 0, 0, 0));
        xTitledBorder1.setTitle(" Personal Information ");
        jPanel1.setBorder(xTitledBorder1);

        xFormPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel1.setCaptionWidth(100);
        xTextField1.setCaption("Last Name");
        xTextField1.setName("entity.lastname");
        xTextField1.setPreferredSize(new java.awt.Dimension(150, 20));
        xTextField1.setRequired(true);
        xFormPanel1.add(xTextField1);

        xTextField2.setCaption("First Name");
        xTextField2.setName("entity.firstname");
        xTextField2.setPreferredSize(new java.awt.Dimension(150, 20));
        xTextField2.setRequired(true);
        xFormPanel1.add(xTextField2);

        xTextField3.setCaption("Middle Name");
        xTextField3.setName("entity.middlename");
        xTextField3.setPreferredSize(new java.awt.Dimension(150, 20));
        xFormPanel1.add(xTextField3);

        xFormPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel3.setCaptionWidth(100);
        xTextField10.setCaption("Citizenship");
        xTextField10.setCaptionWidth(100);
        xTextField10.setName("entity.citizenship");
        xTextField10.setPreferredSize(new java.awt.Dimension(168, 20));
        xTextField10.setRequired(true);
        xFormPanel3.add(xTextField10);

        xComboBox2.setCaption("Marital Status");
        xComboBox2.setCaptionWidth(100);
        xComboBox2.setExpression("#{item.value}");
        xComboBox2.setItemKey("key");
        xComboBox2.setItems("civilstatusList");
        xComboBox2.setName("entity.civilstatus");
        xComboBox2.setPreferredSize(new java.awt.Dimension(168, 22));
        xComboBox2.setRequired(true);
        xFormPanel3.add(xComboBox2);

        xDateField1.setCaption("Birth Date");
        xDateField1.setName("2010-10-10");
        xDateField1.setPreferredSize(new java.awt.Dimension(90, 20));
        xFormPanel3.add(xDateField1);

        xFormPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel2.setCaptionWidth(100);
        xTextField5.setCaption("Street Address");
        xTextField5.setName("entity.currentaddress.street");
        xTextField5.setPreferredSize(new java.awt.Dimension(474, 20));
        xTextField5.setRequired(true);
        xFormPanel2.add(xTextField5);

        xFormPanel5.setCaption("Province");
        xFormPanel5.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel5.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel5.setPreferredSize(new java.awt.Dimension(0, 21));
        xFormPanel5.setShowCaption(false);
        xTextField11.setCaption("Province");
        xTextField11.setCaptionWidth(100);
        xTextField11.setName("entity.currentaddress.province");
        xTextField11.setPreferredSize(new java.awt.Dimension(150, 20));
        xTextField11.setRequired(true);
        xFormPanel5.add(xTextField11);

        xTextField12.setCaption("City");
        xTextField12.setCaptionWidth(45);
        xTextField12.setCellPadding(new java.awt.Insets(0, 10, 0, 0));
        xTextField12.setName("entity.currentaddress.city");
        xTextField12.setPreferredSize(new java.awt.Dimension(130, 20));
        xTextField12.setRequired(true);
        xFormPanel5.add(xTextField12);

        xTextField13.setCaption("Zip Code");
        xTextField13.setCaptionWidth(65);
        xTextField13.setCellPadding(new java.awt.Insets(0, 10, 0, 0));
        xTextField13.setName("entity.currentaddress.zipcode");
        xTextField13.setPreferredSize(new java.awt.Dimension(60, 20));
        xTextField13.setRequired(true);
        xFormPanel5.add(xTextField13);

        xFormPanel2.add(xFormPanel5);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(xFormPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 624, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(xFormPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 272, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(34, 34, 34)
                        .add(xFormPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(xFormPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .add(xFormPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xFormPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle(" Residency ");
        jPanel3.setBorder(xTitledBorder2);

        xFormPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel4.setCaptionWidth(100);

        xFormPanel6.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel6.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel6.setPreferredSize(new java.awt.Dimension(0, 22));
        xFormPanel6.setShowCaption(false);
        xComboBox1.setCaption("Type");
        xComboBox1.setCaptionWidth(98);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("key");
        xComboBox1.setItems("residencyTypes");
        xComboBox1.setName("entity.residency.type");
        xComboBox1.setPreferredSize(new java.awt.Dimension(150, 22));
        xComboBox1.setRequired(true);
        xFormPanel6.add(xComboBox1);

        xDateField2.setCaption("Since");
        xDateField2.setCaptionWidth(100);
        xDateField2.setCellPadding(new java.awt.Insets(0, 53, 0, 0));
        xDateField2.setName("entity.residency.since");
        xDateField2.setPreferredSize(new java.awt.Dimension(100, 22));
        xDateField2.setRequired(true);
        xFormPanel6.add(xDateField2);

        xFormPanel4.add(xFormPanel6);

        xFormPanel7.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel7.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel7.setPreferredSize(new java.awt.Dimension(0, 22));
        xFormPanel7.setShowCaption(false);
        xComboBox3.setCaption("Rent Type");
        xComboBox3.setCaptionWidth(80);
        xComboBox3.setCellPadding(new java.awt.Insets(0, 18, 0, 0));
        xComboBox3.setExpression("#{item.value}");
        xComboBox3.setItemKey("key");
        xComboBox3.setItems("rentTypes");
        xComboBox3.setName("entity.residency.renttype");
        xComboBox3.setPreferredSize(new java.awt.Dimension(150, 22));
        xComboBox3.setRequired(true);
        xFormPanel7.add(xComboBox3);

        xDecimalField1.setCaption("Rent Amount");
        xDecimalField1.setCaptionWidth(100);
        xDecimalField1.setCellPadding(new java.awt.Insets(0, 53, 0, 0));
        xDecimalField1.setName("entity.residency.rentamount");
        xDecimalField1.setPreferredSize(new java.awt.Dimension(100, 22));
        xDecimalField1.setRequired(true);
        xFormPanel7.add(xDecimalField1);

        xFormPanel4.add(xFormPanel7);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(405, 42));
        xTextArea1.setCaption("Remarks");
        xTextArea1.setCaptionWidth(98);
        xTextArea1.setName("entity.residency.remarks");
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel4.add(jScrollPane1);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder3 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder3.setTitle(" Lot Occupancy ");
        jPanel4.setBorder(xTitledBorder3);

        xFormPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel8.setCaptionWidth(100);

        xFormPanel9.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel9.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel9.setPreferredSize(new java.awt.Dimension(0, 22));
        xFormPanel9.setShowCaption(false);
        xComboBox4.setCaption("Type");
        xComboBox4.setCaptionWidth(98);
        xComboBox4.setExpression("#{item.value}");
        xComboBox4.setItemKey("key");
        xComboBox4.setItems("residencyTypes");
        xComboBox4.setName("entity.lotoccupancy.type");
        xComboBox4.setPreferredSize(new java.awt.Dimension(150, 22));
        xComboBox4.setRequired(true);
        xFormPanel9.add(xComboBox4);

        xDateField3.setCaption("Since");
        xDateField3.setCaptionWidth(100);
        xDateField3.setCellPadding(new java.awt.Insets(0, 53, 0, 0));
        xDateField3.setName("entity.lotoccupancy.since");
        xDateField3.setPreferredSize(new java.awt.Dimension(100, 22));
        xDateField3.setRequired(true);
        xFormPanel9.add(xDateField3);

        xFormPanel8.add(xFormPanel9);

        xFormPanel10.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel10.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel10.setPreferredSize(new java.awt.Dimension(0, 22));
        xFormPanel10.setShowCaption(false);
        xComboBox5.setCaption("Rent Type");
        xComboBox5.setCaptionWidth(80);
        xComboBox5.setCellPadding(new java.awt.Insets(0, 18, 0, 0));
        xComboBox5.setExpression("#{item.value}");
        xComboBox5.setItemKey("key");
        xComboBox5.setItems("rentTypes");
        xComboBox5.setName("entity.lotoccupancy.renttype");
        xComboBox5.setPreferredSize(new java.awt.Dimension(150, 22));
        xComboBox5.setRequired(true);
        xFormPanel10.add(xComboBox5);

        xDecimalField2.setCaption("Rent Amount");
        xDecimalField2.setCaptionWidth(100);
        xDecimalField2.setCellPadding(new java.awt.Insets(0, 53, 0, 0));
        xDecimalField2.setName("entity.lotoccupancy.rentamount");
        xDecimalField2.setPreferredSize(new java.awt.Dimension(100, 22));
        xDecimalField2.setRequired(true);
        xFormPanel10.add(xDecimalField2);

        xFormPanel8.add(xFormPanel10);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(405, 42));
        xTextArea2.setCaption("Remarks");
        xTextArea2.setCaptionWidth(98);
        xTextArea2.setName("entity.lotoccupancy.remarks");
        jScrollPane2.setViewportView(xTextArea2);

        xFormPanel8.add(jScrollPane2);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(29, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XComboBox xComboBox3;
    private com.rameses.rcp.control.XComboBox xComboBox4;
    private com.rameses.rcp.control.XComboBox xComboBox5;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XDateField xDateField2;
    private com.rameses.rcp.control.XDateField xDateField3;
    private com.rameses.rcp.control.XDecimalField xDecimalField1;
    private com.rameses.rcp.control.XDecimalField xDecimalField2;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel10;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
    private com.rameses.rcp.control.XFormPanel xFormPanel6;
    private com.rameses.rcp.control.XFormPanel xFormPanel7;
    private com.rameses.rcp.control.XFormPanel xFormPanel8;
    private com.rameses.rcp.control.XFormPanel xFormPanel9;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    private com.rameses.rcp.control.XTextField xTextField1;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField11;
    private com.rameses.rcp.control.XTextField xTextField12;
    private com.rameses.rcp.control.XTextField xTextField13;
    private com.rameses.rcp.control.XTextField xTextField2;
    private com.rameses.rcp.control.XTextField xTextField3;
    private com.rameses.rcp.control.XTextField xTextField5;
    // End of variables declaration//GEN-END:variables
    
}

/*
 * VehicleFormPage.java
 *
 * Created on September 2, 2013, 1:31 PM
 */

package com.rameses.clfc.loan;

import com.rameses.osiris2.themes.FormPage;
import com.rameses.rcp.ui.annotations.Template;
import java.math.BigDecimal;

/**
 *
 * @author  Rameses
 */
@Template(FormPage.class)
public class VehicleFormPage extends javax.swing.JPanel {
    
    /** Creates new form VehicleFormPage */
    public VehicleFormPage() {
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
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xTextField1 = new com.rameses.rcp.control.XTextField();
        xTextField2 = new com.rameses.rcp.control.XTextField();
        xTextField3 = new com.rameses.rcp.control.XTextField();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        xTextField4 = new com.rameses.rcp.control.XTextField();
        xTextField5 = new com.rameses.rcp.control.XTextField();
        xTextField6 = new com.rameses.rcp.control.XTextField();
        xTextField7 = new com.rameses.rcp.control.XTextField();
        xTextField8 = new com.rameses.rcp.control.XTextField();
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xCheckBox1 = new com.rameses.rcp.control.XCheckBox();
        xTextField9 = new com.rameses.rcp.control.XTextField();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xTextField12 = new com.rameses.rcp.control.XTextField();
        xNumberField7 = new com.rameses.rcp.control.XNumberField();
        xNumberField2 = new com.rameses.rcp.control.XNumberField();
        xNumberField3 = new com.rameses.rcp.control.XNumberField();
        xNumberField4 = new com.rameses.rcp.control.XNumberField();
        xNumberField5 = new com.rameses.rcp.control.XNumberField();
        xNumberField6 = new com.rameses.rcp.control.XNumberField();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();

        jPanel1.setLayout(null);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Vehicle Information");
        jPanel1.setBorder(xTitledBorder1);

        xFormPanel1.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xComboBox1.setCaption("Kind of Vehicle");
        xComboBox1.setCaptionWidth(135);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("value");
        xComboBox1.setItems("kinds");
        xComboBox1.setName("entity.kind");
        xComboBox1.setPreferredSize(new java.awt.Dimension(120, 19));
        xComboBox1.setRequired(true);
        xFormPanel1.add(xComboBox1);

        xTextField1.setCaption("Make");
        xTextField1.setCaptionWidth(135);
        xTextField1.setName("entity.make");
        xTextField1.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField1.setRequired(true);
        xFormPanel1.add(xTextField1);

        xTextField2.setCaption("Model");
        xTextField2.setCaptionWidth(135);
        xTextField2.setName("entity.model");
        xTextField2.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField2.setRequired(true);
        xFormPanel1.add(xTextField2);

        xTextField3.setCaption("Body Type");
        xTextField3.setCaptionWidth(135);
        xTextField3.setName("entity.type");
        xTextField3.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField3.setRequired(true);
        xFormPanel1.add(xTextField3);

        xComboBox2.setCaption("Use");
        xComboBox2.setCaptionWidth(135);
        xComboBox2.setExpression("#{item.value}");
        xComboBox2.setItemKey("value");
        xComboBox2.setItems("uses");
        xComboBox2.setName("entity.use");
        xComboBox2.setPreferredSize(new java.awt.Dimension(120, 19));
        xComboBox2.setRequired(true);
        xFormPanel1.add(xComboBox2);

        xDateField1.setCaption("Date Acquired");
        xDateField1.setCaptionWidth(135);
        xDateField1.setName("entity.dateAcquired");
        xDateField1.setPreferredSize(new java.awt.Dimension(100, 19));
        xDateField1.setRequired(true);
        xFormPanel1.add(xDateField1);

        xTextField4.setCaption("Acquired From");
        xTextField4.setCaptionWidth(135);
        xTextField4.setName("entity.acquiredFrom");
        xTextField4.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField4.setRequired(true);
        xFormPanel1.add(xTextField4);

        xTextField5.setCaption("Registered Name");
        xTextField5.setCaptionWidth(135);
        xTextField5.setName("entity.registeredName");
        xTextField5.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField5.setRequired(true);
        xFormPanel1.add(xTextField5);

        xTextField6.setCaption("Chassis No.");
        xTextField6.setCaptionWidth(135);
        xTextField6.setName("entity.chassisno");
        xTextField6.setPreferredSize(new java.awt.Dimension(150, 19));
        xTextField6.setRequired(true);
        xFormPanel1.add(xTextField6);

        xTextField7.setCaption("Plate No.");
        xTextField7.setCaptionWidth(135);
        xTextField7.setName("entity.plateno");
        xTextField7.setPreferredSize(new java.awt.Dimension(150, 19));
        xTextField7.setRequired(true);
        xFormPanel1.add(xTextField7);

        xTextField8.setCaption("Engine No.");
        xTextField8.setCaptionWidth(135);
        xTextField8.setName("entity.engineno");
        xTextField8.setPreferredSize(new java.awt.Dimension(150, 19));
        xTextField8.setRequired(true);
        xFormPanel1.add(xTextField8);

        xNumberField1.setCaption("Market/Appraisal Value");
        xNumberField1.setCaptionWidth(135);
        xNumberField1.setFieldType(BigDecimal.class);
        xNumberField1.setName("entity.appraisedvalue");
        xNumberField1.setPreferredSize(new java.awt.Dimension(100, 19));
        xNumberField1.setRequired(true);
        xFormPanel1.add(xNumberField1);

        jPanel1.add(xFormPanel1);
        xFormPanel1.setBounds(10, 20, 330, 260);

        xFormPanel2.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xCheckBox1.setName("hasORCR");
        xCheckBox1.setOpaque(false);
        xCheckBox1.setPreferredSize(new java.awt.Dimension(53, 19));
        xCheckBox1.setShowCaption(false);
        xCheckBox1.setText(" OR/CR");
        xFormPanel2.add(xCheckBox1);

        xTextField9.setCaption("CR No.");
        xTextField9.setCaptionWidth(110);
        xTextField9.setName("entity.crno");
        xTextField9.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField9.setRequired(true);
        xFormPanel2.add(xTextField9);

        xTextField10.setCaption("Fuel");
        xTextField10.setCaptionWidth(110);
        xTextField10.setName("entity.fuel");
        xTextField10.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xTextField10);

        xTextField11.setCaption("Denomination");
        xTextField11.setCaptionWidth(110);
        xTextField11.setName("entity.denomination");
        xTextField11.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xTextField11);

        xTextField12.setCaption("Series");
        xTextField12.setCaptionWidth(110);
        xTextField12.setName("entity.series");
        xTextField12.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xTextField12);

        xNumberField7.setCaption("Piston Displacement");
        xNumberField7.setCaptionWidth(110);
        xNumberField7.setFieldType(BigDecimal.class);
        xNumberField7.setName("entity.pistondisplacement");
        xNumberField7.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField7);

        xNumberField2.setCaption("No. of cylinders");
        xNumberField2.setCaptionWidth(110);
        xNumberField2.setFieldType(BigDecimal.class);
        xNumberField2.setName("entity.noofcylinders");
        xNumberField2.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField2);

        xNumberField3.setCaption("Net Wt.");
        xNumberField3.setCaptionWidth(110);
        xNumberField3.setFieldType(BigDecimal.class);
        xNumberField3.setName("entity.netwt");
        xNumberField3.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField3);

        xNumberField4.setCaption("Gross Wt.");
        xNumberField4.setCaptionWidth(110);
        xNumberField4.setFieldType(BigDecimal.class);
        xNumberField4.setName("entity.grosswt");
        xNumberField4.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField4);

        xNumberField5.setCaption("Net Capacity");
        xNumberField5.setCaptionWidth(110);
        xNumberField5.setFieldType(BigDecimal.class);
        xNumberField5.setName("entity.netcap");
        xNumberField5.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField5);

        xNumberField6.setCaption("Shipping Wt.");
        xNumberField6.setCaptionWidth(110);
        xNumberField6.setFieldType(BigDecimal.class);
        xNumberField6.setName("entity.shipwt");
        xNumberField6.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField6);

        jPanel1.add(xFormPanel2);
        xFormPanel2.setBounds(350, 20, 220, 260);

        xFormPanel3.setPadding(new java.awt.Insets(5, 0, 0, 0));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 80));
        xTextArea1.setCaption("Remarks");
        xTextArea1.setCaptionWidth(135);
        xTextArea1.setName("entity.remarks");
        xTextArea1.setRequired(true);
        xTextArea1.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane1.setViewportView(xTextArea1);

        xFormPanel3.add(jScrollPane1);

        jPanel1.add(xFormPanel3);
        xFormPanel3.setBounds(10, 270, 560, 90);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.rameses.rcp.control.XCheckBox xCheckBox1;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XNumberField xNumberField2;
    private com.rameses.rcp.control.XNumberField xNumberField3;
    private com.rameses.rcp.control.XNumberField xNumberField4;
    private com.rameses.rcp.control.XNumberField xNumberField5;
    private com.rameses.rcp.control.XNumberField xNumberField6;
    private com.rameses.rcp.control.XNumberField xNumberField7;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextField xTextField1;
    private com.rameses.rcp.control.XTextField xTextField10;
    private com.rameses.rcp.control.XTextField xTextField11;
    private com.rameses.rcp.control.XTextField xTextField12;
    private com.rameses.rcp.control.XTextField xTextField2;
    private com.rameses.rcp.control.XTextField xTextField3;
    private com.rameses.rcp.control.XTextField xTextField4;
    private com.rameses.rcp.control.XTextField xTextField5;
    private com.rameses.rcp.control.XTextField xTextField6;
    private com.rameses.rcp.control.XTextField xTextField7;
    private com.rameses.rcp.control.XTextField xTextField8;
    private com.rameses.rcp.control.XTextField xTextField9;
    // End of variables declaration//GEN-END:variables
    
}

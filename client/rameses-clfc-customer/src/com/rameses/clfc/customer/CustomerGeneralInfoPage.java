/*
 * GeneralInfoPage.java
 *
 * Created on September 3, 2013, 4:39 PM
 */

package com.rameses.clfc.customer;

/**
 *
 * @author  wflores
 */
public class CustomerGeneralInfoPage extends javax.swing.JPanel 
{
    
    public CustomerGeneralInfoPage() {
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
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xTextField10 = new com.rameses.rcp.control.XTextField();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xDateField1 = new com.rameses.rcp.control.XDateField();
        jPanel2 = new javax.swing.JPanel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xTextField5 = new com.rameses.rcp.control.XTextField();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xTextField11 = new com.rameses.rcp.control.XTextField();
        xTextField12 = new com.rameses.rcp.control.XTextField();
        xTextField13 = new com.rameses.rcp.control.XTextField();

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

        xComboBox1.setCaption("Gender");
        xComboBox1.setItemKey("key");
        xComboBox1.setItems("genderItems");
        xComboBox1.setName("entity.gender");
        xFormPanel1.add(xComboBox1);

        xFormPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel3.setCaptionWidth(100);
        xFormPanel3.setPadding(new java.awt.Insets(0, 0, 5, 5));
        xTextField10.setCaption("Citizenship");
        xTextField10.setCaptionWidth(100);
        xTextField10.setName("entity.citizenship");
        xTextField10.setPreferredSize(new java.awt.Dimension(140, 20));
        xTextField10.setRequired(true);
        xFormPanel3.add(xTextField10);

        xComboBox2.setCaption("Marital Status");
        xComboBox2.setCaptionWidth(100);
        xComboBox2.setExpression("#{item.value}");
        xComboBox2.setItemKey("key");
        xComboBox2.setItems("civilstatusList");
        xComboBox2.setName("entity.civilstatus");
        xComboBox2.setPreferredSize(new java.awt.Dimension(140, 22));
        xComboBox2.setRequired(true);
        xFormPanel3.add(xComboBox2);

        xDateField1.setCaption("Birth Date");
        xDateField1.setName("2010-10-10");
        xDateField1.setPreferredSize(new java.awt.Dimension(90, 20));
        xFormPanel3.add(xDateField1);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xFormPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 262, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, xFormPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, xFormPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle(" Current Address Information ");
        jPanel2.setBorder(xTitledBorder2);

        xFormPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xFormPanel2.setCaptionWidth(100);
        xTextField5.setCaption("Street");
        xTextField5.setHint("Street");
        xTextField5.setName("entity.currentaddress.street");
        xTextField5.setPreferredSize(new java.awt.Dimension(410, 20));
        xTextField5.setRequired(true);
        xFormPanel2.add(xTextField5);

        xFormPanel5.setCaption("Province");
        xFormPanel5.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel5.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xFormPanel5.setPreferredSize(new java.awt.Dimension(0, 21));
        xFormPanel5.setShowCaption(false);
        xTextField11.setCaption("Province");
        xTextField11.setCaptionWidth(100);
        xTextField11.setHint("Province");
        xTextField11.setName("entity.currentaddress.province");
        xTextField11.setPreferredSize(new java.awt.Dimension(150, 20));
        xTextField11.setRequired(true);
        xFormPanel5.add(xTextField11);

        xTextField12.setCaption("City");
        xTextField12.setCaptionWidth(88);
        xTextField12.setCellPadding(new java.awt.Insets(0, 20, 0, 0));
        xTextField12.setHint("City");
        xTextField12.setName("entity.currentaddress.city");
        xTextField12.setPreferredSize(new java.awt.Dimension(150, 20));
        xTextField12.setRequired(true);
        xFormPanel5.add(xTextField12);

        xFormPanel2.add(xFormPanel5);

        xTextField13.setCaption("Zip Code");
        xTextField13.setCaptionWidth(100);
        xTextField13.setHint("Zip Code");
        xTextField13.setName("entity.currentaddress.zipcode");
        xTextField13.setPreferredSize(new java.awt.Dimension(75, 20));
        xTextField13.setRequired(true);
        xFormPanel2.add(xTextField13);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(xFormPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XDateField xDateField1;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
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

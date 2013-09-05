/*
 * ApprovalFormPage.java
 *
 * Created on September 2, 2013, 3:37 PM
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
public class ApprovalFormPage extends javax.swing.JPanel {
    
    /** Creates new form ApprovalFormPage */
    public ApprovalFormPage() {
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        xNumberField2 = new com.rameses.rcp.control.XNumberField();
        xNumberField3 = new com.rameses.rcp.control.XNumberField();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xNumberField5 = new com.rameses.rcp.control.XNumberField();
        xNumberField11 = new com.rameses.rcp.control.XNumberField();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xNumberField4 = new com.rameses.rcp.control.XNumberField();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xSeparator1 = new com.rameses.rcp.control.XSeparator();
        xDataTable1 = new com.rameses.rcp.control.XDataTable();
        xButton1 = new com.rameses.rcp.control.XButton();
        xFormPanel6 = new com.rameses.rcp.control.XFormPanel();
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xSeparator2 = new com.rameses.rcp.control.XSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        jPanel7 = new javax.swing.JPanel();
        xDataTable2 = new com.rameses.rcp.control.XDataTable();
        xButton3 = new com.rameses.rcp.control.XButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        xFormPanel9 = new com.rameses.rcp.control.XFormPanel();
        xNumberField6 = new com.rameses.rcp.control.XNumberField();
        xNumberField7 = new com.rameses.rcp.control.XNumberField();
        xNumberField8 = new com.rameses.rcp.control.XNumberField();
        xFormPanel11 = new com.rameses.rcp.control.XFormPanel();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xNumberField10 = new com.rameses.rcp.control.XNumberField();
        xNumberField12 = new com.rameses.rcp.control.XNumberField();
        xFormPanel10 = new com.rameses.rcp.control.XFormPanel();
        xNumberField9 = new com.rameses.rcp.control.XNumberField();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xSeparator3 = new com.rameses.rcp.control.XSeparator();
        xDataTable3 = new com.rameses.rcp.control.XDataTable();
        xButton2 = new com.rameses.rcp.control.XButton();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        xLabel4 = new com.rameses.rcp.control.XLabel();
        xSeparator4 = new com.rameses.rcp.control.XSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Approval Form");
        jPanel1.setBorder(xTitledBorder1);
        jPanel4.setLayout(null);

        jPanel6.setLayout(null);

        com.rameses.rcp.control.border.XLineBorder xLineBorder1 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder1.setLineColor(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(xLineBorder1);

        xFormPanel2.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xNumberField1.setCaption("Applied Amount");
        xNumberField1.setCaptionWidth(110);
        xNumberField1.setEnabled(false);
        xNumberField1.setFieldType(BigDecimal.class);
        xNumberField1.setName("entity.loanamount");
        xNumberField1.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField1);

        xNumberField2.setCaption("Approved Amount");
        xNumberField2.setCaptionWidth(110);
        xNumberField2.setFieldType(BigDecimal.class);
        xNumberField2.setName("entity.shoot.approvedamount");
        xNumberField2.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField2);

        xNumberField3.setCaption("Credit Limit");
        xNumberField3.setCaptionWidth(110);
        xNumberField3.setFieldType(BigDecimal.class);
        xNumberField3.setName("entity.shoot.creditlimit");
        xNumberField3.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel2.add(xNumberField3);

        jPanel6.add(xFormPanel2);
        xFormPanel2.setBounds(10, 10, 240, 70);

        xFormPanel3.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel3.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xComboBox1.setAllowNull(false);
        xComboBox1.setCaption("Absences");
        xComboBox1.setCaptionWidth(110);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("value");
        xComboBox1.setItems("absenceTypes");
        xComboBox1.setName("entity.shoot.absences");
        xComboBox1.setPreferredSize(new java.awt.Dimension(130, 19));
        xFormPanel3.add(xComboBox1);

        xNumberField5.setFieldType(Integer.class);
        xNumberField5.setName("entity.shoot.policy");
        xNumberField5.setPreferredSize(new java.awt.Dimension(50, 19));
        xNumberField5.setShowCaption(false);
        xFormPanel3.add(xNumberField5);

        xNumberField11.setFieldType(Integer.class);
        xNumberField11.setName("entity.shoot.provisions");
        xNumberField11.setPreferredSize(new java.awt.Dimension(50, 19));
        xNumberField11.setShowCaption(false);
        xFormPanel3.add(xNumberField11);

        jPanel6.add(xFormPanel3);
        xFormPanel3.setBounds(10, 70, 380, 30);

        xFormPanel4.setPadding(new java.awt.Insets(4, 0, 0, 0));
        xNumberField4.setCaption("Gradual Increase");
        xNumberField4.setCaptionWidth(110);
        xNumberField4.setFieldType(BigDecimal.class);
        xNumberField4.setName("entity.shoot.increase");
        xNumberField4.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel4.add(xNumberField4);

        jPanel6.add(xFormPanel4);
        xFormPanel4.setBounds(10, 90, 240, 30);

        jPanel4.add(jPanel6);
        jPanel6.setBounds(10, 10, 470, 120);

        xFormPanel5.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel5.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel1.setPadding(new java.awt.Insets(1, 0, 1, 0));
        xLabel1.setShowCaption(false);
        xLabel1.setText("Must/Required Collateral(s)");
        xFormPanel5.add(xLabel1);

        xSeparator1.setPreferredSize(new java.awt.Dimension(700, 19));
        javax.swing.GroupLayout xSeparator1Layout = new javax.swing.GroupLayout(xSeparator1);
        xSeparator1.setLayout(xSeparator1Layout);
        xSeparator1Layout.setHorizontalGroup(
            xSeparator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        xSeparator1Layout.setVerticalGroup(
            xSeparator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );
        xFormPanel5.add(xSeparator1);

        jPanel4.add(xFormPanel5);
        xFormPanel5.setBounds(10, 140, 470, 30);

        xDataTable1.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "description"}
                , new Object[]{"caption", "Description"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            })
        });
        xDataTable1.setHandler("shootMustCollateralHandler");
        xDataTable1.setName("selectedShootMustCollateral");
        jPanel4.add(xDataTable1);
        xDataTable1.setBounds(10, 160, 470, 90);

        xButton1.setImmediate(true);
        xButton1.setName("addShootMustCollateral");
        xButton1.setText("Add");
        jPanel4.add(xButton1);
        xButton1.setBounds(10, 260, 77, 23);

        xFormPanel6.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel6.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xLabel2.setPadding(new java.awt.Insets(1, 0, 1, 0));
        xLabel2.setPreferredSize(new java.awt.Dimension(41, 19));
        xLabel2.setShowCaption(false);
        xLabel2.setText("Remarks");
        xFormPanel6.add(xLabel2);

        xSeparator2.setPreferredSize(new java.awt.Dimension(700, 19));
        javax.swing.GroupLayout xSeparator2Layout = new javax.swing.GroupLayout(xSeparator2);
        xSeparator2.setLayout(xSeparator2Layout);
        xSeparator2Layout.setHorizontalGroup(
            xSeparator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        xSeparator2Layout.setVerticalGroup(
            xSeparator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );
        xFormPanel6.add(xSeparator2);

        jPanel4.add(xFormPanel6);
        xFormPanel6.setBounds(10, 290, 470, 30);

        xTextArea1.setName("entity.shoot.remarks");
        xTextArea1.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane1.setViewportView(xTextArea1);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(10, 312, 470, 70);

        jTabbedPane1.addTab("SHOOT", jPanel4);

        jPanel7.setLayout(null);

        xDataTable2.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "amountapproved"}
                , new Object[]{"caption", "Approved Amount"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"alignment", "RIGHT"}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.DecimalColumnHandler("#,##0.00", -1.0, -1.0, false)}
            }),
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "remarks"}
                , new Object[]{"caption", "Remarks"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            })
        });
        xDataTable2.setHandler("offerHandler");
        xDataTable2.setName("selectedOffer");
        jPanel7.add(xDataTable2);
        xDataTable2.setBounds(10, 10, 470, 130);

        xButton3.setImmediate(true);
        xButton3.setName("addOffer");
        xButton3.setText("Add");
        jPanel7.add(xButton3);
        xButton3.setBounds(10, 150, 51, 23);

        jTabbedPane1.addTab("OFFER", jPanel7);

        jPanel8.setLayout(null);

        jPanel10.setLayout(null);

        com.rameses.rcp.control.border.XLineBorder xLineBorder2 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder2.setLineColor(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(xLineBorder2);

        xFormPanel9.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xNumberField6.setCaption("Applied Amount");
        xNumberField6.setCaptionWidth(110);
        xNumberField6.setEnabled(false);
        xNumberField6.setFieldType(BigDecimal.class);
        xNumberField6.setName("entity.loanamount");
        xNumberField6.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel9.add(xNumberField6);

        xNumberField7.setCaption("Approved Amount");
        xNumberField7.setCaptionWidth(110);
        xNumberField7.setFieldType(BigDecimal.class);
        xNumberField7.setName("entity.option.approvedamount");
        xNumberField7.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel9.add(xNumberField7);

        xNumberField8.setCaption("Credit Limit");
        xNumberField8.setCaptionWidth(110);
        xNumberField8.setFieldType(BigDecimal.class);
        xNumberField8.setName("entity.option.creditlimit");
        xNumberField8.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel9.add(xNumberField8);

        jPanel10.add(xFormPanel9);
        xFormPanel9.setBounds(10, 10, 240, 70);

        xFormPanel11.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel11.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xComboBox2.setAllowNull(false);
        xComboBox2.setCaption("Absences");
        xComboBox2.setCaptionWidth(110);
        xComboBox2.setName("entity.option.absences");
        xComboBox2.setPreferredSize(new java.awt.Dimension(130, 19));
        xFormPanel11.add(xComboBox2);

        xNumberField10.setName("entity.option.policy");
        xNumberField10.setPreferredSize(new java.awt.Dimension(50, 19));
        xNumberField10.setShowCaption(false);
        xFormPanel11.add(xNumberField10);

        xNumberField12.setName("entity.option.provisions");
        xNumberField12.setPreferredSize(new java.awt.Dimension(50, 19));
        xNumberField12.setShowCaption(false);
        xFormPanel11.add(xNumberField12);

        jPanel10.add(xFormPanel11);
        xFormPanel11.setBounds(10, 70, 360, 30);

        xFormPanel10.setPadding(new java.awt.Insets(4, 0, 0, 0));
        xNumberField9.setCaption("Gradual Increase");
        xNumberField9.setCaptionWidth(110);
        xNumberField9.setFieldType(BigDecimal.class);
        xNumberField9.setName("entity.option.increase");
        xNumberField9.setPreferredSize(new java.awt.Dimension(0, 19));
        xFormPanel10.add(xNumberField9);

        jPanel10.add(xFormPanel10);
        xFormPanel10.setBounds(10, 90, 240, 30);

        jPanel8.add(jPanel10);
        jPanel10.setBounds(10, 10, 470, 120);

        xFormPanel7.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel7.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel3.setPadding(new java.awt.Insets(1, 0, 1, 0));
        xLabel3.setShowCaption(false);
        xLabel3.setText("Must/Required Collateral(s)");
        xFormPanel7.add(xLabel3);

        xSeparator3.setPreferredSize(new java.awt.Dimension(700, 19));
        javax.swing.GroupLayout xSeparator3Layout = new javax.swing.GroupLayout(xSeparator3);
        xSeparator3.setLayout(xSeparator3Layout);
        xSeparator3Layout.setHorizontalGroup(
            xSeparator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        xSeparator3Layout.setVerticalGroup(
            xSeparator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );
        xFormPanel7.add(xSeparator3);

        jPanel8.add(xFormPanel7);
        xFormPanel7.setBounds(10, 140, 470, 30);

        xDataTable3.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "description"}
                , new Object[]{"caption", "Description"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            })
        });
        xDataTable3.setHandler("optionMustCollateralHandler");
        xDataTable3.setName("selectedOptionMustCollateral");
        jPanel8.add(xDataTable3);
        xDataTable3.setBounds(10, 160, 470, 90);

        xButton2.setText("Add");
        jPanel8.add(xButton2);
        xButton2.setBounds(10, 260, 77, 23);

        xFormPanel8.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel8.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xLabel4.setPadding(new java.awt.Insets(1, 0, 1, 0));
        xLabel4.setPreferredSize(new java.awt.Dimension(41, 19));
        xLabel4.setShowCaption(false);
        xLabel4.setText("Remarks");
        xFormPanel8.add(xLabel4);

        xSeparator4.setPreferredSize(new java.awt.Dimension(700, 19));
        javax.swing.GroupLayout xSeparator4Layout = new javax.swing.GroupLayout(xSeparator4);
        xSeparator4.setLayout(xSeparator4Layout);
        xSeparator4Layout.setHorizontalGroup(
            xSeparator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        xSeparator4Layout.setVerticalGroup(
            xSeparator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );
        xFormPanel8.add(xSeparator4);

        jPanel8.add(xFormPanel8);
        xFormPanel8.setBounds(10, 290, 470, 30);

        xTextArea2.setName("entity.option.remarks");
        xTextArea2.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane2.setViewportView(xTextArea2);

        jPanel8.add(jScrollPane2);
        jScrollPane2.setBounds(10, 312, 470, 70);

        jTabbedPane1.addTab("OPTION", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.rameses.rcp.control.XButton xButton1;
    private com.rameses.rcp.control.XButton xButton2;
    private com.rameses.rcp.control.XButton xButton3;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XDataTable xDataTable1;
    private com.rameses.rcp.control.XDataTable xDataTable2;
    private com.rameses.rcp.control.XDataTable xDataTable3;
    private com.rameses.rcp.control.XFormPanel xFormPanel10;
    private com.rameses.rcp.control.XFormPanel xFormPanel11;
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
    private com.rameses.rcp.control.XLabel xLabel4;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XNumberField xNumberField10;
    private com.rameses.rcp.control.XNumberField xNumberField11;
    private com.rameses.rcp.control.XNumberField xNumberField12;
    private com.rameses.rcp.control.XNumberField xNumberField2;
    private com.rameses.rcp.control.XNumberField xNumberField3;
    private com.rameses.rcp.control.XNumberField xNumberField4;
    private com.rameses.rcp.control.XNumberField xNumberField5;
    private com.rameses.rcp.control.XNumberField xNumberField6;
    private com.rameses.rcp.control.XNumberField xNumberField7;
    private com.rameses.rcp.control.XNumberField xNumberField8;
    private com.rameses.rcp.control.XNumberField xNumberField9;
    private com.rameses.rcp.control.XSeparator xSeparator1;
    private com.rameses.rcp.control.XSeparator xSeparator2;
    private com.rameses.rcp.control.XSeparator xSeparator3;
    private com.rameses.rcp.control.XSeparator xSeparator4;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    // End of variables declaration//GEN-END:variables
    
}

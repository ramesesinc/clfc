/*
 * ChildrenFormPage.java
 *
 * Created on September 2, 2013, 3:02 PM
 */

package com.rameses.clfc.loan;

import com.rameses.osiris2.themes.OKCancelPage;
import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;

/**
 *
 * @author  Rameses
 */
@StyleSheet
@Template(OKCancelPage.class)
public class ChildFormPage extends javax.swing.JPanel {
    
    /** Creates new form ChildrenFormPage */
    public ChildFormPage() {
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
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xSeparator2 = new com.rameses.rcp.control.XSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        xTextArea1 = new com.rameses.rcp.control.XTextArea();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xLabel4 = new com.rameses.rcp.control.XLabel();
        xSeparator5 = new com.rameses.rcp.control.XSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        xTextArea2 = new com.rameses.rcp.control.XTextArea();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xSeparator1 = new com.rameses.rcp.control.XSeparator();
        xDataTable2 = new com.rameses.rcp.control.XDataTable();
        xButton1 = new com.rameses.rcp.control.XButton();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xSeparator3 = new com.rameses.rcp.control.XSeparator();
        xDataTable1 = new com.rameses.rcp.control.XDataTable();
        xButton2 = new com.rameses.rcp.control.XButton();

        jPanel1.setLayout(null);

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Child Information");
        jPanel1.setBorder(xTitledBorder1);

        xFormPanel1.setPadding(new java.awt.Insets(3, 0, 0, 0));
        xTextField1.setCaption("Name");
        xTextField1.setCaptionWidth(70);
        xTextField1.setName("entity.name");
        xTextField1.setPreferredSize(new java.awt.Dimension(0, 19));
        xTextField1.setRequired(true);
        xFormPanel1.add(xTextField1);

        xNumberField1.setCaption("Age");
        xNumberField1.setCaptionWidth(70);
        xNumberField1.setFieldType(Integer.class);
        xNumberField1.setName("entity.age");
        xNumberField1.setPreferredSize(new java.awt.Dimension(50, 19));
        xNumberField1.setRequired(true);
        xFormPanel1.add(xNumberField1);

        jPanel1.add(xFormPanel1);
        xFormPanel1.setBounds(10, 20, 410, 50);

        xFormPanel3.setCellspacing(0);
        xFormPanel3.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel3.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel2.setPadding(new java.awt.Insets(1, 0, 1, 0));
        xLabel2.setPreferredSize(new java.awt.Dimension(111, 19));
        xLabel2.setShowCaption(false);
        xLabel2.setText("Educational Attainment");
        xFormPanel3.add(xLabel2);

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
        xFormPanel3.add(xSeparator2);

        jPanel1.add(xFormPanel3);
        xFormPanel3.setBounds(10, 70, 200, 30);

        jPanel2.setLayout(new java.awt.BorderLayout());

        xTextArea1.setCaption("Educational Attainment");
        xTextArea1.setName("entity.education");
        xTextArea1.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane1.setViewportView(xTextArea1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 90, 200, 70);

        xFormPanel5.setCellspacing(0);
        xFormPanel5.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel5.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel4.setShowCaption(false);
        xLabel4.setText("Remarks");
        xFormPanel5.add(xLabel4);

        xSeparator5.setPreferredSize(new java.awt.Dimension(700, 19));
        javax.swing.GroupLayout xSeparator5Layout = new javax.swing.GroupLayout(xSeparator5);
        xSeparator5.setLayout(xSeparator5Layout);
        xSeparator5Layout.setHorizontalGroup(
            xSeparator5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        xSeparator5Layout.setVerticalGroup(
            xSeparator5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );
        xFormPanel5.add(xSeparator5);

        jPanel1.add(xFormPanel5);
        xFormPanel5.setBounds(220, 70, 200, 30);

        jPanel3.setLayout(new java.awt.BorderLayout());

        xTextArea2.setCaption("Status");
        xTextArea2.setName("entity.remarks");
        xTextArea2.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane2.setViewportView(xTextArea2);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(220, 90, 200, 70);

        xFormPanel2.setCellspacing(0);
        xFormPanel2.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel2.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel1.setPreferredSize(new java.awt.Dimension(62, 19));
        xLabel1.setShowCaption(false);
        xLabel1.setText("Employment");
        xFormPanel2.add(xLabel1);

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
        xFormPanel2.add(xSeparator1);

        jPanel1.add(xFormPanel2);
        xFormPanel2.setBounds(10, 170, 410, 30);

        xDataTable2.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "employer"}
                , new Object[]{"caption", "Name"}
                , new Object[]{"width", 150}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            }),
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "telno"}
                , new Object[]{"caption", "Tel. No."}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            }),
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "address"}
                , new Object[]{"caption", "Address"}
                , new Object[]{"width", 200}
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
        xDataTable2.setHandler("employmentHandler");
        xDataTable2.setName("selectedEmployment");
        jPanel1.add(xDataTable2);
        xDataTable2.setBounds(10, 190, 410, 80);

        xButton1.setImmediate(true);
        xButton1.setName("addEmployment");
        xButton1.setText("Add");
        jPanel1.add(xButton1);
        xButton1.setBounds(10, 280, 51, 23);

        xFormPanel4.setCellspacing(0);
        xFormPanel4.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel4.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel3.setPreferredSize(new java.awt.Dimension(124, 19));
        xLabel3.setShowCaption(false);
        xLabel3.setText("Other Sources of Income");
        xFormPanel4.add(xLabel3);

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
        xFormPanel4.add(xSeparator3);

        jPanel1.add(xFormPanel4);
        xFormPanel4.setBounds(10, 310, 410, 30);

        xDataTable1.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "name"}
                , new Object[]{"caption", "Source of Income"}
                , new Object[]{"width", 250}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", false}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
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
        xDataTable1.setHandler("otherIncomeHandler");
        xDataTable1.setName("selectedOtherIncome");
        jPanel1.add(xDataTable1);
        xDataTable1.setBounds(10, 330, 410, 80);

        xButton2.setImmediate(true);
        xButton2.setName("addOtherIncome");
        xButton2.setText("Add");
        jPanel1.add(xButton2);
        xButton2.setBounds(10, 420, 51, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.rameses.rcp.control.XButton xButton1;
    private com.rameses.rcp.control.XButton xButton2;
    private com.rameses.rcp.control.XDataTable xDataTable1;
    private com.rameses.rcp.control.XDataTable xDataTable2;
    private com.rameses.rcp.control.XFormPanel xFormPanel1;
    private com.rameses.rcp.control.XFormPanel xFormPanel2;
    private com.rameses.rcp.control.XFormPanel xFormPanel3;
    private com.rameses.rcp.control.XFormPanel xFormPanel4;
    private com.rameses.rcp.control.XFormPanel xFormPanel5;
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLabel xLabel4;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XSeparator xSeparator1;
    private com.rameses.rcp.control.XSeparator xSeparator2;
    private com.rameses.rcp.control.XSeparator xSeparator3;
    private com.rameses.rcp.control.XSeparator xSeparator5;
    private com.rameses.rcp.control.XTextArea xTextArea1;
    private com.rameses.rcp.control.XTextArea xTextArea2;
    private com.rameses.rcp.control.XTextField xTextField1;
    // End of variables declaration//GEN-END:variables
    
}

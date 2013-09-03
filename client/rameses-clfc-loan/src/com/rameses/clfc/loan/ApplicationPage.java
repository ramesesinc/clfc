/*
 * NewJPanel.java
 *
 * Created on August 31, 2013, 3:10 PM
 */

package com.rameses.clfc.loan;

import com.rameses.rcp.ui.annotations.StyleSheet;
import com.rameses.rcp.ui.annotations.Template;
import java.math.BigDecimal;

/**
 *
 * @author  Rameses
 */
@StyleSheet
@Template(CaptureRenewalPage.class)
public class ApplicationPage extends javax.swing.JPanel {
    
    /** Creates new form NewJPanel */
    public ApplicationPage() {
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
        xLookupField1 = new com.rameses.rcp.control.XLookupField();
        xLabel1 = new com.rameses.rcp.control.XLabel();
        xLabel2 = new com.rameses.rcp.control.XLabel();
        xFormPanel2 = new com.rameses.rcp.control.XFormPanel();
        xLabel15 = new com.rameses.rcp.control.XLabel();
        xLabel16 = new com.rameses.rcp.control.XLabel();
        xLabel17 = new com.rameses.rcp.control.XLabel();
        jPanel3 = new javax.swing.JPanel();
        xFormPanel3 = new com.rameses.rcp.control.XFormPanel();
        xLabel3 = new com.rameses.rcp.control.XLabel();
        xSeparator3 = new com.rameses.rcp.control.XSeparator();
        xFormPanel6 = new com.rameses.rcp.control.XFormPanel();
        xLabel5 = new com.rameses.rcp.control.XLabel();
        xLabel6 = new com.rameses.rcp.control.XLabel();
        xFormPanel7 = new com.rameses.rcp.control.XFormPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        xTextArea4 = new com.rameses.rcp.control.XTextArea();
        jPanel4 = new javax.swing.JPanel();
        xFormPanel8 = new com.rameses.rcp.control.XFormPanel();
        xLabel4 = new com.rameses.rcp.control.XLabel();
        xSeparator4 = new com.rameses.rcp.control.XSeparator();
        xFormPanel9 = new com.rameses.rcp.control.XFormPanel();
        xLabel7 = new com.rameses.rcp.control.XLabel();
        xLabel8 = new com.rameses.rcp.control.XLabel();
        xFormPanel10 = new com.rameses.rcp.control.XFormPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        xTextArea5 = new com.rameses.rcp.control.XTextArea();
        jPanel2 = new javax.swing.JPanel();
        xFormPanel4 = new com.rameses.rcp.control.XFormPanel();
        xComboBox3 = new com.rameses.rcp.control.XComboBox();
        xComboBox1 = new com.rameses.rcp.control.XComboBox();
        xComboBox2 = new com.rameses.rcp.control.XComboBox();
        xFormPanel5 = new com.rameses.rcp.control.XFormPanel();
        xNumberField1 = new com.rameses.rcp.control.XNumberField();
        xNumberField2 = new com.rameses.rcp.control.XNumberField();
        jScrollPane3 = new javax.swing.JScrollPane();
        xTextArea3 = new com.rameses.rcp.control.XTextArea();
        xLabel12 = new com.rameses.rcp.control.XLabel();
        jPanel5 = new javax.swing.JPanel();
        xDataTable1 = new com.rameses.rcp.control.XDataTable();
        xButton1 = new com.rameses.rcp.control.XButton();

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder1 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder1.setTitle("Principal Borrower Information");
        jPanel1.setBorder(xTitledBorder1);

        xLookupField1.setCaption("Name");
        xLookupField1.setCaptionWidth(60);
        xLookupField1.setExpression("#{item.name}");
        xLookupField1.setHandler("principalBorrowerLookup");
        xLookupField1.setName("borrower");
        xLookupField1.setPreferredSize(new java.awt.Dimension(0, 19));
        xLookupField1.setRequired(true);
        xFormPanel1.add(xLookupField1);

        com.rameses.rcp.control.border.XLineBorder xLineBorder1 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder1.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel1.setBorder(xLineBorder1);
        xLabel1.setCaption("Address");
        xLabel1.setCaptionWidth(60);
        xLabel1.setDepends(new String[] {"borrower"});
        xLabel1.setName("entity.borrower.address");
        xLabel1.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel1.add(xLabel1);

        com.rameses.rcp.control.border.XLineBorder xLineBorder2 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder2.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel2.setBorder(xLineBorder2);
        xLabel2.setCaption("Birthdate");
        xLabel2.setCaptionWidth(60);
        xLabel2.setDepends(new String[] {"borrower"});
        xLabel2.setName("entity.borrower.birthdate");
        xLabel2.setPreferredSize(new java.awt.Dimension(100, 16));
        xFormPanel1.add(xLabel2);

        com.rameses.rcp.control.border.XLineBorder xLineBorder3 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder3.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel15.setBorder(xLineBorder3);
        xLabel15.setCaption("Marital Status");
        xLabel15.setCaptionWidth(85);
        xLabel15.setDepends(new String[] {"borrower"});
        xLabel15.setName("entity.borrower.status");
        xLabel15.setPreferredSize(new java.awt.Dimension(1000, 16));
        xFormPanel2.add(xLabel15);

        com.rameses.rcp.control.border.XLineBorder xLineBorder4 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder4.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel16.setBorder(xLineBorder4);
        xLabel16.setCaption("Citizenship");
        xLabel16.setCaptionWidth(85);
        xLabel16.setDepends(new String[] {"borrower"});
        xLabel16.setName("entity.borrower.citizenship");
        xLabel16.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel2.add(xLabel16);

        com.rameses.rcp.control.border.XLineBorder xLineBorder5 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder5.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel17.setBorder(xLineBorder5);
        xLabel17.setCaption("Contact No.");
        xLabel17.setCaptionWidth(85);
        xLabel17.setDepends(new String[] {"borrower"});
        xLabel17.setName("entity.borrower.contactno");
        xLabel17.setPreferredSize(new java.awt.Dimension(0, 16));
        xFormPanel2.add(xLabel17);

        jPanel3.setLayout(null);

        jPanel3.setOpaque(false);

        xFormPanel3.setCellspacing(0);
        xFormPanel3.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xLabel3.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel3.setPreferredSize(new java.awt.Dimension(49, 15));
        xLabel3.setShowCaption(false);
        xLabel3.setText("Residency");
        xFormPanel3.add(xLabel3);

        xSeparator3.setPreferredSize(new java.awt.Dimension(300, 15));
        javax.swing.GroupLayout xSeparator3Layout = new javax.swing.GroupLayout(xSeparator3);
        xSeparator3.setLayout(xSeparator3Layout);
        xSeparator3Layout.setHorizontalGroup(
            xSeparator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        xSeparator3Layout.setVerticalGroup(
            xSeparator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        xFormPanel3.add(xSeparator3);

        jPanel3.add(xFormPanel3);
        xFormPanel3.setBounds(0, 0, 300, 20);

        xFormPanel6.setCellpadding(new java.awt.Insets(0, 0, 0, 5));
        xFormPanel6.setCellspacing(1);
        xFormPanel6.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel6.setPadding(new java.awt.Insets(0, 0, 0, 0));
        com.rameses.rcp.control.border.XLineBorder xLineBorder6 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder6.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel5.setBorder(xLineBorder6);
        xLabel5.setCaption("Type");
        xLabel5.setCaptionWidth(60);
        xLabel5.setName("entity.residency.type");
        xLabel5.setPreferredSize(new java.awt.Dimension(90, 16));
        xFormPanel6.add(xLabel5);

        com.rameses.rcp.control.border.XLineBorder xLineBorder7 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder7.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel6.setBorder(xLineBorder7);
        xLabel6.setCaption("Since");
        xLabel6.setCaptionWidth(50);
        xLabel6.setName("entity.residency.since");
        xLabel6.setPreferredSize(new java.awt.Dimension(80, 16));
        xFormPanel6.add(xLabel6);

        jPanel3.add(xFormPanel6);
        xFormPanel6.setBounds(10, 20, 300, 20);

        xFormPanel7.setCellspacing(0);
        xFormPanel7.setPadding(new java.awt.Insets(8, 0, 0, 0));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(0, 32));
        xTextArea4.setCaption("Remarks");
        xTextArea4.setCaptionWidth(60);
        xTextArea4.setName("entity.residency.remarks");
        xTextArea4.setPreferredSize(new java.awt.Dimension(100, 20));
        jScrollPane4.setViewportView(xTextArea4);

        xFormPanel7.add(jScrollPane4);

        jPanel3.add(xFormPanel7);
        xFormPanel7.setBounds(10, 30, 290, 50);

        jPanel4.setLayout(null);

        jPanel4.setOpaque(false);

        xFormPanel8.setCellspacing(0);
        xFormPanel8.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xLabel4.setPadding(new java.awt.Insets(0, 0, 0, 0));
        xLabel4.setPreferredSize(new java.awt.Dimension(71, 15));
        xLabel4.setShowCaption(false);
        xLabel4.setText("Lot Occupancy");
        xFormPanel8.add(xLabel4);

        xSeparator4.setPreferredSize(new java.awt.Dimension(300, 15));
        javax.swing.GroupLayout xSeparator4Layout = new javax.swing.GroupLayout(xSeparator4);
        xSeparator4.setLayout(xSeparator4Layout);
        xSeparator4Layout.setHorizontalGroup(
            xSeparator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        xSeparator4Layout.setVerticalGroup(
            xSeparator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        xFormPanel8.add(xSeparator4);

        jPanel4.add(xFormPanel8);
        xFormPanel8.setBounds(0, 0, 300, 20);

        xFormPanel9.setCellpadding(new java.awt.Insets(0, 0, 0, 5));
        xFormPanel9.setCellspacing(1);
        xFormPanel9.setOrientation(com.rameses.rcp.constant.UIConstants.HORIZONTAL);
        xFormPanel9.setPadding(new java.awt.Insets(0, 0, 0, 0));
        com.rameses.rcp.control.border.XLineBorder xLineBorder8 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder8.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel7.setBorder(xLineBorder8);
        xLabel7.setCaption("Type");
        xLabel7.setCaptionWidth(60);
        xLabel7.setName("entity.lotoccupancy.type");
        xLabel7.setPreferredSize(new java.awt.Dimension(90, 16));
        xFormPanel9.add(xLabel7);

        com.rameses.rcp.control.border.XLineBorder xLineBorder9 = new com.rameses.rcp.control.border.XLineBorder();
        xLineBorder9.setLineColor(new java.awt.Color(204, 204, 204));
        xLabel8.setBorder(xLineBorder9);
        xLabel8.setCaption("Since");
        xLabel8.setCaptionWidth(50);
        xLabel8.setName("entity.lotoccupancy.since");
        xLabel8.setPreferredSize(new java.awt.Dimension(80, 16));
        xFormPanel9.add(xLabel8);

        jPanel4.add(xFormPanel9);
        xFormPanel9.setBounds(10, 20, 300, 20);

        xFormPanel10.setCellspacing(0);
        xFormPanel10.setPadding(new java.awt.Insets(8, 0, 0, 0));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(0, 32));
        xTextArea5.setCaption("Remarks");
        xTextArea5.setCaptionWidth(60);
        xTextArea5.setName("entity.lotoccupancy.remarks");
        xTextArea5.setPreferredSize(new java.awt.Dimension(100, 20));
        jScrollPane5.setViewportView(xTextArea5);

        xFormPanel10.add(jScrollPane5);

        jPanel4.add(xFormPanel10);
        xFormPanel10.setBounds(10, 30, 290, 50);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(xFormPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xFormPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xFormPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder2 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder2.setTitle("Loan Details");
        jPanel2.setBorder(xTitledBorder2);

        xComboBox3.setAllowNull(false);
        xComboBox3.setCaption("Renewal Type");
        xComboBox3.setCaptionWidth(100);
        xComboBox3.setExpression("#{item.value}");
        xComboBox3.setItemKey("value");
        xComboBox3.setItems("renewalTypes");
        xComboBox3.setName("entity.renewaltype");
        xComboBox3.setPreferredSize(new java.awt.Dimension(210, 22));
        xComboBox3.setRequired(true);
        xFormPanel4.add(xComboBox3);

        xComboBox1.setAllowNull(false);
        xComboBox1.setCaption("Client Type");
        xComboBox1.setCaptionWidth(100);
        xComboBox1.setExpression("#{item.value}");
        xComboBox1.setItemKey("value");
        xComboBox1.setItems("clientTypes");
        xComboBox1.setName("entity.clienttype");
        xComboBox1.setPreferredSize(new java.awt.Dimension(210, 22));
        xComboBox1.setRequired(true);
        xFormPanel4.add(xComboBox1);

        xComboBox2.setAllowNull(false);
        xComboBox2.setCaption("Product Type");
        xComboBox2.setCaptionWidth(100);
        xComboBox2.setExpression("#{item.value}");
        xComboBox2.setItemKey("value");
        xComboBox2.setItems("productTypes");
        xComboBox2.setName("entity.producttype");
        xComboBox2.setPreferredSize(new java.awt.Dimension(210, 22));
        xComboBox2.setRequired(true);
        xFormPanel4.add(xComboBox2);

        xNumberField1.setCaption("Term");
        xNumberField1.setCaptionWidth(100);
        xNumberField1.setFieldType(Integer.class);
        xNumberField1.setName("entity.term");
        xNumberField1.setPreferredSize(new java.awt.Dimension(120, 19));
        xNumberField1.setRequired(true);
        xFormPanel5.add(xNumberField1);

        xNumberField2.setCaption("Amount Applied");
        xNumberField2.setCaptionWidth(100);
        xNumberField2.setFieldType(BigDecimal.class);
        xNumberField2.setHint("0.00");
        xNumberField2.setName("entity.amountapplied");
        xNumberField2.setPattern("0.00");
        xNumberField2.setPreferredSize(new java.awt.Dimension(120, 19));
        xNumberField2.setRequired(true);
        xFormPanel5.add(xNumberField2);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(0, 50));
        xTextArea3.setCaption("Purpose of Loan");
        xTextArea3.setCaptionWidth(100);
        xTextArea3.setHint("Specify purpose of loan");
        xTextArea3.setName("entity.purpose");
        xTextArea3.setRequired(true);
        xTextArea3.setTextCase(com.rameses.rcp.constant.TextCase.UPPER);
        jScrollPane3.setViewportView(xTextArea3);

        xLabel12.setText("Purpose of Loan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(xFormPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xFormPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                    .addComponent(xLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(xFormPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(xFormPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        com.rameses.rcp.control.border.XTitledBorder xTitledBorder3 = new com.rameses.rcp.control.border.XTitledBorder();
        xTitledBorder3.setTitle("Previous Loans");
        jPanel5.setBorder(xTitledBorder3);
        xDataTable1.setColumns(new com.rameses.rcp.common.Column[]{
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "dtreleased"}
                , new Object[]{"caption", "Date Released"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", true}
                , new Object[]{"editableWhen", null}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.DateColumnHandler("yyyy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd")}
            }),
            new com.rameses.rcp.common.Column(new Object[]{
                new Object[]{"name", "loanamount"}
                , new Object[]{"caption", "Loan Amount"}
                , new Object[]{"width", 100}
                , new Object[]{"minWidth", 0}
                , new Object[]{"maxWidth", 0}
                , new Object[]{"required", false}
                , new Object[]{"resizable", true}
                , new Object[]{"nullWhenEmpty", true}
                , new Object[]{"editable", true}
                , new Object[]{"editableWhen", null}
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
                , new Object[]{"editable", true}
                , new Object[]{"editableWhen", null}
                , new Object[]{"textCase", com.rameses.rcp.constant.TextCase.UPPER}
                , new Object[]{"typeHandler", new com.rameses.rcp.common.TextColumnHandler()}
            })
        });
        xDataTable1.setHandler("previousLoansHandler");
        xDataTable1.setName("selectedPreviousLoan");

        xButton1.setText("Add");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xDataTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(xButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(xDataTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.rameses.rcp.control.XButton xButton1;
    private com.rameses.rcp.control.XComboBox xComboBox1;
    private com.rameses.rcp.control.XComboBox xComboBox2;
    private com.rameses.rcp.control.XComboBox xComboBox3;
    private com.rameses.rcp.control.XDataTable xDataTable1;
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
    private com.rameses.rcp.control.XLabel xLabel1;
    private com.rameses.rcp.control.XLabel xLabel12;
    private com.rameses.rcp.control.XLabel xLabel15;
    private com.rameses.rcp.control.XLabel xLabel16;
    private com.rameses.rcp.control.XLabel xLabel17;
    private com.rameses.rcp.control.XLabel xLabel2;
    private com.rameses.rcp.control.XLabel xLabel3;
    private com.rameses.rcp.control.XLabel xLabel4;
    private com.rameses.rcp.control.XLabel xLabel5;
    private com.rameses.rcp.control.XLabel xLabel6;
    private com.rameses.rcp.control.XLabel xLabel7;
    private com.rameses.rcp.control.XLabel xLabel8;
    private com.rameses.rcp.control.XLookupField xLookupField1;
    private com.rameses.rcp.control.XNumberField xNumberField1;
    private com.rameses.rcp.control.XNumberField xNumberField2;
    private com.rameses.rcp.control.XSeparator xSeparator3;
    private com.rameses.rcp.control.XSeparator xSeparator4;
    private com.rameses.rcp.control.XTextArea xTextArea3;
    private com.rameses.rcp.control.XTextArea xTextArea4;
    private com.rameses.rcp.control.XTextArea xTextArea5;
    // End of variables declaration//GEN-END:variables
    
}

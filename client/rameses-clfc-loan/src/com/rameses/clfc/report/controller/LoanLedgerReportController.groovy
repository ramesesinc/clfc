package com.rameses.clfc.report.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.reports.*;

class LoanLedgerReportController extends ReportModel
{    
    @Service('LoanLedgerReportService')
    def reportSvc;

    String title = "Loan Ledger";
    def entity;
    
    void preview() {
        viewReport();
    }
    
    def close() {
        return '_close';
    }
    
    def viewHistory() {
        return InvokerUtil.lookupOpener("loanledger:history", [ledgerid: entity.ledgerid]);
    }

    public Map getParameters() {
        return [:];
    }

    public Object getReportData() {
        return reportSvc.getReportData(entity);
    }

    public String getReportName() {
        return "com/rameses/clfc/report/ledger/BorrowerLedger.jasper";
    }

    public SubReport[] getSubReports() {
        return [
            new SubReport('BORROWERLEDGERPAYMENT', 'com/rameses/clfc/report/ledger/BorrowerLedgerPayment.jasper')
        ];
    }
}

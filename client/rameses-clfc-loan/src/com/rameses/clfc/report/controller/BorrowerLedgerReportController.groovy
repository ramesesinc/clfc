package com.rameses.clfc.report.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*
import com.rameses.osiris2.reports.*;

class BorrowerLedgerReportController extends ReportModel
{    
    @Binding
    def binding;
    
    @Service('LoanLedgerService')
    def ledgerSvc;
    
    @Service('LoanLedgerReportService')
    def reportSvc;
    
    def mode = 'mgmt';
    def selectedLedger;
    def searchtext;
    
    def search() {
        ledgerHandler.reload();
    }
    
    def ledgerHandler = [
        fetchList: {o->
            return ledgerSvc.getList([searchtext: searchtext]);
        },
        onOpenItem: {o, colName->
            return view();
        }
    ] as BasicListModel;
    
    def close() {
        return '_close';
    }
    
    def back() {
        mode = 'mgmt';
        return close();
    }
    
    def view() {
        if(!selectedLedger) {
            throw new Exception('No ledger selected.')
            return null;
        }
        viewReport();
        mode = 'view';
        return new Opener(outcome:'preview');
    }    

    public Map getParameters() {
        return [:];
    }

    public Object getReportData() {
        return reportSvc.getReportData(selectedLedger);
    }

    public String getReportName() {
        return "com/rameses/clfc/report/borrower/ledger/BorrowerLedger.jasper";
    }

    public SubReport[] getSubReports() {
        return [
            new SubReport('BORROWERLEDGERPAYMENT', 'com/rameses/clfc/report/borrower/ledger/BorrowerLedgerPayment.jasper')
        ];
    }
}

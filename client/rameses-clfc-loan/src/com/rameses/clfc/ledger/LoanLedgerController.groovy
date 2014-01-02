package com.rameses.clfc.ledger;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.osiris2.reports.*; 

class LoanLedgerController extends ReportModel
{   
    @Binding
    def binding;

    @Service('LoanLedgerService')
    def svc;

    @Service('LoanLedgerReportService')
    def reportSvc;

    String title = "Loan Ledger";
    def entity;
    def page = 'default';
    
    def preview() {
        viewReport();
        page = 'preview'
        return page;
    }
    
    def back() {
        page = 'default';
        return page;
    }

    def close() {
        return '_close';
    }

    boolean getIsDefault() {
        if (page != 'default') return false;
        return true;
    }

    boolean getIsPreview() {
        if (page != 'preview') return false;
        return true;
    }
    
    def open() {
        entity = svc.open(entity);
        page = 'default';
        return page;
    }

    def viewHistory() {
        return InvokerUtil.lookupOpener("loanledger:history", [ledgerid: entity.objid]);
    }

    void rebuild() {
        if (paymentsHandler.hasUncommittedData())
            throw new Exception('Please commit table data before saving.');
            
        entity = svc.rebuild(entity);
        binding.refresh('entity')
        paymentsHandler.reload();
        MsgBox.alert("Successfully rebuilt collection sheet!");
    }

    def paymentsHandler = [
        fetchList: {
            if (!entity.payments) entity.payments= [];
            entity.payments.eachWithIndex{itm, idx->
                itm.objid = idx;
            }
            return entity.payments;
        },
        createItem: { return [objid: entity.payments.size()] },
        onAddItem: {o->
            entity.payments.add(o);
        },
        onRemoveItem: {o->
            if(MsgBox.confirm('You are about to remove this payment. Continue')) {
                entity.payments.remove(o);
                return true;
            }
            return false;
        }
    ] as EditorListModel;


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

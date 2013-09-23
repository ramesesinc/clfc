package com.rameses.clfc.loan.capture;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.rmi.server.UID;

class LoanAppCaptureLedgerController
{
    def application;
    def entity;
    def mode = 'init';
    def paymentTypes = [
        [name:'Schedule/Advance', value:'advance'],
        [name:'Overpayment', value:'over']
    ]
    
    void init() {
        mode = 'init';
        entity = [
            objid: 'LEDGER'+new UID(),
            payments: [],
        ]
    }
    
    def next() {
        mode = 'create';
        return 'main';
    }
    
    def back() {
        mode = 'init';
        return 'default';
    }
    
    def getAppLookupHandler() {
        def handler = {o->
            entity.appno = o.appno;
            entity.borrower = o.borrower;
            entity.interestrate = o.producttype?.interestrate;
            entity.overduerate = o.producttype?.pastduerate;
            entity.underpaymentrate = o.producttype?.underpaymentpenalty;
            entity.term = o.producttype?.term;
            entity.route = o.route;
        }
        return InvokerUtil.lookupOpener("capture_loanapp:lookup", [onselect:handler]);
    }
    
    def paymentsHandler = [
        fetchList: {o->
            if(!entity.payments) entity.payments = [];
            return entity.payments;
        },
        onCreateItem: {
            return [objid: 'PYMNT'+new UID()]
        },
        onRemoveItem: {
            if(MsgBox.confirm("You are about to remove this payment. Continue?")) {
                entity.payments.remove(o);
                return true;
            }
            return false;
        },
        onCommitItem: {o->
            println o
        }
    ] as EditorListModel;
}
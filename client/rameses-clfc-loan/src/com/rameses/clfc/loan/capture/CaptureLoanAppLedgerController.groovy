package com.rameses.clfc.loan.capture;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.rmi.server.UID;
import com.rameses.clfc.util.*;

class LoanAppCaptureLedgerController
{
    @Binding
    def binding;
    
    @Service('LoanLedgerService')
    def service;
    
    def application;
    def entity;
    def mode = 'read';
    def paymentTypes = LoanUtil.paymentTypes;
      
    void init() {
        mode = 'init';
        entity = [
            objid: 'LEDGER'+new UID(),
            payments: []
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
    
    def save() {
        entity.acctid = entity.borrower.objid;
        entity.acctname = entity.borrower.name;
        println 'payments = '+entity.payments;
        entity = service.create(entity);
        mode = 'read';
        init();
        return 'default';
    }
    
    def cancel() {
        if(MsgBox.confirm("You are about to close this window. Continue?")) {
            return '_close';
        }
    }
    
    def getAppLookupHandler() {
        def handler = {o->
            entity.appno = o.appno;
            entity.loanamount = o.loanamount;
            entity.appid = o.objid;
            entity.borrower = o.borrower;
            entity.producttypeid = o.producttype?.name;
            entity.interestrate = o.producttype?.interestrate;
            entity.overduerate = o.producttype?.overduerate;
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
        createItem: {
            return [objid: 'PYMNT'+new UID()]
        },
        onAddItem: {o->
            entity.payments.add(o);
        },
        onRemoveItem: {o->
            if(MsgBox.confirm("You are about to remove this payment. Continue?")) {
                entity.payments.remove(o);
                return true;
            }
            return false;
        }
    ] as EditorListModel;
}
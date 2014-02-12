package com.rameses.clfc.loan.onlinecollection;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.rmi.server.UID;

class PaymentController
{
    @Binding
    def binding;

    @Service("DateService")
    def dateSvc;

    def entity;
    def bill;
    def service;
    def mode = 'init';
    String preferredTitle = "Online Collection";

    PaymentController() {
        try {
            service = InvokerProxy.instance.create('LoanOnlineCollectionService');
        } catch (ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }
    }

    def lookupBorrowerHandler = InvokerUtil.lookupOpener('ledgerborrower:lookup', [
        onselect: {o->
            entity.borrower = o.borrower;
            entity.route = o.route;
            entity.loanapp = o.loanapp;
            binding.refresh('entity');
        }
    ])

    void init() {
        mode = 'init';
        entity = [
            objid       : 'OCD'+new UID(),
            parentid    : 'OC'+new UID(),
            txndate     : dateSvc.serverDate,
            collector   : [
                objid: ClientContext.currentContext.headers.USERID,
                name: ClientContext.currentContext.headers.NAME
            ]
        ];
    }

    def next() {
        mode = 'collect'
        bill = service.computeBilling(entity);
        entity.refno = bill.refno;
        entity.type = bill.paymentmethod;
        return 'main';
    }

    def back() {
        init();
        binding.refresh('entity');
        return 'default';
    }

    def save() {
        if (MsgBox.confirm("The amount paid is "+entity.amount+". Continue?")) {
            service.create(entity);
            MsgBox.alert("Payment successfully saved!");
            init();
            return 'default';
        }
    }

    def close() {
        return '_close';
    }
}
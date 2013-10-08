package com.rameses.clfc.loan.payment;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class PostPaymentController
{
    @Service('LoanRouteService')
    def routeSvc;
    
    @Service('LoanPaymentService')
    def paymentSvc;
    
    @Service('LoanLedgerService')
    def ledgerSvc;
    
    def title = 'Post Payments';
    def route;
    def entity;
    def mode = 'init';
    def unpostedPayments;
    
    def getRouteList() {
        return routeSvc.getList([:]);
    }
    
    def next() {
        def data = paymentSvc.getUnpostedPayments([route_code: route]);
        entity = data.entity;
        unpostedPayments = data.list;
        if(!unpostedPayments) {
            throw new Exception('No unposted payments for this route.')
            return null;
        }
        mode = 'mgmt';
        paymentHandler.reload();
        return 'mgmtpage';
    }
    
    def back() {
        mode = 'init';
        return 'default';
    }
    
    def post() {
        unpostedPayments.each{
            it.entity = entity;
            paymentSvc.postPayment(it);
        }
        ledgerSvc.approveBatchPayment(entity);
        route = null;
        mode = 'init';
        return 'default';
    }
    
    def close() {
        return '_close';
    }
    
    def paymentHandler = [
        fetchList: {o->
            if(!unpostedPayments) unpostedPayments = [];
            return unpostedPayments;
        }
    ] as BasicListModel;
    
    
    def getTotalamount() {
        if(!unpostedPayments) return 0;
        return unpostedPayments.payamount.sum();
    }
    
    boolean getIsAllowPost() {
        if(mode == 'init' || unpostedPayments.isEmpty()) return false;
        return true;
    }
}
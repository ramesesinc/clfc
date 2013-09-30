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
    
    def route;
    def mode = 'init';
    def unpostedPayments;
    
    def getRouteList() {
        return routeSvc.getList([:]);
    }
    
    def next() {
        mode = 'mgmt';
        unpostedPayments = paymentSvc.getUnpostedPayments([route_code: route]);
        if(!unpostedPayments) {
            throw new Exception('No unposted payments for this route.')
            retur null;
        }
        paymentHandler.reload();
        return 'mgmtpage';
    }
    
    def back() {
        mode = 'init';
        return 'default';
    }
    
    def post() {
        unpostedPayments.each{
            paymentSvc.postPayment(it);
        }
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
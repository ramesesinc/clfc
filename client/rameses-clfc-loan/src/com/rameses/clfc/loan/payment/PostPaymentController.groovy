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
    
    def getRouteList() {
        return routeSvc.getList([:]);
    }
    
    def next() {
        mode = 'mgmt';
        paymentHandler.reload();
        return 'mgmtpage';
    }
    
    def back() {
        mode = 'init';
        return 'default';
    }
    
    def post() {
        list.each{
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
            return list;
        }
    ] as EditorListModel;
    
    def getList() {
        return paymentSvc.getUnpostedPayments([route_code: route]);
    }
    
    def getTotalamount() {
        if(!list) return 0;
        return list.payamount.sum();
    }
    
    boolean getIsAllowPost() {
        if(mode == 'init' || list.isEmpty()) return false;
        return true;
    }
}
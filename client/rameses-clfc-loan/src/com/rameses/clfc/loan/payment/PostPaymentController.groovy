package com.rameses.clfc.loan.payment;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class PostPaymentController
{    
    @Binding
    def binding;
    
    
    @Service('LoanPaymentService')
    def paymentSvc;
    
    @Service('LoanLedgerService')
    def ledgerSvc;
    
    def routeSvc;
    
    def title = 'Post Payments';
    def route;
    def entity;
    def mode = 'init';
    def cashBreakdown;
    def unpostedPayments;
    def prevcashbreakdown = [];
    def breakdown;
    def allowEdit = false;
    def iscollector = false;
    
    PostPaymentController() {
        try {
            routeSvc = InvokerProxy.instance.create("LoanRouteService")
        } catch(ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }        
    }
    
    def getRouteList() {
        return routeSvc.getList([:]);
    }
    
    def next() {
        iscollector = ClientContext.currentContext.headers.ROLES.containsKey('LOAN.FIELD_COLLECTOR');
        def data = paymentSvc.getUnpostedPayments([route_code: route]);
        entity = data.entity;
        unpostedPayments = data.list;
        if (!unpostedPayments) {
            throw new Exception('No unposted payments for this route.')
            return null;
        }
        mode = 'read';
        entity.cashbreakdown = paymentSvc.getCashBreakdown(entity);
        
        if (entity.cashbreakdown) breakdown = entity.cashbreakdown.amount.sum();
        else breakdown = 0;
        
        if (entity.cashbreakdown.isEmpty() && iscollector) {
            mode = 'create';
            allowEdit = true;
        }
        rebuildCashBreakdownInvoker();
        paymentHandler.reload();
        //denominations = paymentSvc.getDenominations(entity);
        //denominationHandler.reload();
        return 'mgmtpage';
    }
    
    def rebuildCashBreakdownInvoker() {
        cashBreakdown =  InvokerUtil.lookupOpener('cash:breakdown', [
            entries: entity.cashbreakdown,
            allowEdit: allowEdit,
            onupdate: {o->
                breakdown = entity.cashbreakdown.amount.sum();
                binding.refresh('breakdown');
            }
        ]);
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
        MsgBox.alert('Transaction has been successfully posted'); 
        return 'default';
    }
    
    def viewExemptions() {
        return InvokerUtil.lookupOpener('exemptions:view', [list: unpostedPayments]);
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
        if (!unpostedPayments) return 0;
        return unpostedPayments.payamount.sum();
    }
    
    def save() {
        if (getTotalamount() != breakdown) 
            throw new Exception('Total for denomination does not match total amount for unposted payments.');
        
        //entity.denominations = denominations;
        if (mode == 'create') {
            paymentSvc.saveCashBreakdown(entity);
        } else if (mode == 'edit') {
            paymentSvc.updateCashBreakdown(entity);
        }
        mode = 'read';
        binding.refresh();
    }
    
    def edit() {
        prevcashbreakdown.clear();
        entity.cashbreakdown.each{
            def m = [:];
            m.putAll(it);
            prevcashbreakdown.add(m);
        }
        mode = 'edit';
        allowEdit = true;
        rebuildCashBreakdownInvoker();        
        if (entity.cashbreakdown) breakdown = entity.cashbreakdown.amount.sum();
        binding.refresh();
    }
    
    def cancel() {
        mode = 'read';
        allowEdit = false;
        entity.cashbreakdown.clear();
        entity.cashbreakdown.addAll(prevcashbreakdown);
        prevcashbreakdown.clear();
        rebuildCashBreakdownInvoker();
        if (entity.cashbreakdown) breakdown = entity.cashbreakdown.amount.sum();
        binding.refresh();
    }
    
    boolean getIsAllowPost() {
        if (mode == 'init' || iscollector || breakdown == 0) return false;
        return true;
    }
    
    boolean getIsAllowSave() {
        if (mode == 'init' || mode == 'read' || !iscollector) return false;
        return true;
    }
    
    boolean getIsAllowEdit() {
        if (mode == 'init' || mode != 'read' || !iscollector) return false;
        return true;
    }
}
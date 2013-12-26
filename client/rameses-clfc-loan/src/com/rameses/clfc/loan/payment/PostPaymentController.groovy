package com.rameses.clfc.loan.payment;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.LoanUtil;

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
    def selectedCollectionsheet = [:];
    def unpostedCollectionSheets;
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
        mode = 'read';
        def data = paymentSvc.getUnpostedCollectionSheets([route_code: route]);
        entity = data.entity;
        unpostedCollectionSheets = data.list;
        if (!unpostedCollectionSheets) {
            throw new Exception('No unposted collection sheets found for this route.')
            return null;
        }
        entity.cashbreakdown = paymentSvc.getCashBreakdown(entity);
        if (entity.cashbreakdown.isEmpty() && iscollector) {
            mode = 'create';
            allowEdit = true;
        }
        denominationHandler.reload();
        return 'mgmtpage';
    }
    
    def back() {
        mode = 'init';
        return 'default';
    }
    
    def post() {
        ledgerSvc.approveBatchPayment([entity: entity, unpostedcollectionsheets: unpostedCollectionSheets]);
        route = null;
        mode = 'init';
        MsgBox.alert('Transaction has been successfully posted'); 
        return 'default';
    }
    
    def viewExemptions() {
        return InvokerUtil.lookupOpener('exemptions:view', [list: unpostedCollectionSheets]);
    }
    
    def close() {
        return '_close';
    }

    public void setSelectedCollectionsheet( selectedCollectionsheet ) {
        this.selectedCollectionsheet = selectedCollectionsheet;
        paymentsHandler.reload();
        notesHandler.reload();
    }
    
    def collectionsheetHandler = [
        fetchList: {o->
            if (!unpostedCollectionSheets) unpostedCollectionSheets = [];
            return unpostedCollectionSheets;
        }
    ] as BasicListModel;    
    
    def paymentsHandler = [
        fetchList: {o->
            if (!selectedCollectionsheet?.payments) selectedCollectionsheet?.payments = [];
            return selectedCollectionsheet.payments;
        }
    ] as BasicListModel;

    def notesHandler = [
        fetchList: {o->
            if (!selectedCollectionsheet?.notes) selectedCollectionsheet?.notes = [];
            return selectedCollectionsheet?.notes;
        }
    ] as BasicListModel;

    def denominationHandler = [
        fetchList: {o->
            if (!entity.cashbreakdown) entity.cashbreakdown = LoanUtil.denominations;
            return entity.cashbreakdown;
        },
        onColumnUpdate: {itm, colName->
            if (colName == 'qty') {
                if (!itm.qty) itm.qty = 0;
                itm.amount = itm.denomination*itm.qty;
                binding.refresh('totalbreakdown');
            }
        },
        isAllowAdd: { return false; }
    ] as EditorListModel;

    def getTotalamount() {
        if (!unpostedCollectionSheets) return 0;
        return unpostedCollectionSheets.total.sum();
    }

    def getTotalbreakdown() {
        if (!entity.cashbreakdown) return 0;
        return entity.cashbreakdown.amount.sum();
    }
    
    def save() {
        if (getTotalamount() != getTotalbreakdown()) 
            throw new Exception('Total for denomination does not matched with total amount for unposted collection sheets.');
        
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
        if (entity.cashbreakdown) binding.refresh('totalbreakdown');
        binding.refresh();
    }
    
    def cancel() {
        mode = 'read';
        allowEdit = false;
        entity.cashbreakdown.clear();
        entity.cashbreakdown.addAll(prevcashbreakdown);
        prevcashbreakdown.clear();
        if (entity.cashbreakdown) binding.refresh('totalbreakdown');
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
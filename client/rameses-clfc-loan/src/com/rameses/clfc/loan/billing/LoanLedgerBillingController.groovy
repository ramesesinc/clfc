package com.rameses.clfc.loan.billing;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanLedgerBillingController extends CRUDController
{
    def service;
    
    String serviceName = 'LoanLedgerBillingService';
    String entityName = 'ledgerbilling';
    def collectorLookupHandler = InvokerUtil.lookupOpener('route-collector:lookup', [:]);
    
    String createFocusComponent = 'entity.collector';
    String editFocusComponent = 'entity.collector';  
    boolean showConfirmOnSave = false;
    boolean allowApprove = false;
    boolean allowCreate = false;
    boolean allowDelete = false;
    
    LoanLedgerBillingController() {
        try {
            service = InvokerProxy.instance.create('LoanLedgerBillingService');
        } catch (ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }
    }
    
    Map createEntity() {
        return [ objid:'LB'+new java.rmi.server.UID(), routes:[] ];
    }
    
    def selectedItem;
    def listHandler = [
        fetchList: { return entity.routes; }
    ] as BasicListModel;
    
    void beforeSave(data) {
        if (!data.routes) throw new Exception('Please specify route(s) for this collector.');
    }
    
    def addItem() {
        def handler = {route->
            if (entity.routes.find{ it.code == route.code })
                throw new Exception('Route '+route.code+' already selected.');
            entity.routes.add(route)
            listHandler.reload();
        }
        return InvokerUtil.lookupOpener('route:lookup', [onselect: handler]);
    }
    
    def removeItem() {
        if (selectedItem == null) return;
        if (MsgBox.confirm("You are about to remove the selected item. Continue?")) {
            entity.routes.remove(selectedItem);
            listHandler.reload();
        }
    }   

    void reset() {
        service.resetBilling(entity);
        MsgBox.alert("Successfully reseted billing!");
    }
}

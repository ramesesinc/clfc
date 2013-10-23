package com.rameses.clfc.loan.billing;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanLedgerBillingController extends CRUDController
{
    String serviceName = 'LoanLedgerBillingService';
    String entityName = 'ledgerbilling';
    def collectorLookupHandler = InvokerUtil.lookupOpener('route-collector:lookup', [:]);
    
    String createFocusComponent = 'entity.code';
    String editFocusComponent = 'entity.description';  
    boolean showConfirmOnSave = false;
    boolean allowApprove = false;
    
    Map createEntity() {
        return [ routes:[] ];
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
}

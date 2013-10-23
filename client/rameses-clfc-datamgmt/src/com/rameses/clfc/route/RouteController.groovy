package com.rameses.clfc.route;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*

class RouteController extends CRUDController
{
    String serviceName = 'LoanRouteService';
    String entityName = 'route';

    String createFocusComponent = 'entity.collector';
    String editFocusComponent = 'entity.collector';             
    boolean showConfirmOnSave = false;
    boolean allowApprove = false;

    Map createPermission = [domain:'DATAMGMT', role:'LOAN_DATAMGMT_AUTHOR', permission:'route.create']; 
    Map editPermission = [domain:'DATAMGMT', role:'LOAN_DATAMGMT_AUTHOR', permission:'route.edit']; 
    Map deletePermission = [domain:'DATAMGMT', role:'LOAN_DATAMGMT_AUTHOR', permission:'route.delete']; 
    Map approvePermission = [domain:'DATAMGMT', role:'LOAN_DATAMGMT_AUTHOR', permission:'route.approve']; 
    
    void entityChanged() {
        //listHandler.reload(); 
    }    
    
    def collectors = [];
    def selectedItem;
    def listHandler = [
        fetchList: {o-> return collectors; }  
    ] as BasicListModel;
    
    void afterCreate(data) {
        collectors = [];
        listHandler.reload();
    }
    
    void afterOpen(data) { 
        loadCollectors(); 
    } 
    
    void loadCollectors() {
        collectors = service.getCollectors([code: entity.code]); 
        listHandler.reload(); 
    }
    
    def addItem() {
        return InvokerUtil.lookupOpener('route-collector:lookup', [
            'query.usergroupid': 'LOAN_FIELD_COLLECTOR', 
            'query.searchtext': '%',                 
            onselect: {o-> 
                o.parentid = entity.code; 
                service.addCollector(o); 
                loadCollectors(); 
            } 
        ]);
    }
    
    void removeItem() {
        if (selectedItem == null) return;
        if (MsgBox.confirm('You are about to remove the selected item. Continue?')) {
            service.removeCollector(selectedItem);
            loadCollectors(); 
        }        
    }    
}

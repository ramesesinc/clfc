package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanAppDetailController 
{
    //feed by the caller
    def loanapp, caller, selectedMenu;
    
    @Service('LoanAppService') 
    def service;

    @Service('NewLoanAppService')
    def newLoanAppService;

    def producttype = [:];
    def clienttype;
    def productTypes;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;
    
    def data = [:];
    
    @PropertyChangeListener
    def listener = [
        "producttype": {o->
            if(o == null) return;
            data.producttype = o
        },
        "clienttype": {o->
            if(o == null) return;
            if(o.value != 'MARKETED') data.marketedby = null;
            data.clienttype = o.value;
        }
    ]

    void init() {
        selectedMenu.saveHandler = { save(); }
        selectedMenu.dataChangeHandler = { dataChange(); }
        data = service.open([objid: loanapp.objid]);
        loanapp.clear();
        loanapp.putAll(data);
        dataChange();
        productTypes = newLoanAppService.initEntity().productTypes;
        producttype = productTypes.find{ it.name == data.producttype.name }
        clienttype = clientTypes.find{ it.value == data.clienttype }
    }
    
    void save() {
        if(data.clienttype == 'MARKETED' && !data.marketedby) 
            throw new Exception('Interviewed by is required.');
        
        if(loanapp.state == 'FOR_INSPECTION' && !data.route?.code) 
            throw new Exception("Route is required.")
        
        service.update(data);
    }
    
    void dataChange() {
        data = loanapp;
        if(data.route.code != null ) {
            def route = data.route;
            route.name = "$route.code - $route.description $route.area";
        }
        else data.route.name = ''    
    }
    
    def getRouteLookupHandler() {
        def handler = {route->
            route.name = "$route.code - $route.description $route.area"
            data.route = route;
        }
        return InvokerUtil.lookupOpener("route:lookup", [onselect:handler])
    }
}

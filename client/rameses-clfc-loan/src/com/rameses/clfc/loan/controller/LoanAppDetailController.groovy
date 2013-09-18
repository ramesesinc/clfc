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

    def schedule = [:];
    def clienttype;
    def productTypes;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;
    
    def entity = [:];
    
    @PropertyChangeListener
    def listener = [
        "schedule": {o->
            if(o == null) return;
            entity.schedule = o
        },
        "clienttype": {o->
            if(o == null) return;
            if(o.value != 'MARKETED') entity.marketedby = null;
            entity.clienttype = o.value;
        }
    ]

    void init() {
        selectedMenu.saveHandler = { save(); } 
        entity = service.open([objid: loanapp.objid]);
        productTypes = newLoanAppService.initEntity().productTypes;
        schedule = productTypes.find{ it.name == entity.schedule.name }
        clienttype = clientTypes.find{ it.value == entity.clienttype }
        if(entity.route.code != null ) {
            def route = entity.route;
            route.name = "$route.code-$route.description $route.area";
        }
        else entity.route.name = ''
    }
    
    void save() {
        if(entity.clienttype == 'MARKETED' && !entity.marketedby) 
            throw new Exception('Interviewed by is required.');
        
        if(loanapp.state == 'FOR_INSPECTION' && !entity.route?.code) 
            throw new Exception("Route is required.")
        
        loanapp.putAll(entity);
        service.update(entity); 
    }
    
    def getRouteLookupHandler() {
        def handler = {route->
            route.name = "$route.code-$route.description $route.area"
            entity.route = route;
        }
        return InvokerUtil.lookupOpener("route:lookup", [onselect:handler])
    }
}

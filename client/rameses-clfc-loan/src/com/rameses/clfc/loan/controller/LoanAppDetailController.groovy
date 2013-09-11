package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;

class LoanAppDetailController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    @Service('LoanAppService') 
    def service;

    @Service('LoanProductTypeService')
    def productTypeService;

    def schedule;
    def productTypes;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;
    
    def entity = [:];
    def routeLookupHandler = InvokerUtil.lookupOpener("route:lookup", [:]);
    
    @PropertyChangeListener
    def listener = [
        "schedule": {o->
            entity.schedule = o
        }
    ]

    void init() {
        handlers.saveHandler = { save(); } 
        entity = service.open([objid: loanapp.objid]);
        productTypes = productTypeService.getList([:]);
        schedule = productTypes.find{ it.name == entity.schedule.name }
    }
        
    void save() {
        if( loanapp.state == 'FOR_INSPECTION' && !entity.route ) 
            throw new Exception("Route is required.")

        service.update(entity); 
    }
}

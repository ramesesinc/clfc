package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;

class LoanAppPrincipalBorrowerController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    @Service('PrincipalBorrowerService') 
    def service;    
    
    def beforeSaveHandlers = [:];

    void init() {
        if (loanapp.objid == null) return;
        
        handlers.saveHandler = { save(); }          
        def data = service.open([objid: loanapp.objid]); 
        loanapp.clear();
        loanapp.putAll(data); 
    }

    def createOpenerParams() {
        return [
            beforeSaveHandlers: beforeSaveHandlers, 
            service: service, 
            loanapp: loanapp, 
            mode: caller.mode 
        ]; 
    }
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('loanapp-borrower:plugin', createOpenerParams());
        },
        getOpenerParams: {
            return createOpenerParams(); 
        }
    ] as TabbedPaneModel 
    
    void save() {
        beforeSaveHandlers.each{k,v-> 
            if (v != null) v(); 
        }
        
        def data = [
            objid: loanapp.objid, 
            borrower: loanapp.borrower 
        ]; 
        service.update(data);
    }
}

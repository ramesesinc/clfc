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
    
    @Service('BorrowerService') 
    def service;    
    
    def beforeSaveHandlers = [:];
    
    void setHandlers(handlers) {
        this.handlers = handlers;
        handlers.saveHandler = { save(); }  
        if (loanapp.borrower?.objid == null) return;
        
        loanapp.borrower = service.open([objid: loanapp.borrower.objid]); 
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
        
        def data = loanapp.borrower;
        data._loanappid = loanapp.objid; 
        data._loanappno = loanapp.appno;
        data._datatype = 'principalborrower';
        service.update(data);
    }
}

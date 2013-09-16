package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;
import com.rameses.clfc.borrower.*;

class LoanAppPrincipalBorrowerController   
{
    //feed by the caller
    def caller, handlers, loanapp; 
    
    @Service('PrincipalBorrowerService') 
    def service; 
    
    private def beforeSaveHandlers = [:];
    private def dataChangeHandlers = [:];

    void init() {
        if (loanapp.objid == null) return;
        
        handlers.saveHandler = { save(); }          
        def data = service.open([objid: loanapp.objid]);
        data.borrower.type = 'PRINCIPAL'
        loanapp.clear();
        loanapp.putAll(data); 
        println 'attached handlers-> ' + handlers;
    }

    def createOpenerParams() {
        def ctx = new BorrowerContext(caller, this, service, loanapp);
        ctx.beforeSaveHandlers = beforeSaveHandlers;
        ctx.dataChangeHandlers = dataChangeHandlers;
        return [ borrowerContext: ctx ]; 
    }
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('loanapp-borrower:plugin', createOpenerParams());
        },
        getOpenerParams: {
            return createOpenerParams(); 
        },
        beforeSelect: {item,index-> 
            if (caller?.mode == 'read' || index == 0) return true; 
        
            return (loanapp.borrower?.objid != null); 
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

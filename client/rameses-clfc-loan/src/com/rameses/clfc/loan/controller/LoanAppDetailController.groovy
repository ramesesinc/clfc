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
    
    def entity = [:];
    
    void setHandlers(handlers) { 
        this.handlers = handlers; 
        handlers.saveHandler = { save(); }  
        entity = service.open([objid: loanapp.objid]); 
    }
        
    void save() {
        service.update(data); 
    } 
}

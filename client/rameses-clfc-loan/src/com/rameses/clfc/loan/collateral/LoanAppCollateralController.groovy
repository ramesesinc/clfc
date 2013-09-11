package com.rameses.clfc.loan.business;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanAppCollateralController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    @Service('LoanAppCollateralService') 
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

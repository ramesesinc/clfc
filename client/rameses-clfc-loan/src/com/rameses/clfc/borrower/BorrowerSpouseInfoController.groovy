package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerSpouseInfoController 
{
    //feed by the caller
    def borrowerContext;
    
    //declare variables
    def entity = [:];
    
    void init() {        
        initEntity();
        borrowerContext.addDataChangeHandler('spouseinfo', { initEntity() });
    } 
    
    void initEntity() {
        entity = borrowerContext.borrower.spouse;
        if (entity == null) {
            borrowerContext.borrower.spouse = [:]; 
            entity = borrowerContext.borrower.spouse;
        }
    }
    
    def getLookupBorrower() { 
        def params = [ 
            'query.loanappid': borrowerContext.loanapp.objid, 
            onselect: {o-> 
                entity.putAll(o); 
            } 
        ]; 
        return InvokerUtil.lookupOpener('customer:lookup', params); 
    } 
}

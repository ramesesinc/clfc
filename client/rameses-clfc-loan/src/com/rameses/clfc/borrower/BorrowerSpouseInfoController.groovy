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
        entity = borrowerContext.spouse;
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

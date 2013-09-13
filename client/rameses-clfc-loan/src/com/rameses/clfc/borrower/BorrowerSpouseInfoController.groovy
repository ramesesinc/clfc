package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerSpouseInfoController 
{
    def borrowerContext;
    def entity = [:];
    
    void init() {
        if( borrowerContext.loanapp.borrower?.spouse == null )
            borrowerContext.loanapp.borrower.spouse = [:];
       
        entity = borrowerContext.loanapp.borrower.spouse;
        if (entity != null) {
            def name = entity.lastname + ', ' + entity.firstname;
            if (entity.middlename) name = name + ' ' + entity.middlename;
            if( entity.lastname == null ) name = ''
            entity.name = name;
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

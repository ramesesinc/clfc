package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerGeneralInfoController 
{
    //feed by the caller
    def loanapp, mode;
    
    def entity = [:];
    
    void setLoanapp(loanapp) {
        this.loanapp = loanapp;
        entity = loanapp.borrower;
        if (entity != null) {
            def name = entity.lastname + ', ' + entity.firstname;
            if (entity.middlename) name = name + ' ' + entity.middlename;
            
            entity.name = name;
        }
    }
    
    def getLookupBorrower() {  
        def params = [
            'query.loanappid': loanapp.objid, 
            onselect: {o-> 
                entity.putAll(o); 
            }
        ];
        return InvokerUtil.lookupOpener('customer:lookup', params);
    }
}
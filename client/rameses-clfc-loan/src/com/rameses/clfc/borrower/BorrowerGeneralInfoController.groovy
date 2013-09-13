package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerGeneralInfoController 
{
    def borrowerContext;
    def entity = [:];
    
    void init() {
        entity = borrowerContext.borrower;
    }
    
    def getLookupBorrower() {  
        def params = [
            'query.loanappid': borrowerContext.loanapp.objid, 
            onselect: {o-> 
                def borrower = null; 
                try { borrower = borrowerContext.service.openBorrower([objid: o.objid]); } catch(Throwable t){;} 
                
                if (borrower == null) { 
                    entity.putAll(o); 
                } else {
                    entity.clear(); 
                    entity.putAll(borrower);
                }
                borrowerContext.caller.tabHandler.refresh();
            }
        ];
        return InvokerUtil.lookupOpener('customer:lookup', params);
    }
}
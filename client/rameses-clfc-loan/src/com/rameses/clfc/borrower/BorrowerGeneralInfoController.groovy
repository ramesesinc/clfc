package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerGeneralInfoController 
{
    //feed by the caller
    def service, loanapp, mode, borrower;
    
    def entity = [:];
    
    void init() {
        entity = borrower;
    }
    
    def getLookupBorrower() {  
        def params = [
            'query.loanappid': loanapp.objid, 
            onselect: {o-> 
                def borrower = null; 
                try { borrower = service.openBorrower([objid: o.objid]); } catch(Throwable t){;} 
                
                if (borrower == null) { 
                    entity.putAll(o); 
                } else {
                    entity.clear(); 
                    entity.putAll(borrower);
                }                    
            }, 
            onempty: { 
                entity.clear(); 
            }
        ];
        return InvokerUtil.lookupOpener('customer:lookup', params);
    }
}
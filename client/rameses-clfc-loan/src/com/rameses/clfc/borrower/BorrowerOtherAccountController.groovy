package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import java.rmi.server.UID;

class BorrowerOtherAccountController
{
    def borrowerContext;
    def entity = [:];
    
    void init() {
        if( borrowerContext.borrower?.otheracct == null )
            borrowerContext.borrower.otheracct = [:]
        
        entity = borrowerContext.borrower?.otheracct
        entity.borrowerid = borrowerContext.borrower?.objid;
        entity.objid = 'BOA'+new UID();
    }
}
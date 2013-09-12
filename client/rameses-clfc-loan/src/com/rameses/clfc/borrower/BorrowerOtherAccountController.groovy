package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import java.rmi.server.UID;

class BorrowerOtherAccountController
{
    def loanapp, mode, borrower;
    def entity = [:];
    
    void init() {
        if( borrower?.otheracct == null )
            borrower.otheracct = [:]
        
        entity = borrower?.otheracct
        entity.borrowerid = borrower?.objid;
        entity.objid = 'BOA'+new UID();
    }
}
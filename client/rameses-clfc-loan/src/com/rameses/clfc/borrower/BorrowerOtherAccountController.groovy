package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import java.rmi.server.UID;

class BorrowerOtherAccountController
{
    def loanapp, mode;
    def entity = [:];
    
    void setLoanapp( loanapp ) {
        this.loanapp = loanapp;
        
        if( loanapp.borrower?.otheracct == null )
            loanapp.borrower.otheracct = [:];
            
        entity = loanapp.borrower.otheracct;
        entity.borrowerid = loanapp.borrower?.objid;
        entity.objid = 'BOA'+new UID();
    }
}
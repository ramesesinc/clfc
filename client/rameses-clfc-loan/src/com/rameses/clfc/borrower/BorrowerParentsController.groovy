package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import java.rmi.server.UID;

class BorrowerParentsController
{
    def loanapp, mode, beforeSaveHandlers, borrower;
    def entity = [:]
    
    void init() {
        if( borrower?.parent == null )
            borrower.parent = [:]
                    
        entity = borrower?.parent;
        entity.objid = borrower?.objid;
        beforeSaveHandlers.parentHandler = { validate(); }
    }

    void validate() {
        if( entity ) {
            def hasEntry = (entity.fathername || entity.mothername || entity.fatherage || entity.motherage || entity.address || entity.remarks)
            if( hasEntry ) {
                if( !entity.fathername ) throw new Exception("Father's name is required.")
                if( !entity.mothername ) throw new Exception("Mother's name is required.")
                if( !entity.fatherage ) throw new Exception("Father's age is required.")
                if( !entity.motherage ) throw new Exception("Mother's age is required.")
                if( !entity.address ) throw new Exception("Address is required.")
            }
        }
    }
}
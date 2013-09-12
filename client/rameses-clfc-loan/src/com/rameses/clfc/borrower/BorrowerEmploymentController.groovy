package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEmploymentController
{
    def loanapp, mode, borrower;    

    def selectedEmployment;
    def employmentHandler = [
        fetchList: {o->
            if( !borrower.employments ) borrower.employments = [];
            borrower.employments.each{ it._filetype = "employment" }
            return borrower.employments;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            return [mode: mode]
        }
    ] as EditorListModel; 
    
    def addEmployment() {
        def handler = {employment->
            employment.refid = borrower?.objid;
            borrower.employments.add(employment);
            employmentHandler.reload();
        }
        return InvokerUtil.lookupOpener("employment:create", [handler:handler])
    }    
    
    void removeEmployment() {
        removeItemImpl(selectedEmployment);
    }
    
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrower.employments.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }    
}
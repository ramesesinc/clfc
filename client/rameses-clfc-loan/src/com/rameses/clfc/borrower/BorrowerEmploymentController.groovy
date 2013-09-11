package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEmploymentController
{
    def loanapp, mode;    

    def selectedEmployment;
    def employmentHandler = [
        fetchList: {o->
            if( !loanapp.borrower.employments ) loanapp.borrower.employments = [];
            loanapp.borrower.employments.each{ it._filetype = "employment" }
            return loanapp.borrower.employments;
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
            employment.refid = loanapp.borrower?.objid;
            loanapp.borrower.employments.add(employment);
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
            loanapp.borrower.employments.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }    
}
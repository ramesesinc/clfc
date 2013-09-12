package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEducationController
{
    //feed by the caller
    def loanapp, mode, borrower;

    def selectedEducation;
    def educationHandler = [
        fetchList: {o->
            if( !borrower.educations ) borrower.educations = []
            borrower.educations.each{ it._filetype = "education" }
            return borrower.educations;
        },
        onRemoveItem: {o->
            return removeItemImpl(o); 
        },
        getOpenerParams: {o->
            return [ mode: mode ]
        }
    ] as EditorListModel;
    
    def addEducation() {
        def handler = {education->
            education.borrowerid = borrower?.objid;
            borrower.educations.add(education);
            educationHandler.reload();
        }
        return InvokerUtil.lookupOpener("education:create", [handler:handler]);
    }    
    
    void removeEducation() {
        removeItemImpl(selectedEducation);
    }
            
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrower.educations.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }             
}
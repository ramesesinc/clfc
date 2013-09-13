package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEducationController
{
    def borrowerContext;
    def selectedEducation;
    def educationHandler = [
        fetchList: {o->
            if( !borrowerContext.borrower.educations ) borrowerContext.borrower.educations = []
            borrowerContext.borrower.educations.each{ it._filetype = "education" }
            return borrowerContext.borrower.educations;
        },
        onRemoveItem: {o->
            return removeItemImpl(o); 
        },
        getOpenerParams: {o->
            return [ mode: borrowerContext.mode ]
        }
    ] as EditorListModel;
    
    def addEducation() {
        def handler = {education->
            education.borrowerid = borrowerContext.borrower?.objid;
            borrowerContext.borrower.educations.add(education);
            educationHandler.reload();
        }
        return InvokerUtil.lookupOpener("education:create", [handler:handler]);
    }    
    
    void removeEducation() {
        removeItemImpl(selectedEducation);
    }
            
    boolean removeItemImpl(o) {
        if (borrowerContext.mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrowerContext.borrower.educations.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }             
}
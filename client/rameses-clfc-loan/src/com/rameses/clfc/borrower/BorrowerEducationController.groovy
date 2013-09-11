package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEducationController
{
    //feed by the caller
    def loanapp, mode;

    def selectedEducation;
    def educationHandler = [
        fetchList: {o->
            if( loanapp.borrower.educations == null ) loanapp.borrower.educations = []
            loanapp.borrower.educations.each{ it._filetype = "education" }
            return loanapp.borrower.educations;
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
            education.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.educations.add(education);
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
            loanapp.borrower.educations.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }             
}
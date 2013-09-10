package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerEducationController
{
    def loanapp, mode;

    def addEducation() {
        def handler = {education->
            education.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.educations.add(education);
            educationHandler.reload();
        }
        return InvokerUtil.lookupOpener("education:create", [handler:handler]);
    }

    def educationHandler = [
        fetchList: {o->
            if( loanapp.borrower.educations == null ) loanapp.borrower.educations = []
            loanapp.borrower.educations.each{ it._filetype = "education" }
            return loanapp.borrower.educations;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this education. Continue?")) {
                loanapp.borrower.educations(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [ mode: mode ]
        }
    ] as EditorListModel
}
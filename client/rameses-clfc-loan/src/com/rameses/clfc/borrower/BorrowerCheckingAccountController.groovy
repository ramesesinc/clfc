package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerCheckingAccountController
{
    def loanapp, mode;

    def selectedCheckingAcct;
    def checkingAcctHandler = [
        fetchList: {o->
            if( !loanapp.borrower.checkingaccts ) loanapp.borrower.checkingaccts = [];
            loanapp.borrower.checkingaccts.each{ it._filetype = "checking"; }
            return loanapp.borrower.checkingaccts;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            return [ mode: mode ]
        }
    ] as EditorListModel; 
    
    def addCheckingAcct() {
        def handler = {acct->
            acct.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.checkingaccts.add(acct);
            checkingAcctHandler.reload();
        }
        return InvokerUtil.lookupOpener("checking:create", [handler:handler]);
    }
    
    void removeEducation() {
        removeItemImpl(selectedCheckingAcct);
    }
            
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            loanapp.borrower.checkingaccts.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }      
}
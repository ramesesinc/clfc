package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSavingsAcctController
{
    def loanapp, mode;

    def selectedSavingAcct;
    def savingsAcctHandler = [
        fetchList: {o->
            if( !loanapp.borrower.savingaccts ) loanapp.borrower.savingaccts = [];
            loanapp.borrower.savingaccts.each{ it._filetype = "saving"; }
            return loanapp.borrower.savingaccts;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            return [mode: mode]
        }
    ] as EditorListModel; 
    
    def addSavingAcct() {
        def handler = {acct->
            acct.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.savingaccts.add(acct);
            savingsAcctHandler.reload();
        }
        return InvokerUtil.lookupOpener("saving:create", [handler:handler]);
    }    
    
    void removeSavingAcct() {
        removeItemImpl(selectedSavingAcct);
    }
            
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            loanapp.borrower.savingaccts.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }      
}
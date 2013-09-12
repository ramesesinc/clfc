package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSavingsAcctController
{
    def loanapp, mode, borrower;

    def selectedSavingAcct;
    def savingsAcctHandler = [
        fetchList: {o->
            if( !borrower.savingaccts ) borrower.savingaccts = [];
            borrower.savingaccts.each{ it._filetype = "saving"; }
            return borrower.savingaccts;
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
            acct.borrowerid = borrower?.objid;
            borrower.savingaccts.add(acct);
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
            borrower.savingaccts.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }      
}
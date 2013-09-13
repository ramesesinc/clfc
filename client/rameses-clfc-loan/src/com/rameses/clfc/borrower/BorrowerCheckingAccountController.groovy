package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class BorrowerCheckingAccountController
{
    def borrowerContext;
    def selectedCheckingAcct;
    def checkingAcctHandler = [
        fetchList: {o->
            if( !borrowerContext.borrower.checkingaccts ) borrowerContext.borrower.checkingaccts = [];
            borrowerContext.borrower.checkingaccts.each{ it._filetype = "checking"; }
            return borrowerContext.borrower.checkingaccts;
        },
        onRemoveItem: {o->
            return removeCheckingAcctImpl(o);
        },
        getOpenerParams: {o->
            return [ mode: borrowerContext.mode ]
        }
    ] as EditorListModel; 
    
    def addCheckingAcct() {
        def handler = {acct->
            acct.borrowerid = borrowerContext.borrower?.objid;
            borrowerContext.borrower.checkingaccts.add(acct);
            checkingAcctHandler.reload();
        }
        return InvokerUtil.lookupOpener("checking:create", [handler:handler]);
    }
    
    void removeCheckingAcct() {
        removeCheckingAcctImpl(selectedCheckingAcct);
    }
            
    boolean removeCheckingAcctImpl(o) {
        if (borrowerContext.mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this account. Continue?")) {
            borrowerContext.borrower.checkingaccts.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }
    
    def getHtmlview() {
        def html=new HtmlBuilder();
        return html.buildBankAccount(selectedCheckingAcct);
    }
}
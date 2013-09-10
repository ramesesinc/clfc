package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerCheckingAccountController
{
    def loanapp, mode;

    def addCheckingAcct() {
        def handler = {acct->
            acct.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.checkingaccts.add(acct);
            checkingAcctHandler.reload();
        }
        return InvokerUtil.lookupOpener("checking:create", [handler:handler]);
    }

    def checkingAcctHandler = [
        fetchList: {o->
            if( !loanapp.borrower.checkingaccts ) loanapp.borrower.checkingaccts = [];
            loanapp.borrower.checkingaccts.each{ it._filetype = "checking"; }
            return loanapp.borrower.checkingaccts;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this account. Continue?")) {
                loanapp.borrower.checkingaccts.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [ mode: mode ]
        }
    ] as EditorListModel
}
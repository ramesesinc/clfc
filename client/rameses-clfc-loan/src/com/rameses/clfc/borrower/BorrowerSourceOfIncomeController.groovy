package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSourceOfIncomeController
{
    def loanapp, mode;
    
    def addOtherIncome() {
        def handler = {otherincome->
            otherincome.refid = loanapp.borrower?.objid;
            loanapp.borrower.otherincomes.add(otherincome);
            otherIncomeHandler.reload();
        }
        return InvokerUtil.lookupOpener("otherincome:create", [handler:handler]);
    }

    def otherIncomeHandler = [
        fetchList: {o->
            if( !loanapp.borrower.otherincomes ) loanapp.borrower.otherincomes = [];
            loanapp.borrower.otherincomes.each{ it._filetype = "otherincome"; }
            return loanapp.borrower.otherincomes;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this source of income. Continue?")) {
                loanapp.borrower.otherincomes.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: mode]
        }
    ] as EditorListModel
}
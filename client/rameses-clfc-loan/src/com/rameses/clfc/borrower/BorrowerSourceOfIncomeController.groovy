package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSourceOfIncomeController
{
    def loanapp, mode, borrower;   

    def selectedOtherIncome;
    def otherIncomeHandler = [
        fetchList: {o->
            if( !borrower.otherincomes ) borrower.otherincomes = [];
            borrower.otherincomes.each{ it._filetype = "otherincome"; }
            return borrower.otherincomes;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            return [mode: mode]
        }
    ] as EditorListModel;
    
    def addOtherIncome() {
        def handler = {otherincome->
            otherincome.refid = borrower?.objid;
            borrower.otherincomes.add(otherincome);
            otherIncomeHandler.reload();
        }
        return InvokerUtil.lookupOpener("otherincome:create", [handler:handler]);
    }
    
    void removeOtherIncome() {
        removeItemImpl(selectedOtherIncome);
    }
            
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrower.otherincomes.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }    
}
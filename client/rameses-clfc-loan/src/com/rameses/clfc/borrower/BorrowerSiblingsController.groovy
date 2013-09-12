package com.rameses.clfc.loan.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSiblingsController
{
    def loanapp, mode, borrower;

    def selectedSibling;
    def siblingsHandler = [
        fetchList: {o->
            if( !borrower.siblings ) borrower.siblings = [];
            borrower.siblings.each{ it._filetype = "sibling"; }
            return borrower.siblings;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            return [mode: mode]
        }
    ] as EditorListModel;
    
    def addSibling() {
        def handler = {sibling->
            sibling.borrowerid = borrower?.objid;
            borrower.siblings.add(sibling);
            siblingsHandler.reload();
        }
        return InvokerUtil.lookupOpener("sibling:create", [handler:handler])
    }    
    
    void removeSibling() {
        removeItemImpl(selectedSibling);
    }
            
    boolean removeItemImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrower.siblings.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }    
}
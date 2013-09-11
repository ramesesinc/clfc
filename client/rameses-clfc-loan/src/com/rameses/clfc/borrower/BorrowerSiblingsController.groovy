package com.rameses.clfc.loan.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class BorrowerSiblingsController
{
    def loanapp, mode;

    def selectedSibling;
    def siblingsHandler = [
        fetchList: {o->
            if( loanapp.borrower.siblings == null ) loanapp.borrower.siblings = [];
            loanapp.borrower.siblings.each{ it._filetype = "sibling"; }
            return loanapp.borrower.siblings;
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
            sibling.borrowerid = loanapp.borrower?.objid;
            loanapp.borrower.siblings.add(sibling);
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
            loanapp.borrower.siblings.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }    
}
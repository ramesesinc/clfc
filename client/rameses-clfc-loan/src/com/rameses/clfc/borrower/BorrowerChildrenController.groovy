package com.rameses.clfc.borrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;

class BorrowerChildrenController 
{
    //feed by the caller
    def loanapp, mode, borrower;

    def selectedChild;
    def childrenHandler = [
        fetchList: {o->
            if( !borrower.children ) borrower.children = []
            borrower.children.each{ it._filetype = "child" }
            return borrower.children;
        },
        onRemoveItem: {o->
            return removeChildImpl(o);
        },
        getOpenerParams: {o->
            return [ mode: mode ]
        }
    ] as EditorListModel

    def addChild() {
        def handler = {child->
            child.borrowerid = borrower?.objid;
            borrower.children.add(child);
            childrenHandler.reload();
        }
        return InvokerUtil.lookupOpener("child:create", [handler:handler]);
    }
    
    void removeChild() {
        removeChildImpl(selectedChild); 
    }
    
    boolean removeChildImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this child. Continue?")) {
            borrower.children.remove(o);
            return true;
        } else { 
            return false; 
        } 
    } 
}

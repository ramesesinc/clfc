package com.rameses.clfc.loan.otherlending;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;

class LoanAppOtherLendingController
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    @Service('LoanAppOtherLendingService') 
    def service; 
    
    def otherlendings = [];
    def selectedOtherLending;
    
    void init() {
        handlers.saveHandler = { save(); }  
        def data = service.open([objid: loanapp.objid]);
        loanapp.clear();
        loanapp.putAll(data);
        otherlendings = loanapp.otherlendings;
    }
        
    void save() {
        def data = [ objid: loanapp.objid, otherlendings: otherlendings ]
        service.update(data); 
    }

    def addOtherLending() {
        def handler = {otherlending->
            otherlending.parentid = loanapp.objid;
            otherlendings.add(otherlending);
            otherLendingHandler.reload();
        }
        return InvokerUtil.lookupOpener("otherlending:create", [handler:handler]);
    }
    
    def otherLendingHandler = [
        fetchList: {o->
            if( !otherlendings ) otherlendings = [];
            otherlendings.each{ it._filetype = "otherlending" }
            return otherlendings;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this lending. Continue?")) {
                otherlendings.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: mode];
        }
    ] as EditorListModel
}
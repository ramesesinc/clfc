package com.rameses.clfc.loan.jointborrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.BorrowerContext;

class JointBorrowerController 
{
    //feed by the caller
    def loanapp, service, beforeSaveHandlers, callBackHandler, caller, dataChangeHandlers;  

    @ChangeLog
    def changeLog    
    
    void init() {
        if (loanapp.borrower?.objid == null) return;

        def filetype = loanapp.borrower._filetype;
        def relation = loanapp.borrower.relation;
        def data = service.openBorrower([objid: loanapp.borrower.objid]);
        data.type = 'JOINT';
        data._filetype = filetype;
        data.relation = relation;
        loanapp.borrower.clear();
        loanapp.borrower.putAll(data); 
    }
    
    def createOpenerParams() {
        def ctx = new BorrowerContext(caller, this, service, loanapp);
        ctx.beforeSaveHandlers = beforeSaveHandlers;
        ctx.dataChangeHandlers = dataChangeHandlers;
        return [borrowerContext: ctx];
    }
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('loanapp-jointborrower:plugin', createOpenerParams());
        },
        getOpenerParams: {
            return createOpenerParams(); 
        }
    ] as TabbedPaneModel 
            
    def doOk() {
        if( callBackHandler ) callBackHandler(loanapp.borrower);
        return "_close";
    }
    
    def doCancel() {
        if( caller.mode == 'edit' ) {
            if( !MsgBox.confirm("Changes will be discarded. Continue?") ) return null

            if( changeLog.hasChanges() ) {
                changeLog.undoAll()
                changeLog.clear()
            }
        }
        return "_close"
    }
}

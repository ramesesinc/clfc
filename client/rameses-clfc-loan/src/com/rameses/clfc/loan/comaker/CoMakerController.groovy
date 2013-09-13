package com.rameses.clfc.loan.comaker;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CoMakerController 
{
    def loanapp, mode, handlers, service, beforeSaveHandlers, callBackHandler;  

    @ChangeLog
    def changeLog
        
    void init() {
        if (loanapp.borrower?.objid == null) return;

        def relation = loanapp.borrower.relation
        def data = service.openBorrower([objid: loanapp.borrower.objid]);
        data.type = 'COMAKER';
        data.relation = relation;
        loanapp.borrower.clear();
        loanapp.borrower.putAll(data); 
    }
    
    def createOpenerParams() {
        def borrowerContext = [
            beforeSaveHandlers: beforeSaveHandlers,
            loanapp: loanapp,
            borrower: loanapp.borrower,
            mode: mode 
        ]; 
        return [borrowerContext: borrowerContext];
    }
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('loanapp-comaker:plugin', createOpenerParams());
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
        if( mode == 'edit' ) {
            if( !MsgBox.confirm("Changes will be discarded. Continue?") ) return null

            if( changeLog.hasChanges() ) {
                changeLog.undoAll()
                changeLog.clear()
            }
        }
        return "_close"
    }
}

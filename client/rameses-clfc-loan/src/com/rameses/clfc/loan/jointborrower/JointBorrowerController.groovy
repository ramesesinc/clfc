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
    def source;
    
    void init() {
        if (loanapp.borrower?.objid == null) {
            loanapp.borrower = [
                residency:[:],
                occupancy:[:]
            ];
            return;
        }
        
        source = loanapp.borrower;
        loanapp.borrower = [:];
        loanapp.borrower.putAll(source);
        
        def borrower = loanapp.borrower;
        def spouse = loanapp.borrower.spouse;
        def filetype = loanapp.borrower._filetype;
        def relation = loanapp.borrower.relation;
        def data = service.openBorrower([objid: loanapp.borrower.objid]);
        data.type = 'JOINT';
        data._filetype = filetype;
        data.relation = relation;
        data.residency = borrower.residency;
        data.occupancy = borrower.occupancy;
        data.spouse.residency = spouse.residency;
        data.spouse.occupancy = spouse.occupancy;
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
        },
        beforeSelect: {item,index-> 
            if (caller?.mode == 'read' || index == 0) return true; 
        
            return (loanapp.borrower?.objid != null); 
        }
    ] as TabbedPaneModel 
            
    def doOk() {
        validate();
        beforeSaveHandlers.each{k,v-> 
            if (v != null) v(); 
        }

        if( callBackHandler ) callBackHandler(loanapp.borrower);
        else {
            source.putAll(loanapp.borrower);
            loanapp.borrower = source;
        }
        return "_close";
    }
    
    def doCancel() {
        if( caller.mode == 'edit' ) {
            if( !MsgBox.confirm("Changes will be discarded. Continue?") ) return null
            loanapp.borrower = source;
        }
        return "_close"
    }
    
    void validate() {
        def spouse = loanapp.borrower.spouse;
        if(spouse?.objid != null) {
            if(!spouse.residency?.type) throw new Exception('Spouse Residency: Type is required.');
            if(!spouse.residency?.since) throw new Exception('Spouse Residency: Since is required.');
            if(spouse.residency?.type == 'RENTED') {
                if(!spouse.residency?.renttype) throw new Exception('Spouse Residency: Rent Type is required.');
                if(!spouse.residency?.rentamount) throw new Exception('Spouse Residency: Rent Amount is required.');
            }
            if(!spouse.occupancy?.type) throw new Exception('Spouse Lot Occupancy: Type is required.');            
            if(!spouse.occupancy?.since) throw new Exception('Spouse Lot Occupancy: Since is required.');
            if(spouse.occupancy?.type == 'RENTED') {
                if(!spouse.occupancy?.renttype) throw new Exception('Spouse Lot Occupancy: Rent Type is required.');
                if(!spouse.occupancy?.rentamount) throw new Exception('Spouse Lot Occupancy: Rent Amount is required.');
            }
        }
    }
}

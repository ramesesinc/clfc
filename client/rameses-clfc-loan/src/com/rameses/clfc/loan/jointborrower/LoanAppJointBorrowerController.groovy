package com.rameses.clfc.loan.jointborrower;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class LoanAppJointBorrowerController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    def beforeSaveHandlers = [:];
    
    @Service('JointBorrowerService') 
    def service; 
    
    def borrowers = [];
    def selectedJointBorrower;
    
    void init() {
        handlers.saveHandler = { save(); }  
        def data = service.open([objid: loanapp.objid]);
        loanapp.clear();
        loanapp.putAll(data);
        borrowers = loanapp.borrowers;
    }
        
    void save() {
        def data = [ objid: loanapp.objid, borrowers: borrowers ]
        service.update(data); 
    }

    def addJointBorrower() {
        def params = createOpenerParams()
        params.callBackHandler = {jointborrower->
            borrowers.add(jointborrower);
            jointBorrowerHandler.reload();
        };
        return InvokerUtil.lookupOpener("jointborrower:create", params)
    }
    
    def createOpenerParams() {
        return [
                loanapp: [:],
                mode: caller.mode, 
                service: service, 
                handlers: handlers, 
                beforeSaveHandlers:beforeSaveHandlers
        ]
    }
    
    def jointBorrowerHandler = [
        fetchList: {o->
            if( !borrowers ) borrowers = []
            borrowers.each{ it._filetype = "jointborrower" }
            return borrowers;
        },
        onRemoveItem: {o->
            return removeItemImpl(o);
        },
        getOpenerParams: {o->
            def params = createOpenerParams()
            params.loanapp.borrower = o;
            return params
        }
    ] as EditorListModel;
        
    void removeJointBorrower() {
        removeItemImpl(selectedJointBorrower);
    }
            
    boolean removeItemImpl(o) {
        if (caller.mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this item. Continue?")) {
            borrowers.remove(o);
            return true;
        } else { 
            return false; 
        } 
    } 
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildBorrower(selectedJointBorrower)
    }
}

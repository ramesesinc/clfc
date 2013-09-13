package com.rameses.clfc.loan.comaker;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class LoanAppCoMakerController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    def beforeSaveHandlers = [:];
    
    @Service('ComakerService') 
    def service; 
    
    def borrowers = [];
    def selectedCoMaker;
    
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

    def addCoMaker() {
        def params = createOpenerParams()
        params.callBackHandler = {comaker->
            borrowers.add(comaker);
            coMakerHandler.reload();
        };
        return InvokerUtil.lookupOpener("comaker:create", params)
    }
    
    def createOpenerParams() {
        if( selectedCoMaker == null ) selectedCoMaker = [:];
        return [
                loanapp: [borrower: selectedCoMaker],
                mode: caller.mode, 
                service: service, 
                handlers: handlers, 
                beforeSaveHandlers:beforeSaveHandlers
        ]
    }
    
    def coMakerHandler = [
        fetchList: {o->
            if( !borrowers ) borrowers = []
            borrowers.each{ it._filetype = "comaker" }
            return borrowers;
        },
        onRemoveItem: {o->
            return removeCoMakerImpl(o);
        },
        getOpenerParams: {o->
            return createOpenerParams()
        }
    ] as EditorListModel;
        
    void removeCoMaker() {
        removeCoMakerImpl(selectedCoMaker);
    }
            
    boolean removeCoMakerImpl(o) {
        if (caller.mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this co-maker. Continue?")) {
            borrowers.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildBorrower(selectedCoMaker);
    }
}

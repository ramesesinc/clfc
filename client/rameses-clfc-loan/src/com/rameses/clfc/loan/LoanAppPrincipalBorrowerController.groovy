package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;
import com.rameses.clfc.borrower.*;

class LoanAppPrincipalBorrowerController   
{
    //feed by the caller
    def caller, handlers, loanapp; 
    
    @Service('PrincipalBorrowerService') 
    def service; 
    
    private def beforeSaveHandlers = [:];
    private def dataChangeHandlers = [:];
    def menuItems =  [
        [name:'generalinfo', caption:'General Information'], 
        [name:'spouseinfo', caption:'Spouse Information'], 
        [name:'children', caption:'Children'], 
        /*[name:'collateral', caption:'Collateral'], 
        [name:'otherlending', caption:'Other Lending'], 
        [name:'jointborrower', caption:'Joint Borrower'],
        [name:'comaker', caption:'Co-Maker'], 
        [name:'attachment', caption:'Attachments'], 
        [name:'comment', caption:'Comments'], 
        [name:'summary', caption:'Summary'] */
    ];
    
    def opener;
    
    void init() {
        if (loanapp.objid == null) return;
        
        handlers.saveHandler = { save(); }          
        def data = service.open([objid: loanapp.objid]);
        data.borrower.type = 'PRINCIPAL'
        loanapp.clear();
        loanapp.putAll(data); 
        
    }

    def createOpenerParams() {
        def ctx = new BorrowerContext(caller, this, service, loanapp);
        ctx.beforeSaveHandlers = beforeSaveHandlers;
        ctx.dataChangeHandlers = dataChangeHandlers;
        return [ borrowerContext: ctx ]; 
    }
    
    def tabHandler = [
        getList: {
            return menuItems;
        },
        beforeSelect: {item,index-> 
            if (caller?.mode == 'read' || index == 0) return true; 
            
            return (loanapp.borrower?.objid != null);   
        },
        onselect: {item->
            def invtype = 'borrower-'+item.name+':open'; 
            opener = InvokerUtil.lookupOpener(invtype, createOpenerParams()); 
            subFormHandler.refresh();
        }
    ] as TabbedListModel 
                 
    def subFormHandler = [
        getOpener: { return opener; } 
    ] as SubFormPanelModel;
    
    void save() {
        beforeSaveHandlers.each{k,v-> 
            if (v != null) v(); 
        }

        def data = [
            objid: loanapp.objid, 
            borrower: loanapp.borrower 
        ]; 
        service.update(data);
    }
}

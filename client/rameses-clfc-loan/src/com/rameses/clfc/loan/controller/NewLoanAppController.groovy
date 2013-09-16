package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.*;
import java.rmi.server.UID;

class NewLoanApplicationController 
{
    @Binding 
    def binding;
    
    @Service('NewLoanAppService')
    def service; 

    def mode = 'create';
    def entity;
    def productTypes = [];
    def clientTypes = LoanUtil.clientTypes;
    def appTypes = LOV.LOAN_APP_TYPES;
    
    @PropertyChangeListener
    def listener = [
        "entity.clienttype": {o->
            if(o != 'MARKETED') entity.marketedby = null
            binding.refresh('entity.marketedby');
        }
    ]
    
    void initOnline() {
        entity = service.initEntity();
        entity.mode = 'ONLINE'; 
        entity.apptype = 'NEW';
        productTypes = entity.productTypes;
    }
    
    def initCapture() {
        entity = service.initEntity();
        entity.mode = 'CAPTURE';
        productTypes = entity.productTypes;
        return "capturepage";
    }

    def getTitle() {
        return 'Loan Application: ' + entity.mode;
    }
    
    def customerLookupHandler = InvokerUtil.lookupOpener('customer:lookup', [:]); 
    
    def save() {
        if (entity.clienttype == 'MARKETED') {
            if(!entity.marketedby) throw new Exception('Interviewed by is required.');
        }
        
        if (!MsgBox.confirm('Ensure that all information is correct. Continue?')) return;
        entity = service.create(entity); 
        mode = 'read';
        return 'successpage'; 
    }
    
    def cancel() {
        if (MsgBox.confirm('You are about to close this window. Continue?')) 
            return '_close'; 
        else 
            return null; 
    }
    
    def close() {
        return '_close';
    }
    
    def create() {
        mode = 'create';
        if (entity.mode == 'CAPTURE') {
            return initCapture(); 
        } else {
            initOnline(); 
            return 'default';
        }
    } 
    
    def edit() {
        def opener = InvokerUtil.lookupOpener('loanapp:open', [entity: entity]);
        opener.caption = 'LOAN-'+entity.appno;
        opener.target = 'window';
        binding.fireNavigation(opener); 
        return '_close'; 
    }
    
    def previousLoansHandler = [
        fetchList: {o->
            return entity.previousloans
        },
        onAddItem: {o->
            entity.previousloans.add(o)
        },
        onRemoveItem: {o->
            if( MsgBox.confirm("You are about to remove this loan. Continue?") ) {
                entity.previousloans.remove(o)
                return true
            }
            return false
        },
        onColumnUpdate: {o, colName->
            def item = entity.previousloans.find{ it == o }
            if( item ) item = o
        }
    ] as EditorListModel;   
}

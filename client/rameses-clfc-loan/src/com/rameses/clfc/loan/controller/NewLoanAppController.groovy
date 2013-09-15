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
    
    void initOnline() {
        entity = service.initEntity();
        entity.mode = 'ONLINE'; 
        entity.apptype = 'NEW';
        productTypes = entity.productTypes;
    }
    
    def initCapture() {
        entity = service.initEntity();
        entity.mode = 'CAPTURE';
        entity.apptype = 'NEW';
        productTypes = entity.productTypes;
    }

    def getTitle() {
        return 'New Loan Application: ' + entity.mode;
    }
    
    def customerLookupHandler = InvokerUtil.lookupOpener('customer:lookup', [:]); 
    
    def save() {
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
        if (entity.mode == 'CAPTURE') {
            initCapture(); 
        } else {
            initOnline(); 
        }       
        mode = 'create';
        return 'default';
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

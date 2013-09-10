package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.*;

class NewLoanApplicationController extends CRUDController
{
    String serviceName = 'NewLoanAppService';
    String prefixId = 'LOAN';

    @Service('LoanProductTypeService')
    def productTypeService;
    def productTypes;
    
    def appTypes = LOV.LOAN_APP_TYPES;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;
    
    Map createEntity() {
        return [
            branch: [:], 
            borrower: [:], 
            schedule: [:] 
        ]; 
    }
    
    void initOnline() {
        create(); 
        entity.mode = 'ONLINE';
    }

    void initCapture() {
        create(); 
        entity.mode = 'CAPTURE';
    }
    
    def getProductTypes() {
        if (productTypes == null) { 
            productTypes = productTypeService.getList([:]); 
        } 
        return productTypes;
    }
    
    def getCustomerLookupHandler() {
        return InvokerUtil.lookupOpener('customer:lookup', [:]); 
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
    ] as EditorListModel
}

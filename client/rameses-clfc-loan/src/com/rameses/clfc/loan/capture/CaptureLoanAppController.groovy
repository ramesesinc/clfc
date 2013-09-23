package com.rameses.clfc.loan.capture;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;

class CaptureLoanAppController extends AbstractLoanAppController
{
    @Service('CaptureLoanAppService')
    def service; 
    
    void init() {
        entity = service.initEntity();
        entity.appmode = 'CAPTURE';
        entity.previousloans = [];
        productTypes = entity.productTypes;
    }
    
    def getTitle() { return 'Capture Loan Application'; }
    protected def getService() { return service }
        
    def previousLoansHandler = [
        fetchList: {o->
            if(!entity.previousloans) entity.previousloans = [];
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

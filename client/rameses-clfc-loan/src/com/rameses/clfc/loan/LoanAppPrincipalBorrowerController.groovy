package com.rameses.clfc.loan;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.borrower.*;
import com.rameses.clfc.util.*;

class LoanAppPrincipalBorrower
{
    //feed by the caller
    def caller, handlers, loanapp;
    
    @Binding
    def binding;
    
    @Service('PrincipalBorrowerService')
    def service;
    
    //local variables 
    def borrower;
    def entity = [:];
    def occupancyTypes = LoanUtil.borrowerOccupancyTypes;
    def rentTypes = LoanUtil.rentTypes;
    
    @PropertyChangeListener
    def listener = [
        "entity.residency.type": {o->
            if(o != 'RENTED') {
                entity.residency.renttype = null
                entity.residency.rentamount = null
            }
        },
        "entity.occupancy.type": {o->
            if(o != 'RENTED') {
                entity.occupancy.renttype = null
                entity.occupancy.rentamount = null
            }
        }
    ];
    
    void init() {
        if (loanapp.objid == null) return;

        def newloanapp = service.open([objid: loanapp.objid]);
        loanapp.clear();
        loanapp.putAll(newloanapp);
        entity = loanapp.borrower;        
        handlers.saveHandler = { save() }
        /*borrowerContext.addBeforeSaveHandler('borrower', {
            if(!entity.residency.since) throw new Exception('Residency: Since is required.');
            if(entity.residency.type == 'RENTED') {
                if(!entity.residency.renttype) throw new Exception('Residency: Rent Type is required.');
                if(!entity.residency.rentamount) throw new Exception('Residency: Rent Amount is required.');
            }
            if(!entity.occupancy.since) throw new Exception('Lot Occupancy: Since is required.'); 
            if(entity.occupancy.type == 'RENTED') {
                if(!entity.occupancy.renttype) throw new Exception('Lot Occupancy: Rent Type is required.');
                if(!entity.occupancy.rentamount) throw new Exception('Lot Occupancy: Rent Amount is required.');
            }
        })*/
    }
    
    def getLookupBorrower() {  
        def params = [
            onselect: {o-> 
                def borrower = null; 
                try { borrower = service.openBorrower([objid: o.objid]); } catch(Throwable t){;} 
                
                if (borrower == null) { 
                    entity.putAll(o); 
                } else {
                    entity.clear(); 
                    entity.putAll(borrower);
                } 
                binding.refresh();
            }, 
            onempty: { 
                entity.clear();
                entity.occupancy = [:];
                entity.residency = [:];
                binding.refresh();
            }
        ];
        return InvokerUtil.lookupOpener('customer:search', params);
    }
}

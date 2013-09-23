package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.*;
import java.rmi.server.UID;
import com.rameses.clfc.controller.*;

class NewLoanApplicationController extends AbstractLoanAppController
{
    @Service('NewLoanAppService')
    def service; 
    
    void init() {
        entity = service.initEntity();
        entity.mode = 'ONLINE'; 
        entity.apptype = 'NEW';
        productTypes = entity.productTypes;
    }
    
    def getTitle() { return 'Loan Application: ' + entity.mode; }
    
    protected def getService() { return service; }
}

package com.rameses.clfc.customer;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

public class CustomerControllerContext 
{
    private def caller;
    
    public CustomerControllerContext(def caller) {
        this.caller = caller;
    }
    
    public def getMode() { return caller?.mode; } 
    public def getEntity() { return caller?.entity; } 
} 
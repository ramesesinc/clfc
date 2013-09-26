package com.rameses.clfc.customer;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CustomerConnectionsController 
{
    //feed by the caller 
    def callerContext;
    
    void init() {
        println 'callerContext-> ' + callerContext;
    }
} 
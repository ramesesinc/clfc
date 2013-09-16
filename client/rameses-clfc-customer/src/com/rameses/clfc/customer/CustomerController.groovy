package com.rameses.clfc.customer;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CustomerController 
{
    def mode = 'read';
    def saveHandlers = [:];
    
    def createOpenerParams() {
        return [contextHandler: contextHandler]; 
    } 
    
    def contextHandler = [
        mode: { return mode; }, 
        addSaveHandler: {k,v-> 
            saveHandlers.put(k, v); 
        } 
    ]; 
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('customer:plugin', createOpenerParams());
        },
        getOpenerParams: {
            return createOpenerParams(); 
        },
        beforeSelect: {item,index-> 
            if (mode == 'read' || index == 0) return true; 
        
            return false; 
        }
    ] as TabbedPaneModel 
} 
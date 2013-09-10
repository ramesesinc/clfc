package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.controller.*;

class LoanAppPrincipalBorrowerController 
{
    //feed by the caller
    def loanapp, entity, caller;
    
    def tabHandler = [
        getOpeners: {
            return InvokerUtil.lookupOpeners('loanapp-borrower:plugin', [
                loanapp: loanapp,
                mode: caller.mode
            ]);
        },
        getOpenerParams: {
            return [
                loanapp: loanapp, 
                mode: caller.mode
            ]; 
        }
    ] as TabbedPaneModel 
}

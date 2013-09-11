package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CollateralOtherController
{
    def loanapp, mode, beforeSaveHandlers;
    def entity = [:];
    
    void init() {
        if( loanapp.collateral.other ) entity = loanapp.collateral.other;
        beforeSaveHandlers.otherCollateralSaveHandler = { validate(); }
    }
    
    void validate() {
        if( !loanapp.collateral.other ) 
            loanapp.collateral.other = entity
    }
}
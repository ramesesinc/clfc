package com.rameses.clfc.loan.recommendation;

import com.rameses.rcp.annotations.*;

class LoanAppRecommendationController
{
    def loanapp, caller, handlers;
    
    @Service('LoanAppRecommendationService')
    def service;
    
    def data;
    void init() {
        data = service.open([objid: loanapp.objid]);
    }
}

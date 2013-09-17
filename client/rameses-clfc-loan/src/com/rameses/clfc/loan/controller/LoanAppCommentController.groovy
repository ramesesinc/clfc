package com.rameses.clfc.loan.controller;

import com.rameses.rcp.annotations.*;

class LoanAppCommentController
{
    //feed by the caller
    def caller, handlers, loanapp; 
    
    @Service('LoanAppCommentService')
    def service;
    
    def getHtmlview() {
        return service.getComments([loanappid: loanapp.objid]);
    }
}

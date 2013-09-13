package com.rameses.clfc.borrower;

class BorrowerContext
{
    private def dataChangeHandlers = [:];
    private def beforeSaveHandlers = [:]; 
    private def parentCodeBean;
    private def codeBean;
    private def service;
    private def loanapp;
    
    BorrowerContext(parentCodeBean, codeBean, service, loanapp) {
        this.parentCodeBean = parentCodeBean;
        this.codeBean = codeBean;
        this.service = service;
        this.loanapp = loanapp;
    }

    public def getDataChangeHandlers() { return dataChangeHandlers; } 
    public def getBeforeSaveHandlers() { return beforeSaveHandlers; }     
    
    public def getCaller() { return codeBean; }
    public def getService() { return service; } 
    public def getLoanapp() { return loanapp; } 
    public def getMode() { return parentCodeBean.mode; } 
    public def getBorrower() { return loanapp.borrower; } 

    public def getSpouse() {
        def borrower = loanapp.borrower;
        if (borrower == null) {
            loanapp.borrower = [:]; 
            borrower = loanapp.borrower;
        }
        
        def spouse = borrower.spouse;
        if (spouse == null) {
            borrower.spouse = [:];
            spouse = borrower.spouse;
        }
        return spouse; 
    }
    
    public def getLoanappid() { 
        return loanapp.objid; 
    } 
    
    public def openBorrower( params ) {
        return service.openBorrower(params); 
    }
    
    public void refresh() {
        caller.tabHandler.refresh(); 
    } 
}

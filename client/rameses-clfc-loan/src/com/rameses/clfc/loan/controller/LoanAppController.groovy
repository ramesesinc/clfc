package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanAppController 
{
    @Service('LoanAppService')
    def service;
    
    def loanappid;
    def entity = [:];
    def handlers = [:];
    def mode = 'read';
    
    void open() {
        loanappid = entity.objid;
    }
    
    def getTitle() {
        def buffer = new StringBuffer();
        buffer.append('<html><body>');
        buffer.append('<font color="#483d8b" size="5">'+entity.appno+' - </font>');
        buffer.append('<font color="red" size="5">'+entity.state+'</font><br>');
        buffer.append('<font color="#444444" size="3"><b>'+entity.borrower?.name+'</b></font>');
        buffer.append('</body></html>');
        return buffer.toString();
    }
    
    def selectedMenu;
    def listHandler = [
        getDefaultIcon: {
            return 'Tree.closedIcon'; 
        },         
        getItems: { 
            return [
                [name:'principalborrower', caption:'Principal Borrower'], 
                [name:'loandetail', caption:'Loan Details'], 
                [name:'business', caption:'Business'], 
                [name:'collateral', caption:'Collateral'], 
                [name:'otherlending', caption:'Other Lending'], 
                [name:'jointborrower', caption:'Joint Borrower'],
                [name:'comaker', caption:'Co-Maker'], 
                [name:'attachment', caption:'Attachments'], 
                [name:'comment', caption:'Comments'], 
                [name:'summary', caption:'Summary'] 
            ];
        }, 
        onselect: {o->             
            entity = service.open([objid: loanappid, name:o?.name]); 
            handlers = [:];
        } 
    ] as ListPaneModel;
    
    
    def getOpener() {
        if (selectedMenu == null) {
            return new Opener(outcome:'blankpage'); 
        }
        
        def invtype = 'loanapp-'+selectedMenu.name+':open'; 
        return InvokerUtil.lookupOpener(invtype, [            
            loanapp: entity, 
            caller: this,
            handlers: handlers
        ]); 
    } 
    
    void edit() {
        mode = 'edit';
    }
    
    void save() {
        def saveHandler = handlers.saveHandler; 
        if (saveHandler == null) return; 
        
        saveHandler(); 
        mode = 'read';        
    }
    
    void cancel() {
        mode = 'read';        
    }    
}

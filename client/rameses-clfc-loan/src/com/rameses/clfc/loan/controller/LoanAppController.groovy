package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class LoanAppController 
{
    @Service('LoanAppService')
    def service;
    
    @Binding
    def binding;
    
    def loanappid;
    def entity = [:];
    def handlers = [:];
    def mode = 'read';
    
    @FormTitle
    @FormId
    def getFormId() { 
        return 'LOAN-'+entity.appno;
    }
    
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
    
    def menuItems =  [
        [name:'principalborrower', caption:'Principal Borrower'], 
        [name:'loandetail', caption:'Loan Details'], 
        [name:'business', caption:'Business'], 
        [name:'collateral', caption:'Collateral'], 
        [name:'otherlending', caption:'Other Lending'], 
        [name:'jointborrower', caption:'Joint Borrower'],
        [name:'comaker', caption:'Co-Maker'], 
        [name:'attachment', caption:'Attachments'], 
        [name:'comment', caption:'Comments'],
        [name:'recommendation', caption:'Recommendations'],
        [name:'summary', caption:'Summary'] 
    ];
    
    def selectedMenu;
    def listHandler = [
        getDefaultIcon: {
            return 'Tree.closedIcon'; 
        },         
        getItems: { 
            return menuItems;
        },
        beforeSelect: {o-> 
            return (mode == 'read'); 
        }, 
        onselect: {o->             
            entity = service.open([objid: loanappid, name:o?.name]); 
            subFormHandler.reload();
        } 
    ] as ListPaneModel;
    
    
    def subFormHandler = [
        getOpener: {
            if (selectedMenu == null) {
                return new Opener(outcome:'blankpage'); 
            }

            def op = selectedMenu.opener;
            if (op == null) {
                def invtype = 'loanapp-'+selectedMenu.name+':open'; 
                op = InvokerUtil.lookupOpener(invtype, [ 
                    caller: this, 
                    loanapp: entity, 
                    handlers: handlers 
                ]); 
                selectedMenu.opener = op;
            } 
            return op; 
        } 
    ] as SubFormPanelModel;

    boolean getIsEditable() {
        if((entity.state.matches('INCOMPLETE|PENDING|FOR_INSPECTION')) && mode == 'read') return true;
        return false;
    }
    
    void edit() {
        mode = 'edit';
        subFormHandler.refresh();
    }
    
    void save() {
        def saveHandler = handlers.saveHandler; 
        if (saveHandler == null) return; 
        
        saveHandler(); 
        mode = 'read'; 
        binding.refresh('title');
        subFormHandler.refresh();
    }
    
    void cancel() {
        if (!MsgBox.confirm('Are you sure you want to cancel all the changes made?')) return;
        
        mode = 'read'; 
        selectedMenu.opener = null;
    }
    
    boolean getIsPending() {
        if(entity.state == 'PENDING' && mode == 'read') return true;
        return false;
    }
    
    def submitForInspection() {
        def handler = {o->
            o.objid = loanappid;
            entity.state = service.submitForInspection(o).state;
            binding.refresh('title|formActions|opener');
        }
        return InvokerUtil.lookupOpener("application-forinspection:create", [handler:handler])
    }
    
    boolean getIsForInspection() {
        if(entity.state == 'FOR_INSPECTION' && mode == 'read') return true;
        return false;
    }
    
    def submitForCrecom() {
        println entity.route
        if(!entity.route.code) throw new Exception('Route for loan application is required.');
        if(!entity.businesses) 
            entity.businesses = service.getBusinesses([objid: entity.objid]);
        entity.businesses.each{business->
            if(!business.ci?.evaluation) throw new Exception("CI Report for business $business.tradename is required.");
        }
        def handler = {o->
            o.objid = loanappid;
            entity.state = service.submitForCrecom(o).state;
            binding.refresh('title|formActions|opener');
        } 
        return InvokerUtil.lookupOpener("application-forcrecom:create", [handler:handler]);
    }
    
    boolean getIsForCrecom() {
        if(entity.state == 'FOR_CRECOM' && mode == 'read') return true;
        return false;
    }
    
    def submitForApproval() {
        def handler = {o->
            o.objid = loanappid;
            entity.state = service.submitForApproval(o).state;
            binding.refresh('title|formActions');
        }
        return InvokerUtil.lookupOpener("application-forapproval:create", [handler:handler]);
    }
    
    def returnForCi() {
        def handler = {o->
            o.objid = loanappid;
            entity.state = service.returnForCI(o).state;
            binding.refresh('title|formActions|opener');
        }
        return InvokerUtil.lookupOpener("application-returnforci:create", [handler:handler]);
    }
}

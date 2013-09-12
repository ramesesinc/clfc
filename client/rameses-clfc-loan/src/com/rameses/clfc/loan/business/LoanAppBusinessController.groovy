package com.rameses.clfc.loan.business;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class LoanAppBusinessController 
{
    //feed by the caller
    def loanapp, caller, handlers;
    
    @Service('LoanAppBusinessService') 
    def service; 
    
    def businesses = [];
    def selectedBusiness;
    
    void init() {
        handlers.saveHandler = { save(); }  
        def data = service.open([objid: loanapp.objid]);
        loanapp.clear();
        loanapp.putAll(data);
        businesses = loanapp.businesses;
    }
        
    void save() {
        if( loanapp.state == 'FOR_INSPECTION' ) {
            def business = businesses.find{ it.ci == null }
            if( business ) throw new Exception("CI Report for business $business.tradename is required.");
        }

        def data = [ objid: loanapp.objid, businesses: businesses ]
        service.update(data); 
    }

    def addBusiness() {
        def handler = {business->
            business.parentid = loanapp.objid;
            businesses.add(business);
            businessHandler.reload();
        }
        return InvokerUtil.lookupOpener("business:create", [handler:handler]);
    }

    def businessHandler = [
        fetchList: {o->
            if( !businesses ) businesses = []
            businesses.each{ it._filetype = "business" }
            return businesses;
        },
        onRemoveItem: {o->
            if( caller.mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this business. Continue?")) {
                businesses.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: caller.mode]
        }
    ] as EditorListModel;

    def addCiReport() {
        if( !selectedBusiness ) {
            MsgBox.alert("No business selected.");
            return null;
        }
        def handler = {ci->
            selectedBusiness.ci = ci;
        }
        if( !selectedBusiness.ci ) selectedBusiness.ci = [ filedby: OsirisContext.env.USERID ]
        return InvokerUtil.lookupOpener("cireport:edit", [handler:handler, entity:selectedBusiness.ci, mode:caller.mode])
    }
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildBusiness(selectedBusiness)
    }
}

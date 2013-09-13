package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class CollateralApplianceController
{
    @Binding
    def binding;
    def loanapp, mode, beforeSaveHandlers;
    
    def selectedAppliance;
    def applianceHandler = [
        fetchList: {o->
            if( !loanapp.collateral.appliances ) loanapp.collateral.appliances = [];
            loanapp.collateral.appliances.each{ it._filetype = "appliance" }
            return loanapp.collateral.appliances;
        },
        onRemoveItem: {o->
            return removeApplianceImpl(o); 
        },
        getOpenerParams: {o->
            return [mode: mode, caller:this];
        }
    ] as EditorListModel;
    

    def addAppliance() {
        def handler = {appliance->
            appliance.parentid = loanapp.objid;
            loanapp.collateral.appliances.add(appliance);
            applianceHandler.reload();
        }
        return InvokerUtil.lookupOpener("appliance:create", [handler:handler]);
    }
    
    void removeAppliance() {
        removeApplianceImpl(selectedAppliance); 
    }
    
    boolean removeApplianceImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this appliance. Continue?")) {
            loanapp.collateral.appliances.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildAppliance(selectedAppliance);
    }
}
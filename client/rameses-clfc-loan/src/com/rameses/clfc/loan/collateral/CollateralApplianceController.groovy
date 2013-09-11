package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CollateralApplianceController
{
    def loanapp, mode, beforeSaveHandlers;

    def addAppliance() {
        def handler = {appliance->
            appliance.parentid = loanapp.objid;
            loanapp.collateral.appliances.add(appliance);
            applianceHandler.reload();
        }
        return InvokerUtil.lookupOpener("appliance:create", [handler:handler]);
    }
    
    def applianceHandler = [
        fetchList: {o->
            if( !loanapp.collateral.appliances ) loanapp.collateral.appliances = [];
            loanapp.collateral.appliances.each{ it._filetype = "appliance" }
            return loanapp.collateral.appliances;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this appliance. Continue?")) {
                loanapp.collateral.appliances.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: mode];
        }
    ] as EditorListModel
}
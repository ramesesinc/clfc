package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;

class CollateralPropertyController
{
    def loanapp, mode, beforeSaveHandlers;
    
    def addProperty() {
        def handler = {property->
            property.parentid = loanapp.objid;
            loanapp.collateral.properties.add(property);
            propertyHandler.reload();
        }
        return InvokerUtil.lookupOpener("realproperty:create", [handler:handler]);
    }
    
    def propertyHandler = [
        fetchList: {o->
            if( !loanapp.collateral.properties ) loanapp.collateral.properties = [];
            loanapp.collateral.properties.each{ it._filetype = "realproperty" }
            return loanapp.collateral.properties;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this property. Continue?")) {
                loanapp.collateral.properties.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: mode];
        }
    ] as EditorListModel;
}
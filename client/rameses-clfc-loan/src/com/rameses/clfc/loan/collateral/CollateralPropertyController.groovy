package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.clfc.util.HtmlBuilder;

class CollateralPropertyController
{
    def loanapp, mode, beforeSaveHandlers;
    
    def selectedProperty;
    def propertyHandler = [
        fetchList: {o->
            if( !loanapp.collateral.properties ) loanapp.collateral.properties = [];
            loanapp.collateral.properties.each{ it._filetype = "realproperty" }
            return loanapp.collateral.properties;
        },
        onRemoveItem: {o->
            return removeChildImpl(o); 
        },
        getOpenerParams: {o->
            return [mode: mode];
        }
    ] as EditorListModel;
    
    def addProperty() {
        def handler = {property->
            property.parentid = loanapp.objid;
            loanapp.collateral.properties.add(property);
            propertyHandler.reload();
        }
        return InvokerUtil.lookupOpener("realproperty:create", [handler:handler]);
    }
    
    void removeChild() {
        removeChildImpl(selectedProperty); 
    }
    
    boolean removeChildImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this child. Continue?")) {
            loanapp.collateral.properties.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildProperty(selectedProperty);
    }
}
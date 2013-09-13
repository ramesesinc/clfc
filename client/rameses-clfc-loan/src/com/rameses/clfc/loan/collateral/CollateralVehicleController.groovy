package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.util.HtmlBuilder;

class CollateralVehicleController
{
    @Binding
    def binding;
    def loanapp, mode, beforeSaveHandlers;
    
    def selectedVehicle;
    def vehicleHandler = [
        fetchList: {o->
            if( !loanapp.collateral.vehicles ) loanapp.collateral.vehicles = [];
            loanapp.collateral.vehicles.each{ it._filetype = "vehicle" }
            return loanapp.collateral.vehicles;
        },
        onRemoveItem: {o->
            return removeVehicleImpl(o); 
        },
        getOpenerParams: {o->
            return [mode: mode, caller:this];
        }
    ] as EditorListModel;
    
    def addVehicle() {
        def handler = {vehicle->
            vehicle.parentid = loanapp.objid;
            loanapp.collateral.vehicles.add(vehicle);
            vehicleHandler.reload();
        }
        return InvokerUtil.lookupOpener("vehicle:create", [handler:handler]);
    }
    
    void removeVehicle() {
        removeVehicleImpl(selectedVehicle); 
    }
    
    boolean removeVehicleImpl(o) {
        if (mode == 'read') return false;
        if (MsgBox.confirm("You are about to remove this vehicle. Continue?")) {
            loanapp.collateral.vehicles.remove(o);
            return true;
        } else { 
            return false; 
        } 
    }
    
    def getHtmlview() {
        HtmlBuilder html=new HtmlBuilder();
        return html.buildVehicle(selectedVehicle);
    }
}
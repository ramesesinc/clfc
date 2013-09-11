package com.rameses.clfc.loan.collateral;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CollateralVehicleController
{
    def loanapp, mode, beforeSaveHandlers;
    
    def addVehicle() {
        def handler = {vehicle->
            vehicle.parentid = loanapp.objid;
            loanapp.collateral.vehicles.add(vehicle);
            vehicleHandler.reload();
        }
        return InvokerUtil.lookupOpener("vehicle:create", [handler:handler]);
    }
    
    def vehicleHandler = [
        fetchList: {o->
            if( !loanapp.collateral.vehicles ) loanapp.collateral.vehicles = [];
            loanapp.collateral.vehicles.each{ it._filetype = "vehicle" }
            return loanapp.collateral.vehicles;
        },
        onRemoveItem: {o->
            if( mode == 'edit' ) return false;
            if(MsgBox.confirm("You are about to remove this vehicle. Continue?")) {
                loanapp.collateral.vehicles.remove(o);
                return true;
            }
            return false;
        },
        getOpenerParams: {o->
            return [mode: mode];
        }
    ] as EditorListModel;
}
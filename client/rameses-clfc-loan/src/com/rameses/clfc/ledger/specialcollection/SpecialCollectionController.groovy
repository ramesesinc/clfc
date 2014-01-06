package com.rameses.clfc.ledger.specialcollection;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.rmi.server.UID;

class SpecialCollectionController extends CRUDController
{
    String serviceName = "LoanSpecialCollectionService";
    String entityName = "specialcollection";
    
    boolean allowCreate = false;
    boolean allowEdit = false;
    boolean allowDelete = false;

    def selectedLedger;
    def listModelHandler;
        Map createEntity() {
        return [ 
            objid: 'LSC'+new UID(),
            _specialcollectionrequest: listModelHandler?.selectedEntity,
            ledgers: [],
            routes: []
        ];
    }

    void afterCreate( data ) {
        if (data._specialcollectionrequest) {
            data.collector = data._specialcollectionrequest.collector;
            data.collector.username = data.collector.name;
        }
    }

    def listHandler = [
        fetchList: {
            if (!entity.ledgers) entity.ledgers = [];
            return entity.ledgers;
        }
    ] as BasicListModel;

    def addLedger() {
        def handler = {o->
            if (entity.ledgers.find{ o.objid == it.objid }) throw new Exception("Ledger has already been selected.");

            if (!entity.routes.find{ o.route.code == it.code }) entity.routes.add(o.route);
            entity.ledgers.add(o);
            listHandler.reload();
        }
        return InvokerUtil.lookupOpener('specialcollectionledger:lookup', [onselect: handler, collectorid: entity.collector.objid, billdate: entity._specialcollectionrequest.dtrequested]);
    }


    def removeLedger() {
        if (MsgBox.confirm("You are about to remove this ledger. Continue?")) {
            entity.ledgers.remove(selectedLedger);
            listHandler.reload();
        }
    }
}
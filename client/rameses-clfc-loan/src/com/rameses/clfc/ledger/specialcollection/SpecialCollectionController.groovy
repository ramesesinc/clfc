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
    boolean allowDelete = false;
    boolean allowApprove = false;

    def selectedLedger;
    def listModelHandler;
    Map createEntity() {
        def map = [
            ledgers : [],
            routes  : []
        ];
        if (listModelHandler) {
            map.putAll(listModelHandler.selectedEntity);
        }
        return map;
    }

    /*void afterCreate( data ) {
        if (data._specialcollectionrequest) {
            data.collector = data._specialcollectionrequest.collector;
            data.collector.username = data.collector.name;
            data.remarks = data._specialcollectionrequest.remarks;
        }
    }*/

    def listHandler = [
        fetchList: {
            if (!entity.ledgers) entity.ledgers = [];
            return entity.ledgers;
        }
    ] as EditorListModel;

    void beforeSave( data ) {
        if (listHandler.hasUncommittedData())
            throw new Exception("Please commit table data before saving.");
    }

    def getLookupLedgerHandler() {
        def handler = {o->
            if (entity.ledgers.find{ o.objid == it.objid }) throw new Exception("Ledger has already been selected.");

            if (!entity.routes.find{ o.route.code == it.code }) entity.routes.add(o.route);
            entity.ledgers.add(o);
            listHandler.reload();
        }
        return InvokerUtil.lookupOpener('specialcollectionledger:lookup', [onselect: handler, collectorid: entity.collector.objid, billdate: entity.dtrequested]);
    }

    def removeLedger() {
        if (MsgBox.confirm("You are about to remove this ledger. Continue?")) {
            def route = selectedLedger.route;
            entity.ledgers.remove(selectedLedger);
            if (!entity.ledgers.find{ it.route.code == route.code }) {
                entity.routes.remove(route);
            }
            listHandler.reload();
        }
    }
}
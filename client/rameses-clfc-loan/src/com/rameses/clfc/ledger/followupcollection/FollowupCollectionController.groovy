package com.rameses.clfc.ledger.followupcollection;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import java.rmi.server.UID;

class FollowupCollectionController extends CRUDController
{
    @Binding
    def binding;

    String serviceName = "LoanSpecialCollectionService";
    String entityName = "specialcollection";
    
    boolean allowCreate = false;
    boolean allowDelete = false;
    boolean allowApprove = false;

    def svc;
    FollowupCollectionController() {
        try {
            svc = InvokerProxy.instance.create("LoanSpecialCollectionService");
        } catch(ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }   
    }

    def collectorLookupHandler = InvokerUtil.lookupOpener('route-collector:lookup', [
        onselect : {o->
            def billing = svc.getCurrentCollectorBilling([collectorid: o.objid]);
            if (!billing) throw new Exception("Collector has no billing for today. Create billing first before creating follow-up collections.");

            entity.billingid = billing.objid;
            entity.collector = o;
            for (i in entity) println i;
            binding.refresh('entity.collector');
        }
    ]);

    def selectedLedger;
    def listModelHandler;
    Map createEntity() {
        def map = [
            objid   : 'SC'+new UID(),
            ledgers : [],
            routes  : []
        ];
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
        println 'data-> '+data;
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
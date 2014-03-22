package com.rameses.clfc.loan.fieldcollection;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class FieldCollectionController
{
    @Binding
    def binding;

    def service;
    def entity;
    def mode;
    def collector;
    def collectorList;
    def iscashier = false;
    def totalbreakdown;
    def prevcashbreakdown = [];

    FieldCollectionController() {
        try {
            service = InvokerProxy.instance.create("LoanFieldCollectionService");
        } catch(ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }   
    }

    void init() {
        entity = [:];
        mode = 'init'
        collectorList = service.getCollectors();
    }

    def close() {
        return '_exit';
    }

    def next() {
        mode = 'read';

        entity = service.getFieldCollection([collectorid: collector.objid]);
        if (!entity) throw new Exception("No unposted collection for this collector.");
        
        routesHandler.reload();
        if (!entity.cashbreakdown) {
            mode = 'create';
            totalbreakdown = 0;
        } else {
            totalbreakdown = entity.cashbreakdown.amount.sum();
        }
        return new Opener(outcome:'main');
    }

    def getCashbreakdown() {
        def params = [
            entries         : entity.cashbreakdown,
            totalbreakdown  : totalbreakdown,
            editable        : (mode != 'read'? true: false),
            onupdate        : {o->
                totalbreakdown = o;
            }
        ]
        return InvokerUtil.lookupOpener('clfc:denomination', params);
    }

    boolean getIsAllowEdit() {
        if (mode != 'read') return false;
        return true;
    }
    
    boolean getIsAllowSave() {
        if (mode == 'init' || mode == 'read') return false;
        return true;
    }

    boolean getIsAllowPost() {
        if (mode != 'read' || getTotalcollection() != totalbreakdown) return false;
        return true;
    }

    def routesHandler = [
        fetchList: {o->
            if (!entity.routes) entity.routes = [];
            return entity.routes;
        },
        onOpenItem: {itm, colName->
            def opener = InvokerUtil.lookupOpener('fcloan:open', [route: itm.route, fieldcollectionid: entity.objid])
            opener.caption = "Field Collection: "+itm.route.description+" - "+itm.route.area;
            return opener;
        }
    ] as BasicListModel;

    def getTotalcollection() {
        if (!entity.routes) return 0;
        return entity.routes.total.sum();
    }

    def back() {
        mode = 'init';
        return 'default';
    }

    def save() {
        if (getTotalcollection() != totalbreakdown) 
            throw new Exception('Total amount for denomination does not match with total amount collected.');
        
        if (mode == 'create') {
            entity.cashbreakdown = service.saveCashBreakdown(entity);
        } else if (mode == 'edit') {
            entity.cashbreakdown = service.updateCashBreakdown(entity);
        }
        mode = 'read';
        binding.refresh();
    }

    def edit() {
        mode = 'edit';
        prevcashbreakdown.clear()
        def map;
        entity.cashbreakdown.each{
            map = [:];
            map.putAll(it);
            prevcashbreakdown.add(map);
        }
        prevcashbreakdown.each{
            'prev denomination = '+it;
        }
        binding.refresh();
    }

    def cancel() {
        mode = 'read';
        println 'prev cash breakdown';
        prevcashbreakdown.each{
            'denomination = '+it;
        }
        entity.cashbreakdown.clear();
        entity.cashbreakdown.addAll(prevcashbreakdown);
        totalbreakdown = entity.cashbreakdown.amount.sum();
        binding.refresh();
    }

    def post() {
        if (MsgBox.confirm("You are about to post this collection. Continue?")) {
            service.post(entity);
            MsgBox.alert("Collection successfully posted!");
            init();
            return 'default';
        }
    }
}
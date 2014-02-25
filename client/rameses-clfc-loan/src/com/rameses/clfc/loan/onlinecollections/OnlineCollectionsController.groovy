package com.rameses.clfc.loan.onlinecollections;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class OnlineCollectionController
{
    @Binding
    def binding;

    def service;
    def entity;
    def page = 'init'
    def collector;
    def collectorList
    def txndate;

    OnlineCollectionController() {
        try {
            service = InvokerProxy.instance.create('LoanOnlineCollectionService');
            collectorList = service.getCollectors();
        } catch (ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }
    }

    void init() {
        collector  = [:];
        page = 'init';
    }

    def paymentsHandler = [
        fetchList: {o->
            if (!entity.payments) entity.payments = [];
            return entity.payments;
        }
    ] as BasicListModel;

    def getCollectionDateList() {
        return collector.collectionDates;
    }

    def close() {
        return '_close';
    }

    def next() {
        page = 'main';
        entity = service.getCollectionForPosting([collector: collector, txndate: txndate]);
        paymentsHandler.reload();        
        return page;
    }

    def back() {
        page = 'init';
        return 'default';
    }
}
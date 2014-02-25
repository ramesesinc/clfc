package com.rameses.clfc.loan.fieldcollections;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CollectionController 
{
    @Service("LoanFieldCollectionService")
    def fieldColSvc;

    def page = 'init';
    def entity;
    def route;
    def routeSvc;
    def routeList;
    def selectedCollectionSheet = [:];
    
    CollectionController() {
        try {
            routeSvc = InvokerProxy.instance.create("LoanRouteService");
            routeList = routeSvc.getList([:]);
            routeList = routeList.sort{ it.description }
        } catch(ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }   
    }

    void init() {
        page = 'init';
        entity = [:];
    }

    def close() {
        return '_close';
    }

    def next() {
        page = 'main'
        
        selectedCollectionSheet = [:];
        def params = [ routecode: route.code ];
        entity.collectionsheets = fieldColSvc.getCurrentLoans(params);
        if (!entity.collectionsheets) {
            throw new Exception("No collections to display.");
        }
        collectionSheetsHandler.reload();
        return page;
    }

    def back() {
        page = 'init'
        return 'default';
    }

    public void setSelectedCollectionSheet( selectedCollectionSheet ) {
        this.selectedCollectionSheet = selectedCollectionSheet;
        if (!this.selectedCollectionSheet) selectedCollectionSheet = [:];
        paymentsHandler.reload();
    }

    def collectionSheetsHandler = [
        fetchList: {o->
            if (!entity.collectionsheets) entity.collectionsheets = [];
            return entity.collectionsheets;
        }
    ] as BasicListModel;

    def paymentsHandler = [
        fetchList: {o->
            if (!selectedCollectionSheet.payments) selectedCollectionSheet.payments = [];
            return selectedCollectionSheet.payments;
        }
    ] as BasicListModel;
}
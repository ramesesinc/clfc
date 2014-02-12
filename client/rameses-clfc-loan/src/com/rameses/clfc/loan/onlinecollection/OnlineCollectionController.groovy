package com.rameses.clfc.loan.onlinecollection;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class OnlineCollectionController
{
    @Binding
    def binding;

    def cashbreakdown;
    def service;
    def entity;
    def totalbreakdown = 0;
    def mode = 'init'
    def collector;
    def txndate;
    def prevcashbreakdown = [];
    def selectedPayment;

    OnlineCollectionController() {
        try {
            service = InvokerProxy.instance.create('LoanOnlineCollectionService');
        } catch (ConnectException ce) {
            ce.printStackTrace();
            throw ce;
        }
    }

    void init() {
        collector  = [:];
        mode = 'init';
    }

    def paymentsHandler = [
        fetchList: {o->
            if (!entity.payments) entity.payments = [];
            return entity.payments;
        },
        onOpenItem: {itm, colName->
            def params = [
                txncode             : 'ONLINE',
                afterSaveHandler    : {o->
                    selectedPayment.voidid = o.objid;
                    selectedPayment.pending = 1;
                },
                afterApproveHandler : {o->
                    selectedPayment.voided = 1;
                    selectedPayment.pending = 0;
                    binding.refresh('totalcollection');
                }
            ];
            if (itm.voidid) {
                params.payment = itm;
                return InvokerUtil.lookupOpener('voidrequest:open', params);
            } else {
                params.route = itm.route;
                params.collectionsheet = [
                    loanapp : itm.loanapp,
                    borrower: itm.borrower
                ];
                params.payment = [
                    objid       : itm.objid,
                    payamount   : itm.amount
                ];
                params.collector = [
                    objid   : ClientContext.currentContext.headers.USERID,
                    name    : ClientContext.currentContext.headers.NAME
                ];
                return InvokerUtil.lookupOpener('voidrequest:create', params);
            }
        }
    ] as BasicListModel;

    public def getTotalcollection() {
        if (!entity.payments) return 0;
        return entity.payments.findAll{ it.voided == 0 }.amount.sum();
    }

    def getCollectorList() {
        println 'get collector list';
        return service.getCollectors();
    }

    def getCollectionDateList() {
        return collector.collectionDates;
    }

    def close() {
        return '_exit';
    }

    def next() {
        mode = 'read';
        entity = service.getCollectionForPosting([collector: collector, txndate: txndate]);
        paymentsHandler.reload();
        if (!entity.cashbreakdown) {
            mode = 'create';
            totalbreakdown = 0;
        } else {
            totalbreakdown = entity.cashbreakdown.amount.sum();
        }
        return 'main';
    }

    public def getCashbreakdown() {
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

    public boolean getIsAllowSave() {
        if (mode == 'init' || mode == 'read') return false;
        return true;
    }

    public boolean getIsAllowEdit() {
        if (mode == 'init' || mode != 'read') return false;
        return true;
    }

    public boolean getIsAllowPost() {
        if (mode == 'init' || mode != 'read' || totalbreakdown != getTotalcollection()) return false;
        return true;
    }

    def back() {
        mode = 'init';
        return 'default';
    }

    def save() {
        if (totalbreakdown != getTotalcollection())
            throw new Exception('Total for denomination does not match with total amount collected.');

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
        println 'mode = '+mode;
        prevcashbreakdown.clear();
        def map;
        entity.cashbreakdown.each{
            map = [:];
            map.putAll(it);
            prevcashbreakdown.add(map);
        }
        binding.refresh();
    }

    def cancel() {
        mode = 'read';
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
            return new Opener(outcome:'default')
        }
    }
}
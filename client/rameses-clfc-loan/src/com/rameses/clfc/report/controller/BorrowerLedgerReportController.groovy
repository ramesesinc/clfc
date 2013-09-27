package com.rameses.clfc.report.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*
import com.rameses.osiris2.reports.*;

class BorrowerLedgerReportController //extends ReportModel
{    
    //@Service('LoanLedgerService')
    def ledgerSvc;
    
    def mode = 'mgmt';
       
    void init() {
        println 'loan ledger'
    }
    
    def selectedLedger;
    def ledgerHandler = [
        fetchList: {o->
            /*def list = ledgerSvc.getList([:]);
            println 'list = '+list;*/
            return [];
        }
    ] as BasicListModel;
    
    def close() {
        return '_close';
    }
    
    def back() {
        mode = 'mgmt';
        return 'default';
    }
    
    def view() {
        viewReport();
        mode = 'view';
        return 'preview'
    }
    
    //TEMP DATA
    /*def data = [
        borrowername	: 'LIAN YOUNG',
        spousename	: 'SUMMER YOUNG',
        borroweraddress	: 'CEBU CITY',
        borrowertelno   : '233-4152',
        comakername	: 'LEN VALDEZ',
        comakeraddress	: 'CEBU CITY',
        comakertelno	: '412-5689',
        officename      : 'OFFICE 1',
        officeaddress   : 'CEBU CITY',
        officetelno     : '233-8959',
        collateral      : 'COLLATERAL 1',
        computercode    : 'CC-5989',
        pn              : 'PN',
        checkno         : '65515616',
        originalamtloan : 10000.00,
        dategranted     : new Date(),
        datematurity    : new Date(),
        interestrate    : 20.00,
        term            : 129,
        totalcharges    : 10000.00,
        dailypayment    : 100.00,

        payments        : [
                            [
                                paymentschedule : new Date(),
                                originalamount  : 10000.00,
                                partialpayment  : 100.00,
                                balanceamount   : 9900.00,
                                interestpaid    : 10.00,
                                penaltycharge   : 10.00,
                                totalpayment    : 110.00,
                                receiptno       : '0001',
                                datepaid        : new Date(),
                                remarks         : 'TEST'
                            ],

                            [
                                paymentschedule : new Date(),
                                originalamount  : 10000.00,
                                partialpayment  : 100.00,
                                balanceamount   : 9800.00,
                                interestpaid    : 20.00,
                                penaltycharge   : 20.00,
                                totalpayment    : 120.00,
                                receiptno       : '0002',
                                datepaid        : new Date(),
                                remarks         : 'TEST'
                            ],

                            [
                                paymentschedule : new Date(),
                                originalamount  : 10000.00,
                                partialpayment  : 100.00,
                                balanceamount   : 9700.00,
                                interestpaid    : 30.00,
                                penaltycharge   : 30.00,
                                totalpayment    : 130.00,
                                receiptno       : '0003',
                                datepaid        : new Date(),
                                remarks         : 'TEST'
                            ]
                          ]
    ];*/

    /*public Map getParameters() {
        return [:];
    }

    public Object getReportData() {
        return [:];
    }

    public String getReportName() {
        return "com/rameses/clfc/report/borrower/ledger/BorrowerLedger.jasper";
    }

    public SubReport[] getSubReports() {
        return [
            new SubReport('BORROWERLEDGERPAYMENT', 'com/rameses/clfc/report/borrower/ledger/BorrowerLedgerPayment.jasper')
        ];
    }*/
}

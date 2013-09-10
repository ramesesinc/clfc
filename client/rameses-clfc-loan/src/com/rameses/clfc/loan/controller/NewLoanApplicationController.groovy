import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.*;

class NewLoanApplicationController extends CRUDController
{
    @Service('LoanProductTypeService')
    def prodTypeSvc

    @PropertyChangeListener
    def listener = [
        "entity.clienttype": {o->
            if( entity.clienttype != 'MARKET' ) entity.marketedby = null
            binding.refresh('entity.marketedby')
        }
    ]

    String serviceName = 'LoanAppService';
    String prefixId = 'LOAN';

    def renewalTypes = LOV.LOAN_RENEWAL_TYPES;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;

    Map createEntity() { 
        return [
            apptype: 'NEW',
            version:1,
            appno:'1',
            loanno:'1',
            branch:[
                code:'006',
                name:'N. BACALSO BRANCH'
            ],
            previousloans:[],
        ]; 
    } 

    void initOnline() {
        create(); 
        entity.mode = 'ONLINE';
    }

    def initCapture() {
        create(); 
        entity.mode = 'CAPTURE';
        return "capture"
    }    
    
    def getCustomerLookupHandler() {
        return InvokerUtil.lookupOpener('customer:lookup', [:]); 
    }

    def getProductTypes() {
        return prodTypeSvc.getList([:])
    }

    def previousLoansHandler = [
        fetchList: {o->
            return entity.previousloans
        },
        onAddItem: {o->
            entity.previousloans.add(o)
        },
        onRemoveItem: {o->
            if( MsgBox.confirm("You are about to remove this loan. Continue?") ) {
                entity.previousloans.remove(o)
                return true
            }
            return false
        },
        onColumnUpdate: {o, colName->
            def item = entity.previousloans.find{ it == o }
            if( item ) item = o
        }
    ] as EditorListModel
}

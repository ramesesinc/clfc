import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
import com.rameses.clfc.loan.*;

class NewLoanApplicationController extends CRUDController
{
    String serviceName = 'LoanAppService';
    String prefixId = 'LOAN';

    def appTypes = LOV.LOAN_APP_TYPES;
    def clientTypes = LOV.LOAN_CLIENT_TYPES;
    def productTypes = LOV.LOAN_PROD_TYPES;

    Map createEntity() { 
        return [
            previousloans:[], 
            schedule: [
                term:                120, 
                interestrate:        5.00, 
                pastduerate:         6.00, 
                underpaymentpenalty: 3.00,                 
                surchargerate:       3.00, 
                absentpenalty:       3.00, 
                code:               'DAILY' 
            ] 
        ]; 
    } 

    void initOnline() {
        create(); 
        entity.mode = 'ONLINE';
    }

    void initCapture() {
        create(); 
        entity.mode = 'CAPTURE';
    }
    
    
    def getCustomerLookupHandler() {
        return InvokerUtil.lookupOpener('customer:lookup', [:]); 
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

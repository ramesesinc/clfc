package rameses.clfc.main;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.rcp.framework.*;
import com.rameses.util.*;
import com.rameses.osiris2.*;
import com.rameses.osiris2.client.*;

public class HomeControllerImpl {

    def formActions = [];
    
    void init(){
        
        def actionProvider = ClientContext.currentContext.actionProvider;
        formActions = actionProvider.lookupActions('home.action');
        formActions.each{ 
            if (!it.icon) it.icon = 'images/home-icon.png';

            def target = it.properties.target+'';
            if (!target.matches('window|popup|process|_window|_popup|_process')) 
            	it.properties.target = 'window'; 
        }
    }

}

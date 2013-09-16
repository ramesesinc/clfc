package com.rameses.clfc.customer;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;

class CustomerSearchController extends BasicLookupModel 
{
    //to be feed by the caller
    
    @Service('CustomerService')
    def service;
    
    def opener;
    
    void init() { 
        
    } 

    boolean show(String searchtext) { 
        customerlistHandler.searchtext = searchtext; 
        return true; 
    }
    
    def getTitle() {
        return ''' 
            <font color="#808080" size="5"><b>Search Customers</b></font><br>
        '''; 
    } 
    
    def getValue() { 
        return customerlistHandler.selectedValue; 
    } 
    
    def selectedCustomer; 
    def customerlistHandler = [ 
        fetchList: {o-> 
            return service.getList(o);  
        } 
    ] as PageListModel;

    void search() { 
        customerlistHandler.reload(); 
    } 
    
    void moveFirstPage() {  
        customerlistHandler.moveFirstPage(); 
    } 
    
    void moveBackPage() { 
        customerlistHandler.moveBackPage(); 
    } 
    
    void moveNextPage() {  
        customerlistHandler.moveNextPage(); 
    } 
    
    void moveLastPage() {}     
    
    def create() {
        
    }
    
    void view() {
        
    }
} 
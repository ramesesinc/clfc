package com.rameses.clfc.branch;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*;
import com.rameses.osiris2.common.*;
        
public class BranchInfoController extends CRUDController 
{
    def node, filetype;

    String preferredTitle = 'Branch Information';    
    String serviceName = 'OrgAdminService';
    String entityName  = 'BRANCH'; 
    
    String createFocusComponent = 'entity.objid';
    String editFocusComponent = 'entity.name';            
    boolean showConfirmOnSave = false;
    boolean allowApprove = false;
    
    Map createPermission = [domain:'DATAMGMT', role:'DATAMGMT_AUTHOR', permission:'branch.create']; 
    Map editPermission = [domain:'DATAMGMT', role:'DATAMGMT_AUTHOR', permission:'branch.edit']; 
    Map deletePermission = [domain:'DATAMGMT', role:'DATAMGMT_AUTHOR', permission:'branch.delete']; 
    Map approvePermission = [domain:'DATAMGMT', role:'DATAMGMT_AUTHOR', permission:'branch.approve'];     
    
    boolean isAllowCreate() {
        return (node != null); 
    }
    
    Map createEntity() {
        return [
            orgclass:       'BRANCH', 
            parentid:       node?.objid, 
            parentclass:    node?.orgclass 
        ];
    } 

    void afterCreate(data) {
        data.objid = null; 
    }
}
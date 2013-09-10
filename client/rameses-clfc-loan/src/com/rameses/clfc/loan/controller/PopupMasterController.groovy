package com.rameses.clfc.loan.controller;

import com.rameses.rcp.common.*;
import com.rameses.rcp.annotations.*;
import com.rameses.osiris2.client.*;

public class PopupMasterController
{
    @Binding
    def binding

    @ChangeLog
    def changeLog
    
    def entity
    def handler
    def mode

    public def createEntity() {
        return [:]
    }

    public final init() {
        entity = createEntity()
    }
    
    public def create() {
        init()
        mode = 'create'
        return null
    }
    
    public def doOk() {
        if( handler ) handler(entity)
        return "_close"
    }

    public def doCancel() {
        if( mode == 'edit' ) {
            if( !MsgBox.confirm("Changes will be discarded. Continue?") ) return null

            if( changeLog.hasChanges() ) {
                changeLog.undoAll()
                changeLog.clear()
            }
            mode = 'read'
            return null
        }
        return "_close"
    }
}
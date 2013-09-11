package com.rameses.clfc.util;

class LoanUtil
{
    private static def kindTypes;
    private static def occupancyTypes;
    private static def rentTypes;
    private static def ownershipTypes;
    private static def vehicleKinds;
    private static def vehicleUses;
    private static def propertyClassificationTypes;
    private static def propertyUomTypes;
    private static def propertyModeOfAcquisitionTypes;
    private static def otherLendingModesOfPayment;
    
    public static def getKindTypes() {
        if( kindTypes == null ) {
            kindTypes = [
                [key:'SARI_SARI', value:'SARI-SARI STORE'], 
                [key:'CARENDERIA', value:'CARENDERIA'], 
                [key:'OPERATOR', value:'OPERATOR'], 
                [key:'OTHERS', value:'OTHERS']
            ]
        }
        return kindTypes;
    }

    public static def getOccupancyTypes() {
        if( occupancyTypes == null ) {
            occupancyTypes = [
                [key:'OWNED', value:'OWNED'], 
                [key:'RENTED', value:'RENTED'], 
                [key:'FREE_USE', value:'FREE USE'], 
                [key:'OTHERS', value:'OTHERS']
            ]
        }
        return occupancyTypes;
    }

    public static def getRentTypes() {
        if( rentTypes == null ) {
            rentTypes = [
                [key:'W_CONTRACT', value:'W/CONTRACT'], 
                [key:'WO_CONTRACT', value:'W/O CONTRACT']
            ]
        }
        return rentTypes;
    }

    public static def getOwnershipTypes() {
        if( ownershipTypes == null ) {
            ownershipTypes = [
                [key:'SOLE', value:'SOLE PROPRIETORSHIP'], 
                [key:'PARTNERSHIP', value:'PARTNERSHIP'], 
                [key:'CORPORATION', value:'CORPORATION']
            ]
        }
        return ownershipTypes;
    }
    
    public static def getVehicleKinds() {
        if( vehicleKinds == null ) {
            vehicleKinds = [
                [key:'MOTORCYCLE', value:'MOTORCYCLE'], 
                [key:'PUJ', value:'PUJ'], 
                [key:'OTHERS', value:'OTHERS']
            ]
        }
        return vehicleKinds;
    }
    
    public static def getVehicleUses() {
        if( vehicleUses == null ) {
            vehicleUses =  [
                [key:'FOR_HIRE', value:'FOR HIRE'], 
                [key:'PRIVATE', value:'PRIVATE']
            ]
        }
        return vehicleUses;
    }
    
    public static def getPropertyClassificationTypes() {
        if( propertyClassificationTypes == null ) {
            propertyClassificationTypes = [
                [key:'RESIDENCIAL', value:'RESIDENCIAL'], 
                [key:'AGRICULTURAL', value:'AGRICULTURAL']
            ]
        }
        return propertyClassificationTypes;
    }
    
    public static def getPropertyUomTypes() {
        if( propertyUomTypes == null ) {
            propertyUomTypes = [
                [key:'SQM', value:'sqm'], 
                [key:'HECTARES', value:'hectares']
            ]
        }
        return propertyUomTypes;
    }
    
    public static def getPropertyModeOfAcquisitionTypes() {
        if( propertyModeOfAcquisitionTypes == null ) {
            propertyModeOfAcquisitionTypes = [
                [key:'INHERETED', value:'INHERETED'], 
                [key:'SALE', value:'SALE'], 
                [key:'DONATION', value:'DONATION'], 
                [key:'GRANTS', value:'GRANTS/AWARDS'], 
                [key:'OTHERS', value:'OTHERS']
            ]
        }
        return propertyModeOfAcquisitionTypes;
    }
    
    public static def getOtherLendingModesOfPayment() {
        if( otherLendingModesOfPayment == null ) {
            otherLendingModesOfPayment = [
                [key:'DAILY', value:'DAILY'], 
                [key:'WEEKLY', value:'WEEKLY'], 
                [key:'MONTHLY', value:'MONTHLY']
            ]
        }
        return otherLendingModesOfPayment;
    }
}
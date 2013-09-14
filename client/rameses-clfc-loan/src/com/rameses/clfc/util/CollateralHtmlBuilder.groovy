package com.rameses.clfc.util;

class CollateralHtmlBuilder extends HtmlBuilder
{
    public def buildAppliance( appliance ) {
        if (appliance == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td width="100px">Type : </td>
                    <td>$appliance.type</td>
                </tr>
                <tr>
                    <td>Brand : </td>
                    <td>$appliance.brand</td>
                </tr>
                <tr>
                    <td>Date Acquired : </td>
                    <td>$appliance.dtacquired</td>
                </tr>
                <tr>
                    <td>Model No. : </td>
                    <td>$appliance.modelno</td>
                </tr>
                <tr>
                    <td>Serial No. : </td>
                    <td>$appliance.serialno</td>
                </tr>
                <tr>
                    <td>Market Value : </td>
                    <td>$appliance.marketvalue</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td>
                        <p>$appliance.remarks</p>
                    </td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildProperty( property ) {
        if (property == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td width="100px">Classification : </td>
                    <td>$property.classification</td>
                </tr>
                <tr>
                    <td>Location : </td>
                    <td>$property.location</td>
                </tr>
                <tr>
                    <td>Area : </td>
                    <td>$property.areavalue $property.areauom</td>
                </tr>
                <tr>
                    <td>Zonal Value : </td>
                    <td>$property.zonalvalue</td>
                </tr>
                <tr>
                    <td>Date Acquired : </td>
                    <td>${ifNull(property.dtacquired, '-')}</td>
                </tr>
                <tr>
                    <td>Acquired From : </td>
                    <td>$property.acquiredfrom</td>
                </tr>
                <tr>
                    <td class="nowrap">Mode of Acquisition : </td>
                    <td>$property.modeofacquisition</td>
                </tr>
                <tr>
                    <td>Registered Name : </td>
                    <td>$property.registeredname</td>
                </tr>
                <tr>
                    <td>Market Value : </td>
                    <td>$property.marketvalue</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td> <p>$property.remarks</p> </td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildVehicle( vehicle ) {
        if (vehicle == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td class="nowrap" width="120px">
                        Kind of Vehicle : 
                    </td>
                    <td>$vehicle.kind</td>
                </tr>
                <tr>
                    <td>Make : </td>
                    <td>$vehicle.make</td>
                </tr>
                <tr>
                    <td>Model : </td>
                    <td>$vehicle.model</td>
                </tr>
                <tr>
                    <td>Body Type : </td>
                    <td>$vehicle.bodytype</td>
                </tr>
                <tr>
                    <td>Use : </td>
                    <td>$vehicle.usetype</td>
                </tr>
                <tr>
                    <td>Date Acquired : </td>
                    <td>$vehicle.dtacquired</td>
                </tr>
                <tr>
                    <td>Acquired From : </td>
                    <td>$vehicle.acquiredfrom</td>
                </tr>
                <tr>
                    <td>Registered Name : </td>
                    <td>$vehicle.registeredname</td>
                </tr>
                <tr>
                    <td>Chassis No. : </td>
                    <td>$vehicle.chassisno</td>
                </tr>
                <tr>
                    <td>Plate No. : </td>
                    <td>$vehicle.plateno</td>
                </tr>
                <tr>
                    <td>Engine No. : </td>
                    <td>$vehicle.engineno</td>
                </tr>
                <tr>
                    <td>Market Value : </td>
                    <td>$vehicle.marketvalue</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td> <p>$vehicle.remarks</p> </td>
                </tr>
                </table>
            </body>
        """; 
        return getHtml(htmlbody);
    }
}
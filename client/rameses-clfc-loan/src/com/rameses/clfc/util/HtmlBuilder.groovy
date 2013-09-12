package com.rameses.clfc.util;

class HtmlBuilder
{
    private def ifNull( value, defaultvalue ) {
        if( value == null ) return defaultvalue;
        return value;
    }
    
    private def getHtml( htmlbody ) {
        return  """
                    <html>
                        <head>
                            <style type="text/css">
                                .bold { font-weight: bold; }
                                body {
                                    font-family: tahoma;
                                    font-size: 10px;
                                }
                                table {
                                    padding-left: 10px;
                                    width: 700px;
                                }
                            </style>
                        </head>
                        ${body}
                    </html>
                """
    }
    
    public def buildBorrower( borrower ) {
        if( borrower == null ) return '';
        def htmlbody =  """
                        <body>
                            <font class="bold">Personal Information</font>
                            <table>
                                <tbody>
                                    <tr>
                                        <td>Name: </td>
                                        <td>$borrower.lastname, $borrower.firstname ${ifNull(borrower.middlename, '')}</td>
                                    </tr>
                                    <tr>
                                        <td>Address: </td>
                                        <td>${ifNull(borrower.address, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Birth Date: </td>
                                        <td>${ifNull(borrower.birthdate, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Marital Status: </td>
                                        <td>${ifNull(borrower.civilstatus, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Citizenship: </td>
                                        <td>${ifNull(borrower.citizenship, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Contact No.: </td>
                                        <td>${ifNull(borrower.contactno, '-')}</td>
                                    </tr>
                                </tbody>
                            </table><br/>
                            <font class="bold">Residency</font>
                            <table>
                                <tbody>
                                    <tr>
                                        <td width="120px">Type: </td>
                                        <td width="150px">${ifNull(borrower.residency?.type, '-')}</td>
                                        <td width="120px">Since: </td>
                                        <td width="150px">${ifNull(borrower.residency?.since, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Rent Type: </td>
                                        <td>${ifNull(borrower.residency?.renttype, '-')}</td>
                                        <td>Rent Amount: </td>
                                        <td>${ifNull(borrower.residency?.rentamount, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Remarks: </td>
                                        <td colspan="3">${ifNull(borrower.residency?.remarks, '-')}</td>
                                    </tr>
                                </tbody>
                            </table><br/>
                            <font class="bold">Lot Occupancy</font>
                            <table>
                                <tbody>
                                    <tr>
                                        <td width="120px">Type: </td>
                                        <td width="150px">${ifNull(borrower.occupancy?.type, '-')}</td>
                                        <td width="120px">Since: </td>
                                        <td width="150px">${ifNull(borrower.occupancy?.since, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Rent Type: </td>
                                        <td>${ifNull(borrower.occupancy?.renttype, '-')}</td>
                                        <td>Rent Amount: </td>
                                        <td>${ifNull(borrower.occupancy?.rentamount, '-')}</td>
                                    </tr>
                                    <tr>
                                        <td>Remarks: </td>
                                        <td colspan="3">${ifNull(borrower.occupancy?.remarks, '-')}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </body>
                    """
        return getHtml(htmlbody)
    }
    
    public def buildBusiness( business ) {
        if( business == null ) return '';
        def htmlbody =  """
                            <body>
                                <font class="bold">$business.tradename</font>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td width="100px">Kind of Business: </td>
                                            <td>$business.kind</td>
                                        </tr>
                                        <tr>
                                            <td>Stall Size(in mtrs.): </td>
                                            <td>$business.stallsize</td>
                                        </tr>
                                        <tr>
                                            <td>Address: </td>
                                            <td>$business.address</td>
                                        </tr>
                                        <tr>
                                            <td>Occupancy: </td>
                                            <td>$business.occupancy.type</td>
                                        </tr>
                                        <tr>
                                            <td>Rent Type</td>
                                            <td>${ifNull(business.occupancy.renttype, '-')}</td>
                                        </tr>
                                        <tr>
                                            <td>Rent Amount: </td>
                                            <td>${ifNull(business.occupancy.rentamount, '-')}</td>
                                        </tr>
                                        <tr>
                                            <td>Business Started: </td>
                                            <td>$business.dtstarted</td>
                                        </tr>
                                        <tr>
                                            <td>Ownership: </td>
                                            <td>$business.ownership</td>
                                        </tr>
                                        <tr>
                                            <td>Capital Invested: </td>
                                            <td>$business.capital</td>
                                        </tr>
                                        <tr>
                                            <td>Esimated Daily Sales: </td>
                                            <td>$business.avgsales</td>
                                        </tr>
                                        <tr>
                                            <td>Business Hours: </td>
                                            <td>$business.officehours</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <br/><font class="bold">Credit Investigation Report</font>
                                                <table width="100%">
                                                    <tbody>
                                                        <tr>
                                                            <td>Business Evaluation: </td>
                                                            <td>
                                                                <p>$business.ci.evaluation</p>
                                                            </td>                                                    
                                                        </tr>
                                                        <tr>
                                                            <td>Physical Outlook: </td>
                                                            <td>
                                                                <p>$business.ci.physicaloutlook
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                Cash Count<br/>
                                                                <table width="100%">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td width="80px">Filed By: </td>
                                                                            <td>$business.ci.filedby</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Date: </td>
                                                                            <td>$business.ci.dtfiled</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Time: </td>
                                                                            <td>$business.ci.timefiled</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>Amount: </td>
                                                                            <td>$business.ci.amount</td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </body>
                        """
                
        return getHtml(htmlbody);
    }
    
    public def buildVehicle( vehicle ) {
        if( vehicle == null ) return '';
        def htmlbody =  """
                            <body>
                                <font class="bold">Vehicle Information</font>
                                /*<table>
                                    <tbody>
                                        <tr>
                                            <td>Kind of Vehicle: </td>
                                            <td>$vehicle.kind</td>
                                        </tr>
                                        <tr>
                                            <td>Make: </td>
                                            <td>$vehicle.make</td>
                                        </tr>
                                        <tr>
                                            <td>Model: </td>
                                            <td>$vehicle.model</td>
                                        </tr>
                                        <tr>
                                            <td>Body Type: </td>
                                            <td>$vehicle.bodytype</td>
                                        </tr>
                                        <tr>
                                            <td>Use: </td>
                                            <td>$vehicle.usetype</td>
                                        </tr>
                                        <tr>
                                            <td>Date Acquired: </td>
                                            <td>$vehicle.dtacquired</td>
                                        </tr>
                                        <tr>
                                            <td>Acquired From: </td>
                                            <td>$vehicle.acquiredfrom</td>
                                        </tr>
                                        <tr>
                                            <td>Registered Name: </td>
                                            <td>$vehicle.registeredname</td>
                                        </tr>
                                        <tr>
                                            <td>Chassis No.: </td>
                                            <td>$vehicle.chassisno</td>
                                        </tr>
                                        <tr>
                                            <td>Plate No.: </td>
                                            <td>$vehicle.plateno</td>
                                        </tr>
                                        <tr>
                                            <td>Engine No.: </td>
                                            <td>$vehicle.engineno</td>
                                        </tr>
                                        <tr>
                                            <td>Market/Appraisal Value: </td>
                                            <td>$vehicle.marketvalue</td>
                                        </tr>
                                        <tr>
                                            <td>Remarks: </td>
                                            <td>
                                                <p>$vehicle.remarks</p>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>*/
                            </body>
                        """
        return getHtml(htmlbody);
    }
}
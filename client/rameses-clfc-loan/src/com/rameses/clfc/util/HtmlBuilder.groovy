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
                        .nowrap { white-space:nowrap; }
                        .bold { font-weight: bold; }
                        .subtitle { color:#808080; font-weight:bold; }
                        .subtable { 
                            border:1; cellspacing:0; cellpadding:0; 
                            spacing:0; padding:0; border-collapse:collapse; 
                        }
                        body {
                            font-family: tahoma;
                            font-size: 10px;
                        }
                        table {
                            padding-left: 10px;
                        }
                        p { text-alignment: left; }
                    </style>
                </head>
                ${htmlbody}
            </html>
        """;
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
        if (business == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>                
                <table>
                <tr>
                    <td>Tradename : </td>
                    <td>$business.tradename</td>
                </tr>                
                <tr>
                    <td class="nowrap">Kind of Business : </td>
                    <td>$business.kind</td>
                </tr>
                <tr>
                    <td>Stall Size(in mtrs.) : </td>
                    <td>$business.stallsize</td>
                </tr>
                <tr>
                    <td>Address : </td>
                    <td>$business.address</td>
                </tr>
                <tr>
                    <td>Occupancy : </td>
                    <td>$business.occupancy.type</td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp; Rent Type : </td>
                    <td>${ifNull(business.occupancy.renttype, '-')}</td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp; Rent Amount : </td>
                    <td>${ifNull(business.occupancy.rentamount, '-')}</td>
                </tr>
                <tr>
                    <td>Business Started : </td>
                    <td>$business.dtstarted</td>
                </tr>
                <tr>
                    <td>Ownership : </td>
                    <td>$business.ownership</td>
                </tr>
                <tr>
                    <td>Capital Invested : </td>
                    <td>$business.capital</td>
                </tr>
                <tr>
                    <td class="nowrap">Esimated Daily Sales : </td>
                    <td>$business.avgsales</td>
                </tr>
                <tr>
                    <td>Business Hours : </td>
                    <td>$business.officehours</td>
                </tr> 
                </table><br/> 
                       
                <font class="bold">Credit Investigation Report</font>
                <table style="width:100%;"> 
                <tr>
                    <td>
                        <span class="subtitle">Business Evaluation</span>
                        <table class="subtable" border="1" style="width:100%;">
                        <tr>
                            <td style="padding: 10px 10px 10px 10px;">
                                <p>${ifNull(business.ci?.evaluation, '-')}</p>
                            </td>
                        </tr> 
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br/>
                        <span class="subtitle">Physical Outlook</span>
                        <table class="subtable" border="1" style="width:100%;">
                        <tr>
                            <td style="padding: 10px 10px 10px 10px;">
                                <p>${ifNull(business.ci?.physicaloutlook, '-')}</p>
                            </td>
                        </tr> 
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br/>
                        <span class="subtitle">Cash Count</span>
                        <table>
                        <tr>
                            <td width="80px">Filed By: </td>
                            <td>${ifNull(business.ci?.filedby, '-')}</td>
                        </tr>
                        <tr>
                            <td>Date: </td>
                            <td>${ifNull(business.ci.dtfiled, '-')}</td>
                        </tr>
                        <tr>
                            <td>Time: </td>
                            <td>${ifNull(business.ci.timefiled, '-')}</td>
                        </tr>
                        <tr>
                            <td>Amount: </td>
                            <td>${ifNull(business.ci.amount, '-')}</td>
                        </tr>
                        </table>
                    </td>
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
    
    public def buildOtherLending( otherlending ) {
        if (otherlending == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td width="120px">Kind of Loan: </td>
                    <td>${ifNull(otherlending.kind, '-')}</td>
                </tr>
                <tr>
                    <td>Name of Lending Inst.: </td>
                    <td>$otherlending.name</td>
                </tr>
                <tr>
                    <td>Address: </td>
                    <td>${ifNull(otherlending.address, '-')}</td>
                </tr>
                <tr>
                    <td>Loan Amount: </td>
                    <td>$otherlending.amount</td>
                </tr>
                <tr>
                    <td>Date Granted: </td>
                    <td>${ifNull(otherlending.dtgranted, '-')}</td>
                </tr>
                <tr>
                    <td>Maturity Date: </td>
                    <td>${ifNull(otherlending.dtmatured, '-')}</td>
                </tr>
                <tr>
                    <td>Term: </td>
                    <td>${ifNull(otherlending.term, '-')}</td>
                </tr>
                <tr>
                    <td>Interest Rate: </td>
                    <td>${ifNull(otherlending.interest, '-')}</td>
                </tr>
                <tr>
                    <td>Mode of Payment: </td>
                    <td>${ifNull(otherlending.paymentmode, '-')}</td>
                </tr>
                <tr>
                    <td>Payment: </td>
                    <td>$otherlending.paymentamount</td>
                </tr>
                <tr>
                    <td>Collateral(s): </td>
                    <td> <p>${ifNull(otherlending.collateral, '-')}</p> </td>
                </tr>
                <tr>
                    <td>Remarks: </td>
                    <td> <p>${ifNull(otherlending.remarks, '-')}</p> </td>
                </tr>
                </table>
            </body>
        """; 
        return getHtml(htmlbody);
    }
    
    public def buildBankAccount( bankacct ) {
        if (bankacct == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td class="nowrap">Bank Name : </td>
                    <td>$bankacct.bankname</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td> <p>${ifNull(bankacct.remarks, '-')}</p> </td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildChild( child ) {
        if( child == null ) return '';
        def htmlbody =  """
            <body>
                <table>
                <tr>
                    <td colspan="3"><font class="bold">General Information</font></td> 
                </tr>                
                <tr>
                    <td width="5">&nbsp;</td>
                    <td width="120px">Name: </td>
                    <td>$child.name</td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Age: </td>
                    <td>$child.age</td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Educational Attainment: </td>
                    <td> <p>${ifNull(child.education, '-')}</p> </td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Remarks: </td>
                    <td><p>${ifNull(child.remarks, '-')}</p></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <br><font class="bold">Employments</font>
                    </td> 
                </tr>
                ${getEmploymentList(child.employments)}
                <tr>
                    <td colspan="3">
                        <br><font class="bold">Other Sources of Income</font>
                    </td> 
                </tr>
                ${getOtherIncomeList(child.otherincomes)}        
                </table>
            </body>
        """
        return getHtml(htmlbody);
    }
    
    private def getEmploymentList( list ) {
        if (list.isEmpty()) {
            return '''
                <tr>
                    <td width="5">&nbsp;</td>
                    <td class="nowrap" colspan="2">
                        <i>-- No available information found --</i>                         
                    </td> 
                </tr> 
            ''';
        } 
        
        def buffer = new StringBuffer();
        list.each { 
            def s = """ 
                <tr>
                    <td width="5">&#8226; </td>
                    <td>Employer : </td>
                    <td>$it.employer</td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Remarks : </td>
                    <td><p>$it.remarks</p></td>
                </tr>
            """;
            buffer.append(s); 
        }
        return buffer.toString();
    }
    
    private def getOtherIncomeList( list ) {
        if (list.isEmpty()) {
            return '''
                <tr>
                    <td width="5">&nbsp;</td>
                    <td class="nowrap" colspan="2">
                        <i>-- No available information found --</i>                         
                    </td> 
                </tr> 
            ''';
        } 
        
        def buffer = new StringBuffer();
        list.each { 
            def s = """ 
                <tr>
                    <td width="5">&#8226; </td>
                    <td>Source of Income : </td>
                    <td>$it.name</td>
                </tr>
                <tr>
                    <td width="5">&nbsp; </td>
                    <td>Remarks : </td>
                    <td><p>${ifNull(it.remarks, '-')}</p></td>
                </tr>
            """;
            buffer.append(s); 
        }
        return buffer.toString(); 
    }
    
    public def buildSibling( sibling ) {
        if (sibling == null) return '';
        
        def htmlbody =  """
            <body>
                <table>
                <tr>
                    <td colspan="3"><font class="bold">General Information</font></td> 
                </tr>                                
                <tr>    
                    <td width="5">&nbsp;</td>
                    <td>Name : </td>
                    <td>$sibling.name</td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Age : </td>
                    <td>$sibling.age</td>
                </tr>
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>Address : </td>
                    <td>$sibling.address</td>
                </tr>
                <tr>
                    <td colspan="3">
                        <br><font class="bold">Employments</font>
                    </td> 
                </tr>  
                ${getEmploymentList(sibling.employments)}
                <tr>
                    <td colspan="3">
                        <br><font class="bold">Other Sources of Income</font>
                    </td> 
                </tr>  
                ${getOtherIncomeList(sibling.otherincomes)}
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildEmployment( employment ) {
        if (employment == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td>Empoyer : </td>
                    <td>$employment.employer</td>
                </tr>
                <tr>
                    <td>Tel. No. : </td>
                    <td>${ifNull(employment.contactno, '-')}</td>
                </tr>
                <tr>
                    <td>Address : </td>
                    <td>${ifNull(employment.address, '-')}</td>
                </tr>
                <tr>
                    <td>Position : </td>
                    <td>${ifNull(employment.position, '-')}</td>
                </tr>
                <tr>
                    <td>Salary : </td>
                    <td>${ifNull(employment.salary, '-')}</td>
                </tr>
                <tr>
                    <td class="nowrap">Length of Service : </td>
                    <td>${ifNull(employment.lengthofservice, '-')}</td>
                </tr>
                <tr>
                    <td>Status : </td>
                    <td>${ifNull(employment.status, '-')}</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td><p>$employment.remarks</p></td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildOtherIncome( otherincome ) {
        if (otherincome == null) return '';
        
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td class="nowrap">Source of Income : </td>
                    <td>$otherincome.name</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td> <p>${ifNull(otherincome.remarks, '-')}</p> </td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
    
    public def buildEducation( education ) {
        if( education == null ) return '';
        def htmlbody =  """
            <body>
                <font class="bold">General Information</font>
                <table>
                <tr>
                    <td>Education Attainment : </td>
                    <td>$education.attainment</td>
                </tr>
                <tr>
                    <td>School : </td>
                    <td>$education.school</td>
                </tr>
                <tr>
                    <td>Remarks : </td>
                    <td> <p>${ifNull(education.remarks, '-')}</p> </td>
                </tr>
                </table>
            </body>
        """;
        return getHtml(htmlbody);
    }
}
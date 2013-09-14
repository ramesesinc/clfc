package com.rameses.clfc.util;

class BorrowerInfoHtmlBuilder extends HtmlBuilder
{
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
}

package com.rameses.clfc.util;

class HtmlBuilder
{
    protected def ifNull( value, defaultvalue ) {
        if( value == null ) return defaultvalue;
        return value;
    }
    
    protected def getHtml( htmlbody ) {
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
    
    protected def getEmploymentList( list ) {
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
    
    protected def getOtherIncomeList( list ) {
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
}
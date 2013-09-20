package com.rameses.clfc.util;

class CommentHtmlBuilder extends HtmlBuilder
{
    public def buildComments(comments) {
        def htmlbody = 	"""
                            <font class="bold">Comments</font><br/><br/>
                            <table>
                                <tbody>
                                    ${getComments(comments)}
                                </tbody>
                            </table>
                        """
        return getHtml(htmlbody);
    }
    
    private def getComments( list ) {
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
                    <td width="5">>></td>
                    <td>posted by $it.postedby @ $it.dtposted</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <p style="padding: 0px 10px 0px 0px;">$it.remarks</p>
                    </td>
                </tr>
            """;
            buffer.append(s); 
        }
        return buffer.toString();
        
    }
}

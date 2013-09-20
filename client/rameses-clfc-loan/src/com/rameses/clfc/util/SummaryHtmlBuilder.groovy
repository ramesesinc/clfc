package com.rameses.clfc.util;

import com.rameses.util.TemplateProvider;

class SummaryHtmlBuilder extends HtmlBuilder
{
    public def buildSummary( summary ) {
        def url = 'com/rameses/clfc/loan/summary/';
        def template = TemplateProvider.instance;
        StringBuffer sb=new StringBuffer();
        def ifnull = {v, dv-> 
            ifNull(v, dv);
        }
        
        sb.append(template.getResult(url+'loandetail.gtpl', [summary: summary, ifnull: ifnull]));
        sb.append(template.getResult(url+'borrower.gtpl', [borrower: summary.borrower, ifnull: ifnull]))
        sb.append(template.getResult(url+'spouse.gtpl', [spouse: summary.borrower.spouse, ifnull: ifnull]))
        return getHtml(sb.toString());
    }
    
    private getSpouse( borrower ) {
        if(!borrower.spouse) return ''
    }
}

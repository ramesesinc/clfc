package com.rameses.clfc.android.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rameses.client.services.AbstractService;

public class LoanBillingService extends AbstractService
{

	public String getServiceName() {
		return "MobileBillingService";
	} 
	
	public List<Map> getRoutes(Map params) {
		return (List<Map>) invoke("getRoutes", params);
	}
	
	public Map downloadBilling(Map params) {
		return (Map) invoke("downloadBilling", params);
	}
}
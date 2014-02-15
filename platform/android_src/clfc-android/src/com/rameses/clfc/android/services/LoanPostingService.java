package com.rameses.clfc.android.services;

import java.util.List;
import java.util.Map;

import com.rameses.client.services.AbstractService;

public class LoanPostingService extends AbstractService
{
	public String getServiceName() {
		return "MobilePostingService";
	}
	
	public Map voidPayment(Map params) {
		return (Map) invoke("voidPayment", params);
	}
	
	public Map postPayment (Map params) {
		return (Map) invoke("postPayment", params);
	}
	
	public Map updateRemarks(Map params) {
		return (Map) invoke("updateRemarks", params);
	}
	
	public Map removeRemarks(Map params) {
		return (Map) invoke("removeRemarks", params);
	}
	
	public boolean isVoidPaymentApproved(Map params) {
		return (Boolean) invoke("isVoidPaymentApproved", params);
	}
	
	public Map postSpecialCollectionRequest(Map params) {
		return (Map) invoke("postSpecialCollectionRequest", params);
	}
	
	public Map downloadSpecialCollection(Map params) {
		return (Map) invoke("downloadSpecialCollection", params);
	}
	
	public Map remitRouteCollection(Map params) {
		return (Map) invoke("remitRouteCollection", params);
	}
}

package com.csi.sbs.sysadmin.business.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.model.HeaderModel;




public class SRUtil {
	
	
	/**
	 * 不带header
	 * @param path
	 * @param json
	 * @return
	 */
	public static ResponseEntity<String> sendNoWithHeader(RestTemplate restTemplate,String path, String json) {
		ResponseEntity<String> result = restTemplate.postForEntity(path, PostUtil.getRequestEntity(json),String.class);
	    //System.out.println("====="+result);
	    if(result.getStatusCodeValue()==200){
	    	return result;
	    }
		return result;
	}
	
	/**
	 * 带header
	 */
	public static ResponseEntity<String> sendWithHeader(RestTemplate restTemplate,String path, HeaderModel header,String json) {
		HttpHeaders requestHeaders = new HttpHeaders();
		
        requestHeaders.add("developerID", header.getUserID());
        requestHeaders.add("countryCode", header.getCountryCode());
        requestHeaders.add("clearingCode", header.getClearingCode());
        requestHeaders.add("branchCode", header.getBranchCode());
        requestHeaders.add("customerNumber", header.getCustomerNumber());
        requestHeaders.add("sandBoxId", header.getSandBoxId());
        requestHeaders.add("dockerId", header.getDockerId());
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
       
		ResponseEntity<String> result = restTemplate.postForEntity(path, requestEntity,String.class);
	    if(result.getStatusCodeValue()==200){
	    	return result;
	    }
		return result;
	}

}

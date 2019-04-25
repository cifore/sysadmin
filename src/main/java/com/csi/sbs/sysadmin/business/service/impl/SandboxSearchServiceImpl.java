package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.clientmodel.SandboxSearchModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.service.SandboxSearchService;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.constant.PathConstant;

@Service("SandboxSearchService")
public class SandboxSearchServiceImpl implements SandboxSearchService{

	@Override
	public Map<String, Object> getTableSandboxInfo(RestTemplate restTemplate, SandboxSearchModel ase) throws Exception {
		Map<String, Object> map =  new HashMap<String, Object>();
		String tablename = ase.getTablename();
		String path = null;
		if(tablename.equals(SysConstant.T_CUSTOMER_MASTER_SANDBOX) || tablename.equals(SysConstant.T_SAVINGACCOUNT_MASTER)
		|| tablename.equals(SysConstant.T_FEXACCOUNT_MASTER)|| tablename.equals(SysConstant.T_TERMDEPOSIT_MASTER) || tablename.equals(SysConstant.T_CURRENTACCOUNT_MASTER)
		|| tablename.equals(SysConstant.T_TERMDEPOSIT_DETAIL) || tablename.equals(SysConstant.T_PRECIOUSMETALACCOUNT_MASTER)
		|| tablename.equals(SysConstant.T_TRANSACTION_LOG)){
			
			path = PathConstant.DEPOSIT_SANDBOXSEARCH;
			
		}else if(tablename.equals(SysConstant.T_STOCKTRADINGACCOUNT_MASTER) || tablename.equals(SysConstant.T_MUTUALFUNDACCOUNT_MASTER)
				|| tablename.equals(SysConstant.T_MUTUALFUND_PLATFORM_LOG)|| tablename.equals(SysConstant.T_STOCK_PLATFORM_LOG) ){
			
			path = PathConstant.INVESTMENT_SANDBOXSEARCH;
	
		}else if(tablename.equals(SysConstant.T_CREDITCARD_MASTER) || tablename.equals(SysConstant.T_CREDITCARD_TRANSACTION_DETAIL)){
			
			path = PathConstant.CREDITCARD_SANDBOXSEARCH;

		}else{
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041004),ExceptionConstant.ERROR_CODE4041004);
		}
		String param = JsonProcess.changeEntityTOJSON(ase);
		ResponseEntity<String> result = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param),String.class);
		JSONObject str1 = XmlToJsonUtil.xmlToJson(result.getBody());
		String str2 = JsonProcess.returnValue(str1, "Map");
		JSONObject str3 = JSON.parseObject(str2);
		String list = JsonProcess.returnValue(str3, "list");
		if(JSON.parseArray(list) == null || JSON.parseArray(list).size() == 0 ){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041005),ExceptionConstant.ERROR_CODE4041005);
		}
		
		map.put("list", JSON.parseArray(list));
		return map;
	}}
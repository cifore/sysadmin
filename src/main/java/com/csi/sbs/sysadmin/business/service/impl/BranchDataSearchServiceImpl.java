package com.csi.sbs.sysadmin.business.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.BranchDataSearchModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.PathConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.service.BranchDataSearchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.csi.sbs.sysadmin.business.util.SRUtil;

@Service("BranchDataSearchService")
public class BranchDataSearchServiceImpl implements BranchDataSearchService{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil getTableBranchInfo(RestTemplate restTemplate, BranchDataSearchModel bdsm) throws Exception {
		String path = null;
		ResultUtil result = new ResultUtil();
		if(SysConstant.depositTable().contains(bdsm.getTablename())){
			path=PathConstant.DEPOSIT_BRANCHSEARCH;
		}
		if(SysConstant.creditCardTable().contains(bdsm.getTablename())){
			path=PathConstant.CREDITCARD_BRANCHSEARCH;
		}
		if(SysConstant.investmentTable().contains(bdsm.getTablename())){
			path=PathConstant.INVESTMENT_BRANCHSEARCH;
		}
		if(path==null || "".equals(path)){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041004),ExceptionConstant.ERROR_CODE4041004);
		}
		//返回数据处理
		ResponseEntity<String> res = SRUtil.sendNoWithHeader(restTemplate, path, JsonProcess.changeEntityTOJSON(bdsm));
		if(res.getStatusCodeValue()!=200){
			//查询失败
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041005),ExceptionConstant.ERROR_CODE4041005);
		}
		if(res.getBody().equals("<List/>") || res.getBody()==null || "".equals(res.getBody())){
			//查询失败
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041005),ExceptionConstant.ERROR_CODE4041005);
		}
		JSONObject str1 = XmlToJsonUtil.xmlToJson(res.getBody());
		String str2 = JsonProcess.returnValue(str1, "List");
		JSONObject str3 = JSON.parseObject(str2);
		String str4 = JsonProcess.returnValue(str3, "item");
		if(str4==null || "".equals(str4)){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041005),ExceptionConstant.ERROR_CODE4041005);
		}
		result.setCode("1");
		result.setMsg("Search Success!");
		result.setData(JSON.parse(str4));
		return result;
	}

}

package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.FindBranchCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.GetCountryCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReBranchModel;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.service.BranchService;
import com.csi.sbs.sysadmin.business.util.AvailableNumberUtil;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("BranchService")
public class BranchServiceImpl implements BranchService {

	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchdao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCountryCodes() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<BranchEntity> countryCodes = branchdao.findCountryCodes();
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("countryCodes", countryCodes);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getClearingCodeByCountryCode(GetCountryCodeModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		BranchEntity country = new BranchEntity();
		country.setCountrycode(ase.getCountrycode());
		List<BranchEntity> clearingCodes = branchdao.findClearingCodeByCountry(country);
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("clearingCodes", clearingCodes);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBrancoByCC(FindBranchCodeModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		BranchEntity branchInfo = new BranchEntity();
		branchInfo.setCountrycode(ase.getCountrycode());
		branchInfo.setBranchcode(ase.getClearingcode());
		List<BranchEntity> branchCodes = branchdao.findMany(branchInfo);
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("branchCodes", branchCodes);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil addBranch(RestTemplate restTemplate) {
		ResultUtil result = new ResultUtil();
		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			result.setCode("0");
			result.setMsg("调用服务接口地址失败");
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");

		// 调用系统参数服务接口
		String param2 = "{\"item\":\"ClearingCode,BranchNumber,LocalCcy,NextAvailableCustomerNumber,CountryCode\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			result.setCode("0");
			result.setMsg("调用系统参数失败");
		}

		// 返回数据处理
		String clearcode = "";
		String branchnumber = "";
		@SuppressWarnings("unused")
		String localCCy = "";
		String countryCode = "";
		@SuppressWarnings("unused")
		String accountnumber = "";
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("CountryCode")) {
				countryCode = temp;
			}
			if (revalue.equals("BranchNumber")) {
				branchnumber = temp;
			}
			if (revalue.equals("ClearingCode")) {
				clearcode = temp;
			}
			if (revalue.equals("LocalCcy")) {
				localCCy = temp;
			}
		}

		// 判断branchnumber 是否达到最大
		BranchEntity branchsearch = new BranchEntity();
		branchsearch.setCountrycode(countryCode);
		branchsearch.setClearingcode(clearcode);
		branchsearch.setBranchcode("999");
		BranchEntity rebranchsearch = (BranchEntity) branchdao.findOne(branchsearch);
		if(rebranchsearch!=null){
			result.setCode("0");
			result.setMsg("branch number 已经达到最大");
			return result;
		}
		
		BranchEntity branch = new BranchEntity();
		branch.setId(UUIDUtil.generateUUID());
		branch.setCountrycode(countryCode);
		branch.setClearingcode(clearcode);
		branch.setBranchcode(branchnumber);
		branchdao.insert(branch);
		// 返回信息
		ReBranchModel rebranch = new ReBranchModel();
		rebranch.setCountrycode(countryCode);
		rebranch.setClearingcode(clearcode);
		rebranch.setBranchcode(branchnumber);
        
		if(Integer.parseInt(branchnumber)<999){
			// 递增
			AvailableNumberUtil.avaBranchNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_BRANCHNUMBER);
		}
		
		result.setCode("1");
		result.setMsg("add Success");
		result.setData(rebranch);
		return result;
	}

}

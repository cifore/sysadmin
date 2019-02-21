package com.csi.sbs.sysadmin.business.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.CheckCCBModel;
import com.csi.sbs.sysadmin.business.clientmodel.FindBranchCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.GetCountryCodeModel;
import com.csi.sbs.sysadmin.business.service.BranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/branch")
public class BranchController {

	@Resource
	private BranchService branchService;
	
	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountryCodes", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public String getCountryCodes() throws Exception {
		Map<String,Object> map = branchService.getCountryCodes();
		return objectMapper.writeValueAsString(map);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getClearingCodeByCountryCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String getClearingCodeByCountryCode(@RequestBody GetCountryCodeModel ase) throws Exception {
		try {
			return objectMapper.writeValueAsString(branchService.getClearingCodeByCountryCode(ase));
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getBrancoByCC", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String getBrancoByCC(@RequestBody FindBranchCodeModel ase) throws Exception {
		try {
			return objectMapper.writeValueAsString(branchService.getBrancoByCC(ase));
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/checkccbInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String checkccbInfo(@RequestBody CheckCCBModel ase) throws Exception {
		try {
			return objectMapper.writeValueAsString(branchService.checkccbInfo(ase));
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}

	/**
	 * 增加branch
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addBranch", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to add a branch.", notes = "version 0.0.1")
	@ApiIgnore()
	public ResultUtil addBranch() throws Exception {
		try {
			return branchService.addBranch(restTemplate);
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
}

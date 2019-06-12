package com.csi.sbs.sysadmin.business.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.BranchDataSearchModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.service.BranchDataSearchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/branchdata")
public class BranchCodeSearchController {
	
    ObjectMapper objectMapper = new ObjectMapper();
	
    @Resource
    private BranchDataSearchService branchDataSearchService;
	
	@Resource
	private RestTemplate restTemplate;
	
	/**
	 * 获取所有包含branchCode的表信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getBranchCodeTable", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return branchCode Table.", notes = "version 0.0.1")
	public ResultUtil getBranchCodeTable() throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			result.setCode(String.valueOf(ExceptionConstant.SUCCESS_CODE2001006));
			result.setMsg(ExceptionConstant.getSuccessMap().get(ExceptionConstant.SUCCESS_CODE2001006));
			result.setData(SysConstant.getBranchCodeTable());
			return result;
		} catch (Exception e) {
			result.setCode(String.valueOf(ExceptionConstant.ERROR_CODE5001010));
			result.setMsg(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001010));
			return result;
		}
	}
	
	
	/**
	 * 根据表名字和branchCode 返回信息
	 * @param ase
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getBranchCodeTableInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return branchCode Table Information.", notes = "version 0.0.1")
	public ResultUtil getBranchCodeTableInfo(@RequestBody @Validated BranchDataSearchModel branchDataSearchModel) throws Exception {
	     try{
	    	 return branchDataSearchService.getTableBranchInfo(restTemplate, branchDataSearchModel); 
	     }catch(Exception e){
	    	 throw e;
	     }
	}

}

package com.csi.sbs.sysadmin.business.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.service.SandboxSearchService;
import com.csi.sbs.sysadmin.business.clientmodel.SandboxSearchModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/sandbox")
public class SandBoxSearchController {

	
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Resource
	private SandboxSearchService sandboxSearchService;
	
	@Resource
	private RestTemplate restTemplate;

	/**
	 * 获取所有会产生沙盘数据的表信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getSandBoxTable", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return sandBox Table.", notes = "version 0.0.1")
	public ResultUtil getSandBoxTable() throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			result.setCode(String.valueOf(ExceptionConstant.SUCCESS_CODE2001004));
			result.setMsg(ExceptionConstant.getSuccessMap().get(ExceptionConstant.SUCCESS_CODE2001004));
			result.setData(SysConstant.getSandBoxTable());
			return result;
		} catch (Exception e) {
			result.setCode(String.valueOf(ExceptionConstant.ERROR_CODE5001008));
			result.setMsg(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001008));
			return result;
		}
	}
	
	@RequestMapping(value = "/getSandBoxTableInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return sandBox Table Information.", notes = "version 0.0.1")
	public Map<String, Object> getSandBoxTableInfo(@RequestBody @Validated SandboxSearchModel ase) throws Exception {
			return sandboxSearchService.getTableSandboxInfo(restTemplate, ase); 
	}
}

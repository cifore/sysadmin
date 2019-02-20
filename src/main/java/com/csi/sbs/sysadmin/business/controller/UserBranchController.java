package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.service.UserBranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;


@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/userbranch")
public class UserBranchController {
	
	@Resource
	private UserBranchService userBranchService;
	
	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();
	
	
	/**
	 * 增加userbranch
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addUserBranch", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to add a userbranch.", notes = "version 0.0.1")
	@ApiIgnore()
	public ResultUtil addUserBranch(@RequestBody AddUserBranchModel addUserBranchModel) throws Exception {
		try {
			return userBranchService.addUserBranch(addUserBranchModel, restTemplate);
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}

}

package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.entity.LoginModel;
import com.csi.sbs.sysadmin.business.service.LoginService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/login")
@Api(value = "The controller is login")
public class LoginController {

	@Resource
	private LoginService loginService;

	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api return login result", notes = "version 0.0.1")
	public ResultUtil login(@RequestBody LoginModel loginModel, HttpServletRequest request) throws Exception {
		try {
			// 获取请求头参数
			@SuppressWarnings("unused")
			String userID = request.getHeader("developerID");
			String countryCode = request.getHeader("countryCode");
			String clearingCode = request.getHeader("clearingCode");
			String branchCode = request.getHeader("branchCode");
			HeaderModel header = new HeaderModel();
			header.setCountryCode(countryCode);
			header.setClearingCode(clearingCode);
			header.setBranchCode(branchCode);
			return loginService.login(header, loginModel, restTemplate);
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("Login Fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}

}

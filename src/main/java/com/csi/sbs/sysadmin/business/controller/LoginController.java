package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddLoginUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.LoginModel;
import com.csi.sbs.sysadmin.business.service.LoginInService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/login")
@Api(value = "The controller is login")
public class LoginController {

	@Resource
	private LoginInService loginInService;

	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginIn", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API allows users to log in SBS.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
		@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
		@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
		@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
		@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."),
    })
	public ResultUtil loginIn(@RequestBody LoginModel loginModel, HttpServletRequest request) throws Exception {
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
			return loginInService.loginIn(header, loginModel, restTemplate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/authorize/{loginPK}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to authorize SBS users.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
		@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
		@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
		@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
		@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."),
    })
	public ResultUtil authorize(@ApiParam(name = "loginPK", value = "Information returned by login. eg:00f7893cfbd64e66aa66ab29bbfbaa05", required = true) @PathVariable("loginPK") String loginPK,
			HttpServletRequest request) throws Exception{
		try{
			return loginInService.authorize(loginPK, restTemplate);
		}catch(Exception e){
			throw e;
		}
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userCreation", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to create new SBS users.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
		@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
		@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
		@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
		@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."),
    })
	public ResultUtil userCreation(@RequestBody AddLoginUserModel alm) throws Exception{
		try{
			return loginInService.createLoginUser(alm,restTemplate);
		}catch(Exception e){
			throw e;
		}
	}
}

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

import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.service.UserBranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
		@ApiResponse(code = 404, message = "Situation: The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
		@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
		@ApiResponse(code = 400, message = "Situation: Request has malformed, missing or non-compliant JSON body, URL parameters or header fields.Action: Please go back and refer to the method Model details to recheck the message and make sure your input is correct."),
		@ApiResponse(code = 401, message = "Situation: Authorization header missing or invalid token. Action: Please make sure the token, key or relevant parameters are correct."),
		@ApiResponse(code = 403, message = "Situation: Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
		@ApiResponse(code = 405, message = "Situation: A request method is not supported for the requested deposit account. Action: Please make sure that you’re using a POST method to get the requested deposit account details."),
		@ApiResponse(code = 408, message = "Situation: The server timed out waiting for the request. Action: Try again later."),
		@ApiResponse(code = 500, message = "Situation: Something went wrong on the API gateway or micro-service. Action: check your network and try again later."),
		@ApiResponse(code = 503, message = "Situation: Service version deprecation. Action: contact API platform support to fix the service issue.") })
	@ApiIgnore()
	public ResultUtil addUserBranch(@RequestBody @Validated AddUserBranchModel addUserBranchModel) throws Exception {
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

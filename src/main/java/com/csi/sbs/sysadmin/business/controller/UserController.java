package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.UserService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin")
@Api(value = "The controller is getting user Infomation")
public class UserController {

	@Resource
	private UserService userService;

	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/getUserInfo/{userid}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This api return user infomation about limits", notes = "version 0.0.1")
	@ApiIgnore()
	public String getUserInfo(@PathVariable("userid") String userid) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		UserEntity user = userService.selectByUserID(userid);
		if (user != null) {
			return objectMapper.writeValueAsString(user);
		} else {
			map.put("msg", "此用户不存在");
			map.put("code", "0");
			return objectMapper.writeValueAsString(map);
		}

	}

	/**
	 * 增加用户
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to add a user.", notes = "version 0.0.1")
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
	public ResultUtil addUser(@RequestBody @Validated AddUserModel addUserModel) throws Exception {
		try {
			return userService.addUser(addUserModel);
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
}
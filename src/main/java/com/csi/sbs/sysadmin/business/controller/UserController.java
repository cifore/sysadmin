package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
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
	@ApiIgnore()
	public ResultUtil addUser(@RequestBody AddUserModel addUserModel) throws Exception {
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
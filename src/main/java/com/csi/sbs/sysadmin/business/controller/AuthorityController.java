package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.service.PermissionService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/permission")
public class AuthorityController {

	@Resource
	private PermissionService permissionService;
	
	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 权限验证
	 * 
	 * @param addUserModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/permissionValidate", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to validate permission", notes = "version 0.0.1")
	@ApiIgnore()
	public String permissionValidate(PermissionModel permissionModel) throws Exception {
		try {
			return objectMapper.writeValueAsString(permissionService.validate(permissionModel));
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userAuthorize", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to Authorize", notes = "version 0.0.1")
	public ResultUtil userAuthorize(@RequestBody AddUserModel addUserModel) throws Exception {
		try {
			return permissionService.userAuthorize(restTemplate, addUserModel);
		} catch (Exception e) {
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	
	

}

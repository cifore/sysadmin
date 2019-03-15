package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.service.TokenService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/token")
public class TokenExpiredController {
	
	
	@Resource
	private TokenService tokenService;
	
	
	/**
	 * 权限验证
	 * 
	 * @param addUserModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/setExpire", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to setTokenExpire", notes = "version 0.0.1")
	@ApiIgnore()
	public ResultUtil setExpire() throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			tokenService.setTokenExpired();
			
			result.setCode("1");
			result.setMsg("设置token失效成功");
			return result;
		} catch (Exception e) {
			result.setCode("0");
			result.setMsg("设置token失效失败");
			return result;
		}
	}

}

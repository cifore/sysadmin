package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.entity.SysTransactionLogEntity;
import com.csi.sbs.sysadmin.business.service.SysTransactionLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/log")
@Api(value="The controller is log create")
public class SysTransactionLogController {

	@Resource
	private SysTransactionLogService stlservice;

	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/writeTransactionLog", method = RequestMethod.POST)
	@ResponseBody
	public String createTransactionLog(@RequestBody SysTransactionLogEntity stl) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			stlservice.writeTransactionLog(stl);			
			map.put("msg", "save log success");
			map.put("code", "1");
			return objectMapper.writeValueAsString(map);
		}catch(Exception e){
			map.put("msg", "save log fail");
			map.put("code", "0");
			throw new RuntimeException(objectMapper.writeValueAsString(map));
		}
		  
	}

}

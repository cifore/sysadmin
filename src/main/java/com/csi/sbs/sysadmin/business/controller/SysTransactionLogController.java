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

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.entity.SysTransactionLogEntity;
import com.csi.sbs.sysadmin.business.service.SysTransactionLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/log")
public class SysTransactionLogController {

	@Resource
	private SysTransactionLogService stlservice;

	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/writeTransactionLog", method = RequestMethod.POST)
	@ResponseBody
	public String createTransactionLog(@RequestBody SysTransactionLogEntity stl) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			stl.setId(UUIDUtil.generateUUID());
			stlservice.writeTransactionLog(stl);
			
			map.put("msg", "日志插入成功");
			map.put("code", "1");
		} catch (Exception e) {
			map.put("msg", "日志插入失败");
			map.put("code", "0");
		}

		return objectMapper.writeValueAsString(map);
	}

}

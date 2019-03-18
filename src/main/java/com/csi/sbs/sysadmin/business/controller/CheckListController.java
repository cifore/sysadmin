package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.clientmodel.ApiNameModel;
import com.csi.sbs.sysadmin.business.clientmodel.TestApiModel;
import com.csi.sbs.sysadmin.business.entity.CheckListEntity;
import com.csi.sbs.sysadmin.business.exception.SearchException;
import com.csi.sbs.sysadmin.business.service.CheckListService;
import com.csi.sbs.sysadmin.business.service.ModuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin")
@Api(value = "Then controller is getting infomations about models and api.")
public class CheckListController {

	@Resource
	private CheckListService checkListService;

	@Resource
	private ModuleService moduleService;

	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/queryApiList", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public String queryApiList() throws JsonProcessingException {
		Map<String, Object> map = checkListService.queryApiList();
		if (map.get("list") == null) {
			objectMapper.writeValueAsString(map);
		}
		return objectMapper.writeValueAsString(map.get("list"));
	}

	// 获取API详情
	@RequestMapping(value = "/getApiInfo/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public String getApiInfo(@PathVariable("id") String id) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		CheckListEntity apiInfo = checkListService.selectById(id);
		if (apiInfo != null) {
			return objectMapper.writeValueAsString(apiInfo);
		} else {
			map.put("msg", "无此接口");
			map.put("code", "0");
			return objectMapper.writeValueAsString(map);
		}
	}

	// 调用API接口
	@RequestMapping(value = "/testApiSend", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String testApi(@RequestBody TestApiModel ase) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestmode = ase.getRequestmode();
		String apiAddress = ase.getApiaddress();
		String result = null;
		JSON.parse(ase.getInputDesc());
		if (requestmode.equals("GET")) {
			result = restTemplate.getForEntity(apiAddress, String.class).getBody();
		} else if (requestmode.equals("POST")) {
			ResponseEntity<String> response = restTemplate.postForEntity(apiAddress,
					PostUtil.getRequestEntity(ase.getInputDesc()), String.class);
			result = response.getBody();
		}
		if (result == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
			return objectMapper.writeValueAsString(map);
		}
		return result;
	}

	/**
	 * 根据API名称获取API内部调用地址
	 * @param request
	 * @param response
	 * @param anm
	 * @return
	 * @throws JsonProcessingException
	 * @throws SearchException 
	 */
	@RequestMapping(value = "/getServiceInternalURL", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api return api's url base on api's name.", notes = "version 0.0.1")
	@ApiIgnore()
	public String getServiceInternalURL(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody ApiNameModel anm) throws Exception {
		Map<String, Object> map = null;
		try {
			map = checkListService.getServiceInternalURL(anm);
		} catch (Exception e) {
			throw e;
		}
		return objectMapper.writeValueAsString(map);
	}
}

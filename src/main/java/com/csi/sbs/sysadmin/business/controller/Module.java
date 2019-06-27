package com.csi.sbs.sysadmin.business.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.IDModel;
import com.csi.sbs.sysadmin.business.entity.ModuleEntity;
import com.csi.sbs.sysadmin.business.service.ModuleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/module")
public class Module {

		@Resource
		private ModuleService moduleservice;
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		@RequestMapping(value = "/queryModuleList", method = RequestMethod.GET)
		@ResponseBody
		public String queryModuleList() throws Exception{
			Map<String, Object> map = moduleservice.getAll();
			return objectMapper.writeValueAsString(map.get("list"));
		}
		
		@RequestMapping(value="insert", method = RequestMethod.POST)
		@ResponseBody
		public String insert(@RequestBody ModuleEntity ase) throws Exception{
			Map<String, Object> map = moduleservice.insertModule(ase);
			return objectMapper.writeValueAsString(map);
		}
		
		@RequestMapping(value="update", method = RequestMethod.POST)
		@ResponseBody
		public String update(@RequestBody ModuleEntity ase) throws Exception{
			Map<String, Object> map = moduleservice.updateModule(ase);
			return objectMapper.writeValueAsString(map);
		}
		
		@RequestMapping(value="delete", method = RequestMethod.POST)
		@ResponseBody
		public String delete(@RequestBody IDModel idModel) throws Exception{
			Map<String, Object> map = moduleservice.deleteModule(idModel);
			return objectMapper.writeValueAsString(map);
		}
		
		@RequestMapping(value="findModule", method = RequestMethod.POST)
		@ResponseBody
		public String findModule(@RequestBody IDModel idModel) throws Exception{
			Map<String, Object> map = moduleservice.findModule(idModel);
			return objectMapper.writeValueAsString(map);
		}
		
}

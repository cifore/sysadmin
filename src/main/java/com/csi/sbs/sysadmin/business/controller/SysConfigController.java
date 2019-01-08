package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/sysconfig")
public class SysConfigController {
	
	
	
	   @Resource
	   private SysConfigService sysConfigService;
	   
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       
       
       @RequestMapping(value = "/{query}", method = RequestMethod.POST)
       @ResponseBody
   	   public String query() throws JsonProcessingException{
    	   Map<String,Object> map = new HashMap<String,Object>();
    	   List<SysConfigEntity> sysconfig = null;
           try{
        	   sysconfig = sysConfigService.querySysConfig();
           }catch(Exception e){
        	   map.put("msg", "查询失败");
               map.put("code", "0");
               return objectMapper.writeValueAsString(map);
           }
           			  
		   return objectMapper.writeValueAsString(sysconfig);
   	   }

}

package com.csi.sbs.sysadmin.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.SysParamsModel;
import com.csi.sbs.sysadmin.business.clientmodel.SysReturnParamsModel;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/sysadmin/sysconfig")
public class SysConfigController {
	
	
	
	   @Resource
	   private SysConfigService sysConfigService;
	   
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       
       @RequestMapping(value = "/{index}", method = RequestMethod.GET)
       @ApiIgnore()
   	   public String index() throws JsonProcessingException{
    	  return "index";
   	   }

       @RequestMapping(value = "/{index}/{testApi}", method = RequestMethod.GET)
       @ApiIgnore()
   	   public String testApi() throws JsonProcessingException{
    	  return "testApi";
   	   }
       
       @RequestMapping(value = "/getSystemParameter", method = RequestMethod.POST)
       @ResponseBody
   	   public String getSystemParameter(@RequestBody SysParamsModel spm) throws JsonProcessingException{
    	   Map<String,Object> map = new HashMap<String,Object>();
    	   List<SysConfigEntity> sysconfig = null;
    	   List<SysReturnParamsModel> respmList = null;
    	   String[] item = spm.getItem().split(",");
    	   List<String> list = new ArrayList<String>();
    	   for(int i=0;i<item.length;i++){
    		   list.add(item[i]);
    	   }
    	   SysConfigEntity sce = new SysConfigEntity();
    	   sce.setParams(list);
           try{
        	   sysconfig = sysConfigService.querySysConfig(sce);
        	   respmList = new ArrayList<SysReturnParamsModel>();
        	   if(sysconfig!=null && sysconfig.size()>0){
        		   for(int i=0;i<sysconfig.size();i++){
        			   SysReturnParamsModel respm = new SysReturnParamsModel();
            		   respm.setItem(sysconfig.get(i).getItem());
            		   respm.setValue(sysconfig.get(i).getValue());
            		   respmList.add(respm);
        		   }
        	   }
           }catch(Exception e){
        	   map.put("msg", "查询失败");
               map.put("code", "0");
               return objectMapper.writeValueAsString(map);
           }
           			  
		   return objectMapper.writeValueAsString(respmList);
   	   }

}

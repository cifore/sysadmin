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

//import com.csi.sbs.sysadmin.business.clientmodel.IDModel;
import com.csi.sbs.sysadmin.business.clientmodel.SysParamsModel;
import com.csi.sbs.sysadmin.business.clientmodel.SysReturnParamsModel;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/sysadmin/sysconfig")
@Api(value = "Then controller is sysconfig")
public class SysConfigController {
	
	
	
	   @Resource
	   private SysConfigService sysConfigService;
	   
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       @RequestMapping(value = "/getSystemParameter", method = RequestMethod.POST)
       @ResponseBody
       @ApiOperation(value = "This API is designed to retrieve specified system parameter values.", notes = "version 0.0.1")
   	   public String getSystemParameter(@RequestBody SysParamsModel sysParamsModel) throws JsonProcessingException{
    	   Map<String,Object> map = new HashMap<String,Object>();
    	   List<SysConfigEntity> sysconfig = null;
    	   List<SysReturnParamsModel> respmList = null;
    	   String[] item = sysParamsModel.getItem().split(",");
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
       
       @RequestMapping(value = "/querySysConfList", method = RequestMethod.GET)
       @ResponseBody
       @ApiIgnore
   	   public String querySysConfList() throws JsonProcessingException{
    	   Map<String,Object> map = sysConfigService.querySysConfList();  
		   return objectMapper.writeValueAsString(map.get("list"));
   	   }
       
       @RequestMapping(value = "/updateParam", method = RequestMethod.POST)
       @ResponseBody
       @ApiIgnore
   	   public String updateParam(@RequestBody SysConfigEntity sysConfigEntity) throws JsonProcessingException{
    	   Map<String,Object> map = sysConfigService.updateParam(sysConfigEntity);
		   return objectMapper.writeValueAsString(map);
   	   }
       
//       @RequestMapping(value = "/findItem", method = RequestMethod.POST)
//       @ResponseBody
//   	   public String findItem(@RequestBody IDModel sysConfigEntity) throws Exception{
//    	   Map<String,Object> map = sysConfigService.findItem(sysConfigEntity.getId());
//		   return objectMapper.writeValueAsString(map);
//   	   }
//       
//       @RequestMapping(value = "/insertParam", method = RequestMethod.POST)
//       @ResponseBody
//   	   public String insertParam(@RequestBody SysConfigEntity sysConfigEntity) throws Exception{
//    	   Map<String,Object> map = sysConfigService.insertParam(sysConfigEntity);
//		   return objectMapper.writeValueAsString(map);
//   	   }

}

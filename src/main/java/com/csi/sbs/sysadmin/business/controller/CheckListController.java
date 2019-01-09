package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.ApiNameModel;
import com.csi.sbs.sysadmin.business.entity.CheckListEntity;
import com.csi.sbs.sysadmin.business.service.CheckListService;
import com.csi.sbs.sysadmin.business.service.ModuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/sysadmin")
public class CheckListController {
	
	   @Resource
	   private CheckListService checkListService;
	   
	   @Resource
	   private ModuleService moduleService;
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       @RequestMapping(value = "/{queryApiList}", method = RequestMethod.GET)
       @ResponseBody
       public String queryApiList() throws JsonProcessingException{
//    	   Map<String,Object> map = new HashMap<String,Object>();
    	   
    	   
    	   List<CheckListEntity> apiList = checkListService.queryAll();
    	   
    	   for(int i=0 ; i<apiList.size(); i++){
    		   String moduleid = apiList.get(i).getModuleid();
    		   apiList.get(i).setModulename(moduleService.selectById(moduleid).getName());
    	   }
    	   
    	   return objectMapper.writeValueAsString(apiList);
       }
       
       @RequestMapping(value = "/{getServiceInternalURL}", method = RequestMethod.POST)
       @ResponseBody
       public String getServiceInternalURL(final HttpServletRequest request,
	            final HttpServletResponse response,@RequestBody ApiNameModel anm) throws JsonProcessingException{
    	   Map<String,Object> map = new HashMap<String,Object>();
    	   String apiName = anm.getApiname();
    	   String internaURL = "";
    	  try{
    		  internaURL = checkListService.selectByName(apiName).getInternalurl();
    	  }catch(Exception e){
    		  map.put("msg", "查询失败");
              map.put("code", "0");
              return objectMapper.writeValueAsString(map);
    	  }  
    	   map.put("internaURL", internaURL);
    	   return objectMapper.writeValueAsString(map);
       }
}

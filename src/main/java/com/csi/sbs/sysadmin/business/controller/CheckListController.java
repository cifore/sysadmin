package com.csi.sbs.sysadmin.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	   
       ObjectMapper objectMapper = new ObjectMapper();
       
       @RequestMapping(value = "/queryApiList", method = RequestMethod.GET)
       @ResponseBody
       @ApiIgnore()
       public String queryApiList() throws JsonProcessingException{
    	   List<CheckListEntity> apiList = checkListService.queryAll();
    	   
    	   for(int i=0 ; i<apiList.size(); i++){
    		   String moduleid = apiList.get(i).getModuleid();
    		   apiList.get(i).setModulename(moduleService.selectById(moduleid).getName());
    	   }
    	   
    	   return objectMapper.writeValueAsString(apiList);
       }
       
       
       //获取API详情
       @RequestMapping(value = "/getApiInfo/{id}", method = RequestMethod.GET)
       @ResponseBody
       @ApiIgnore()
       public String getApiInfo(@PathVariable("id") String id ) throws JsonProcessingException{
    	   Map<String,Object> map = new HashMap<String,Object>(); 
    	   CheckListEntity apiInfo = checkListService.selectById(id);
    	   if(apiInfo != null){
    		   return objectMapper.writeValueAsString(apiInfo); 
    	   }else{
    		   map.put("msg", "无此接口");
               map.put("code", "0");
               return objectMapper.writeValueAsString(map);
    	   }
    	   
       }
       
//     //调用API接口
//       @RequestMapping(value = "/testApiSend", method = RequestMethod.POST)
//       @ResponseBody
//       @ApiIgnore()
//       public String testApi(@RequestBody TestApiModel ase) throws JsonProcessingException{
//    	   Map<String,Object> map = new HashMap<String,Object>();
//    	   String requestmode = ase.getRequestmode();
//    	   String apiAddress = ase.getApiaddress();
//    	   String result = null;
//    	   //JSONObject jsonObject = new JSONObject();
//    	   JSON.parse(ase.getInputDesc());
//    	   if(requestmode.equals("GET")){
//    		   result = ConnGetClient.get(apiAddress);
//    	   }else if(requestmode.equals("POST")){
//    		   result = ConnPostClient.postJson(apiAddress, ase.getInputDesc());   
//    	   }
//    	   if(result==null){
//        	   map.put("msg", "调用系统参数失败");
//        	   map.put("code", "0");
//        	   return objectMapper.writeValueAsString(map);
//           }
//    	   return result;
//       }
       
       
	@RequestMapping(value = "/getServiceInternalURL", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api return api's url base on api's name.", notes = "version 0.0.1")
	public String getServiceInternalURL(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody ApiNameModel anm) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String apiName = anm.getApiname();
		String internaURL = "";
		try {
			internaURL = checkListService.selectByName(apiName).getInternalurl();
		} catch (Exception e) {
			map.put("msg", "查询失败");
			map.put("code", "0");
			return objectMapper.writeValueAsString(map);
		}
		map.put("internaURL", internaURL);
		return objectMapper.writeValueAsString(map);
	}
}

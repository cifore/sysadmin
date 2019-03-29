package com.csi.sbs.sysadmin.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.SaveNextAvailableNumberModel;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin//解决跨域请求
@Controller
@RequestMapping("/sysadmin/generate")
@Api(value = "Then controller is getAvailableNumber")
public class GenerateAvailableController {
	 
	 @Resource
	 private SysConfigService sysConfigService;
	 
	 ObjectMapper objectMapper = new ObjectMapper();
	
	
	 @RequestMapping(value = "/getNextAvailableNumber/{item}", method = RequestMethod.GET)
	 @ResponseBody
	 @ApiOperation(value = "This API is designed to retrieve next available number of different number types.", notes = "version 0.0.1")
 	 public String getNextAvailableNumber(@ApiParam(name = "item", value = "item eg: NextAvailableCustomerNumber", required = true) @PathVariable("item") String item) throws JsonProcessingException{
		 Map<String,Object> map = new HashMap<String,Object>();
		 try{
			 //调用系统参数方法获取下一个可用的CustomerNumber/**/**/..
			 List<String> list = new ArrayList<String>();
	    	 list.add(item);
	    	 SysConfigEntity sce = new SysConfigEntity();
	    	 sce.setParams(list);
			 List<SysConfigEntity> result1 = sysConfigService.querySysConfig(sce);
	         if(result1==null || result1.size()==0){
	        	   map.put("msg", "调用系统参数失败");
	        	   map.put("code", "0");
	         }
	         
	         //返回数据处理
	         String currentAvailableNumber = "";
	         for(int i=0;i<result1.size();i++){
	      	  if(result1.get(i).getItem().equals(item)){
	      		currentAvailableNumber = result1.get(i).getValue();
	      	  }
	         }
	         map.put("msg", "获取成功");
	         map.put("nextAvailableNumber", currentAvailableNumber);
	         map.put("code", "1");
		 }catch(Exception e){
			 map.put("msg", "获取失败");
      	     map.put("code", "0");
		 }
		 
		 return objectMapper.writeValueAsString(map);
 	 }
	 
	 
	 @RequestMapping(value = "/saveNextAvailableNumber", method = RequestMethod.POST)
	 @ResponseBody
	 @ApiIgnore()
 	 public String saveNextAvailableNumber(@RequestBody SaveNextAvailableNumberModel saveNextAvailableNumberModel) throws JsonProcessingException{
		 Map<String,Object> map = new HashMap<String,Object>();
		 try{
	         //保存AvailableNumber
	         SysConfigEntity sce = new SysConfigEntity();
	         sce.setValue(saveNextAvailableNumberModel.getValue());
	         sce.setItem(saveNextAvailableNumberModel.getItem());
	         sysConfigService.updateNextAvailableCustomerNum(sce);
	         
	         map.put("msg", "保存成功");
	         map.put("code", "1");
		 }catch(Exception e){
			 e.printStackTrace();
			 map.put("msg", "保存失败");
      	     map.put("code", "0");
		 }
		 
		 return objectMapper.writeValueAsString(map);
 	 }

}

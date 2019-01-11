package com.csi.sbs.sysadmin.business.controller;

import java.util.ArrayList;
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
@RequestMapping("/sysadmin/generate")
public class GenerateAvailableController {
	 
	 @Resource
	 private SysConfigService sysConfigService;
	 
	 ObjectMapper objectMapper = new ObjectMapper();
	
	
	 @RequestMapping(value = "/{getNextAvailableNumber}", method = RequestMethod.GET)
	 @ResponseBody
 	 public String getNextAvailableNumber() throws JsonProcessingException{
		 Map<String,Object> map = new HashMap<String,Object>();
		 try{
			 //调用系统参数方法获取下一个可用的CustomerNumber/**/**/..
			 List<String> list = new ArrayList<String>();
	    	 list.add("NextAvailableCustomerNumber");
	    	 SysConfigEntity sce = new SysConfigEntity();
	    	 sce.setParams(list);
			 List<SysConfigEntity> result1 = sysConfigService.querySysConfig(sce);
	         if(result1==null || result1.size()==0){
	        	   map.put("msg", "调用系统参数失败");
	        	   map.put("code", "0");
	         }
	         
	         //返回数据处理
	         String currentAvailableCustomerNumber = "";
	         int nextAvailableCustomerNumber = 0;
	         for(int i=0;i<result1.size();i++){
	      	  if(result1.get(i).getItem().equals("NextAvailableCustomerNumber")){
	      		currentAvailableCustomerNumber = result1.get(i).getValue();
	      	  }
	         }
	         nextAvailableCustomerNumber = Integer.parseInt(currentAvailableCustomerNumber);
	         //可用number加1
	         nextAvailableCustomerNumber = nextAvailableCustomerNumber+1;
	         String availableNumber = String.valueOf(nextAvailableCustomerNumber);
	         int availableNumberLength = availableNumber.length();
	         String appendSave = "";
	         //可用number长度判断
	         switch(availableNumberLength){
	            case 1:
	               appendSave = "0000"+nextAvailableCustomerNumber;
	               break;
	            case 2:
	               appendSave = "000"+nextAvailableCustomerNumber;
	               break;
	            case 3:
	               appendSave = "00"+nextAvailableCustomerNumber;
	               break;
	            case 4:
	               appendSave = "0"+nextAvailableCustomerNumber;
	               break;
	            case 5:
	               appendSave = nextAvailableCustomerNumber+"";
	               break;
	         }
	         
	         //保存AvailableNumber
	         SysConfigEntity sce2 = new SysConfigEntity();
	         sce2.setValue(appendSave);
	         sce2.setItem("NextAvailableCustomerNumber");
	         sysConfigService.updateNextAvailableCustomerNum(sce2);
	         
	         map.put("msg", "获取成功");
	         map.put("nextAvailableNumber", currentAvailableCustomerNumber);
	         map.put("code", "1");
		 }catch(Exception e){
			 map.put("msg", "获取失败");
      	     map.put("code", "0");
		 }
		 
		 return objectMapper.writeValueAsString(map);
 	 }

}

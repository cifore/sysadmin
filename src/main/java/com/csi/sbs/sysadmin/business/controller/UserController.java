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
import org.springframework.web.bind.annotation.PathVariable;

import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin //解决跨域请求
@Controller
@RequestMapping("/sysadmin")
public class UserController{
	
	@Resource
	private UserService userService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/getUserInfo/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo(@PathVariable("userid") String userid) throws JsonProcessingException{
		Map<String,Object> map = new HashMap<String,Object>();
		UserEntity user = userService.selectByUserID(userid);
		if(user != null){
			return objectMapper.writeValueAsString(user);
		}else{
			map.put("msg", "此用户不存在");
            map.put("code", "0");
            return objectMapper.writeValueAsString(map);
		}
		
	}
}
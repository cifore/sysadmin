package com.csi.sbs.sysadmin.business.controller;

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

import com.csi.sbs.sysadmin.business.clientmodel.CurrencyModel;
import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;
import com.csi.sbs.sysadmin.business.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin")
@Api(value="The controller is about foreign exchange")
public class CurrencyController{
	
	@Resource 
	private CurrencyService currencyService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/getCurrencys", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This api returns all currency", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 0, message = "查询失败") })
	public String getCurrencys() throws JsonProcessingException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<CurrencyEntity> list = null;		
		 
		try{
			 list = currencyService.queryAll();
         }catch(Exception e){
      	   	 map.put("msg", "查询失败");
             map.put("code", "0");
             return objectMapper.writeValueAsString(map);
         }
		return objectMapper.writeValueAsString(list);
	}
	
	/**
	 * 
	 * 根据ccycode判断currency是否存在
	 * 
	 * @param ase
	 * @return
	 * @throws JsonProcessingException
	 * 
	 * */
	@RequestMapping(value = "/isSupportbyccy", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is check whether we have the currency ", notes = "version 0.0.1")
	@ApiImplicitParam(paramType = "body", name = "ase", required = true, value = "CurrencyModel")
	public boolean isSupportbyccy(@RequestBody CurrencyModel ase) throws JsonProcessingException{
		CurrencyEntity ccyInfo  = currencyService.queryByCcyCode(ase.getCcycode());
		if(ccyInfo == null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 
	 * 根据ccycode查询currency信息
	 * 
	 * @param ase
	 * @return
	 * @throws JsonProcessingException
	 * 
	 * */
	@RequestMapping(value = "/queryByCcyCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This api is check whether we have the currency ", notes = "version 0.0.1")
	@ApiImplicitParam(paramType = "body", name = "ase", required = true, value = "CurrencyModel")
	public String queryByCcyCode(@RequestBody CurrencyModel ase) throws JsonProcessingException{
		CurrencyEntity ccyInfo  = currencyService.queryByCcyCode(ase.getCcycode());
		return objectMapper.writeValueAsString(ccyInfo);
	}
}
package com.csi.sbs.sysadmin.business.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.CurrencyModel;
import com.csi.sbs.sysadmin.business.clientmodel.DeleteCurrencyModel;
import com.csi.sbs.sysadmin.business.clientmodel.InsertCurrencyModel;
import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;
import com.csi.sbs.sysadmin.business.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/currency")
@Api(value="The controller is about foreign exchange")
public class CurrencyController{
	
	@Resource 
	private CurrencyService currencyService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	//Alina 获取所有货币汇率列表
	@RequestMapping(value = "/currencyRetrieval", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This api is designed to retrieve all supported currency in SBS.", notes = "version 0.0.1")
	public String getCurrencys() throws Exception{
		Map<String,Object> map = currencyService.getCurrencys();
		if(map.get("list") == null){
			return objectMapper.writeValueAsString(map);
		}
		return objectMapper.writeValueAsString(map.get("list"));
	}
	
	/**
	 * 
	 * 根据ccycode判断currency是否存在
	 * 
	 * @param ase
	 * @return
	 * @throws Exception
	 * 
	 * */
	@RequestMapping(value = "/isSupportbyccy", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String isSupportbyccy(@RequestBody CurrencyModel ase) throws Exception{
		Map<String,Object> map  = currencyService.queryByCcyCode(ase.getCcycode());
		if(map.get("code").equals("0")){
			return "false";
		}else{
			return "true";
		}
	}
	
	/**
	 * 
	 * 根据ccycode查询currency信息
	 * 
	 * @param ase
	 * @return
	 * @throws Exception
	 * 
	 * */
	@RequestMapping(value = "/queryByCcyCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String queryByCcyCode(@RequestBody CurrencyModel ase) throws Exception{
		Map<String,Object> map = currencyService.queryByCcyCode(ase.getCcycode());
		if(map.get("ccyInfo") ==  null){
			return objectMapper.writeValueAsString(map);
		}
        return objectMapper.writeValueAsString(map.get("ccyInfo"));	
	}
	
	//Alina 插入汇率信息
	@RequestMapping(value = "/insertCurrency", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String insertCurrency(@RequestBody @Validated InsertCurrencyModel ase) throws Exception{
		Map<String,Object> map = currencyService.insertCurrency(ase);
        return objectMapper.writeValueAsString(map);	
	}
	
	//Alina 更新汇率信息
	@RequestMapping(value = "/updateCurrency", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String updateCurrency(@RequestBody CurrencyEntity ase) throws Exception{
		Map<String,Object> map = currencyService.updateCurrency(ase);
        return objectMapper.writeValueAsString(map);	
	}
	
	//Alina 更新汇率信息
	@RequestMapping(value = "/deleteCurrency", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public String deleteCurrency(@RequestBody @Validated DeleteCurrencyModel ase) throws Exception{
		Map<String,Object> map = currencyService.deleteCurrency(ase.getId());
        return objectMapper.writeValueAsString(map);	
	}
}
package com.csi.sbs.sysadmin.business.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.sysadmin.business.clientmodel.AddTransactionTypeModel;
import com.csi.sbs.sysadmin.business.service.TransactionTypeService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin/trantype")
@Api(value = "The controller is Transaction Type")
public class TransactionTypeController {

	@Resource
	private TransactionTypeService transactionTypeService;
	
	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 增加交易类型参数
	 * @param addTransactionTypeModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addTranType", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public ResultUtil addTranType(@RequestBody AddTransactionTypeModel addTransactionTypeModel) throws Exception {
		try{
			return transactionTypeService.addTranType(addTransactionTypeModel);
		}catch(Exception e){
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}
	
	/**
	 * 根据交易类型查询
	 * @param spm
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryTranType/{trantype}", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public ResultUtil queryTranType(@PathVariable("trantype") String trantype) throws Exception {
		try{
			return transactionTypeService.queryByType(trantype);
		}catch(Exception e){
			ResultUtil result = new ResultUtil();
			result.setCode("0");
			result.setMsg("fail");
			throw new RuntimeException(objectMapper.writeValueAsString(result));
		}
	}

}

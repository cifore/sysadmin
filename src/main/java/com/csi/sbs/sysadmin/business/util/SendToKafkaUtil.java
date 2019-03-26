package com.csi.sbs.sysadmin.business.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.sysadmin.business.clientmodel.SendMessageModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.exception.CallOtherException;

public class SendToKafkaUtil {
	
	@SuppressWarnings("rawtypes")
	private static ResultUtil result = new ResultUtil();
	
	
	@SuppressWarnings("rawtypes")
	public static ResultUtil sendMsgToKafka(SendMessageModel smm,RestTemplate restTemplate) throws Exception{
		try{
			ResponseEntity<String> res = restTemplate.postForEntity(SysConstant.SEND_TOKAFKA_URL,
					PostUtil.getRequestEntity(JsonProcess.changeEntityTOJSON(smm)), String.class);
			if(res.getStatusCodeValue()==200){
				result.setCode(String.valueOf(ExceptionConstant.SUCCESS_CODE2001003));
				result.setMsg(ExceptionConstant.getExceptionMap().get(ExceptionConstant.SUCCESS_CODE2001003));
				return result;
			}
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001004),ExceptionConstant.ERROR_CODE5001004);
		}catch(Exception e){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001004),ExceptionConstant.ERROR_CODE5001004);
		}
	}

}

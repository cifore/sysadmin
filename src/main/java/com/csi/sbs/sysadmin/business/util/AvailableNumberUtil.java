package com.csi.sbs.sysadmin.business.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.sysadmin.business.constant.SysConstant;

public class AvailableNumberUtil {
	
	@SuppressWarnings("unused")
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 可用Number加一
	 */
	public static void avaBranchNumberIncrease(RestTemplate restTemplate,String item) {
		Map<String,Object> map = new HashMap<String,Object>();
		String currentNumber = restTemplate.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/"+SysConstant.NEXT_AVAILABLE_BRANCHNUMBER, String.class).getBody();
		if (currentNumber == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}
		int nextAvailableNumber = 0;
		nextAvailableNumber = Integer.parseInt(JsonProcess.returnValue(JsonProcess.changeToJSONObject(currentNumber), "nextAvailableNumber"));
		// 可用number加1
		nextAvailableNumber = nextAvailableNumber + 1;
		String availableNumber = String.valueOf(nextAvailableNumber);
		int availableNumberLength = availableNumber.length();
		String appendSave = "";
		// 可用number长度判断
		switch (availableNumberLength) {
		case 1:
			appendSave = "00" + nextAvailableNumber;
			break;
		case 2:
			appendSave = "0" + nextAvailableNumber;
			break;
		case 3:
			appendSave = "" + nextAvailableNumber;
			break;
		}
		
        String param = "{\"value\":\""+appendSave+"\",\"item\":\""+item+"\"}";
		ResponseEntity<String> result = restTemplate.postForEntity("http://SYSADMIN/sysadmin/generate/saveNextAvailableNumber", PostUtil.getRequestEntity(param),String.class);
		if (!result.getStatusCode().equals("200")) {
			map.put("msg", "生成下一个可用number失败");
			map.put("code", "0");
		}
	}
	
	
	
	
	
	
	/**
	 * 沙盘身份证号码递增
	 * @param restTemplate
	 * @param item
	 */
	public static void sandBoxCustomerIDIncrease(RestTemplate restTemplate,String item) {
		Map<String,Object> map = new HashMap<String,Object>();
		String currentNumber = restTemplate.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/"+SysConstant.SANDBOX_CUSTOMERID, String.class).getBody();
		if (currentNumber == null) {
			map.put("msg", "调用系统参数失败");
			map.put("code", "0");
		}
		int nextAvailableNumber = 0;
		nextAvailableNumber = Integer.parseInt(JsonProcess.returnValue(JsonProcess.changeToJSONObject(currentNumber), "nextAvailableNumber"));
		// 可用number加1
		nextAvailableNumber = nextAvailableNumber + 1;
		String availableNumber = String.valueOf(nextAvailableNumber);
		int availableNumberLength = availableNumber.length();
		String appendSave = "";
		// 可用number长度判断
		switch (availableNumberLength) {
		case 1:
			appendSave = "00000000" + nextAvailableNumber;
			break;
		case 2:
			appendSave = "0000000" + nextAvailableNumber;
			break;
		case 3:
			appendSave = "000000" + nextAvailableNumber;
			break;
		case 4:
			appendSave = "00000" + nextAvailableNumber;
			break;
		case 5:
			appendSave = "0000" + nextAvailableNumber;
			break;
		case 6:
			appendSave = "000" + nextAvailableNumber;
			break;
		case 7:
			appendSave = "00" + nextAvailableNumber;
			break;
		case 8:
			appendSave = "0" + nextAvailableNumber;
			break;
		case 9:
			appendSave = "" + nextAvailableNumber;
			break;
		}
		
        String param = "{\"value\":\""+appendSave+"\",\"item\":\""+item+"\"}";
		ResponseEntity<String> result = restTemplate.postForEntity("http://SYSADMIN/sysadmin/generate/saveNextAvailableNumber", PostUtil.getRequestEntity(param),String.class);
		if (!result.getStatusCode().equals("200")) {
			map.put("msg", "生成下一个可用number失败");
			map.put("code", "0");
		}
	}
	
	

}

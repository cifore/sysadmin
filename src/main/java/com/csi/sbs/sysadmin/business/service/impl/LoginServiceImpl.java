package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.JwtTokenProviderUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReLoginModel;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.entity.CustomerTokenRelationEntity;
import com.csi.sbs.sysadmin.business.entity.LoginModel;
import com.csi.sbs.sysadmin.business.entity.TokenEntity;
import com.csi.sbs.sysadmin.business.entity.UserClaimsEntity;
import com.csi.sbs.sysadmin.business.service.CustomerTokenRelationService;
import com.csi.sbs.sysadmin.business.service.LoginService;
import com.csi.sbs.sysadmin.business.service.TokenService;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	
	
	@Resource
	private TokenService tokenService;
	
	@Resource
	private CustomerTokenRelationService customerTokenRelationService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public ResultUtil login(HeaderModel header,LoginModel loginModel,RestTemplate restTemplate) throws Exception {
		ResultUtil result = new ResultUtil();
		/**
		 * 调用deposit 登录部分
		 */
		ResponseEntity<String> redata = restTemplate.postForEntity(
				"http://" + CommonConstant.getDEPOSIT() + SysConstant.LOGIN_PATH + "",
				PostUtil.getRequestEntity(JsonProcess.changeEntityTOJSON(loginModel)), String.class);
		if(redata.getStatusCodeValue()!=200){
			result.setCode("0");
			result.setMsg("Login Fail");
			return result;
		}
		JSONObject str = XmlToJsonUtil.xmlToJson(redata.getBody());
		String str1 = JsonProcess.returnValue(str, "ResultUtil");
		JSONObject str2 = JsonProcess.changeToJSONObject(str1);
		String str3 = JsonProcess.returnValue(str2, "code");
		if(!str3.equals("1")){
			result.setCode("0");
			result.setMsg("Login Fail");
			return result;
		}
		//customerID
		String str4 = JsonProcess.returnValue(str2, "data");
		/**
		 * token 处理部分
		 */
		// 密钥 客户的登录密码
		JwtTokenProviderUtil jwtTokenProvider = new JwtTokenProviderUtil(loginModel.getPwd());
		UserClaimsEntity claims = new UserClaimsEntity();
		claims.setUserID(header.getUserID());
		claims.setCountryCode(header.getCountryCode());
		claims.setClearingCode(header.getClearingCode());
		claims.setBranchCode(header.getBranchCode());
		claims.setCustomerID(str4);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Date currdate = format.parse(format.format(new Date()));
		Calendar ca = Calendar.getInstance();
		ca.setTime(currdate);
		ca.add(Calendar.DATE, SysConstant.VALIDITYDAYS);// num为增加的天数，可以改变的
		currdate = ca.getTime();
		String enddate = format.format(currdate);
		// 设置token的失效日期
		claims.setExpiration(format.parse(enddate));
		// 生成token
		String token = jwtTokenProvider.createToken(claims);
		//System.out.println("生成的token：" + token);
		/**
		 * 保存token
		 */
		TokenEntity st = new TokenEntity();
		st.setToken(token);
		st.setExpiredate(format.parse(enddate));
		tokenService.save(st);
		/**
		 * 授权token给客户
		 */
		CustomerTokenRelationEntity ct = new CustomerTokenRelationEntity();
		ct.setCustomerid(str4);
		ct.setTokenid(st.getId());
		customerTokenRelationService.save(ct);
		
		ReLoginModel rlm = new ReLoginModel();
		rlm.setCustomerID(str4);
		rlm.setToken(token);
		
		result.setCode("1");
		result.setMsg("Login Success");
		result.setData(rlm);
		return result;
	}

}

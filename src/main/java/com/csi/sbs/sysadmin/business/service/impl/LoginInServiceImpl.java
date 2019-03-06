package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.JwtTokenProviderUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.FindCustomerModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.LoginModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReCustomerModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReLoginModel;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.LoginInDao;
import com.csi.sbs.sysadmin.business.entity.CustomerTokenRelationEntity;
import com.csi.sbs.sysadmin.business.entity.LoginInEntity;
import com.csi.sbs.sysadmin.business.entity.TokenEntity;
import com.csi.sbs.sysadmin.business.entity.UserClaimsEntity;
import com.csi.sbs.sysadmin.business.service.CustomerTokenRelationService;
import com.csi.sbs.sysadmin.business.service.LoginInService;
import com.csi.sbs.sysadmin.business.service.TokenService;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("LoginInService")
public class LoginInServiceImpl implements LoginInService {

	@SuppressWarnings("rawtypes")
	@Resource
	private LoginInDao loginInDao;
	
	@Resource
	private TokenService tokenService;
	
	@Resource
	private CustomerTokenRelationService customerTokenRelationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public ResultUtil loginIn(HeaderModel header, LoginModel loginModel, RestTemplate restTemplate) throws Exception {
		ResultUtil result = new ResultUtil();
		/**
		 * 登录
		 */
		LoginInEntity loginIn = new LoginInEntity();
		loginIn.setLoginname(loginModel.getLoginname());
		loginIn.setLoginpwd(loginModel.getLoginpwd());
		LoginInEntity reLoginIn = (LoginInEntity) loginInDao.findOne(loginIn);
        if(reLoginIn==null){
        	result.setCode("0");
        	result.setMsg("Login Fail");
        	return result;
        }
        /**
         * 根据customer主键去查询 customerNumber
         */
        FindCustomerModel fcm = new FindCustomerModel();
        fcm.setiD(reLoginIn.getCustomerpk());
        //调用deposit 查询  customer方法
        ResponseEntity<String> customerResult = restTemplate.postForEntity(
				"http://" + CommonConstant.getDEPOSIT() + SysConstant.GET_CUSTOMER_URL + "",
				PostUtil.getRequestEntity(JsonProcess.changeEntityTOJSON(fcm)), String.class);
		if(customerResult.getStatusCodeValue()!=200){
			result.setCode("0");
			result.setMsg("Get CustomerNumber Fail");
			return result;
		}
        JSONObject str1 = XmlToJsonUtil.xmlToJson(customerResult.getBody());
        String str2 = JsonProcess.returnValue(str1, "ResultUtil");
        ResultUtil cru = JSON.parseObject(str2, ResultUtil.class);
        if(cru.getCode().equals("0")){
        	result.setCode("0");
        	result.setMsg("Get CustomerNumber Fail");
        	return result;
        }
        ReCustomerModel rcm = JSON.parseObject(JSON.toJSONString(cru.getData()), ReCustomerModel.class);
        /**
		 * token 处理部分
		 */
		// 密钥 客户的登录密码
		JwtTokenProviderUtil jwtTokenProvider = new JwtTokenProviderUtil(loginModel.getLoginpwd());
		UserClaimsEntity claims = new UserClaimsEntity();
		claims.setDeveloperID(header.getUserID());
		claims.setCountryCode(header.getCountryCode());
		claims.setClearingCode(header.getClearingCode());
		claims.setBranchCode(header.getBranchCode());
		claims.setLoginName(reLoginIn.getLoginname());
		claims.setCustomerNumber(rcm.getCustomernumber());
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
		// System.out.println("生成的token：" + token);
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
		ct.setLogininpk(reLoginIn.getId());
		ct.setTokenid(st.getId());
		customerTokenRelationService.save(ct);

		ReLoginModel rlm = new ReLoginModel();
		rlm.setLoginName(reLoginIn.getLoginname());
		rlm.setToken(token);

		result.setCode("1");
		result.setMsg("Login Success");
		result.setData(rlm);
		return result;
	}
}

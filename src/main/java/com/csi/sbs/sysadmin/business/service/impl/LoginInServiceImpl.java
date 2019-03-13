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
import com.codingapi.tx.annotation.TxTransaction;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.JwtTokenProviderUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.FindCustomerModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.LoginModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReCustomerModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReLoginModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.CustomerTokenRelationDao;
import com.csi.sbs.sysadmin.business.dao.LoginInDao;
import com.csi.sbs.sysadmin.business.dao.TokenDao;
import com.csi.sbs.sysadmin.business.entity.CustomerTokenRelationEntity;
import com.csi.sbs.sysadmin.business.entity.LoginInEntity;
import com.csi.sbs.sysadmin.business.entity.TokenEntity;
import com.csi.sbs.sysadmin.business.entity.UserClaimsEntity;
import com.csi.sbs.sysadmin.business.exception.AcceptException;
import com.csi.sbs.sysadmin.business.exception.AuthorityException;
import com.csi.sbs.sysadmin.business.exception.CallOtherException;
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
	
	@SuppressWarnings("rawtypes")
	@Resource
	private TokenDao tokenDao;
	
	@Resource
	private TokenService tokenService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private CustomerTokenRelationDao customerTokenRelationDao;
	
	@Resource
	private CustomerTokenRelationService customerTokenRelationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@TxTransaction(isStart = true)
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
        	throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE2021001),ExceptionConstant.ERROR_CODE2021001);
        }
        
        result.setCode(String.valueOf(ExceptionConstant.SUCCESS_CODE2001001));
        result.setMsg(ExceptionConstant.getSuccessMap().get(ExceptionConstant.SUCCESS_CODE2001001));
        result.setData(reLoginIn.getId());
        
        return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@TxTransaction(isStart = true)
	@Transactional
	public ResultUtil authorize(String loginPK,RestTemplate restTemplate) throws Exception {
		ResultUtil result = new ResultUtil();
		/**
		 * 根据loginPK去查询登录表
		 */
		LoginInEntity lie = new LoginInEntity();
		lie.setId(loginPK);
		LoginInEntity relie = (LoginInEntity) loginInDao.findOne(lie);
		if(relie==null){
			//授权失败
			throw new AuthorityException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4031001),ExceptionConstant.ERROR_CODE4031001);
		}
		/**
         * 根据customerNumber去查询 customer
         */
        FindCustomerModel fcm = new FindCustomerModel();
        fcm.setCustomerNumber(relie.getCustomernumber());
        //调用deposit 查询  customer方法
        ResponseEntity<String> customerResult = restTemplate.postForEntity(
				"http://" + CommonConstant.getDEPOSIT() + SysConstant.GET_CUSTOMER_URL + "",
				PostUtil.getRequestEntity(JsonProcess.changeEntityTOJSON(fcm)), String.class);
		if(customerResult.getStatusCodeValue()!=200){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001001),ExceptionConstant.ERROR_CODE5001001);
		}
        JSONObject str1 = XmlToJsonUtil.xmlToJson(customerResult.getBody());
        String str2 = JsonProcess.returnValue(str1, "ResultUtil");
        ResultUtil cru = JSON.parseObject(str2, ResultUtil.class);
        if(cru.getCode().equals("0")){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001001),ExceptionConstant.ERROR_CODE5001001);
        }
        ReCustomerModel rcm = JSON.parseObject(JSON.toJSONString(cru.getData()), ReCustomerModel.class);
        /**
		 * token 处理部分
		 */
		// 密钥 客户的登录密码
		JwtTokenProviderUtil jwtTokenProvider = new JwtTokenProviderUtil("123456");
		UserClaimsEntity claims = new UserClaimsEntity();
		claims.setDeveloperID(relie.getDeveloperid());
		claims.setCountryCode(rcm.getCountrycode());
		claims.setClearingCode(rcm.getClearingcode());
		claims.setBranchCode(rcm.getBranchcode());
		claims.setLoginName(relie.getLoginname());
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
		 * 根据loginPK去查询tokenID
		 */
		CustomerTokenRelationEntity cte = new CustomerTokenRelationEntity();
		cte.setLogininpk(loginPK);
		CustomerTokenRelationEntity recte = (CustomerTokenRelationEntity) customerTokenRelationDao.findOne(cte);
		if(recte!=null){
			/**
			 * 根据返回的tokenid去查询token
			 */
			TokenEntity tk = new TokenEntity();
			tk.setId(recte.getTokenid());
			TokenEntity retk = (TokenEntity) tokenDao.findOne(tk);
			if(retk==null){
				//customertoken关系表有脏数据//授权失败
				throw new AuthorityException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4031001),ExceptionConstant.ERROR_CODE4031001);
			}else{
				if(retk.getState().equals(SysConstant.TOKEN_STATE1)){
					token=retk.getToken();
					//已经授权
					//throw new AuthorityException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4031002),ExceptionConstant.ERROR_CODE4031002);
				}else{
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
					ct.setLogininpk(loginPK);
					ct.setTokenid(st.getId());
					customerTokenRelationService.save(ct);
				}
			}
		}else{
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
			ct.setLogininpk(loginPK);
			ct.setTokenid(st.getId());
			customerTokenRelationService.save(ct);
		}
		ReLoginModel rlm = new ReLoginModel();
		rlm.setLoginName(relie.getLoginname());
		rlm.setToken(token);

		result.setCode("1");
		result.setMsg("Authorize Success");
		result.setData(rlm);
		return result;
	}
}
package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.TokenDao;
import com.csi.sbs.sysadmin.business.entity.TokenEntity;
import com.csi.sbs.sysadmin.business.service.TokenService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("TokenService")
public class TokenServiceImpl implements TokenService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private TokenDao tokenDao;
	
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil save(TokenEntity token) throws Exception {
		ResultUtil result = new ResultUtil();
//		//校验token是否存在
//		TokenEntity retoken = (TokenEntity) tokenDao.findOne(token);
//		if(retoken!=null && retoken.getState().equals(SysConstant.TOKEN_STATE1)){
//			result.setCode("0");
//			result.setMsg("Token already exists.");
//			return result;
//		}
		token.setId(UUIDUtil.generateUUID());
		token.setCreatedate(format1.parse(format1.format(new Date())));
		token.setState(SysConstant.TOKEN_STATE1);
		tokenDao.insert(token);
		result.setCode("1");
		result.setMsg("Token generated successfully:"+token.getToken());
		return result;
	}

}

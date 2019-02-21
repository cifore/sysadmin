package com.csi.sbs.sysadmin.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.UserBranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;


@Service("UserBranchService")
public class UserBranchServiceImpl implements UserBranchService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private UserDao userDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil addUserBranch(AddUserBranchModel userbranch,RestTemplate restTemplate) {
		ResultUtil result = new ResultUtil();
		//校验userid 是否存在
		UserEntity user = new UserEntity();
		user.setUserid(userbranch.getUserid());
		UserEntity reuser = (UserEntity) userDao.findOne(user);
		if(reuser==null){
			result.setCode("0");
			result.setMsg("UserID does not exist");
			return result;
		}
		//校验bankID是否存在
		BranchEntity bank = new BranchEntity();
		bank.setId(userbranch.getBankid());
		BranchEntity rebank = (BranchEntity) branchDao.findOne(bank);
		if(rebank==null){
			result.setCode("0");
			result.setMsg("BankID does not exist");
			return result;
		}
		//校验是否已经授权
		UserBranchEntity userBranchSearch = new UserBranchEntity();
		userBranchSearch.setUserid(userbranch.getUserid());
		userBranchSearch.setBankid(userbranch.getBankid());
		UserBranchEntity reubs = (UserBranchEntity) userBranchDao.findOne(userBranchSearch);
		if(reubs!=null){
			result.setCode("0");
			result.setMsg("This user has authorized");
			return result;
		}
		
		UserBranchEntity userBranchEntity = new UserBranchEntity();
		userBranchEntity.setId(UUIDUtil.generateUUID());
		userBranchEntity.setUserid(userbranch.getUserid());
		userBranchEntity.setBankid(userbranch.getBankid());
		userBranchDao.insert(userBranchEntity);
		
		result.setCode("1");
		result.setMsg(userbranch.getUserid()+" authorizes "+ userbranch.getBankid() +" to succeed");
		return result;
	}

}

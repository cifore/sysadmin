package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.codingapi.tx.annotation.TxTransaction;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.SandBoxModel;
import com.csi.sbs.sysadmin.business.clientmodel.SandboxSaveModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.SandboxDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.SandboxEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.service.SandboxService;
import com.csi.sbs.sysadmin.business.util.ResponseUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("SandboxService")
public class SandboxServiceImpl implements SandboxService {

	@SuppressWarnings("rawtypes")
	@Resource
	private SandboxDao sandboxDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private UserDao userDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;
	
	
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@TxTransaction(isStart = true)
	@Transactional
	public ResultUtil randomOne(SandBoxModel sandBoxModel) throws Exception {
		/**
		 * 校验developerid是否存在
		 * 根据developerId查询developer表主键(developer表为t_user表)
		 */
		UserEntity isDeveloperExist = new UserEntity();
		isDeveloperExist.setUserid(sandBoxModel.getDeveloperId());
		UserEntity res_isDE = (UserEntity) userDao.findOne(isDeveloperExist);
		if (res_isDE == null) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041002),
					ExceptionConstant.ERROR_CODE4041002);
		}
		
		/**
		 * 校验是否已经给developer指派了sandboxid 根据developer主键查询
		 */
		UserBranchEntity searchUserBranch = new UserBranchEntity();
		searchUserBranch.setUserid(res_isDE.getId());
		UserBranchEntity res_searchUB = (UserBranchEntity) userBranchDao.findOne(searchUserBranch);
		if (res_searchUB != null && !StringUtils.isEmpty(res_searchUB.getSandboxid())) {
			// developer已经被分配sandboxid,返回分配的sandboxid
			return ResponseUtil.responseS(ExceptionConstant.SUCCESS_CODE200, res_searchUB.getSandboxid());
		}
		// 随机获取一个未被分配的sandboxid
		SandboxEntity getSandboxID = new SandboxEntity();
		getSandboxID.setCount(1);// 随机获取一条
		getSandboxID.setState(SysConstant.NOT_USED);// 未被使用
		SandboxEntity res_getSID = sandboxDao.randomOne(getSandboxID);
		if (res_getSID == null) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041006),
					ExceptionConstant.ERROR_CODE4041006);
		}
		// 将获取到的sandboxid标记为:已使用
		SandboxEntity markSandboxID = new SandboxEntity();
		markSandboxID.setState(SysConstant.USED);// 已被使用
		markSandboxID.setId(res_getSID.getId());
		markSandboxID.setLastupdateddate(format1.parse(format1.format(new Date())));
		sandboxDao.markUsed(markSandboxID);
		
		// developer还没有被分配sandboxid,进行分配
		UserBranchEntity updateUserBranch = new UserBranchEntity();
		updateUserBranch.setSandboxid(res_getSID.getSandboxid());
		updateUserBranch.setUserid(res_isDE.getId());

		userBranchDao.appSandBoxForDeveloper(updateUserBranch);
		return ResponseUtil.responseS(ExceptionConstant.SUCCESS_CODE200, res_getSID.getSandboxid());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@TxTransaction(isStart = true)
	@Transactional
	public ResultUtil save(SandboxSaveModel sandboxSaveModel) throws Exception {
		//model change
		SandboxEntity sandboxEntity = new SandboxEntity();
		sandboxEntity.setId(UUIDUtil.generateUUID());
		sandboxEntity.setSandboxid(sandboxSaveModel.getSandboxid());
		sandboxEntity.setState(SysConstant.NOT_USED);//未被使用
		sandboxEntity.setCreatedate(format1.parse(format1.format(new Date())));
		sandboxDao.insert(sandboxEntity);
		return ResponseUtil.responseS(ExceptionConstant.SUCCESS_CODE200, null);
	}

}

package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.service.PermissionService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {

	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil validate(PermissionModel permissionModel) {
		ResultUtil result = new ResultUtil();
		// 根据UserID 去查询 userbranch 关系表
		UserBranchEntity userbranch = new UserBranchEntity();
		userbranch.setUserid(permissionModel.getUserID());
		List<UserBranchEntity> list = userBranchDao.findMany(userbranch);
		if (list == null) {
			// 无权限
			result.setCode("0");
			result.setMsg("无权限");
			return result;
		}
		if (list.size() == 1) {
			// 根据bankID去查询countryCode,clearingCode,branchCode
			BranchEntity branch = new BranchEntity();
			branch.setId(list.get(0).getBankid());
			BranchEntity rebranch = (BranchEntity) branchDao.findOne(branch);
			if (rebranch == null) {
				result.setCode("0");
				result.setMsg("根据bankID 查询countryCode,clearingCode,branchCode 出错");
				return result;
			}
			HeaderModel header = new HeaderModel();
			header.setCountryCode(rebranch.getCountrycode());
			header.setClearingCode(rebranch.getClearingcode());
			header.setBranchCode(rebranch.getBranchcode());
			result.setCode("1");
			result.setMsg("权限验证通过");
			result.setData(header);
			return result;
		}
		if (list.size() > 1) {
			if(
				StringUtils.isEmpty(permissionModel.getCountryCode()) || 
				StringUtils.isEmpty(permissionModel.getClearingCode()) ||
				StringUtils.isEmpty(permissionModel.getBranchCode())
			){
				// 用户可操作多家银行数据，需要前端指定
				result.setCode("0");
				result.setMsg("用户可操作多家银行数据,请指定countryCode,clearingCode,branchCode");
				return result;
			}else{
				BranchEntity branch = new BranchEntity();
				branch.setCountrycode(permissionModel.getCountryCode());
				branch.setClearingcode(permissionModel.getClearingCode());
				branch.setBranchcode(permissionModel.getBranchCode());
				BranchEntity rebranch = (BranchEntity) branchDao.findOne(branch);
				if(rebranch==null){
					//无权限
					result.setCode("0");
					result.setMsg("无权限");
					return result;
				}
				HeaderModel header = new HeaderModel();
				header.setCountryCode(rebranch.getCountrycode());
				header.setClearingCode(rebranch.getClearingcode());
				header.setBranchCode(rebranch.getBranchcode());
				result.setCode("1");
				result.setMsg("权限验证通过");
				result.setData(header);
				return result;
			}
		}
		return result;
	}

}

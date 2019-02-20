package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReAuthorityModel;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.PermissionService;
import com.csi.sbs.sysadmin.business.util.AvailableNumberUtil;
import com.csi.sbs.sysadmin.business.util.PostUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {

	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private UserDao userDao;

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
			if (StringUtils.isEmpty(permissionModel.getCountryCode())
					|| StringUtils.isEmpty(permissionModel.getClearingCode())
					|| StringUtils.isEmpty(permissionModel.getBranchCode())) {
				// 用户可操作多家银行数据，需要前端指定
				result.setCode("0");
				result.setMsg("用户可操作多家银行数据,请指定countryCode,clearingCode,branchCode");
				return result;
			} else {
				BranchEntity branch = new BranchEntity();
				branch.setCountrycode(permissionModel.getCountryCode());
				branch.setClearingcode(permissionModel.getClearingCode());
				branch.setBranchcode(permissionModel.getBranchCode());
				BranchEntity rebranch = (BranchEntity) branchDao.findOne(branch);
				if (rebranch == null) {
					// 无权限
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public ResultUtil userAuthorize(RestTemplate restTemplate, AddUserModel addUserModel) {
		ResultUtil result = new ResultUtil();
		// check user
		UserEntity user = new UserEntity();
		user.setUserid(addUserModel.getUserid());
		UserEntity reuser = (UserEntity) userDao.findOne(user);
		if (reuser != null) {
			result.setCode("0");
			result.setMsg("User already exists");
			return result;
		}
		user.setEmail(addUserModel.getEmail());
		user.setId(UUIDUtil.generateUUID());
		user.setUsername(addUserModel.getUsername());
		userDao.insert(user);
		//返回的userID
		String reuserID = user.getId();

		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			result.setCode("0");
			result.setMsg("调用服务接口地址失败");
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");

		// 调用系统参数服务接口
		String param2 = "{\"item\":\"ClearingCode,BranchNumber,LocalCcy,NextAvailableCustomerNumber,CountryCode\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			result.setCode("0");
			result.setMsg("调用系统参数失败");
		}

		// 返回数据处理
		String clearcode = "";
		String branchnumber = "";
		@SuppressWarnings("unused")
		String localCCy = "";
		String countryCode = "";
		@SuppressWarnings("unused")
		String accountnumber = "";
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("CountryCode")) {
				countryCode = temp;
			}
			if (revalue.equals("BranchNumber")) {
				branchnumber = temp;
			}
			if (revalue.equals("ClearingCode")) {
				clearcode = temp;
			}
			if (revalue.equals("LocalCcy")) {
				localCCy = temp;
			}
		}

		// 判断branchnumber 是否达到最大
		BranchEntity branchsearch = new BranchEntity();
		branchsearch.setCountrycode(countryCode);
		branchsearch.setClearingcode(clearcode);
		branchsearch.setBranchcode("999");
		BranchEntity rebranchsearch = (BranchEntity) branchDao.findOne(branchsearch);
		if (rebranchsearch != null) {
			result.setCode("0");
			result.setMsg("branch number 已经达到最大");
			return result;
		}

		BranchEntity branch = new BranchEntity();
		branch.setId(UUIDUtil.generateUUID());
		branch.setCountrycode(countryCode);
		branch.setClearingcode(clearcode);
		branch.setBranchcode(branchnumber);
		branchDao.insert(branch);
		//返回的bankID
		String rebankID = branch.getId();

		if (Integer.parseInt(branchnumber) < 999) {
			// 递增
			AvailableNumberUtil.avaBranchNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_BRANCHNUMBER);
		}
		
		

		// 校验userid 是否存在
		UserEntity user2 = new UserEntity();
		user2.setUserid(reuserID);
		UserEntity reuser2 = (UserEntity) userDao.findOne(user);
		if (reuser2 == null) {
			result.setCode("0");
			result.setMsg("UserID does not exist");
			return result;
		}
		// 校验bankID是否存在
		BranchEntity bank = new BranchEntity();
		bank.setId(rebankID);
		BranchEntity rebank = (BranchEntity) branchDao.findOne(bank);
		if (rebank == null) {
			result.setCode("0");
			result.setMsg("BankID does not exist");
			return result;
		}
		// 校验是否已经授权
		UserBranchEntity userBranchSearch = new UserBranchEntity();
		userBranchSearch.setUserid(reuserID);
		userBranchSearch.setBankid(rebankID);
		UserBranchEntity reubs = (UserBranchEntity) userBranchDao.findOne(userBranchSearch);
		if (reubs != null) {
			result.setCode("0");
			result.setMsg("This user has authorized");
			return result;
		}

		UserBranchEntity userBranchEntity = new UserBranchEntity();
		userBranchEntity.setId(UUIDUtil.generateUUID());
		userBranchEntity.setUserid(reuserID);
		userBranchEntity.setBankid(rebankID);
		userBranchDao.insert(userBranchEntity);
		
		//返回信息
		ReAuthorityModel reAuthorityModel = new ReAuthorityModel();
		reAuthorityModel.setUserid(reuserID);
		reAuthorityModel.setUsername(addUserModel.getUsername());
		reAuthorityModel.setEmail(addUserModel.getEmail());
		reAuthorityModel.setCountrycode(countryCode);
		reAuthorityModel.setClearingcode(clearcode);
		reAuthorityModel.setBranchcode(branchnumber);
		result.setCode("1");
		result.setMsg("Authorization successful");
		result.setData(reAuthorityModel);

		return result;
	}

}

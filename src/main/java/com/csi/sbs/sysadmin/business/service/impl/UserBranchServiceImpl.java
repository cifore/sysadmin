package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.ImportUtil;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.SandBoxModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.constant.PathConstant;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.exception.OtherException;
import com.csi.sbs.sysadmin.business.sandbox.deposit.AddAccountSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.CustomerMasterSandBox;
import com.csi.sbs.sysadmin.business.service.UserBranchService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;
import com.csi.sbs.sysadmin.business.util.SRUtil;

@Service("UserBranchService")
public class UserBranchServiceImpl implements UserBranchService {

	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private UserDao userDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchDao;

	@SuppressWarnings("unused")
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil addUserBranch(AddUserBranchModel userbranch, RestTemplate restTemplate) {
		ResultUtil result = new ResultUtil();
		// 校验userid 是否存在
		UserEntity user = new UserEntity();
		user.setUserid(userbranch.getUserid());
		UserEntity reuser = (UserEntity) userDao.findOne(user);
		if (reuser == null) {
			result.setCode("0");
			result.setMsg("UserID does not exist");
			return result;
		}
		// 校验bankID是否存在
		BranchEntity bank = new BranchEntity();
		bank.setId(userbranch.getBankid());
		BranchEntity rebank = (BranchEntity) branchDao.findOne(bank);
		if (rebank == null) {
			result.setCode("0");
			result.setMsg("BankID does not exist");
			return result;
		}
		// 校验是否已经授权
		UserBranchEntity userBranchSearch = new UserBranchEntity();
		userBranchSearch.setUserid(userbranch.getUserid());
		userBranchSearch.setBankid(userbranch.getBankid());
		UserBranchEntity reubs = (UserBranchEntity) userBranchDao.findOne(userBranchSearch);
		if (reubs != null) {
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
		result.setMsg(userbranch.getUserid() + " authorizes " + userbranch.getBankid() + " to succeed");
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public ResultUtil appSandBoxForDeveloper(SandBoxModel sbm, RestTemplate restTemplate) throws Exception {
		// 根据developerId查询user表主键
		UserEntity u = new UserEntity();
		u.setUserid(sbm.getDeveloperId());
		@SuppressWarnings("unchecked")
		UserEntity reu = (UserEntity) userDao.findOne(u);
		if (reu == null) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041002),
					ExceptionConstant.ERROR_CODE4041002);
		}
		// model change
		UserBranchEntity ube = new UserBranchEntity();
		ube.setSandboxid(sbm.getSandBoxId());
		ube.setUserid(reu.getId());

		int i = userBranchDao.appSandBoxForDeveloper(ube);
		if (i > 0) {
			ResultUtil result = new ResultUtil();
			result.setCode(String.valueOf(ExceptionConstant.SUCCESS_CODE2001003));
			result.setMsg(ExceptionConstant.getSuccessMap().get(ExceptionConstant.SUCCESS_CODE2001003)
					+ "---developerID:" + sbm.getDeveloperId() + "---sandBoxId:" + sbm.getSandBoxId());

			/**
			 * 生成沙盘数据
			 */
			generateSandBoxData(restTemplate, sbm.getSandBoxId());
			return result;
		}
		throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE5001007),
				ExceptionConstant.ERROR_CODE5001007);
	}

	@SuppressWarnings("rawtypes")
	private void generateSandBoxData(RestTemplate restTemplate, String sandBoxId) throws Exception {
		// 读取t_customer_master模板数据到 CustomerMasterSandBox.class
		List list = ImportUtil.importData(PathConstant.CUSTOMER_TEMPLATE, CustomerMasterSandBox.class);
		// 创建请求头
		HeaderModel header = new HeaderModel();
		// 返回结果
		ResponseEntity<String> result = null;
		int j = 0;
		// 请求deposit创建customer的服务地址
		if (list != null && list.size() > 0) {
			// 循环创建customer
			for (int i = 0; i < list.size(); i++) {
				CustomerMasterSandBox cms = (CustomerMasterSandBox) list.get(i);
				// 请求头赋值
				header.setCountryCode(cms.getCountrycode());
				header.setClearingCode(cms.getClearingcode());
				header.setBranchCode(cms.getBranchcode());
				header.setSandBoxId(sandBoxId);
				cms.setCustomerID(String.valueOf(j + 1));
				cms.setDateOfBirth("1975-08-25");
				// System.out.println("----------" +
				// JsonProcess.changeEntityTOJSON(cms));
				// 创建customer
				result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_CUSTOMER_URL, header,
						JsonProcess.changeEntityTOJSON(cms));
				// 创建savingAccount
				String accountNumber = createSavingAccount(header, result, restTemplate);
				// 创建fexAccount
				createFexAccount(header, result, restTemplate, accountNumber);
				// 创建tdAccount
				createTDAccount(header, result, restTemplate, accountNumber);
				// 创建stockAccount
				createStockAccount(header, result, restTemplate, accountNumber);
				// 创建贵金属账号
				createPreciousAccount(header, result, restTemplate, accountNumber);
				// 创建基金账号
				createMutualAccount(header, result, restTemplate, accountNumber);
				// 创建currentAccount
				createCurrentAccount(header, result, restTemplate);
				j = j + 1;
			}
		}
		//System.out.println(result);
	}

	/**
	 * 创建savingAccount
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @throws Exception
	 */
	private String createSavingAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate)
			throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("001");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		header.setCustomerNumber(customerNumber);
		// 创建savingAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result);
	}

	/**
	 * 创建currentAccount
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @throws Exception
	 */
	private void createCurrentAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate)
			throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("002");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		header.setCustomerNumber(customerNumber);
		// 创建CurrentAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 创建外汇账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private void createFexAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("003");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		asb.setRelaccountnumber(realAccountNumber);
		header.setCustomerNumber(customerNumber);
		// 创建fexAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 创建定存账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private void createTDAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("100");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		asb.setRelaccountnumber(realAccountNumber);
		header.setCustomerNumber(customerNumber);
		// 创建tdAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 创建股票账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private void createStockAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("300");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		asb.setRelaccountnumber(realAccountNumber);
		header.setCustomerNumber(customerNumber);
		// 创建stockAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 创建贵金属账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private void createPreciousAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("400");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		asb.setRelaccountnumber(realAccountNumber);
		header.setCustomerNumber(customerNumber);
		// 创建preciousAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 创建基金账号
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private void createMutualAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("500");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		asb.setRelaccountnumber(realAccountNumber);
		header.setCustomerNumber(customerNumber);
		// 创建mutualAccount
		result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		// System.out.println("----------" + result);
	}

	/**
	 * 解析创建customer返回的结果获取customerNumber
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String getCustomerNumber(ResponseEntity<String> result) throws Exception {
		String customerNumber = null;
		// 解析返回的结果
		if (result.getStatusCodeValue() == 200) {
			JSONObject str1 = XmlToJsonUtil.xmlToJson(result.getBody());
			String str2 = JsonProcess.returnValue(str1, "ResultUtil");
			ResultUtil res = JSON.parseObject(str2, ResultUtil.class);
			if (res.getCode().equals("1")) {
				JSONObject str3 = JSON.parseObject(res.getData().toString());
				customerNumber = JsonProcess.returnValue(str3, "customernumber");
			}
		}
		return customerNumber;
	}

	/**
	 * 解析创建Account返回的结果获取AccountNumber
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String getAccountNumber(ResponseEntity<String> result) throws Exception {
		String accountNumber = null;
		// 解析返回的结果
		if (result.getStatusCodeValue() == 200) {
			JSONObject str1 = XmlToJsonUtil.xmlToJson(result.getBody());
			String str2 = JsonProcess.returnValue(str1, "ResultUtil");
			ResultUtil res = JSON.parseObject(str2, ResultUtil.class);
			if (res.getCode().equals("1")) {
				accountNumber = res.getData().toString();
			}
		}
		return accountNumber;
	}

}

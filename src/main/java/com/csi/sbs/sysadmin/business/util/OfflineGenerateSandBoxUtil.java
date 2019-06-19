package com.csi.sbs.sysadmin.business.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.common.business.util.ImportUtil;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.sysadmin.business.clientmodel.QueryTdDetailSysadminModel;
import com.csi.sbs.sysadmin.business.clientmodel.SandboxSaveModel;
import com.csi.sbs.sysadmin.business.clientmodel.otherservice.TermDepositDetailModel;
import com.csi.sbs.sysadmin.business.constant.PathConstant;
import com.csi.sbs.sysadmin.business.constant.SysConstant;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.CreditCardOpenSandBox;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.CustomerCreditSandBox;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.CustomerCurrencyAccountSandBox;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.PointRedemptionSandBox;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.PostTransDeInputSandBox;
import com.csi.sbs.sysadmin.business.sandbox.creditcard.RepaymentSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.AddAccountSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.CustomerMasterSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.DepositSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.TermDepositMasterSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.TermDepositRenewalSandBox;
import com.csi.sbs.sysadmin.business.sandbox.deposit.TransactionSandBox;
import com.csi.sbs.sysadmin.business.sandbox.foreignexchange.ExchangeSandBox;
import com.csi.sbs.sysadmin.business.sandbox.investment.FundBuyTradingSandBox;
import com.csi.sbs.sysadmin.business.sandbox.investment.StockTradingSandBox;
import com.csi.sbs.sysadmin.business.service.impl.SandboxServiceImpl;


public class OfflineGenerateSandBoxUtil {
	
	
//	public void test(SandboxServiceImpl s) throws Exception{
//		SandboxSaveModel sm = new SandboxSaveModel();
//		sm.setSandboxid("123456");
//		s.save(sm);
//	}
	
	private String customerTemplate="/home/lbs/t_customer_master.xls";
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SandboxSaveModel sm = new SandboxSaveModel();
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public void generateSandBox(RestTemplate restTemplate,SandboxServiceImpl sandBoxSave) throws Exception {
		String sandBoxId = UUIDUtil.generateUUID();
		sm.setSandboxid(sandBoxId);
		//保存sandboxid
		sandBoxSave.save(sm);
		// 读取t_customer_master模板数据到 CustomerMasterSandBox.class
		List list = ImportUtil.importData(customerTemplate, CustomerMasterSandBox.class);
		// 创建请求头
		HeaderModel header = new HeaderModel();
		// 返回结果
		ResponseEntity<String> result = null;
		// saving account
		String savingAccountNumber = null;
		String j1 = null;
		JSONObject j2 = null;
		String customerID = null;
		// 请求deposit创建customer的服务地址
		if (list != null && list.size() > 0) {
			// 循环创建customer
			for (int i = 0; i < list.size(); i++) {
				Thread.sleep(1000);
				// 获取沙盘身份证号码
				j1 = restTemplate
						.getForEntity(PathConstant.NEXT_AVAILABLE + SysConstant.SANDBOX_CUSTOMERID, String.class)
						.getBody();
				j2 = JSON.parseObject(j1);
				customerID = JsonProcess.returnValue(j2, "nextAvailableNumber");
				CustomerMasterSandBox cms = (CustomerMasterSandBox) list.get(i);
				// 请求头赋值
				header.setCountryCode(cms.getCountrycode());
				header.setClearingCode(cms.getClearingcode());
				header.setBranchCode(cms.getBranchcode());
				header.setSandBoxId(sandBoxId);
				header.setUserID("1");
				// header.setDockerId(null);
				cms.setCustomerID(SysConstant.SANDBOX_CUSTOMERID_SAMPLE + customerID);
				cms.setDateOfBirth("1975-08-25");
				// 创建customer
				result = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_CUSTOMER_URL, header,
						JsonProcess.changeEntityTOJSON(cms));
				// 创建savingAccount
				String accountNumber = null;
				for(int n=0;n<=15;n++){
					accountNumber = createSavingAccount(header, result, restTemplate);
				}
				savingAccountNumber = accountNumber;
				// 存款
				deposit(header, result, accountNumber, restTemplate);
				// 创建fexAccount
				String fexaccountnumber = createFexAccount(header, result, restTemplate, accountNumber);
				// 创建tdAccount
				String tdAccountNumber = createTDAccount(header, result, restTemplate, accountNumber);
				// 创建定存单
				String tdNumber = createTermDepositApplication(header, result, accountNumber, tdAccountNumber,
						restTemplate);
				// 创建stockAccount
				String stockAccountNumber = createStockAccount(header, result, restTemplate, accountNumber);
				// 创建贵金属账号
				createPreciousAccount(header, result, restTemplate, accountNumber);
				// 创建基金账号
				String mutualAccountNumber = createMutualAccount(header, result, restTemplate, accountNumber);
				// 创建currentAccount-创建4个current账号
				String currentAccount = null;
				for (int k = 0; k < 4; k++) {
					currentAccount = createCurrentAccount(header, result, restTemplate);
				}
				// 创建信用卡账号
				String creditCardAccountNumber = createCreditCard(header, result, restTemplate, accountNumber, cms);
				// 转账
				transfer(header, result, currentAccount, accountNumber, restTemplate);
				// 基金买入
				subscription(header, result, accountNumber, mutualAccountNumber, restTemplate);
				// 股票交易(买)
				stockTrading(header, result, accountNumber, stockAccountNumber, restTemplate);
				// 外汇交易
				currencyExchange(header, result, accountNumber, fexaccountnumber, restTemplate);
				// 信用卡还款
				creditCardRepeyment(header, result, accountNumber, creditCardAccountNumber, restTemplate);
				// 信用卡交易
				transactionPosting(header, result, creditCardAccountNumber, restTemplate);
				// 信用卡积分消费
				redemption(header, result, creditCardAccountNumber, restTemplate);
				AvailableNumberUtil.sandBoxCustomerIDIncrease(restTemplate, SysConstant.SANDBOX_CUSTOMERID);
			}
			/**
			 * saving;current;fex;td;precious 账号时间做旧处理
			 */
			String d1 = restTemplate.getForEntity(PathConstant.ACCOUNT_OLD_DATE + "/" + sandBoxId, String.class)
					.getBody();
			/**
			 * stock;mutual 账号时间做旧处理
			 */
			String d2 = restTemplate.getForEntity(PathConstant.ACCOUNT_OLD_DATEI + "/" + sandBoxId, String.class)
					.getBody();
			// 获取某个沙盘下的td_detail数据
			JSONArray td1 = getTdDetail(header, sandBoxId, restTemplate);
			if (td1 != null && td1.size() > 0) {
				for (int n = 0; n < td1.size(); n++) {
					TermDepositDetailModel tdd = JSON.parseObject(td1.get(n).toString(), TermDepositDetailModel.class);
					Thread.sleep(1000);
					// 定存到期续存
					termDepositRenewal(header, result, tdd.getAccountnumber(), tdd.getDepositnumber(), "1week",
							restTemplate);
				}
			}
		}
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result_);
	}

	/**
	 * 创建currentAccount
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @throws Exception
	 */
	private String createCurrentAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate)
			throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		AddAccountSandBox asb = new AddAccountSandBox();
		asb.setAccountType("002");
		asb.setCurrencyCode("HKD");
		asb.setCustomerNumber(customerNumber);
		header.setCustomerNumber(customerNumber);
		// 创建CurrentAccount
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result_);
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
	private String createFexAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));

		return getAccountNumber(result_);
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
	private String createTDAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result_);
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
	private String createStockAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result_);
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
	@SuppressWarnings("unused")
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
	}

	/**
	 * 创建基金账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @throws Exception
	 */
	private String createMutualAccount(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
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
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREATE_ACCOUNT_URL, header,
				JsonProcess.changeEntityTOJSON(asb));
		return getAccountNumber(result_);
	}

	/**
	 * 创建信用卡账号
	 * 
	 * @param header
	 * @param result
	 * @param restTemplate
	 * @param realAccountNumber
	 * @param cms
	 * @throws Exception
	 */
	private String createCreditCard(HeaderModel header, ResponseEntity<String> result, RestTemplate restTemplate,
			String realAccountNumber, CustomerMasterSandBox cms) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(customerNumber);
		CustomerCreditSandBox cm = new CustomerCreditSandBox();
		cm.setAccommodation(cms.getAccommodation());
		cm.setChinesename(cms.getChinesename());
		cm.setCompanyaddress(cms.getCompanyaddress());
		cm.setCompanyphonenumber(cms.getCompanyphonenumber());
		cm.setCustomerid(cms.getCustomerID());
		cm.setDateofbirth(cms.getDateOfBirth());
		cm.setEducation(cms.getEducation());
		cm.setEmailaddress(cms.getEmailaddress());
		cm.setEmployercompanyname(cms.getEmployercompanyname());
		cm.setFirstname(cms.getFirstname());
		cm.setGender(cms.getGender());
		cm.setIssuecountry(cms.getIssueCountry());
		cm.setLastname(cms.getLastname());
		cm.setMailingaddress(cms.getMailingAddress());
		cm.setMaritalstatus(cms.getMaritalstatus());
		cm.setMobilephonenumber(cms.getMobilePhoneNumber());
		cm.setMonthlysalary(cms.getMonthlysalary());
		cm.setNationality(cms.getNationality());
		cm.setOccupation(cms.getOccupation());
		cm.setPermanentresidencestatus(cms.getPermanentresidencestatus());
		cm.setPosition(cms.getPosition());
		cm.setResidencephonenumber(cms.getResidencephonenumber());
		cm.setResidentialaddress(cms.getResidentialaddress());
		cm.setWechatid(cms.getWechatid());
		cm.setYearsofresidence(cms.getYearsofresidence());
		cm.setYearsofservices(cms.getYearsofservices());

		CreditCardOpenSandBox co = new CreditCardOpenSandBox();
		co.setApprovedlimit(new BigDecimal(10000));
		co.setCashadvancelimit(new BigDecimal(5000));
		co.setCreditcardtype("V");
		co.setExpirydate(CalculateMaturityDateUtil.plusMonth(36, format2.format(new Date())));
		co.setIssuancedate(format2.format(new Date()));
		co.setRepaymentaccountnum(realAccountNumber);
		co.setRepaymentcycle("M");
		co.setVerificationcode("001");

		CustomerCurrencyAccountSandBox cca = new CustomerCurrencyAccountSandBox();
		cca.setAccount(co);
		cca.setCustomer(cm);

		// System.out.println("-----------"+JsonProcess.changeEntityTOJSON(cca));
		// 创建信用卡账号
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREDITCARD_OPEN, header,
				JsonProcess.changeEntityTOJSON(cca));
		return getCreditCardAccountNum(result_);
	}

	/**
	 * 创建定存单
	 * 
	 * @param debitAccountNumber
	 * @param tdAccountNumber
	 * @param restTemplate
	 * @param result
	 * @throws Exception
	 */
	private String createTermDepositApplication(HeaderModel header, ResponseEntity<String> result,
			String debitAccountNumber, String tdAccountNumber, RestTemplate restTemplate) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(customerNumber);
		TermDepositMasterSandBox tdmsb = new TermDepositMasterSandBox();
		tdmsb.setDebitAccountNumber(debitAccountNumber);
		tdmsb.setTdAccountNumber(tdAccountNumber);
		tdmsb.setTdAmount("10000");
		tdmsb.setTdCcy("HKD");
		tdmsb.setTdContractPeriod("1month");

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate,
				PathConstant.CREATE_TERMDEPOSIT_APPLICATION, header, JsonProcess.changeEntityTOJSON(tdmsb));
		return getTDNumber(result_);
	}

	/**
	 * 存款
	 * 
	 * @param debitAccountNumber
	 * @param restTemplate
	 * @param result
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void deposit(HeaderModel header, ResponseEntity<String> result, String debitAccountNumber,
			RestTemplate restTemplate) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(customerNumber);
		DepositSandBox dsb = new DepositSandBox();
		dsb.setAccountNumber(debitAccountNumber);
		dsb.setCurrencycode("HKD");
		dsb.setDepositAmount("5000000");

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.DEPOSIT, header,
				JsonProcess.changeEntityTOJSON(dsb));
	}

	/**
	 * 定存到期取款
	 * 
	 * @param header
	 * @param result
	 * @param tdAccountNumber
	 * @param tdNumber
	 * @param debitAccountNumber
	 * @param restTemplate
	 * @throws Exception
	 */
	// private void termDepositDrawDown(HeaderModel header,
	// ResponseEntity<String> result, String tdAccountNumber,
	// String tdNumber, String debitAccountNumber, RestTemplate restTemplate)
	// throws Exception {
	// // 解析返回的结果
	//// String customerNumber = getCustomerNumber(result);
	// header.setCustomerNumber(null);
	// TermDepositDrawDownSandBox tsb = new TermDepositDrawDownSandBox();
	// tsb.setDebitAccountNumber(debitAccountNumber);
	// tsb.setTdAccountNumber(tdAccountNumber);
	// tsb.setTdNumber(tdNumber);
	//
	// @SuppressWarnings("unused")
	// ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate,
	// PathConstant.TERMDEPOSIT_DRAWDOWN, header,
	// JsonProcess.changeEntityTOJSON(tsb));
	// }

	/**
	 * 定存到期续存
	 * 
	 * @param header
	 * @param result
	 * @param tdAccountNumber
	 * @param tdNumber
	 * @param tdRenewalPeriod
	 * @param restTemplate
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void termDepositRenewal(HeaderModel header, ResponseEntity<String> result, String tdAccountNumber,
			String tdNumber, String tdRenewalPeriod, RestTemplate restTemplate) throws Exception {
		// 解析返回的结果
		// String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(null);
		TermDepositRenewalSandBox tsb = new TermDepositRenewalSandBox();
		tsb.setTdaccountnumber(tdAccountNumber);
		tsb.setTdnumber(tdNumber);
		tsb.setTdRenewalPeriod(tdRenewalPeriod);

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.TERMDEPOSIT_RENEWAL, header,
				JsonProcess.changeEntityTOJSON(tsb));
	}

	/**
	 * 基金买入
	 * 
	 * @param header
	 * @param result
	 * @param debitaccountnumber
	 * @param mutualaccountnumber
	 */
	private void subscription(HeaderModel header, ResponseEntity<String> result, String debitaccountnumber,
			String mutualaccountnumber, RestTemplate restTemplate) {
		FundBuyTradingSandBox fb = new FundBuyTradingSandBox();
		fb.setDebitaccountnumber(debitaccountnumber);
		fb.setFundaccountnumber(mutualaccountnumber);
		fb.setFundcode(SysConstant.SANDBOX_FUNDCODE);
		fb.setTradingamount(new BigDecimal(1));

		@SuppressWarnings("unused")
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.FUND_BUY, header,
				JsonProcess.changeEntityTOJSON(fb));
	}

	/**
	 * 股票交易
	 * 
	 * @param header
	 * @param result
	 * @param debitaccountnumber
	 * @param stockaccountnumber
	 * @param restTemplate
	 * @throws ParseException
	 */
	private void stockTrading(HeaderModel header, ResponseEntity<String> result, String debitaccountnumber,
			String stockaccountnumber, RestTemplate restTemplate) throws ParseException {
		String date1 = format.format(new Date());
		String date2 = CalculateMaturityDateUtil.plusDay(2, date1);
		StockTradingSandBox ss = new StockTradingSandBox();
		ss.setDebitaccountnumber(debitaccountnumber);
		ss.setExpiryDate(date2);
		ss.setOrderType("Fix Price");
		ss.setSellAll("");
		ss.setSharingNo("200");
		ss.setStkaccountnumber(stockaccountnumber);
		ss.setStockCode("0100.HK");
		ss.setTradingOption("Buy");
		ss.setTradingPrice("7.2");

		@SuppressWarnings("unused")
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.STOCK_TRAD, header,
				JsonProcess.changeEntityTOJSON(ss));
	}

	/**
	 * 外汇交易
	 * 
	 * @param header
	 * @param result
	 * @param debitaccountnumber
	 * @param fexaccountnumber
	 * @param restTemplate
	 */
	@SuppressWarnings("unused")
	private void currencyExchange(HeaderModel header, ResponseEntity<String> result, String debitaccountnumber,
			String fexaccountnumber, RestTemplate restTemplate) {
		ExchangeSandBox eb = new ExchangeSandBox();
		eb.setActionCode("Buy");
		eb.setCcycode("USD");
		eb.setDebitaccountnumber(debitaccountnumber);
		eb.setExchangeAmount("10");
		eb.setFexAccountNumber(fexaccountnumber);

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.FOREIGNEXCHANGE, header,
				JsonProcess.changeEntityTOJSON(eb));
	}

	/**
	 * 转账
	 * 
	 * @param header
	 * @param result
	 * @param inAccountNumber
	 * @param outAccountNumber
	 * @param restTemplate
	 */
	@SuppressWarnings("unused")
	private void transfer(HeaderModel header, ResponseEntity<String> result, String inAccountNumber,
			String outAccountNumber, RestTemplate restTemplate) {
		TransactionSandBox tb = new TransactionSandBox();
		tb.setTransferAmount("1");
		tb.setTransferInAccountNumber(inAccountNumber);
		tb.setTransferOutAccountNumber(outAccountNumber);

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.TRANSFER, header,
				JsonProcess.changeEntityTOJSON(tb));
	}

	/**
	 * 信用卡还款
	 * 
	 * @param header
	 * @param result
	 * @param debitaccountnumber
	 * @param creditcardnumber
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void creditCardRepeyment(HeaderModel header, ResponseEntity<String> result, String debitaccountnumber,
			String creditcardnumber, RestTemplate restTemplate) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(customerNumber);
		RepaymentSandBox rb = new RepaymentSandBox();
		rb.setCreditcardnumber(creditcardnumber);
		rb.setDebitaccountnumber(debitaccountnumber);
		rb.setRepaymentAmount(new BigDecimal(10));

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREDITCARD_REPEYMENT, header,
				JsonProcess.changeEntityTOJSON(rb));
	}

	/**
	 * 信用卡交易
	 * 
	 * @param header
	 * @param result
	 * @param creditcardnumber
	 * @param restTemplate
	 */
	@SuppressWarnings("unused")
	private void transactionPosting(HeaderModel header, ResponseEntity<String> result, String creditcardnumber,
			RestTemplate restTemplate) {
		PostTransDeInputSandBox pb = new PostTransDeInputSandBox();
		pb.setCreditcardnumber(creditcardnumber);
		pb.setMerchantnumber(SysConstant.SANDBOX_MERCHANT_NUMBER);
		pb.setTransactionamount(new BigDecimal(10));
		pb.setTransactionccy("USD");
		pb.setTransactiontime(format2.format(new Date()));

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREDITCARD_TRANSFER, header,
				JsonProcess.changeEntityTOJSON(pb));
	}

	/**
	 * 信用卡积分消费
	 * 
	 * @param header
	 * @param result
	 * @param creditcardnumber
	 * @param restTemplate
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void redemption(HeaderModel header, ResponseEntity<String> result, String creditcardnumber,
			RestTemplate restTemplate) throws Exception {
		// 解析返回的结果
		String customerNumber = getCustomerNumber(result);
		header.setCustomerNumber(customerNumber);
		PointRedemptionSandBox pb = new PointRedemptionSandBox();
		pb.setAmount(new BigDecimal(2));
		pb.setCreditcardnumber(creditcardnumber);
		pb.setProductcode(SysConstant.SANDBOX_PRODUCTCODE);

		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.CREDITCARD_REDEMPTION, header,
				JsonProcess.changeEntityTOJSON(pb));
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

	/**
	 * 解析创建定存单返回的tdNumber
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String getTDNumber(ResponseEntity<String> result) throws Exception {
		String tdNumber = null;
		// 解析返回的结果
		if (result.getStatusCodeValue() == 200) {
			JSONObject str1 = XmlToJsonUtil.xmlToJson(result.getBody());
			String str2 = JsonProcess.returnValue(str1, "ResultUtil");
			ResultUtil res = JSON.parseObject(str2, ResultUtil.class);
			if (res.getCode().equals("1")) {
				tdNumber = res.getData().toString();
			}
		}
		return tdNumber;
	}

	/**
	 * 解析创建信用卡返回的信用卡账号
	 * 
	 * @param result
	 * @return
	 */
	private String getCreditCardAccountNum(ResponseEntity<String> result) {
		String creditCardNumber = null;
		// 解析返回的结果
		if (result.getStatusCodeValue() == 200) {
			if (!StringUtils.isEmpty(result.getBody())) {
				JSONObject str1 = JSON.parseObject(result.getBody());
				String str2 = JsonProcess.returnValue(str1, "code");
				if (str2.equals("1")) {
					creditCardNumber = JsonProcess.returnValue(str1, "creditcardnumber");
				}
			}
		}
		return creditCardNumber;
	}

	/**
	 * 获取某个沙盘下的td_detail数据
	 * 
	 * @param header
	 * @param sandBoxId
	 * @param restTemplate
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private JSONArray getTdDetail(HeaderModel header, String sandBoxId, RestTemplate restTemplate) throws Exception {
		QueryTdDetailSysadminModel qm = new QueryTdDetailSysadminModel();
		qm.setSandBoxId(sandBoxId);
		ResponseEntity<String> result_ = SRUtil.sendWithHeader(restTemplate, PathConstant.GETTD_DETAIL, header,
				JsonProcess.changeEntityTOJSON(qm));
		JSONArray str3 = null;
		// 解析返回的结果
		if (result_.getStatusCodeValue() == 200) {
			JSONObject str1 = XmlToJsonUtil.xmlToJson(result_.getBody());
			String str2 = JsonProcess.returnValue(str1, "ResultUtil");
			ResultUtil res = JSON.parseObject(str2, ResultUtil.class);
			if (res.getCode().equals("1")) {
				str3 = JSON.parseArray(res.getData().toString());
			}
		}
		return str3;
	}

}

package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.clientmodel.CheckCCBModel;
import com.csi.sbs.sysadmin.business.clientmodel.FindBranchCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.GetCountryCodeModel;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.service.BranchService;


@Service("BranchService")
public class BranchServiceImpl implements BranchService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchdao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCountryCodes() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<BranchEntity> countryCodes = branchdao.findCountryCodes();
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("countryCodes", countryCodes);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getClearingCodeByCountryCode(GetCountryCodeModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		BranchEntity country = new BranchEntity();
		country.setCountrycode(ase.getCountrycode());
		List<BranchEntity> clearingCodes = branchdao.findClearingCodeByCountry(country);
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("clearingCodes", clearingCodes);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBrancoByCC(FindBranchCodeModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		BranchEntity branchInfo = new BranchEntity();
		branchInfo.setCountrycode(ase.getCountrycode());
		branchInfo.setBranchcode(ase.getClearingcode());
		List<BranchEntity> branchCodes = branchdao.findMany(branchInfo);
		map.put("msg", "Check Success");
		map.put("code", "1");
		map.put("branchCodes", branchCodes);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkccbInfo(CheckCCBModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		BranchEntity branchEntity =  new BranchEntity();
		branchEntity.setCountrycode(ase.getCountrycode());
		branchEntity.setClearingcode(ase.getClearingcode());
		branchEntity.setBranchcode(ase.getBranchcode());
		BranchEntity branchInfo = (BranchEntity) branchdao.findOne(branchEntity);
		if(branchInfo != null){
			map.put("branchInfo", branchInfo);
			map.put("msg", "Find Record Succssfully");
			map.put("code", "1");
			return map;
		}
		map.put("msg", "The Record Doesn't Exist");
		map.put("code", "0");
		return map;
	}

}

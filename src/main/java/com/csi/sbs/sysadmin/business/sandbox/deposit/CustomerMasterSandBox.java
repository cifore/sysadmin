package com.csi.sbs.sysadmin.business.sandbox.deposit;

import com.csi.sbs.common.business.annotation.ExcelVOAttribute;

public class CustomerMasterSandBox {

	private String customerID;

	@ExcelVOAttribute(name = "countrycode", column = "A")
	private String countrycode;

	@ExcelVOAttribute(name = "clearingcode", column = "B")
	private String clearingcode;

	@ExcelVOAttribute(name = "branchcode", column = "C")
	private String branchcode;

	@ExcelVOAttribute(name = "firstname", column = "D")
	private String firstname;

	@ExcelVOAttribute(name = "lastname", column = "E")
	private String lastname;

	@ExcelVOAttribute(name = "mobilePhoneNumber", column = "F")
	private String mobilePhoneNumber;

	@ExcelVOAttribute(name = "issueCountry", column = "G")
	private String issueCountry;

	@ExcelVOAttribute(name = "dateOfBirth", column = "H")
	private String dateOfBirth;

	@ExcelVOAttribute(name = "chinesename", column = "I")
	private String chinesename;

	@ExcelVOAttribute(name = "gender", column = "J")
	private String gender;

	@ExcelVOAttribute(name = "nationality", column = "K")
	private String nationality;

	@ExcelVOAttribute(name = "permanentresidencestatus", column = "L")
	private String permanentresidencestatus;

	@ExcelVOAttribute(name = "maritalstatus", column = "M")
	private String maritalstatus;

	@ExcelVOAttribute(name = "education", column = "N")
	private String education;

	@ExcelVOAttribute(name = "residentialaddress", column = "O")
	private String residentialaddress;

	@ExcelVOAttribute(name = "emailaddress", column = "P")
	private String emailaddress;

	@ExcelVOAttribute(name = "mailingAddress", column = "Q")
	private String mailingAddress;

	@ExcelVOAttribute(name = "residencephonenumber", column = "R")
	private String residencephonenumber;

	@ExcelVOAttribute(name = "wechatid", column = "S")
	private String wechatid;

	@ExcelVOAttribute(name = "accommodation", column = "T")
	private String accommodation;

	@ExcelVOAttribute(name = "yearsofresidence", column = "U")
	private String yearsofresidence;

	@ExcelVOAttribute(name = "occupation", column = "V")
	private String occupation;

	@ExcelVOAttribute(name = "employercompanyname", column = "W")
	private String employercompanyname;

	@ExcelVOAttribute(name = "position", column = "X")
	private String position;

	@ExcelVOAttribute(name = "companyaddress", column = "Y")
	private String companyaddress;

	@ExcelVOAttribute(name = "companyphonenumber", column = "Z")
	private String companyphonenumber;

	@ExcelVOAttribute(name = "yearsofservices", column = "AA")
	private String yearsofservices;

	@ExcelVOAttribute(name = "monthlysalary", column = "AB")
	private String monthlysalary;

	

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getIssueCountry() {
		return issueCountry;
	}

	public void setIssueCountry(String issueCountry) {
		this.issueCountry = issueCountry;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPermanentresidencestatus() {
		return permanentresidencestatus;
	}

	public void setPermanentresidencestatus(String permanentresidencestatus) {
		this.permanentresidencestatus = permanentresidencestatus;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getResidentialaddress() {
		return residentialaddress;
	}

	public void setResidentialaddress(String residentialaddress) {
		this.residentialaddress = residentialaddress;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getResidencephonenumber() {
		return residencephonenumber;
	}

	public void setResidencephonenumber(String residencephonenumber) {
		this.residencephonenumber = residencephonenumber;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}

	public String getYearsofresidence() {
		return yearsofresidence;
	}

	public void setYearsofresidence(String yearsofresidence) {
		this.yearsofresidence = yearsofresidence;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmployercompanyname() {
		return employercompanyname;
	}

	public void setEmployercompanyname(String employercompanyname) {
		this.employercompanyname = employercompanyname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompanyaddress() {
		return companyaddress;
	}

	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}

	public String getCompanyphonenumber() {
		return companyphonenumber;
	}

	public void setCompanyphonenumber(String companyphonenumber) {
		this.companyphonenumber = companyphonenumber;
	}

	public String getYearsofservices() {
		return yearsofservices;
	}

	public void setYearsofservices(String yearsofservices) {
		this.yearsofservices = yearsofservices;
	}

	public String getMonthlysalary() {
		return monthlysalary;
	}

	public void setMonthlysalary(String monthlysalary) {
		this.monthlysalary = monthlysalary;
	}

	
}
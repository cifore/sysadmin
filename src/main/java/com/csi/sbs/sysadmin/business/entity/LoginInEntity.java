package com.csi.sbs.sysadmin.business.entity;

public class LoginInEntity {
	
	private String id;

    private String loginname;
	
    private String customernumber;

    private String loginpwd;
    
    private String usertype;

    private String developerid;
    
    
    

    public String getCustomernumber() {
		return customernumber;
	}

	public void setCustomernumber(String customernumber) {
		this.customernumber = customernumber;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getDeveloperid() {
		return developerid;
	}

	public void setDeveloperid(String developerid) {
		this.developerid = developerid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd == null ? null : loginpwd.trim();
    }
}
package com.csi.sbs.sysadmin.business.entity;

public class LoginInEntity {
	
	private String id;

    private String loginname;
	
    private String customerpk;

    private String loginpwd;
    
    
    

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

	public String getCustomerpk() {
        return customerpk;
    }

    public void setCustomerpk(String customerpk) {
        this.customerpk = customerpk == null ? null : customerpk.trim();
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd == null ? null : loginpwd.trim();
    }
}
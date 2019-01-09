package com.csi.sbs.sysadmin.business.entity;

import java.util.Date;

public class CheckListEntity {
    private String id;

    private String apiname;

    private String apidescription;

    private String requestmode;

    private String moduleid;
    
    private String modulename;

    private String inputdescription;

    private String outputdescription;

    private String apiaddress;
    
    private String internalurl;

    private String owner;

    private String status;

    private String version;

    private String versiondesc;

    private Date lastupdatedate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname == null ? null : apiname.trim();
    }

    public String getApidescription() {
        return apidescription;
    }

    public void setApidescription(String apidescription) {
        this.apidescription = apidescription == null ? null : apidescription.trim();
    }

    public String getRequestmode() {
        return requestmode;
    }

    public void setRequestmode(String requestmode) {
        this.requestmode = requestmode == null ? null : requestmode.trim();
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public String getInputdescription() {
        return inputdescription;
    }

    public void setInputdescription(String inputdescription) {
        this.inputdescription = inputdescription == null ? null : inputdescription.trim();
    }

    public String getOutputdescription() {
        return outputdescription;
    }

    public void setOutputdescription(String outputdescription) {
        this.outputdescription = outputdescription == null ? null : outputdescription.trim();
    }

    public String getApiaddress() {
        return apiaddress;
    }

    public void setApiaddress(String apiaddress) {
        this.apiaddress = apiaddress == null ? null : apiaddress.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getVersiondesc() {
        return versiondesc;
    }

    public void setVersiondesc(String versiondesc) {
        this.versiondesc = versiondesc == null ? null : versiondesc.trim();
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

	public String getInternalurl() {
		return internalurl;
	}

	public void setInternalurl(String internalurl) {
		this.internalurl = internalurl;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
}
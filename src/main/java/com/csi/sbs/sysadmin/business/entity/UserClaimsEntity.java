package com.csi.sbs.sysadmin.business.entity;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.impl.JwtMap;

/**
 * 权限信息对象
 * @author Tony
 *
 */
public class UserClaimsEntity extends JwtMap implements Claims {
	
    private String[] scope;
    private String grantType = "password";
    private String userID;
    private String customerID;
	private String countryCode;
	private String clearingCode;
	private String branchCode;
    
 
    public String[] getScope() {
        return scope;
    }
 
    public void setScope(String[] scope) {
        this.scope = scope;
        setValue("scope", this.scope);
    }
 
    public String getGrantType() {
        return grantType;
    }
 
    public void setGrantType(String grantType) {
        this.grantType = grantType;
        setValue("grantType", this.grantType);
    }
 
    public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
		setValue("userID", this.userID);
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
		setValue("customerID", this.customerID);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		setValue("countryCode", this.countryCode);
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public void setClearingCode(String clearingCode) {
		this.clearingCode = clearingCode;
		setValue("clearingCode", this.clearingCode);
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
		setValue("branchCode", this.branchCode);
	}

	@Override
    public String getIssuer() {
        return getString(ISSUER);
    }
 
    @Override
    public Claims setIssuer(String iss) {
        setValue(ISSUER, iss);
        return this;
    }
 
    @Override
    public String getSubject() {
        return getString(SUBJECT);
    }
 
    @Override
    public Claims setSubject(String sub) {
        setValue(SUBJECT, sub);
        return this;
    }
 
    @Override
    public String getAudience() {
        return getString(AUDIENCE);
    }
 
    @Override
    public Claims setAudience(String aud) {
        setValue(AUDIENCE, aud);
        return this;
    }
 
    @Override
    public Date getExpiration() {
        return get(Claims.EXPIRATION, Date.class);
    }
 
    @Override
    public Claims setExpiration(Date exp) {
        setDate(Claims.EXPIRATION, exp);
        return this;
    }
 
    @Override
    public Date getNotBefore() {
        return get(Claims.NOT_BEFORE, Date.class);
    }
 
    @Override
    public Claims setNotBefore(Date nbf) {
        setDate(Claims.NOT_BEFORE, nbf);
        return this;
    }
 
    @Override
    public Date getIssuedAt() {
        return get(Claims.ISSUED_AT, Date.class);
    }
 
    @Override
    public Claims setIssuedAt(Date iat) {
        setDate(Claims.ISSUED_AT, iat);
        return this;
    }
 
    @Override
    public String getId() {
        return getString(ID);
    }
 
    @Override
    public Claims setId(String jti) {
        setValue(Claims.ID, jti);
        return this;
    }
 
    @Override
    public <T> T get(String claimName, Class<T> requiredType) {
        Object value = get(claimName);
        if (value == null) {
            return null;
        }
 
        if (Claims.EXPIRATION.equals(claimName)
                || Claims.ISSUED_AT.equals(claimName)
                || Claims.NOT_BEFORE.equals(claimName)) {
            value = getDate(claimName);
        }
 
        if (requiredType == Date.class && value instanceof Long) {
            value = new Date((Long) value);
        }
 
        if (!requiredType.isInstance(value)) {
            throw new RequiredTypeException("Expected value to be of type: "
                    + requiredType + ", but was " + value.getClass());
        }
 
        return requiredType.cast(value);
    }
 
}
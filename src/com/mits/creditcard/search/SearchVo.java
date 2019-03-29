package com.mits.creditcard.search;

//this class is acts as a bean class for search module 
public class SearchVo {
	String appNumber;
	String custId;
	String appStatus;
	String fromDate;
	String toDate;
	String createdDate;

	public String getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(String appNumber) {
		this.appNumber = appNumber;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "SearchVo [appNumber=" + appNumber + ", custId=" + custId + ", appStatus=" + appStatus + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", createdDate=" + createdDate + "]";
	}

}

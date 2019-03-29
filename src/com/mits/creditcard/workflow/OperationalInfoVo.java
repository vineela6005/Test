package com.mits.creditcard.workflow;

import java.sql.Date;

//this class is acts as a bean class for workflow module
public class OperationalInfoVo 
{
	String app_number;
	String custid;
	String credit_rating;
	String card_type;
	String kyc_check;
	String card_details;
	int credit_limit;
	String app_status;
	Date created_date;
	String created_user;
	String last_modified_user;
	Date last_modified_date;
	
	public String getApp_number() {
		return app_number;
	}
	public void setApp_number(String app_number) {
		this.app_number = app_number;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCredit_rating() {
		return credit_rating;
	}
	public void setCredit_rating(String credit_rating) {
		this.credit_rating = credit_rating;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getKyc_check() {
		return kyc_check;
	}
	public void setKyc_check(String kyc_check) {
		this.kyc_check = kyc_check;
	}
	public String getCard_details() {
		return card_details;
	}
	public void setCard_details(String card_details) {
		this.card_details = card_details;
	}
	public int getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(int credit_limit) {
		this.credit_limit = credit_limit;
	}
	public String getApp_status() {
		return app_status;
	}
	public void setApp_status(String app_status) {
		this.app_status = app_status;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getCreated_user() {
		return created_user;
	}
	public void setCreated_user(String created_user) {
		this.created_user = created_user;
	}
	public String getLast_modified_user() {
		return last_modified_user;
	}
	public void setLast_modified_user(String last_modified_user) {
		this.last_modified_user = last_modified_user;
	}
	public Date getLast_modified_date() {
		return last_modified_date;
	}
	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}
	
	
	

}

package com.mits.creditcard.workflow;

//this class is acts as a bean class for workflow module
public class AddressInfoVo 
{
	String custid;
	String cor_address;
	String work_address;
	String per_address;
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCor_address() {
		return cor_address;
	}
	public void setCor_address(String cor_address) {
		this.cor_address = cor_address;
	}
	public String getWork_address() {
		return work_address;
	}
	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	public String getPer_address() {
		return per_address;
	}
	public void setPer_address(String per_address) {
		this.per_address = per_address;
	}
	
	
	
}

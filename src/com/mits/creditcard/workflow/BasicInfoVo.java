package com.mits.creditcard.workflow;

import java.sql.Date;

//this class is acts as a bean class for workflow module
public class BasicInfoVo
{
	String custid;
	String firstName;
	String lastName;
	String middleName;
	String gender;
	Date dateofbirth;
	String pan_no;
	String father_Name;
	String dateofbirth1;
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getFather_Name() {
		return father_Name;
	}
	public void setFather_Name(String father_Name) {
		this.father_Name = father_Name;
	}
	
	
	
	
}

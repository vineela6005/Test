package com.mits.creditcard.workflow;

import java.util.ArrayList;

//this class is acts as a bean class for workflow module
public class ApplicationInfoVo 
{
	BasicInfoVo baiscInfo;
	EmployementInfoVo empInfo;
	OperationalInfoVo operInfo;
	AddressInfoVo addInfo;

	ArrayList<DocumentsVo> objDocuments=null;
	
	public ArrayList<DocumentsVo> getObjDocuments() {
		return objDocuments;
	}
	public void setObjDocuments(ArrayList<DocumentsVo> objDocuments) {
		this.objDocuments = objDocuments;
	}
	public BasicInfoVo getBaiscInfo() {
		return baiscInfo;
	}
	public void setBaiscInfo(BasicInfoVo baiscInfo) {
		this.baiscInfo = baiscInfo;
	}
	public EmployementInfoVo getEmpInfo() {
		return empInfo;
	}
	public void setEmpInfo(EmployementInfoVo empInfo) {
		this.empInfo = empInfo;
	}
	public OperationalInfoVo getOperInfo() {
		return operInfo;
	}
	public void setOperInfo(OperationalInfoVo operInfo) {
		this.operInfo = operInfo;
	}
	public AddressInfoVo getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(AddressInfoVo addInfo) {
		this.addInfo = addInfo;
	}
	
	

}

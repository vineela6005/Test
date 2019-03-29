package com.mits.creditcard.workflow;

//this class stores all the constants used in work flow module
public class WorkFlowConstants
{
	//in insert method
	public static final String INSERTQUERYBASICINFO="insert into CC_BASICCUSTOMERINFO (CUSTID,FIRSTNAME,LASTNAME,MIDDLENAME,GENDER,DATEOFBIRTH,PAN_NO,FATHER_NAME) values(seq_custid.nextval,?,?,?,?,?,?,?)";
	public static final String INSERTQUERYGETCUSTID="select * from CC_BASICCUSTOMERINFO where pan_no=? ";
	public static final String INSERTQUERYEMPINFO="insert into CC_EMPLOYMENTINFO values(?,?,?,?,?,?)";
	public static final String INSERTQUERYOPERINFO="insert into CC_OPERATIONAL_INFO (app_number,custid,app_status,CREATED_USER) values (seq_appno.nextval,?,?,?)";
	public static final String INSERTQUERADDINFO="insert into CC_ADDRESSINFO values(?,?,?,?)";
	public static final String INSERTQUERYOPERINFOGETCUSTID="select * from CC_OPERATIONAL_INFO where custid=?";
	public static final String INSERTQUERYGETDOC="insert into CC_DOCUMENT_TYPE values(?,?,?,?,?,?)";
	
	//in getResentApplications()
	//public static final String
}

package com.mits.creditcard.search;

//this class stores all the constants used in search module
public class SearchConstants 
{
	public static final String QUERYMAIN="SELECT APP_NUMBER,APP_STATUS,CUSTID,LAST_MODIFIED_DATE FROM CC_OPERATIONAL_INFO WHERE 1=1";
	public static final String QUERYGETDOCS="SELECT da.DOCUMENT_TYPE,da.MIME_TYPE,da.FILE_NAME,da.FILE_SIZE,da.DOC_OBJ from CC_OPERATIONAL_INFO op,CC_DOCUMENT_TYPE da WHERE op.APP_NUMBER=da.APP_NUMBER AND op.APP_NUMBER=? AND da.DOCUMENT_TYPE=?";
	public static final String QUERYGETOPERINFO="select * from CC_OPERATIONAL_INFO where APP_NUMBER=?";
	public static final String QUERYBASICINFO="SELECT * FROM CC_BASICCUSTOMERINFO WHERE CUSTID=?";
	public static final String QUERYADDINFO="select * from cc_addressinfo cc where custid=?";
	public static final String QUERYEMPINFO="select * from cc_employmentinfo where custid=?";
}

package com.mits.creditcard.login;

//this class stores all the constants used in login module
public class LoginConstants 
{
	public final static String  QUERYSELECTINLOGIN="select * from CC_TBLUSERMASTE_LO where userid=? and password=?";
	public final static String  QUERYUSERID="select Id from cC_tblUserMaste_lo where UserId=?";
	public final static String  QUERYROLENAME="select ur.Id id,ur.RoleId roleid,rm.RoleName rolename "
			+ "from cc_tblUserRoleMappin_lo ur,cc_tblRoleMaste_lo rm where ur.roleid=rm.roleid and ur.id=";

}

package com.mits.creditcard.login;

import java.util.List;


//this class is acts as a bean class for login module
public class UserVo 
{
	int id;
	String userName;
	String password;
	String email;
	int isActive;
	String userId;
	
	RoleVo role;
	
	List roleBeanList = null;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List getRoleBeanList() {
		return roleBeanList;
	}

	public void setRoleBeanList(List roleBeanList) {
		this.roleBeanList = roleBeanList;
	}

	public UserVo() 
	{
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", isActive=" + isActive + "]";
	}

}

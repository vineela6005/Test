package com.mits.creditcard.login;

import java.util.List;

import org.apache.log4j.Logger;

import com.mits.creditcard.dbconnections.DbConnections;
import com.mits.creditcard.workflow.WorkFlowController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//this class is used for database operations
public class LoginDao {

	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);

	//this method is used for validating the credentials
	public static boolean login(UserVo bean) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String querySelect = null, userId = null, upass = null;
		int id = 0;
		boolean loginStatus = false;

		userId = bean.getUserId();
		upass = bean.getPassword();
		LOGGER.info("User Data : " + userId + "\t" + upass);

		try {
			connection = DbConnections.getConnectionFromDS();
			querySelect = LoginConstants.QUERYSELECTINLOGIN;
			preparedStatement = connection.prepareStatement(querySelect);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, upass);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("id");
				LOGGER.info("id  : " + id);
				loginStatus = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(connection, resultSet, preparedStatement);
		}
		return loginStatus;
	}
    
	//this method returns the rolename of the user from the database to the loginservlet
	public static UserVo getUserDetails(String userId) {
		System.out.println("in getuserdetails start ****....");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query_userid = null;
		String rolename = null;
		int roleid = 0;
		UserVo user = null;

		Statement statement = null;
		ResultSet resultSet_rolename = null;

		try {
			connection = DbConnections.getConnectionFromDS();
			query_userid = LoginConstants.QUERYUSERID;

			LOGGER.info("userid " + userId);
			LOGGER.info(query_userid);
			preparedStatement = connection.prepareStatement(query_userid);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			int uid = 0;
			LOGGER.info("before rs1 next");
			if (resultSet.next()) {
				LOGGER.info("in rs1 next");
				uid = resultSet.getInt("id");

			}

			LOGGER.info("uid from db****" + uid + "***");

			List roleBeanList = new ArrayList();

			if (uid > 0) {

				LOGGER.info("uid > 0 ");

				String query_uid = LoginConstants.QUERYROLENAME + uid;

				statement = connection.createStatement();
				// ps2.setInt(1,uid);
				LOGGER.info("query2 :" + query_uid);
				resultSet_rolename = statement.executeQuery(query_uid);
				LOGGER.info("before rs2 next");
				while (resultSet_rolename.next()) {
					RoleVo obj = new RoleVo();
					obj.setRoleId(resultSet_rolename.getInt("roleid"));
					obj.setRoleName(resultSet_rolename.getString("rolename"));

					roleBeanList.add(obj);
					System.out.println("in rs2 next");
				}
			}

			user = new UserVo();
			user.setRoleBeanList(roleBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(null, resultSet, preparedStatement);
			DbConnections.closeConnection(connection, resultSet_rolename, statement);

		}
		return user;
	}
}

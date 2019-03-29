package com.mits.creditcard.dbconnections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

//This class is used for database connection
public class DbConnections 
{
	//establishing the connection using data source
	public static Connection getConnectionFromDS()
	{
		Connection con=null;
		
		try
		{
			InitialContext context=new InitialContext();
			DataSource ds=(DataSource)context.lookup("mits655jndiname");
			con=ds.getConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	 
	//closing the connection
	public  static void closeConnection(Connection con,ResultSet rs,Statement st)
	{
		try
		{
			if(rs != null)
				rs.close();
			if(st!= null)
				st.close();

			if(con != null)
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

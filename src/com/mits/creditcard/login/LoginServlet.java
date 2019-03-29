package com.mits.creditcard.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mits.creditcard.workflow.WorkFlowController;

//this class is used for validating the credentials of the user who logs in login.jsp form
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);

	public LoginServlet() {
		super();
	}

	//this method called by doPost() which gives the data from login.jsp through request
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("in doget() in loginservlet...");
		boolean loginStatus = false;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String userId = request.getParameter("id");
			String password = request.getParameter("upass");

			UserVo objBean = new UserVo();
			objBean.setUserId(userId);
			objBean.setPassword(password);

			loginStatus = LoginDao.login(objBean);

			LOGGER.info("login status  : " + loginStatus);

			System.out.println(loginStatus);
			String rolename = "";
			if (loginStatus) {
				System.out.println("userid " + userId);
				UserVo objUserBean = LoginDao.getUserDetails(userId);

				ArrayList roleBeanList = (ArrayList) objUserBean.getRoleBeanList();
				if (roleBeanList.size() > 0) {
					RoleVo role = (RoleVo) roleBeanList.get(0);

					rolename = (String) role.getRoleName();
					LOGGER.info("rolename  : " + rolename);
				}

				if (rolename != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("loginSession", objUserBean);

					RequestDispatcher rs = request.getRequestDispatcher("homepage.jsp");
					rs.include(request, response);
				}

				// response.sendRedirect(arg0);
				/*
				 * if("DATAENTRY".equalsIgnoreCase(rolename)) { HttpSession
				 * session=request.getSession(true); session.setAttribute("dataentry", userId);
				 * 
				 * RequestDispatcher rs=request.getRequestDispatcher("homepage.jsp");
				 * rs.include(request, response); } else
				 * if("AUTHORIZER".equalsIgnoreCase(rolename)) { HttpSession
				 * session=request.getSession(true); session.setAttribute("authorizer", userId);
				 * 
				 * RequestDispatcher rs=request.getRequestDispatcher("homepage.jsp");
				 * rs.include(request, response); }
				 */
			} else {
				out.println("Invalid credentials..");
				RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
				rs.include(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	//this method is called by the action attribute in the login.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

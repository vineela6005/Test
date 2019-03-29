package com.mits.creditcard.search;

import java.io.IOException;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mits.creditcard.workflow.DocumentsVo;
import com.mits.creditcard.workflow.WorkFlowController;

//this class is used for searching the user through the values taken in search.jsp  
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchController() {
		super();
	}

	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);
	private static final int BUFFER_SIZE = 20000000;

	// this method takes the values from the search.jsp and returns data to
	// searchuser.jsp through arraylist
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String buttonVal = request.getParameter("buttonVal");

		LOGGER.info("in doget()....buttonVal : " + buttonVal);

		try {
			if ("Search".equalsIgnoreCase(buttonVal)) {
				LOGGER.info("in search block..");

				String app_num = request.getParameter("app_no");
				String custid = request.getParameter("custid");
				String app_status = request.getParameter("app_status");

				String fromDt = request.getParameter("from_date");
				String toDt = request.getParameter("to_date");

				LOGGER.info("fromDate : " + fromDt);
				LOGGER.info("toDate : " + toDt);

				SearchVo searchVo = new SearchVo();
				searchVo.setAppNumber(app_num);
				searchVo.setCustId(custid);
				searchVo.setAppStatus(app_status);
				searchVo.setToDate(fromDt);
				searchVo.setFromDate(toDt);

				SearchDao searchDao = new SearchDao();

				LOGGER.info("vo obj : " + searchVo.toString());

				ArrayList<HashMap<String, String>> searchArrayObj = searchDao.search(searchVo);

				LOGGER.info("back from dao..searchObj : " + searchArrayObj);

				if (searchArrayObj != null) {
					LOGGER.info("in searchArrayObj!=null ");

					request.setAttribute("searchObj", searchArrayObj);
					RequestDispatcher rs = request.getRequestDispatcher("searchuser.jsp");
					rs.include(request, response);
				} else {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();

					out.println("Invalid details...");
					RequestDispatcher rs = request.getRequestDispatcher("search.jsp");
					rs.include(request, response);
				}
			} else if ("searchform".equalsIgnoreCase(buttonVal)) {
				LOGGER.info("in searchfrom block...");

				String appNumber = request.getParameter("appNo");

				SearchDao searchDao = new SearchDao();
				ArrayList<HashMap<String, String>> searchFromArrayObj = searchDao.getAllDetails(appNumber);

				LOGGER.info("searchFromArrayObj before :" + searchFromArrayObj);

				if (searchFromArrayObj != null) {
					LOGGER.info("searchFromArrayObj after :" + searchFromArrayObj);

					request.setAttribute("searchFromArrayObj", searchFromArrayObj);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchform.jsp");
					requestDispatcher.include(request, response);
				}
			} else if (("app_form".equalsIgnoreCase(buttonVal)) || "id_proof".equalsIgnoreCase(buttonVal)
					|| "sdocs".equalsIgnoreCase(buttonVal)) {
				LOGGER.info("in documents download block..buttonVal..." + buttonVal);

				InputStream objectDocumentInputStream = null;
				ServletOutputStream outputStream = null;

				String applicationNumber = request.getParameter("app_no");
				String documentType = request.getParameter("form");

				LOGGER.info("app no in servlet : " + applicationNumber);
				LOGGER.info("doc type in servlet : " + documentType);

				DocumentsVo objDocumentsFromDB = SearchDao.getDocument(applicationNumber, documentType);

				LOGGER.info("back to servlet...objFromDB : " + objDocumentsFromDB);

				if (objDocumentsFromDB != null) {
					LOGGER.info("objDocumentsFromDB!=null");
					String documentTypeFromDB = objDocumentsFromDB.getDocumentType();
					String mimeType = objDocumentsFromDB.getMimeType();
					String fileName = objDocumentsFromDB.getFileName();
					Integer fileSize = (int) (long) objDocumentsFromDB.getFileSize();
					objectDocumentInputStream = objDocumentsFromDB.getDocumentInputStramObject();

					String headerKey = "Content-Disposition";
					String extension = "";

					if (mimeType.endsWith("image/jpeg")) {
						extension = ".jpg";
					} else if (mimeType.endsWith("application/pdf")) {
						extension = ".pdf";
					}
					else if (mimeType.endsWith("image/png")) {
						extension = ".png";
					}

					String headerValue = String.format("attachment; filename=\"%s\"", fileName + extension);

					response.setContentType(mimeType);
					response.setContentLength(fileSize);
					response.setHeader(headerKey, headerValue);

					outputStream = response.getOutputStream();

					byte[] buffer = new byte[BUFFER_SIZE];
					int bytesRead = -1;

					while ((bytesRead = objectDocumentInputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}

					if (outputStream != null) {
						outputStream.close();
					}
					if (objectDocumentInputStream != null) {
						objectDocumentInputStream.close();
					}
				}
				else
				{
					response.setContentType("text/html");
					PrintWriter printWriter=response.getWriter();
					
					printWriter.write("not downloaded..please try again..");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	// this method is called in search.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

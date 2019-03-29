package com.mits.creditcard.workflow;

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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

//this class is used for all the operations in workflow module
public class WorkFlowController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public WorkFlowController() {
		super();
	}

	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);
	private static final int BUFFER_SIZE = 20000000;

	/*
	 *
	 * calls the rework.jsp by giving the data through hashmap using rework() in
	 * workflowdao cals the verifyUser.jsp by giving the data through hashmap using
	 * retriveRecord() in workflowdao cals the reworkUser.jsp by giving the data
	 * through hashmap returned by WorkFlowDao.reworkUser(appNo);
	 */
	// this method is been called for all the operations in the workflowcontroller
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// HttpSession session = request.getSession(false);
		String buttonValue = request.getParameter("buttonVal");

		LOGGER.info("in controller.....reworkArrayObject..." + buttonValue);

		response.setContentType("text/html");
		// calls the verify.jsp by giving the data through hashmap using verify() in
		// workflowdao

		try {
			if ("Verify".equalsIgnoreCase(buttonValue)) {

				LOGGER.info("in verify servlet");

				ArrayList<HashMap<String, String>> objArray = WorkFlowDao.verify();

				LOGGER.info("objArray : " + objArray);

				request.setAttribute("arrayObject", objArray);
				RequestDispatcher rs = request.getRequestDispatcher("verify.jsp");
				rs.forward(request, response);
			}

			// this block takes the data from the addapplication.jsp and calls
			// insertApplication() in workflowdao
			else if ("Add".equalsIgnoreCase(buttonValue)) {
				LOGGER.info("in add servlet..");
				PrintWriter out = response.getWriter();
				try {

					LOGGER.info("in doGet()....started....");

					LOGGER.info("in add application block......." + buttonValue);

					ApplicationInfoVo appInfo = null;

					BasicInfoVo basicInfo = null;
					EmployementInfoVo empInfo = null;
					AddressInfoVo addInfo = null;
					OperationalInfoVo opInfo = null;
					DocumentsVo objDocuments = null;

					boolean status = false;
					String pan = null;
					String climit = null;

					ArrayList<DocumentsVo> documents = new ArrayList<DocumentsVo>();

					Part partAppForm = request.getPart("applicationform");

					if (partAppForm != null) {
						String applicationform = "applicationform";
						String filename = partAppForm.getName();
						long size = partAppForm.getSize();
						String contentType = partAppForm.getContentType();
						InputStream is = partAppForm.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}
					Part partIdProof = request.getPart("idproof");
					if (partIdProof != null) {
						String applicationform = "idproof";
						String filename = partIdProof.getName();
						long size = partIdProof.getSize();
						String contentType = partIdProof.getContentType();
						InputStream is = partIdProof.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}
					Part partSupportingdoc = request.getPart("sdoc");
					if (partSupportingdoc != null) {
						String applicationform = "sdoc";
						String filename = partSupportingdoc.getName();
						long size = partSupportingdoc.getSize();
						String contentType = partSupportingdoc.getContentType();
						InputStream is = partSupportingdoc.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}

					basicInfo = new BasicInfoVo();
					basicInfo.setFirstName(request.getParameter("fname"));
					basicInfo.setLastName(request.getParameter("lname"));
					basicInfo.setMiddleName(request.getParameter("mname"));
					basicInfo.setGender(request.getParameter("gender"));

					String date = request.getParameter("dob");
					LOGGER.info("date in retrived format : " + date);
					java.sql.Date sqlDate = java.sql.Date.valueOf(date);

					LOGGER.info("date in sql format : " + sqlDate);
					basicInfo.setDateofbirth(sqlDate);
					pan = request.getParameter("pan");
					basicInfo.setPan_no(pan);
					basicInfo.setFather_Name(request.getParameter("fat"));

					empInfo = new EmployementInfoVo();
					empInfo.setDesignation(request.getParameter("des"));
					empInfo.setAnnual_income(Integer.parseInt(request.getParameter("income")));
					empInfo.setEmployment_type(request.getParameter("emptype"));
					empInfo.setExperience(request.getParameter("exp"));

					addInfo = new AddressInfoVo();
					addInfo.setCor_address(request.getParameter("caddress"));
					addInfo.setWork_address(request.getParameter("waddress"));
					addInfo.setPer_address(request.getParameter("paddress"));

					LOGGER.info("addinfo obj in workflowcontroller: " + addInfo);

					opInfo = new OperationalInfoVo();
					opInfo.setApp_status("new");
					opInfo.setCreated_user(request.getParameter("roleName"));
					opInfo.setCard_details(request.getParameter("cardetails"));
					opInfo.setCard_type(request.getParameter("cardtype"));
					climit = request.getParameter("climit");

					appInfo = new ApplicationInfoVo();
					appInfo.setBaiscInfo(basicInfo);
					appInfo.setEmpInfo(empInfo);
					appInfo.setAddInfo(addInfo);
					appInfo.setOperInfo(opInfo);
					appInfo.setObjDocuments(documents);

					LOGGER.info("before appinfo!=null");
					if (appInfo != null) {
						LOGGER.info("after appindo!=null");

						status = WorkFlowDao.insertApplication(appInfo);
						if (status) {
							LOGGER.info("Status : " + status);

							out.println("inserted..");
						} else {
							LOGGER.info("Status : " + status);

							RequestDispatcher rs = request.getRequestDispatcher("");
							rs.include(request, response);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}

			/*
			 * takes the application number from verify.jsp and gets the record through
			 * hashmap from workflowdao and redirects it to verifyuser.jsp
			 */
			else if ("verify_user".equalsIgnoreCase(buttonValue)) {
				String app_num = request.getParameter("app-no");
				LOGGER.info("in verify_user()");
				ArrayList<HashMap<String, String>> retriveRecord = WorkFlowDao.retriveRecord(app_num);

				LOGGER.info("Retrived obj : " + retriveRecord);
				request.setAttribute("objArray", retriveRecord);
				RequestDispatcher rs = request.getRequestDispatcher("verifyuser.jsp");
				rs.include(request, response);
			}

			/*
			 * buttonval is taken from verifyuser.jsp and updates the status to rework
			 */
			else if ("rework".equalsIgnoreCase(buttonValue)) {
				PrintWriter out = response.getWriter();

				String custid = request.getParameter("custid");
				LOGGER.info("in rework block..custid:" + custid);

				OperationalInfoVo objOper = new OperationalInfoVo();
				objOper.setCard_type(request.getParameter("card_type"));
				objOper.setCredit_rating(request.getParameter("credit_risk"));
				objOper.setKyc_check(request.getParameter("kyc_check"));
				objOper.setCard_details(request.getParameter("card_details"));
				String c_limit = request.getParameter("credit_limit");
				LOGGER.info("c_limit : " + c_limit);
				LOGGER.info("card_type : " + request.getParameter("card_type"));
				LOGGER.info("credit_risk : " + request.getParameter("credit_risk"));
				LOGGER.info("kyc_check : " + request.getParameter("kyc_check"));
				LOGGER.info("card_details : " + request.getParameter("card_details"));
				LOGGER.info("c_limit : " + c_limit);

				int climit = Integer.parseInt(c_limit);
				LOGGER.info("c_limit integer parse: " + c_limit);
				objOper.setCredit_limit(climit);

				boolean reworkStatus = WorkFlowDao.reworkStatus(objOper, custid);
				LOGGER.info("back to rework from dao..status before if block: " + reworkStatus);

				if (reworkStatus) {
					LOGGER.info("in if block..." + reworkStatus);
					out.println("status changed to rework..");

				} else {
					LOGGER.info("in else block..." + reworkStatus);
					out.println("status not changed to rework..");

				}

				/*
				 * buttonval is taken from verifyuser.jsp takes the data of operationalVo
				 * through request and sets the object and updates the status to rework and
				 * operationalinfo table in database
				 */
			} else if ("approve".equalsIgnoreCase(buttonValue)) {
				PrintWriter out = response.getWriter();

				String custid = request.getParameter("custid");
				String cardType = request.getParameter("card_type");
				String kycCheck = request.getParameter("kyc_check");
				String creditLimit = request.getParameter("credit_limit");
				String creditRisk = request.getParameter("credit_risk");
				String cardDetails = request.getParameter("card_details");

				LOGGER.info("operational info from verifyuser.jsp ");
				LOGGER.info("custid : " + custid);
				LOGGER.info("cardType : " + cardType);
				LOGGER.info("kycCheck : " + kycCheck);
				LOGGER.info("creditLimit : " + creditLimit);
				LOGGER.info("creditRisk : " + creditRisk);
				LOGGER.info("cardDetails : " + cardDetails);

				OperationalInfoVo operationalInfoVo = new OperationalInfoVo();
				operationalInfoVo.setCustid(custid);
				operationalInfoVo.setCard_type(cardType);
				operationalInfoVo.setKyc_check(kycCheck);
				operationalInfoVo.setCredit_limit(Integer.parseInt(creditLimit));
				operationalInfoVo.setCredit_rating(creditRisk);
				operationalInfoVo.setCard_details(cardDetails);

				LOGGER.info("in approve block..custid:" + operationalInfoVo);
				boolean status = WorkFlowDao.approveStatus(operationalInfoVo);
				LOGGER.info("back to approve from dao..status before if block: " + status);
				if (status) {
					LOGGER.info("in if block..." + status);
					out.println("Approved");
				} else {
					LOGGER.info("in else block..." + status);
					out.println("Not Approved..please try again");
				}

				/*
				 * buttonval is taken from verifyuser.jsp and updates the status to reject in
				 * operationalinfo table in database
				 */
			} else if ("reject".equalsIgnoreCase(buttonValue)) {
				PrintWriter out = response.getWriter();

				String custid = request.getParameter("custid");
				LOGGER.info("in reject block..custid:" + custid);
				boolean status = WorkFlowDao.rejectStatus(custid);
				LOGGER.info("back to rework from dao..status before if block: " + status);
				if (status) {
					LOGGER.info("in if block..." + status);
					out.println("Rejected");
				} else {
					LOGGER.info("in else block..." + status);
					out.println("Not Rejected..please try again");
				}

				/*
				 * buttonval is taken from verifyuser.jsp takes the application no and doc type
				 * through request and calls the dao method for getting the document from db and
				 * for downloading the document
				 */
			} else if (("app_form".equalsIgnoreCase(buttonValue)) || "id_proof".equalsIgnoreCase(buttonValue)
					|| "sdocs".equalsIgnoreCase(buttonValue)) {
				LOGGER.info("in documents download block..buttonVal..." + buttonValue);

				InputStream objectDocumentInputStream = null;
				ServletOutputStream outputStream = null;

				String applicationNumber = request.getParameter("app_no");
				String documentType = request.getParameter("form");

				LOGGER.info("app no in servlet : " + applicationNumber);
				LOGGER.info("doc type in servlet : " + documentType);

				DocumentsVo objDocumentsFromDB = WorkFlowDao.getDocument(applicationNumber, documentType);

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

				/*
				 * buttonval is taken from rework.jsp returns all the records with status
				 * 'rework'
				 */
			} else if ("ReworkLink".equalsIgnoreCase(buttonValue)) {
				LOGGER.info("in ReworkLink block in servlet");

				ArrayList<HashMap<String, String>> objArray = WorkFlowDao.reworkLink();

				LOGGER.info("objArray in servlet : " + objArray);

				request.setAttribute("reworkArrayObject", objArray);
				RequestDispatcher rs = request.getRequestDispatcher("rework.jsp");
				rs.forward(request, response);

				/*
				 * buttonval is taken from reworkuser.jsp takes the application number for
				 * getting the whole record from database and redirects to reworkuser.jsp
				 */
			} else if ("rework_user".equalsIgnoreCase(buttonValue)) {
				String appNo = request.getParameter("app-no");
				LOGGER.info("in rework_user block...");
				ArrayList<HashMap<String, String>> retriveRecord = WorkFlowDao.reworkUser(appNo);

				LOGGER.info("Retrived obj : " + retriveRecord);
				request.setAttribute("objArray", retriveRecord);
				RequestDispatcher rs = request.getRequestDispatcher("reworkuser.jsp");
				rs.include(request, response);

				/*
				 * buttonval is taken from reworkuser.jsp and updates the opearationalinfo table
				 * in dba
				 */
			} else if ("Submit".equalsIgnoreCase(buttonValue)) {

				// out.println("in rework_user...in workflowcontroller....");
				LOGGER.info("in submit block in servlet..");
				PrintWriter out = response.getWriter();
				try {

					LOGGER.info("in doGet()....started....");

					LOGGER.info("in add application block......." + buttonValue);

					ApplicationInfoVo appInfo = null;

					BasicInfoVo basicInfo = null;
					EmployementInfoVo empInfo = null;
					AddressInfoVo addInfo = null;
					OperationalInfoVo opInfo = null;
					DocumentsVo objDocuments = null;

					boolean status = false;
					String pan = null;
					String climit = null;

					ArrayList<DocumentsVo> documents = new ArrayList<DocumentsVo>();

					Part partAppForm = request.getPart("applicationform");

					if (partAppForm != null) {
						String applicationform = "applicationform";
						String filename = partAppForm.getName();
						long size = partAppForm.getSize();
						String contentType = partAppForm.getContentType();
						InputStream is = partAppForm.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}
					Part partIdProof = request.getPart("idproof");
					if (partIdProof != null) {
						String applicationform = "idproof";
						String filename = partIdProof.getName();
						long size = partIdProof.getSize();
						String contentType = partIdProof.getContentType();
						InputStream is = partIdProof.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}
					Part partSupportingdoc = request.getPart("sdoc");
					if (partSupportingdoc != null) {
						String applicationform = "sdoc";
						String filename = partSupportingdoc.getName();
						long size = partSupportingdoc.getSize();
						String contentType = partSupportingdoc.getContentType();
						InputStream is = partSupportingdoc.getInputStream();

						objDocuments = new DocumentsVo();
						objDocuments.setFileName(filename);
						objDocuments.setFileSize(size);
						objDocuments.setDocumentType(applicationform);
						objDocuments.setMimeType(contentType);
						objDocuments.setDocumentInputStramObject(is);
						documents.add(objDocuments);
					}

					basicInfo = new BasicInfoVo();
					basicInfo.setCustid(request.getParameter("custid"));
					basicInfo.setFirstName(request.getParameter("fname"));
					basicInfo.setLastName(request.getParameter("lname"));
					basicInfo.setMiddleName(request.getParameter("mname"));
					basicInfo.setGender(request.getParameter("gender"));

					String date = request.getParameter("dob");
					LOGGER.info("date in retrived format : " + date);
					java.sql.Date sqlDate = java.sql.Date.valueOf(date);

					LOGGER.info("date in sql format : " + sqlDate);
					basicInfo.setDateofbirth(sqlDate);
					pan = request.getParameter("pan");
					basicInfo.setPan_no(pan);
					basicInfo.setFather_Name(request.getParameter("fat"));

					empInfo = new EmployementInfoVo();
					empInfo.setDesignation(request.getParameter("des"));
					LOGGER.info("Income : " + Integer.parseInt(request.getParameter("income")));
					empInfo.setAnnual_income(Integer.parseInt(request.getParameter("income")));
					empInfo.setEmployment_type(request.getParameter("emptype"));
					empInfo.setExperience(request.getParameter("exp"));

					addInfo = new AddressInfoVo();
					addInfo.setCor_address(request.getParameter("caddress"));
					addInfo.setWork_address(request.getParameter("waddress"));
					addInfo.setPer_address(request.getParameter("paddress"));

					opInfo = new OperationalInfoVo();
					opInfo.setApp_status("new");

					appInfo = new ApplicationInfoVo();
					appInfo.setBaiscInfo(basicInfo);
					appInfo.setEmpInfo(empInfo);
					appInfo.setAddInfo(addInfo);
					appInfo.setOperInfo(opInfo);
					appInfo.setObjDocuments(documents);

					LOGGER.info("before appinfo!=null");
					if (appInfo != null) {
						LOGGER.info("after appindo!=null");

						status = WorkFlowDao.updateApplication(appInfo);
						if (status) {
							LOGGER.info("Status : " + status);

							out.println("inserted..");
						} else {
							LOGGER.info("Status : " + status);

							RequestDispatcher rs1 = request.getRequestDispatcher("");
							rs1.include(request, response);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

			}
			/*
			 * this block executes when the any of the button value does not match..
			 */
			else {
				PrintWriter out = response.getWriter();

				out.println("in else block...in workflowcontroller....");
				RequestDispatcher rs = request.getRequestDispatcher("homepage.jsp");
				rs.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	// this is called from the all the .jsp's,link's of the workflowmodule
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
	}

}

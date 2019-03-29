package com.mits.creditcard.workflow;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mits.creditcard.dbconnections.DbConnections;

//this dao class is used by the workflowcontroller for all the database operations.
public class WorkFlowDao {
	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);

	// used for inserting the data taken in addapplication.jsp
	public static boolean insertApplication(ApplicationInfoVo info) {
		LOGGER.info("insert()...started...");

		Connection con = null;

		PreparedStatement psOperInfo = null, psBasicInfo = null, psEmpInfo = null, psAddInfo = null;
		ResultSet rsOperInfo = null;
		String queryOperInfo = null, queryBasicInfo = null, queryEmpInfo = null, queryAddInfo = null;

		String querygetcustid = null;

		int countBasicInfo = 0;
		boolean status = false;
		String custid = null;

		BasicInfoVo baiscInfo = info.getBaiscInfo();
		EmployementInfoVo empInfo = info.getEmpInfo();
		AddressInfoVo addInfo = info.getAddInfo();
		OperationalInfoVo operInfo = info.getOperInfo();

		con = DbConnections.getConnectionFromDS();

		try {
			con.setAutoCommit(false);

			queryBasicInfo = WorkFlowConstants.INSERTQUERYBASICINFO;
			LOGGER.info("basic query : " + queryBasicInfo);
			psBasicInfo = con.prepareStatement(queryBasicInfo);
			psBasicInfo.setString(1, baiscInfo.getFirstName());
			psBasicInfo.setString(2, baiscInfo.getLastName());
			psBasicInfo.setString(3, baiscInfo.getMiddleName());
			psBasicInfo.setString(4, baiscInfo.getGender());
			psBasicInfo.setDate(5, baiscInfo.getDateofbirth());
			LOGGER.info("Date inserted : " + baiscInfo.getDateofbirth());
			psBasicInfo.setString(6, baiscInfo.getPan_no());
			psBasicInfo.setString(7, baiscInfo.getFather_Name());
			countBasicInfo = psBasicInfo.executeUpdate();
			System.out.println("basic info count : " + countBasicInfo);

			querygetcustid = WorkFlowConstants.INSERTQUERYGETCUSTID;
			LOGGER.info("select query in count>0 : " + querygetcustid);
			PreparedStatement preparedStatement = con.prepareStatement(querygetcustid);
			preparedStatement.setString(1, baiscInfo.getPan_no());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				custid = resultSet.getString("custid");
				System.out.println("custid : " + custid);
			}

			queryEmpInfo = WorkFlowConstants.INSERTQUERYEMPINFO;
			LOGGER.info("emp query : " + queryEmpInfo);
			psEmpInfo = con.prepareStatement(queryEmpInfo);
			psEmpInfo.setString(1, custid);
			psEmpInfo.setString(2, empInfo.getEmployment_type());
			psEmpInfo.setString(3, empInfo.getEmployer_name());
			psEmpInfo.setString(4, empInfo.getExperience());
			psEmpInfo.setString(5, empInfo.getDesignation());
			psEmpInfo.setInt(6, empInfo.getAnnual_income());
			int countEmpInfo = psEmpInfo.executeUpdate();
			LOGGER.info("emp info count : " + countEmpInfo);

			String status1 = "new";

			String queryOperInfo1 = WorkFlowConstants.INSERTQUERYOPERINFO;
			LOGGER.info("op query : " + queryOperInfo1);
			PreparedStatement psOperInfo1 = con.prepareStatement(queryOperInfo1);
			psOperInfo1.setString(1, custid);
			psOperInfo1.setString(2, status1);
			psOperInfo1.setString(3, operInfo.getCreated_user());
			/*
			 * ps.setString(3, operInfo.getCard_type()); ps.setString(4,
			 * operInfo.getKyc_check()); ps.setString(5, operInfo.getCard_details());
			 * ps.setInt(6, operInfo.getCredit_limit()); ps.setString(7,
			 * operInfo.getApp_status()); ps.setDate(8, operInfo.getCreated_date());
			 * ps.setString(9, operInfo.getCreated_user()); ps.setString(10,
			 * operInfo.getLast_modified_user()); ps.setDate(11,
			 * operInfo.getLast_modified_date());
			 */
			int countOperaInfo = psOperInfo1.executeUpdate();
			LOGGER.info("operational info count : " + countOperaInfo);

			queryAddInfo = WorkFlowConstants.INSERTQUERADDINFO;
			LOGGER.info("add query : " + queryAddInfo);
			psAddInfo = con.prepareStatement(queryAddInfo);
			psAddInfo.setString(1, custid);
			psAddInfo.setString(2, addInfo.getCor_address());
			psAddInfo.setString(3, addInfo.getWork_address());
			psAddInfo.setString(4, addInfo.getPer_address());
			int countAddInfo = psAddInfo.executeUpdate();
			LOGGER.info("add info count : " + countAddInfo);

			String queryOperInfo2 = WorkFlowConstants.INSERTQUERYOPERINFOGETCUSTID;
			PreparedStatement psOperInfo2 = con.prepareStatement(queryOperInfo2);
			psOperInfo2.setString(1, custid);
			// ps4.setString(1, custid);
			rsOperInfo = psOperInfo2.executeQuery();
			String app_no = null;
			while (rsOperInfo.next()) {
				app_no = rsOperInfo.getString("app_number");
				LOGGER.info("App no : " + app_no);
			}

			ArrayList<DocumentsVo> doc = info.getObjDocuments();
			for (DocumentsVo objDocs : doc) {
				String queryDocInfo = WorkFlowConstants.INSERTQUERYGETDOC;
				PreparedStatement psDocInfo = con.prepareStatement(queryDocInfo);
				psDocInfo.setString(1, app_no);
				psDocInfo.setString(2, objDocs.getDocumentType());
				psDocInfo.setString(3, objDocs.getMimeType());
				psDocInfo.setString(4, objDocs.getFileName());
				psDocInfo.setDouble(5, objDocs.getFileSize());
				psDocInfo.setBlob(6, objDocs.getDocumentInputStramObject());
				psDocInfo.executeUpdate();
			}

			status = true;

			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(null, rsOperInfo, psAddInfo);
			DbConnections.closeConnection(null, null, psBasicInfo);
			DbConnections.closeConnection(null, null, psEmpInfo);
			DbConnections.closeConnection(con, null, psOperInfo);
		}
		return status;
	}

	// used to send data of records with'rework' status to the rework.jsp
	public static ApplicationInfoVo getResentApplications() {
		Connection con = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		ResultSet rs = null, rs1 = null, rs2 = null;
		String query = null, query1 = null, query2 = null;

		String custid = null;
		String firstName = null;
		String lastName = null;
		String middleName = null;
		String gender = null;
		String dateofbirth = null;
		int pan_no = 0;
		String father_Name = null;
		// String dateofbirth1;

		String employment_type = null;
		String employer_name = null;
		String experience = null;
		String designation = null;
		int annual_income = 0;

		String cor_address = null;
		String work_address = null;
		String per_address = null;

		String card_type = null;
		String card_details = null;
		int credit_limit = 0;

		BasicInfoVo baiscInfo = null;
		AddressInfoVo addInfo = null;
		EmployementInfoVo empInfo = null;
		OperationalInfoVo operInfo = null;
		ApplicationInfoVo apInfo = null;

		con = DbConnections.getConnectionFromDS();

		try {

			query = "select * from CC_OPERATIONAL_INFO where APP_STATUS='rework'";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				custid = rs.getString("custid");
				firstName = rs.getString("firstname");
				lastName = rs.getString("lastname");
				middleName = rs.getString("middlename");
				gender = rs.getString("gender");
				dateofbirth = rs.getString("dateofbirth");
				pan_no = rs.getInt("pan_no");
				father_Name = rs.getString("father_name");
			}

			query = "select * from CC_EMPLOYMENTINFO where custid=?";
			ps = con.prepareStatement(query);
			ps.setString(1, custid);
			rs = ps.executeQuery();
			while (rs.next()) {
				employment_type = rs.getString("employment_type");
				experience = rs.getString("experience");
				designation = rs.getString("designation");
				annual_income = rs.getInt("annual_income");
			}

			query = "select * from CC_addressINFO where custid=?";
			ps = con.prepareStatement(query);
			ps.setString(1, custid);
			rs = ps.executeQuery();
			while (rs.next()) {
				cor_address = rs.getString("cor_address");
				work_address = rs.getString("work_address");
				per_address = rs.getString("per_address");
			}

			query = "select * from CC_OPERATIONAL_INFO where custid=?";
			ps = con.prepareStatement(query);
			ps.setString(1, custid);
			rs = ps.executeQuery();
			while (rs.next()) {
				card_type = rs.getString("card_type");
				card_details = rs.getString("card_details");
				credit_limit = rs.getInt("credit_limit");
			}

			baiscInfo = new BasicInfoVo();
			baiscInfo.setCustid(custid);
			baiscInfo.setFather_Name(father_Name);
			baiscInfo.setFirstName(firstName);
			baiscInfo.setMiddleName(middleName);
			baiscInfo.setLastName(lastName);
			baiscInfo.setGender(gender);
			// baiscInfo.setDateofbirth(dateofbirth);
			// baiscInfo.setPan_no(pan_no);

			addInfo = new AddressInfoVo();
			addInfo.setCor_address(cor_address);
			addInfo.setWork_address(work_address);
			addInfo.setPer_address(per_address);
			addInfo.setCustid(custid);

			empInfo = new EmployementInfoVo();
			empInfo.setAnnual_income(annual_income);
			empInfo.setCustid(custid);
			empInfo.setDesignation(designation);
			empInfo.setEmployer_name(employer_name);
			empInfo.setEmployment_type(employment_type);
			empInfo.setExperience(experience);

			operInfo = new OperationalInfoVo();
			operInfo.setCard_details(card_details);
			operInfo.setCard_type(card_type);
			operInfo.setCredit_limit(credit_limit);
			operInfo.setCustid(custid);

			apInfo = new ApplicationInfoVo();
			apInfo.setAddInfo(addInfo);
			apInfo.setBaiscInfo(baiscInfo);
			apInfo.setEmpInfo(empInfo);
			apInfo.setOperInfo(operInfo);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(con, rs, ps);
		}

		return apInfo;
	}

	public static ArrayList<java.util.HashMap<String, String>> verify() {
		ArrayList<java.util.HashMap<String, String>> objArray = null;

		try {
			LOGGER.info("in verify().............");

			Connection con = null;
			PreparedStatement psOperInfo = null;
			ResultSet rsOperInfo = null;
			String queryOperInfo = null;

			String app_no = null;
			String custid = null;
			String status = null;
			Date lastModifiedDate = null;
			String modDate = null;
			String firstName = null, lastName = null, pan_no = null;

			java.util.HashMap<String, String> map = null;

			objArray = new ArrayList<java.util.HashMap<String, String>>();
			con = DbConnections.getConnectionFromDS();

			try {

				queryOperInfo = "SELECT op.APP_NUMBER,op.APP_STATUS,op.CUSTID,ba.FIRSTNAME,ba.LASTNAME,ba.PAN_NO,op.LAST_MODIFIED_DATE\r\n"
						+ "FROM CC_OPERATIONAL_INFO op,\r\n" + "  CC_BASICCUSTOMERINFO ba\r\n"
						+ "WHERE op.CUSTID=ba.CUSTID\r\n" + "AND (op.APP_STATUS='new' or op.APP_STATUS='verify')";
				System.out.println("basic query : " + queryOperInfo);
				psOperInfo = con.prepareStatement(queryOperInfo);
				rsOperInfo = psOperInfo.executeQuery();
				while (rsOperInfo.next()) {

					map = new java.util.HashMap<String, String>();

					app_no = rsOperInfo.getString("app_number");
					custid = rsOperInfo.getString("custid");
					status = rsOperInfo.getString("app_status");
					firstName = rsOperInfo.getString("FIRSTNAME");
					lastName = rsOperInfo.getString("LASTNAME");
					pan_no = rsOperInfo.getString("PAN_NO");
					lastModifiedDate = rsOperInfo.getDate("LAST_MODIFIED_DATE");
					modDate = " " + lastModifiedDate;
					map.put("app_no", app_no);
					map.put("custid", custid);
					map.put("app_status", status);
					map.put("last_modifiedDate", modDate);
					map.put("firstname", firstName);
					map.put("lastname", lastName);
					map.put("pan_no", pan_no);

					objArray.add(map);

					LOGGER.info("array" + objArray);

				}

			} catch (Exception e) {
				e.printStackTrace();

				// TODO: handle exception
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		LOGGER.info("array" + objArray);
		return objArray;
	}

	public static ArrayList<java.util.HashMap<String, String>> retriveRecord(String app_no) {

		LOGGER.info("in retrive record()....." + app_no);
		Connection con = null;
		PreparedStatement psOperInfo = null, psBasicInfo = null, psEmpInfo = null, psAddInfo = null;
		ResultSet rsOperInfo = null, rsBasicInfo = null, rsEmpInfo = null, rsAddInfo = null;
		String queryOperInfo = null, queryBasicInfo = null, queryEmpInfo = null, queryAddInfo = null;

		String custid = null;
		/*
		 * String status = null; Date lastModifiedDate = null; String modDate = null;
		 */

		ArrayList<java.util.HashMap<String, String>> objArray = null;
		java.util.HashMap<String, String> map = null;

		map = new java.util.HashMap<String, String>();
		objArray = new ArrayList<java.util.HashMap<String, String>>();

		con = DbConnections.getConnectionFromDS();
		try {
			LOGGER.info("in try block.......");
			con.setAutoCommit(false);

			queryOperInfo = "select * from CC_OPERATIONAL_INFO where APP_NUMBER=?";
			System.out.println("basic query : " + queryOperInfo);
			psOperInfo = con.prepareStatement(queryOperInfo);
			psOperInfo.setString(1, app_no);
			rsOperInfo = psOperInfo.executeQuery();
			while (rsOperInfo.next()) {
				custid = rsOperInfo.getString("custid");
				LOGGER.info("custid   :" + custid);
				map.put("custid", custid);
				map.put("app_no", app_no);
			}

			String dob = null;

			queryBasicInfo = "SELECT * FROM CC_BASICCUSTOMERINFO WHERE CUSTID=?";
			psBasicInfo = con.prepareStatement(queryBasicInfo);
			psBasicInfo.setString(1, custid);
			rsBasicInfo = psBasicInfo.executeQuery();
			while (rsBasicInfo.next()) {
				map.put("firstname", rsBasicInfo.getString("firstname"));
				map.put("lastname", rsBasicInfo.getString("lastname"));
				map.put("middlename", rsBasicInfo.getString("middlename"));
				map.put("gender", rsBasicInfo.getString("gender"));
				map.put("pan_no", rsBasicInfo.getString("pan_no"));
				map.put("fathername", rsBasicInfo.getString("father_name"));
				dob = rsBasicInfo.getString("dateofbirth");
				map.put("dob", dob);

				LOGGER.info("basic info........." + map);
				objArray.add(map);
			}

			queryAddInfo = "select * from cc_addressinfo cc where custid=?";
			psAddInfo = con.prepareStatement(queryAddInfo);
			psAddInfo.setString(1, custid);
			rsAddInfo = psAddInfo.executeQuery();
			while (rsAddInfo.next()) {
				map.put("cor_address", rsAddInfo.getString("cor_address"));
				map.put("work_address", rsAddInfo.getString("work_address"));
				map.put("per_address", rsAddInfo.getString("per_address"));

				LOGGER.info("address info........." + map);
				objArray.add(map);
			}

			String annual_income_string = null;
			long annual_income = 0;

			queryEmpInfo = "select * from cc_employmentinfo where custid=?";
			psEmpInfo = con.prepareStatement(queryEmpInfo);
			psEmpInfo.setString(1, custid);
			rsEmpInfo = psEmpInfo.executeQuery();
			while (rsEmpInfo.next()) {
				map.put("employment_type", rsEmpInfo.getString("employment_type"));
				map.put("experience", rsEmpInfo.getString("experience"));
				map.put("designation", rsEmpInfo.getString("designation"));
				annual_income = rsEmpInfo.getLong("annual_income");
				annual_income_string = " " + annual_income;
				map.put("annual_income", annual_income_string);

				LOGGER.info("employment info........." + map);
				objArray.add(map);
			}
			/*
			 * InputStream documentInputStramObject=null;
			 * 
			 * queryDocType =
			 * "select * from cc_document_type where app_number=? and filename=?"; psDocType
			 * = con.prepareStatement(queryDocType); psDocType.setString(1, app_no);
			 * psDocType.setString(2, "applicationform"); rsDocType =
			 * psDocType.executeQuery(); while (rsDocType.next()) {
			 * rsDocType.getBlob("DOC_OBJ"); }
			 */
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}

		LOGGER.info("obj array  : " + objArray);
		return objArray;

	}

	public static boolean approveStatus(OperationalInfoVo operationalInfoVo) {
		boolean status = false;
		LOGGER.info("approvestatus()...started...operationalInfoVo : " + operationalInfoVo);

		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;
		String query = null;
		String app_status = "approved";

		con = DbConnections.getConnectionFromDS();

		LOGGER.info("111");
		try {
			query = "update CC_OPERATIONAL_INFO SET APP_STATUS=?,CREDIT_RATING=?,CARD_TYPE=?,"
					+ "KYC_CHECK=?,CARD_DETAILS=?,CREDIT_LIMIT=? where CUSTID=?";
			ps = con.prepareStatement(query);
			LOGGER.info("222");
			ps.setString(1, app_status);
			ps.setString(2, operationalInfoVo.getCredit_rating());
			ps.setString(3, operationalInfoVo.getCard_type());
			ps.setString(4, operationalInfoVo.getKyc_check());
			ps.setString(5, operationalInfoVo.getCard_details());
			ps.setInt(6, operationalInfoVo.getCredit_limit());
			ps.setString(7, operationalInfoVo.getCustid());
			LOGGER.info("333");
			count = ps.executeUpdate();
			LOGGER.info("444");
			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: handle exception
		return status;
	}

	public static boolean rejectStatus(String custid) {
		boolean status = false;
		LOGGER.info("rejectSstatus()...started...");

		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;
		String query = null;
		String app_status = "rejected";

		con = DbConnections.getConnectionFromDS();

		try {
			con.setAutoCommit(false);

			query = "update CC_OPERATIONAL_INFO SET APP_STATUS=? where CUSTID=?";
			ps = con.prepareStatement(query);
			ps.setString(1, app_status);
			ps.setString(2, custid);
			count = ps.executeUpdate();
			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.getAutoCommit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		// TODO: handle exception
		return status;
	}

	public static boolean reworkStatus(OperationalInfoVo objOper, String custid) {
		LOGGER.info("in reworkStatus()...custid..." + custid);
		LOGGER.info("oper obj : " + objOper);

		String card_type = objOper.getCard_type();
		String credit_rating = objOper.getCredit_rating();
		String kyc_check = objOper.getKyc_check();
		String card_details = objOper.getCard_details();
		int credit_limit = objOper.getCredit_limit();

		boolean status = false;
		LOGGER.info("reworkStatus()...started...");

		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;
		String query = null;

		con = DbConnections.getConnectionFromDS();

		try {

			query = "update CC_OPERATIONAL_INFO SET CREDIT_RATING=?,CARD_TYPE=?,KYC_CHECK=?,CARD_DETAILS=?,CREDIT_LIMIT=?,APP_STATUS=?"
					+ " where CUSTID=?";
			ps = con.prepareStatement(query);
			ps.setString(1, credit_rating);
			ps.setString(2, card_type);
			ps.setString(3, kyc_check);
			ps.setString(4, card_details);
			ps.setInt(5, credit_limit);
			ps.setString(6, "rework");
			ps.setString(7, custid);
			count = ps.executeUpdate();
			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		return status;
	}

	public static DocumentsVo getDocument(String applicationNumber, String documentType) {
		LOGGER.info("in getDocument()...app no and doc type : " + applicationNumber + "\t" + documentType);
		DocumentsVo objDocuments = new DocumentsVo();
		Connection connection = null;
		String queryDocument = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnections.getConnectionFromDS();
			queryDocument = "SELECT da.DOCUMENT_TYPE,da.MIME_TYPE,da.FILE_NAME,da.FILE_SIZE,da.DOC_OBJ "
					+ "from CC_OPERATIONAL_INFO op,CC_DOCUMENT_TYPE da \r\n"
					+ "WHERE op.APP_NUMBER=da.APP_NUMBER AND op.APP_NUMBER=? AND da.DOCUMENT_TYPE=?";
			preparedStatement = connection.prepareStatement(queryDocument);
			preparedStatement.setString(1, applicationNumber);
			preparedStatement.setString(2, documentType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				LOGGER.info("in dao while loop....");

				String documentTypeFromDB = resultSet.getString("DOCUMENT_TYPE");
				String mimeType = resultSet.getString("MIME_TYPE");
				String fileName = resultSet.getString("FILE_NAME");
				long fileSize = resultSet.getLong("FILE_SIZE");
				Blob documentObj = resultSet.getBlob("DOC_OBJ");
				InputStream docInputStream = documentObj.getBinaryStream();
				objDocuments.setDocumentType(documentTypeFromDB);
				objDocuments.setMimeType(mimeType);
				objDocuments.setFileName(fileName);
				objDocuments.setFileSize(fileSize);
				objDocuments.setDocumentInputStramObject(docInputStream);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objDocuments;
	}

	public static ArrayList<HashMap<String, String>> reworkLink() {

		LOGGER.info("in reworkLink record().....");
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String QUERY = null;
		String dob = null;
		String annual_income_string = null;
		long annual_income = 0;

		ArrayList<java.util.HashMap<String, String>> objArray = null;
		java.util.HashMap<String, String> map = null;

		map = new java.util.HashMap<String, String>();
		objArray = new ArrayList<java.util.HashMap<String, String>>();

		con = DbConnections.getConnectionFromDS();

		try {
			LOGGER.info("in try block.......");
			con.setAutoCommit(false);

			QUERY = "SELECT op.CUSTID,op.APP_STATUS,op.APP_NUMBER,op.LAST_MODIFIED_DATE,\r\n"
					+ "ba.FIRSTNAME,ba.LASTNAME,ba.MIDDLENAME,ba.GENDER,ba.PAN_NO,ba.FATHER_NAME,ba.DATEOFBIRTH,\r\n"
					+ "ad.COR_ADDRESS,ad.WORK_ADDRESS,ad.PER_ADDRESS,\r\n"
					+ "emp.EMPLOYMENT_TYPE,emp.EXPERIENCE,emp.DESIGNATION,emp.ANNUAL_INCOME\r\n"
					+ "FROM CC_OPERATIONAL_INFO op,\r\n" + "  CC_BASICCUSTOMERINFO ba,\r\n" + "  CC_ADDRESSINFO ad,\r\n"
					+ "  CC_EMPLOYMENTINFO emp\r\n"
					+ "WHERE op.CUSTID=ba.CUSTID AND ba.custid=ad.custid AND ad.custid=emp.custid\r\n"
					+ "AND (op.APP_STATUS='rework')";
			System.out.println("basic query : " + QUERY);
			preparedStatement = con.prepareStatement(QUERY);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				LOGGER.info("custid   :" + resultSet.getString("custid"));
				map.put("custid", resultSet.getString("custid"));
				map.put("app_no", resultSet.getString("app_number"));
				map.put("app_status", resultSet.getString("app_status"));
				map.put("last_modified_date", resultSet.getString("LAST_MODIFIED_DATE"));

				map.put("firstname", resultSet.getString("firstname"));
				map.put("lastname", resultSet.getString("lastname"));
				map.put("middlename", resultSet.getString("middlename"));
				map.put("gender", resultSet.getString("gender"));
				map.put("pan_no", resultSet.getString("pan_no"));
				map.put("fathername", resultSet.getString("father_name"));
				dob = resultSet.getString("dateofbirth");
				map.put("dob", dob);

				map.put("cor_address", resultSet.getString("cor_address"));
				map.put("work_address", resultSet.getString("work_address"));
				map.put("per_address", resultSet.getString("per_address"));

				map.put("employment_type", resultSet.getString("employment_type"));
				map.put("experience", resultSet.getString("experience"));
				map.put("designation", resultSet.getString("designation"));
				annual_income = resultSet.getLong("annual_income");
				annual_income_string = " " + annual_income;
				map.put("annual_income", annual_income_string);

				objArray.add(map);
				LOGGER.info("array obj in dao : " + objArray);
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}
		return objArray;

	}

	public static ArrayList<HashMap<String, String>> reworkUser(String appNo) {
		LOGGER.info("in retrive record()....." + appNo);
		Connection con = null;
		PreparedStatement psOperInfo = null, psBasicInfo = null, psEmpInfo = null, psAddInfo = null;
		ResultSet rsOperInfo = null, rsBasicInfo = null, rsEmpInfo = null, rsAddInfo = null;
		String queryOperInfo = null, queryBasicInfo = null, queryEmpInfo = null, queryAddInfo = null;

		String custid = null;
		/*
		 * String status = null; Date lastModifiedDate = null; String modDate = null;
		 */

		ArrayList<java.util.HashMap<String, String>> objArray = null;
		java.util.HashMap<String, String> map = null;

		map = new java.util.HashMap<String, String>();
		objArray = new ArrayList<java.util.HashMap<String, String>>();

		con = DbConnections.getConnectionFromDS();
		try {
			LOGGER.info("in try block.......");
			con.setAutoCommit(false);

			queryOperInfo = "select * from CC_OPERATIONAL_INFO where APP_NUMBER=?";
			System.out.println("basic query : " + queryOperInfo);
			psOperInfo = con.prepareStatement(queryOperInfo);
			psOperInfo.setString(1, appNo);
			rsOperInfo = psOperInfo.executeQuery();
			while (rsOperInfo.next()) {
				custid = rsOperInfo.getString("custid");
				LOGGER.info("custid   :" + custid);
				map.put("custid", custid);
				map.put("app_no", appNo);
			}

			Date dob = null;

			queryBasicInfo = "SELECT * FROM CC_BASICCUSTOMERINFO WHERE CUSTID=?";
			psBasicInfo = con.prepareStatement(queryBasicInfo);
			psBasicInfo.setString(1, custid);
			rsBasicInfo = psBasicInfo.executeQuery();
			while (rsBasicInfo.next()) {
				map.put("firstname", rsBasicInfo.getString("firstname"));
				map.put("lastname", rsBasicInfo.getString("lastname"));
				map.put("middlename", rsBasicInfo.getString("middlename"));
				map.put("gender", rsBasicInfo.getString("gender"));
				map.put("pan_no", rsBasicInfo.getString("pan_no"));
				map.put("fathername", rsBasicInfo.getString("father_name"));
				dob = rsBasicInfo.getDate("dateofbirth");
				String date = dob + "";
				map.put("dob", date);

				LOGGER.info("basic info........." + map);
				objArray.add(map);
			}

			queryAddInfo = "select * from cc_addressinfo cc where custid=?";
			psAddInfo = con.prepareStatement(queryAddInfo);
			psAddInfo.setString(1, custid);
			rsAddInfo = psAddInfo.executeQuery();
			while (rsAddInfo.next()) {
				map.put("cor_address", rsAddInfo.getString("cor_address"));
				map.put("work_address", rsAddInfo.getString("work_address"));
				map.put("per_address", rsAddInfo.getString("per_address"));

				LOGGER.info("address info........." + map);
				objArray.add(map);
			}

			String annual_income_string = null;
			long annual_income = 0;

			queryEmpInfo = "select * from cc_employmentinfo where custid=?";
			psEmpInfo = con.prepareStatement(queryEmpInfo);
			psEmpInfo.setString(1, custid);
			rsEmpInfo = psEmpInfo.executeQuery();
			while (rsEmpInfo.next()) {
				map.put("employment_type", rsEmpInfo.getString("employment_type"));
				map.put("experience", rsEmpInfo.getString("experience"));
				map.put("designation", rsEmpInfo.getString("designation"));
				annual_income = rsEmpInfo.getLong("annual_income");
				annual_income_string = annual_income + "";
				map.put("annual_income", annual_income_string);

				LOGGER.info("employment info........." + map);
				objArray.add(map);
			}
			/*
			 * InputStream documentInputStramObject=null;
			 * 
			 * queryDocType =
			 * "select * from cc_document_type where app_number=? and filename=?"; psDocType
			 * = con.prepareStatement(queryDocType); psDocType.setString(1, app_no);
			 * psDocType.setString(2, "applicationform"); rsDocType =
			 * psDocType.executeQuery(); while (rsDocType.next()) {
			 * rsDocType.getBlob("DOC_OBJ"); }
			 */
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}

		LOGGER.info("obj array  : " + objArray);
		return objArray;

	}

	public static boolean updateApplication(ApplicationInfoVo info) {
		LOGGER.info("update()...started...");

		Connection con = null;

		BasicInfoVo baiscInfo = info.getBaiscInfo();
		EmployementInfoVo empInfo = info.getEmpInfo();
		AddressInfoVo addInfo = info.getAddInfo();
		boolean status = false;

		con = DbConnections.getConnectionFromDS();

		try {
			con.setAutoCommit(false);

			String QUERYBASIINFO = "UPDATE CC_BASICCUSTOMERINFO SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,GENDER=?,DATEOFBIRTH=?,"
					+ "PAN_NO=?,FATHER_NAME=? WHERE CUSTID=?";
			LOGGER.info("basic query : " + QUERYBASIINFO);
			PreparedStatement preparedStatementBasicInfo = con.prepareStatement(QUERYBASIINFO);
			preparedStatementBasicInfo.setString(1, baiscInfo.getFirstName());
			preparedStatementBasicInfo.setString(2, baiscInfo.getLastName());
			preparedStatementBasicInfo.setString(3, baiscInfo.getMiddleName());
			preparedStatementBasicInfo.setString(4, baiscInfo.getGender());
			preparedStatementBasicInfo.setDate(5, baiscInfo.getDateofbirth());
			LOGGER.info("Date inserted : " + baiscInfo.getDateofbirth());
			preparedStatementBasicInfo.setString(6, baiscInfo.getPan_no());
			preparedStatementBasicInfo.setString(7, baiscInfo.getFather_Name());
			preparedStatementBasicInfo.setString(8, baiscInfo.getCustid());
			int countBasicInfo = preparedStatementBasicInfo.executeUpdate();
			System.out.println("basic info count : " + countBasicInfo);

			String QUERYEMPINFO = "UPDATE CC_EMPLOYMENTINFO SET EMPLOYMENT_TYPE=?,EXPERIENCE=?,"
					+ "DESIGNATION=?,ANNUAL_INCOME=? WHERE CUSTID=?";
			LOGGER.info("emp query : " + QUERYEMPINFO);
			PreparedStatement preparedStatementEmpInfo = con.prepareStatement(QUERYEMPINFO);
			preparedStatementEmpInfo.setString(1, empInfo.getEmployment_type());
			// preparedStatementEmpInfo.setString(, empInfo.getEmployer_name());
			preparedStatementEmpInfo.setString(2, empInfo.getExperience());
			preparedStatementEmpInfo.setString(3, empInfo.getDesignation());
			preparedStatementEmpInfo.setInt(4, empInfo.getAnnual_income());
			preparedStatementEmpInfo.setString(5, baiscInfo.getCustid());
			int countEmpInfo = preparedStatementEmpInfo.executeUpdate();
			LOGGER.info("emp info count : " + countEmpInfo);

			String status1 = "new";

			String QUERYOPERINFO = "UPDATE CC_OPERATIONAL_INFO SET APP_STATUS='verify' WHERE CUSTID=?";
			LOGGER.info("op query : " + QUERYOPERINFO);
			PreparedStatement preparedStatementOperInfo = con.prepareStatement(QUERYOPERINFO);
			preparedStatementOperInfo.setString(1, baiscInfo.getCustid());
			int countOperInfo = preparedStatementOperInfo.executeUpdate();
			LOGGER.info("operational info count : " + countOperInfo);

			String QUERYADDINFO = "UPDATE CC_ADDRESSINFO SET COR_ADDRESS=?,WORK_ADDRESS=?,PER_ADDRESS=? WHERE CUSTID=?";
			LOGGER.info("add query : " + QUERYADDINFO);
			PreparedStatement preparedStatementAddInfo = con.prepareStatement(QUERYADDINFO);
			preparedStatementAddInfo.setString(4, baiscInfo.getCustid());
			preparedStatementAddInfo.setString(1, addInfo.getCor_address());
			preparedStatementAddInfo.setString(2, addInfo.getWork_address());
			preparedStatementAddInfo.setString(3, addInfo.getPer_address());
			int countAddInfo = preparedStatementAddInfo.executeUpdate();
			LOGGER.info("add info count : " + countAddInfo);

			String QUERYOPERINFO1 = "SELECT APP_NUMBER FROM CC_OPERATIONAL_INFO WHERE CUSTID=?";
			PreparedStatement preparedStatementOperInfo1 = con.prepareStatement(QUERYOPERINFO1);
			preparedStatementOperInfo1.setString(1, baiscInfo.getCustid());
			ResultSet resultSet = preparedStatementOperInfo1.executeQuery();
			String app_no = null;
			while (resultSet.next()) {
				app_no = resultSet.getString("app_number");
				LOGGER.info("App no : " + app_no);
			}

			ArrayList<DocumentsVo> doc = info.getObjDocuments();
			for (DocumentsVo objDocs : doc) {
				String QUERYDOCINFO = "UPDATE CC_DOCUMENT_TYPE SET DOCUMENT_TYPE=?,MIME_TYPE=?,"
						+ "FILE_NAME=?,FILE_SIZE=?,DOC_OBJ=? WHERE APP_NUMBER=?";
				PreparedStatement preparedStatementDocInfo = con.prepareStatement(QUERYDOCINFO);
				preparedStatementDocInfo.setString(6, app_no);
				preparedStatementDocInfo.setString(1, objDocs.getDocumentType());
				preparedStatementDocInfo.setString(2, objDocs.getMimeType());
				preparedStatementDocInfo.setString(3, objDocs.getFileName());
				preparedStatementDocInfo.setDouble(4, objDocs.getFileSize());
				preparedStatementDocInfo.setBlob(5, objDocs.getDocumentInputStramObject());
				preparedStatementDocInfo.executeUpdate();
			}

			status = true;

			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqlException) {
				// TODO Auto-generated catch block
				sqlException.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbConnections.closeConnection(con, null, null);
		}
		return status;
	}

}

package com.mits.creditcard.search;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mits.creditcard.dbconnections.DbConnections;
import com.mits.creditcard.workflow.DocumentsVo;
import com.mits.creditcard.workflow.WorkFlowController;

//is used to perform 
public class SearchDao {
	static final Logger LOGGER = Logger.getLogger(WorkFlowController.class);

	public ArrayList<HashMap<String, String>> search(SearchVo searchVo) {
		LOGGER.info("in searchdao()...searVo : " + searchVo);

		String app_number = searchVo.getAppNumber();
		String app_status = searchVo.getAppStatus();
		String custId = searchVo.getCustId();
		String fromDate = searchVo.getFromDate();
		String toDate = searchVo.getToDate();

		LOGGER.info("in searchdao()...searVo : " + app_number + "\t" + app_status + "\t" + custId + "\t" + fromDate
				+ "\t" + toDate);

		Connection connection = DbConnections.getConnectionFromDS();
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		try {
			String QUERY = SearchConstants.QUERYMAIN;
			StringBuilder stringBuilder = new StringBuilder();

			if (app_number != null && app_number.length() > 0) {
				stringBuilder.append(" AND APP_NUMBER='" + app_number + "'");
			}
			if (app_status != null && app_status.equalsIgnoreCase("all")) {
				// stringBuilder.append(" AND APP_STATUS='"+app_status+"'");
			} else if (app_status != null && app_status.trim().length() > 0) {
				stringBuilder.append(" AND APP_STATUS='" + app_status + "'");
			}
			if (custId != null && custId.length() > 0) {
				stringBuilder.append(" AND CUSTID='" + custId + "'");
			}
			if (fromDate != null && (fromDate.trim().length()) > 0) {
				stringBuilder.append(" AND CREATED_DATE <= '" + fromDate + "'");
			}
			/*
			 * Date sqlToday=new Date(0); Date currentDate=new Date(sqlToday.getTime());
			 * DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy"); Date
			 * today=dateFormat.format(currentDate);
			 */
			if (toDate != null && (toDate.trim().length()) > 0) {
				stringBuilder.append(" AND CREATED_DATE >= '" + toDate + "'");
			}

			String QUERY1 = QUERY + stringBuilder;
			LOGGER.info("query : " + QUERY1);

			// String QUERYOPERINFO="select * from CC_OPERATIONAL_INFO where app_number=?";
			Statement createStatement = connection.createStatement();
			ResultSet resultSetExecuteQuery = createStatement.executeQuery(QUERY1);
			// PreparedStatement preparedStatement=connection.prepareStatement(QUERY1);
			// PreparedStatement preparedStatement=connection.prepareStatement(QUERY1);
			// preparedStatement.setString(1,searchVo.getAppNumber());
			// ResultSet resultSetExecuteQuery=preparedStatement.executeQuery();
			while (resultSetExecuteQuery.next()) {
				String appNum = resultSetExecuteQuery.getString("app_number");
				String appStatus = resultSetExecuteQuery.getString("app_status");
				String custid = resultSetExecuteQuery.getString("custid");
				Date lastModifiedDate = resultSetExecuteQuery.getDate("LAST_MODIFIED_DATE");

				SearchVo searchVo2 = new SearchVo();
				searchVo2.setAppNumber(appNum);
				searchVo2.setAppStatus(appStatus);
				searchVo.setCustId(custid);
				searchVo2.setCreatedDate(lastModifiedDate + "");

				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("appNumber", appNum);
				hashMap.put("appStatus", appStatus);
				hashMap.put("custId", custid);
				hashMap.put("lastModifiedDate", lastModifiedDate + "");

				arrayList.add(hashMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		LOGGER.info("arraylist obj : " + arrayList);

		return arrayList;

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
			queryDocument = SearchConstants.QUERYGETDOCS;
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

	public ArrayList<java.util.HashMap<String, String>> getAllDetails(String appNumber) {
		LOGGER.info("in  getAllDetails()....." + appNumber);
		Connection con = null;
		PreparedStatement psOperInfo = null, psBasicInfo = null, psEmpInfo = null, psAddInfo = null;
		ResultSet rsOperInfo = null, rsBasicInfo = null, rsEmpInfo = null, rsAddInfo = null;
		String queryOperInfo = null, queryBasicInfo = null, queryEmpInfo = null, queryAddInfo = null;
		String kycCheck = null;
		String cardType = null;
		String cardDetails = null;
		String creditLimit = null;
		String creditRating = null;

		String custid = null;

		ArrayList<java.util.HashMap<String, String>> objArray = null;
		java.util.HashMap<String, String> map = null;

		map = new java.util.HashMap<String, String>();
		objArray = new ArrayList<java.util.HashMap<String, String>>();

		con = DbConnections.getConnectionFromDS();
		try {
			LOGGER.info("in try block.......");
			con.setAutoCommit(false);

			queryOperInfo = SearchConstants.QUERYGETOPERINFO;
			System.out.println("basic query : " + queryOperInfo);
			psOperInfo = con.prepareStatement(queryOperInfo);
			psOperInfo.setString(1, appNumber);
			rsOperInfo = psOperInfo.executeQuery();
			while (rsOperInfo.next()) {
				custid = rsOperInfo.getString("custid");
				cardType = rsOperInfo.getString("card_type");
				kycCheck = rsOperInfo.getString("kyc_check");
				cardDetails = rsOperInfo.getString("card_details");
				creditLimit = rsOperInfo.getString("credit_limit");
				creditRating = rsOperInfo.getString("CREDIT_RATING");

				LOGGER.info("custid   :" + custid);
				map.put("custid", custid);
				map.put("cardType", cardType);
				map.put("kycCheck", kycCheck);
				map.put("cardDetails", cardDetails);
				map.put("creditLimit", creditLimit);
				map.put("app_no", appNumber);
				map.put("creditRating", creditRating);

				LOGGER.info("Operational info........." + map);
				objArray.add(map);
			}

			String dob = null;

			queryBasicInfo = SearchConstants.QUERYBASICINFO;
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

			queryAddInfo = SearchConstants.QUERYADDINFO;
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

			queryEmpInfo = SearchConstants.QUERYEMPINFO;
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

			// String queryOperInfo=""
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

		return objArray;
	}
}

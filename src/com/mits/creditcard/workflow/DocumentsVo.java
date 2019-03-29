package com.mits.creditcard.workflow;

import java.io.InputStream;
import java.io.OutputStream;

//this class is acts as a bean class for workflow module
public class DocumentsVo
{
	private String documentType;
	private String mimeType;
	private String fileName;
	private double fileSize;
	private InputStream documentInputStramObject;
	private OutputStream documentOutputStream;
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public InputStream getDocumentInputStramObject() {
		return documentInputStramObject;
	}
	public void setDocumentInputStramObject(InputStream documentInputStramObject) {
		this.documentInputStramObject = documentInputStramObject;
	}
	public OutputStream getDocumentOutputStream() {
		return documentOutputStream;
	}
	public void setDocumentOutputStream(OutputStream documentOutputStream) {
		this.documentOutputStream = documentOutputStream;
	}
	
	

}

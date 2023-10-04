package com.ktdsuniversity.edu.bbs.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class BoardVO {

	private int id;
	
	@NotBlank(message = "제목을 입력해주세요.")
	private String subject;
	
	@NotBlank(message = "내용을 입력해주세요.")
	private String content;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "올바른 형식으로 입력해주세요,")
	private String email;
	
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	private String fileName;
	private String originFileName;
	private String ipAddr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getMdfyDt() {
		return mdfyDt;
	}

	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

}

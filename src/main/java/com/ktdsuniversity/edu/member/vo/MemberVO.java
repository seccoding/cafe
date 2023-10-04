package com.ktdsuniversity.edu.member.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MemberVO {

	@NotBlank(message="이메일을 입력해주세요.")
	@Email(message="이메일 형태로 입력해주세요.")
	private String email;
	
	@NotBlank(message="이름을 입력해주세요.")
	private String name;
	
	@NotBlank(message="비밀번호를 입력해주세요.")
	@Size(min=10, message="비밀번호는 최소 10자리 이상 입력해주세요.")
	private String password;
	
	@NotBlank(message="비밀번호 확인을 입력해주세요.")
	private String confirmPassword;
	
	private String salt;
	private String blockYn;
	private int loginCnt;
	private String latestLoginSuccessDate;
	private String latestLoginFailDate;
	private String latestAccessIp;
	private String registDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getBlockYn() {
		return blockYn;
	}

	public void setBlockYn(String blockYn) {
		this.blockYn = blockYn;
	}

	public int getLoginCnt() {
		return loginCnt;
	}

	public void setLoginCnt(int loginCnt) {
		this.loginCnt = loginCnt;
	}

	public String getLatestLoginSuccessDate() {
		return latestLoginSuccessDate;
	}

	public void setLatestLoginSuccessDate(String latestLoginSuccessDate) {
		this.latestLoginSuccessDate = latestLoginSuccessDate;
	}

	public String getLatestLoginFailDate() {
		return latestLoginFailDate;
	}

	public void setLatestLoginFailDate(String latestLoginFailDate) {
		this.latestLoginFailDate = latestLoginFailDate;
	}

	public String getLatestAccessIp() {
		return latestAccessIp;
	}

	public void setLatestAccessIp(String latestAccessIp) {
		this.latestAccessIp = latestAccessIp;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

}

package com.ktdsuniversity.edu.exceptions;

import com.ktdsuniversity.edu.member.vo.MemberVO;

/**
 * 회원가입할 때 이미 가입된 이메일로 가입을 시도하면
 * 발생하는 예외.
 * 
 * 화면으로 가입정보를 전달해 주어야 함.
 */
public class AlreadyUseException extends RuntimeException {

	private static final long serialVersionUID = 6062148073962465674L;
	
	/**
	 * 가입정보
	 */
	private MemberVO memberVO;
	
	public AlreadyUseException(MemberVO memberVO, String message) {
		super(message);
		this.memberVO = memberVO;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	
}

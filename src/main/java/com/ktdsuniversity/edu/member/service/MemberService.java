package com.ktdsuniversity.edu.member.service;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public interface MemberService {

	/**
	 * 파라미터로 전달된 이메일이 사용가능한지 확인.
	 * @param email 사용자가 가입 요청한 이메일
	 * @return 사용가능한 이메일인지 여부 (true: 사용가능한 이메
	 */
	public boolean checkAvailableEmail(String email);
	
	/**
	 * 회원가입을 처리한다.
	 * @param memberVO 사용자가 입력한 회원 정보
	 * @return 회원가입이 정상적으로 처리되었는지 여부.
	 */
	public boolean createNewMember(MemberVO memberVO);
	
	/**
	 * 이메일과 비밀번호로 회원정보를 조회.
	 * 비밀번호가 3번 틀릴 경우 로그인은 블럭되며, 
	 * 관리자가 처리하기 전까지는 로그인이 불가능하다.
	 * @param memberVO 이메일과 비밀번호
	 * @return 이메일과 비밀번호가 일치하는 회원의 정보.
	 */
	public MemberVO getMember(MemberVO memberVO);
	
}

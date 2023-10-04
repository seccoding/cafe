package com.ktdsuniversity.edu.member.dao;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public interface MemberDao {

	/**
	 * 파라미터로 전달된 이메일이 DB에 몇 건 존재하는지 확인한다.
	 * @param email 사용자가 가입 요청한 이메일
	 * @return 동일한 이메일로 등록된 회원의 수
	 */
	public int getEmailCount(String email);
	
	/**
	 * 회원가입 쿼리를 실행한다.
	 * @param memberVO 사용자가 입력한 회원 정보
	 * @return DB에 Insert한 회원의 개수
	 */
	public int createNewMember(MemberVO memberVO);
	
	/**
	 * 로그인 시 비밀번호 암호화를 위해 기존에 발급했던 salt 값을 조회.
	 * @param email 조회할 이메일
	 * @return 회원가입시 발급받은 salt값
	 */
	public String getSalt(String email);
	
	/**
	 * 이메일과 비밀번호로 회원 정보를 조회.
	 * @param memberVO 이메일과 비밀번호
	 * @return 이메일과 비밀번호가 일치하는 회원의 정보
	 */
	public MemberVO getMember(MemberVO memberVO);
	
	/**
	 * 로그인이 정상적으로 수행되었을 때,
	 * LOGIN_CNT는 0으로
	 * LATEST_LOGIN_SUCCESS_DATE는 현재 시간으로
	 * LATEST_ACCESS_IP는 현재 IP로 업데이트한다.
	 * @param memberVO 이메일과 접근 IP
	 * @return 업데이트 된 회원 정보의 수
	 */
	public int successLogin(MemberVO memberVO);
	
	/**
	 * 로그인이 실패했을 때(이메일은 맞지만 비밀번호가 틀린경우)
	 * LOGIN_CNT는 1증가하고
	 * LATEST_LOGIN_FAIL_DATE는 현재시간으로
	 * LATEST_ACCESS_IP는 현재 IP로 업데이트 한다.
	 * @param memberVO 이메일과 접근 IP
	 * @return 업데이트 된 회원 정보의 수
	 */
	public int failLogin(MemberVO memberVO);
	
	/**
	 * 로그인이 3회 실패했을 때 BLOCK_YN을 Y로 변경한다.
	 * BLOCK_YN이 Y인 회원은 절대 로그인 할 수 없다.
	 * @param email
	 * @return
	 */
	public int blockMember(String email);
	
}

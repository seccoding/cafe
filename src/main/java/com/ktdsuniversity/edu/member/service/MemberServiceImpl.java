package com.ktdsuniversity.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean checkAvailableEmail(String email) {
		int emailCount = memberDao.getEmailCount(email);
		return emailCount == 0;
	}
	
	@Override
	public boolean createNewMember(MemberVO memberVO) {
		int emailCount = memberDao.getEmailCount(memberVO.getEmail());
		if (emailCount > 0) {
			throw new IllegalArgumentException("Email이 이미 사용중입니다.");
		}
		
		// TODO 암호화 처리 한 이후에 삭제해야 함.
		memberVO.setSalt("-");
		
		int insertCount = memberDao.createNewMember(memberVO);
		return insertCount > 0;
	}

}

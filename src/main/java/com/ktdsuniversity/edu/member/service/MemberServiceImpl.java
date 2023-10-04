package com.ktdsuniversity.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.beans.SHA;
import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private SHA sha;
	
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
		
		String salt = sha.generateSalt();
		String rawPassword = memberVO.getPassword();
		String encodedPassword = sha.getEncrypt(rawPassword, salt);
		memberVO.setSalt(salt);
		memberVO.setPassword(encodedPassword);
		
		int insertCount = memberDao.createNewMember(memberVO);
		return insertCount > 0;
	}

	@Override
	public MemberVO getMember(MemberVO memberVO) {
		String salt = memberDao.getSalt(memberVO.getEmail());
		if (salt == null) {
			throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		String password = memberVO.getPassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		memberVO.setPassword(encryptedPassword);
		
		MemberVO member = memberDao.getMember(memberVO);
		if (member == null) {
			memberDao.failLogin(memberVO);
			memberDao.blockMember(memberVO.getEmail());
			throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		memberDao.successLogin(memberVO);
		return member;
	}

}

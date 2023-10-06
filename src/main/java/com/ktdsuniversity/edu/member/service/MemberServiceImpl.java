package com.ktdsuniversity.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.beans.SHA;
import com.ktdsuniversity.edu.exceptions.AlreadyUseException;
import com.ktdsuniversity.edu.exceptions.UserIdentifyNotMatchException;
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
	
	@Transactional
	@Override
	public boolean createNewMember(MemberVO memberVO) {
		int emailCount = memberDao.getEmailCount(memberVO.getEmail());
		if (emailCount > 0) {
			throw new AlreadyUseException(memberVO, "Email이 이미 사용중입니다.");
		}
		
		String salt = sha.generateSalt();
		String rawPassword = memberVO.getPassword();
		String encodedPassword = sha.getEncrypt(rawPassword, salt);
		memberVO.setSalt(salt);
		memberVO.setPassword(encodedPassword);
		
		int insertCount = memberDao.createNewMember(memberVO);
		return insertCount > 0;
	}

//	@Transactional
	// 업데이트 후 의도적으로 예외를 발생시켰을 때는
	// @Transactional를 적용하면 안된다.
	@Override
	public MemberVO getMember(MemberVO memberVO) {
		String salt = memberDao.getSalt(memberVO.getEmail());
		if (salt == null) {
			throw new UserIdentifyNotMatchException(memberVO, "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		String password = memberVO.getPassword();
		String encryptedPassword = sha.getEncrypt(password, salt);
		memberVO.setPassword(encryptedPassword);
		
		MemberVO member = memberDao.getMember(memberVO);
		if (member == null) {
			memberDao.failLogin(memberVO);
			memberDao.blockMember(memberVO.getEmail());
			throw new UserIdentifyNotMatchException(memberVO, "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		if (member.getBlockYn().equalsIgnoreCase("Y")) {
			throw new UserIdentifyNotMatchException(memberVO, "비밀번호가 3회이상 틀려 아이디가 차단되었습니다. 관리자에게 문의하세요.");
		}
		
		memberDao.successLogin(memberVO);
		return member;
	}
	
	@Transactional
	@Override
	public boolean deleteMe(String email) {
		int deleteCount = memberDao.deleteMe(email);
		return deleteCount > 0;
	}

}

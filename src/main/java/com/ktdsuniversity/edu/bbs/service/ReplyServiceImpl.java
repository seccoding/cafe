package com.ktdsuniversity.edu.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.bbs.dao.ReplyDao;
import com.ktdsuniversity.edu.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.exceptions.AjaxPageNotFoundException;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return replyDao.getAllReplies(boardId);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		int insertCount = replyDao.createNewReply(replyVO);
		return insertCount > 0;
	}

	@Transactional
	@Override
	public boolean deleteOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDao.getOneReply(replyId);
		if (!email.equals(replyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		
		int deleteCount = replyDao.deleteOneReply(replyId);
		return deleteCount > 0;
	}

	@Transactional
	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		ReplyVO originReplyVO = replyDao.getOneReply(replyVO.getReplyId());
		if (!originReplyVO.getEmail().equals(originReplyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		
		int updateCount = replyDao.modifyOneReply(replyVO);
		return updateCount > 0;
	}

	@Transactional
	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDao.getOneReply(replyId);
		if (email.equals(replyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		
		int updateCount = replyDao.recommendOneReply(replyId);
		return updateCount > 0;
	}
	
}

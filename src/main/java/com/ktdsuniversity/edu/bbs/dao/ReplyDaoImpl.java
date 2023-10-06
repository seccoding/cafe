package com.ktdsuniversity.edu.bbs.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.bbs.vo.ReplyVO;

@Repository
public class ReplyDaoImpl extends SqlSessionDaoSupport implements ReplyDao {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return getSqlSession().selectList("getAllReplies", boardId);
	}

	@Override
	public ReplyVO getOneReply(int replyId) {
		return getSqlSession().selectOne("getOneReply", replyId);
	}

	@Override
	public int createNewReply(ReplyVO replyVO) {
		return getSqlSession().insert("createNewReply", replyVO);
	}

	@Override
	public int deleteOneReply(int replyId) {
		return getSqlSession().delete("deleteOneReply", replyId);
	}

	@Override
	public int modifyOneReply(ReplyVO replyVO) {
		return getSqlSession().update("modifyOneReply", replyVO);
	}

	@Override
	public int recommendOneReply(int replyId) {
		return getSqlSession().update("recommendOneReply", replyId);
	}
	
}

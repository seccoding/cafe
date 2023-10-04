package com.ktdsuniversity.edu.bbs.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.bbs.vo.BoardVO;

@Repository
public class BoardDAOImpl extends SqlSessionDaoSupport 
                          implements BoardDAO {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int getBoardAllCount() {
		return getSqlSession().selectOne("getBoardAllCount");
	}

	@Override
	public List<BoardVO> getAllBoard() {
		return getSqlSession().selectList("getAllBoard");
	}

	@Override
	public int createNewBoard(BoardVO boardVO) {
		// getSqlSession().insert()는 insert한 ROW의 개수를 반환한다.
		return getSqlSession().insert("createNewBoard", boardVO);
	}

	@Override
	public int increaseViewCount(int id) {
		return getSqlSession().update("increaseViewCount", id);
	}

	@Override
	public BoardVO getOneBoardVO(int id) {
		return getSqlSession().selectOne("getOneBoardVO", id);
	}

	@Override
	public int updateOneBoard(BoardVO boardVO) {
		// Query에게 전달할 수 있는 파라미터의 수는 1개다.
		// 2개 이상은 보낼 수가 없다.
		return getSqlSession().update("updateOneBoard", boardVO);
	}

	@Override
	public int deleteOneBoard(int id) {
		return getSqlSession().delete("deleteOneBoard", id);
	}

}





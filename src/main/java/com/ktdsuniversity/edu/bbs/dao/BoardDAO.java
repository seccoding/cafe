package com.ktdsuniversity.edu.bbs.dao;

import java.util.List;

import com.ktdsuniversity.edu.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.bbs.vo.SearchBoardVO;

public interface BoardDAO {

	/**
	 * DB에 저장된 모든 게시글의 수를 조회
	 * @return 게시글의 수
	 */
	public int getBoardAllCount();
	
	/**
	 * DB에 저장된 모든 게시글의 목록을 조회
	 * @return 게시글 목록
	 */
	public List<BoardVO> getAllBoard();
	
	/**
	 * DB에 저장된 모든 게시글의 목록을 조회.
	 * @param searchBoardVO 검색할 조건 (페이지번호, 노출할 목록 개수 등)
	 * @return DB에서 조회된 게시글의 목록 (Pagination)
	 */
	public List<BoardVO> searchAllBoard(SearchBoardVO searchBoardVO);
	
	/**
	 * DB에 새로운 게시글을 등록한다.
	 * @param boardVO 사용자가 입력한 게시글 정보
	 * @return DB에 Insert한 개수.
	 */
	public int createNewBoard(BoardVO boardVO);
	
	/**
	 * 파라미터로 전달받은 게시글의 ID의 조회수를 1 증가 시킨다.
	 * @param id 조회할 게시글의 ID (번호)
	 * @return DB 업데이트한 개수.
	 */
	public int increaseViewCount(int id);
	
	/**
	 * 파라미터로 전달받은 게시글의 ID의 게시글 정보를 조회한다.
	 * @param id 조회할 게시글의 ID (번호)
	 * @return 조회된 게시글의 정보(내용)
	 */
	public BoardVO getOneBoardVO(int id);
	
	/**
	 * DB에 게시글의 정보를 수정한다.
	 * BoardVO의 ID 값에 수정할 게시글의 ID값이 있어야 한다.
	 * @param boardVO 사용자가 수정한 게시글의 정보
	 * @return DB에 Update한 게시글의 수
	 */
	public int updateOneBoard(BoardVO boardVO);
	
	/**
	 * 파라미터로 전달받은 게시글의 ID의 게시글을 삭제한다.
	 * @param id 삭제하려는 게시글의 ID
	 * @return DB에서 Delete한 게시글의 수
	 */
	public int deleteOneBoard(int id);
}










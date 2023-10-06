package com.ktdsuniversity.edu.bbs.service;

import java.util.List;

import com.ktdsuniversity.edu.bbs.vo.ReplyVO;

public interface ReplyService {

	/**
	 * 게시글의 모든 댓글을 조회한다.
	 * @param boardId 조회할 게시글의 번호
	 * @return 게시글에 등록된 모든 댓글 목록
	 */
	public List<ReplyVO> getAllReplies(int boardId);
	
	/**
	 * 게시글에 댓글을 등록한다.
	 * @param replyVO 등록할 댓글의 정보
	 * @return 댓글 등록 성공 여부
	 */
	public boolean createNewReply(ReplyVO replyVO);
	
	/**
	 * 댓글 하나를 삭제한다.
	 * @param replyId 삭제할 댓글의 번호
	 * @return 댓글 삭제 성공 여부
	 */
	public boolean deleteOneReply(int replyId, String email);
	
	/**
	 * 댓글 내용을 수정한다.
	 * @param replyVO 수정할 댓글의 정보
	 * @return 댓글 수정 성공 여부
	 */
	public boolean modifyOneReply(ReplyVO replyVO);
	
	/**
	 * 댓글의 추천수를 1증가시킨다.
	 * @param replyId 추천수를 1증가시킬 댓글 번호
	 * @return 댓츨 추천 성공 여부
	 */
	public boolean recommendOneReply(int replyId, String email);
	
}

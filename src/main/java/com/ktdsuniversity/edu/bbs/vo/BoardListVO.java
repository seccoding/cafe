package com.ktdsuniversity.edu.bbs.vo;

import java.util.List;

public class BoardListVO {

	private int boardCnt;
	private List<BoardVO> boardList;

	public int getBoardCnt() {
		return boardCnt;
	}

	public void setBoardCnt(int boardCnt) {
		this.boardCnt = boardCnt;
	}

	public List<BoardVO> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<BoardVO> boardList) {
		this.boardList = boardList;
	}

}

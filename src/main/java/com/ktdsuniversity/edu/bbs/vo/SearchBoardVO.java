package com.ktdsuniversity.edu.bbs.vo;

public class SearchBoardVO {

	/**
	 * 검색할 페이지의 번호 (0-base)
	 */
	private int pageNo;

	/**
	 * 한 목록에 노출시킬 게시글의 개수
	 */
	private int listSize;

	/**
	 * 생성할 최대 페이지의 수
	 */
	private int pageCount;

	public SearchBoardVO() {
		// listSize를 별도로 지정하지 않으면
		// 한 게시글 목록에는 10개만 노출된다.
		this.listSize = 10;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 게시글의 개수를 할당하면
	 * 생성되어야 하는 페이지의 최대 크기가 pageCount에 할당된다.
	 * @param listCount 검색된 게시글의 총 개수.
	 */
	public void setPageCount(int listCount) {
		this.pageCount = (int) Math.ceil( (double) listCount / this.listSize );
	}

}

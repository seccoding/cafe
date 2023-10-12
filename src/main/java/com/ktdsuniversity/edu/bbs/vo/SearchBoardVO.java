package com.ktdsuniversity.edu.bbs.vo;

import com.ktdsuniversity.edu.common.vo.AbstractSearchVO;

public class SearchBoardVO extends AbstractSearchVO {

	/**
	 * 제목검색 혹은 내용검색을 하기위한 구분자.
	 * 제목검색: subject
	 * 내용검색: content
	 */
	private String searchType;

	/**
	 * 검색어
	 */
	private String searchKeyword;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

}

<!-- Page Directive -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Taglib Directive -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<script type="text/javascript">
    $().ready(function() {
    	$("#search-btn").click(function() {
    		movePage()
    	})
    })
    
    function movePage(pageNo = 0) {
    	$("#pageNo").val(pageNo)
    	
    	$("#search-form").attr({
    		"method": "get",
    		"action": "/board/list"
    	}).submit()
    }
    
</script>

<jsp:include page="../layout/header.jsp" />	

	<div class="grid">
	    
	    <form id="search-form" 
	          method="get" 
	          action="/board/list">
		    <div>
		        <select name="searchType">
		            <option value="subject" ${searchBoardVO.searchType eq 'subject' ? 'selected' : ''}>제목</option>
		            <option value="content" ${searchBoardVO.searchType eq 'content' ? 'selected' : ''}>내용</option>
		        </select>
		        <input type="text" name="searchKeyword" value="${searchBoardVO.searchKeyword}" />
		        <input type="hidden" id="pageNo" name="pageNo" />
		        <button id="search-btn">검색</button>
		    </div>
	    </form>
	    
		<div class="right-align">
			총 ${boardList.boardCnt} 건의 게시글이 검색되었습니다.
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>이메일</th>
					<th>조회수</th>
					<th>등록일</th>
					<th>수정일</th>
				</tr>
			</thead>
			<tbody>
			    <c:choose>
			        <c:when test="${not empty boardList.boardList}">
			            <c:forEach items="${boardList.boardList}" var="board">
		                    <tr>
		                        <td>${board.id}</td>
		                        <td>
		                            <a href="/board/view?id=${board.id}">
		                                <c:out value="${board.subject}" />
		                            </a>
		                        </td>
		                        <td>${board.memberVO.name} (${board.email})</td>
		                        <td>${board.viewCnt}</td>
		                        <td>${board.crtDt}</td>
		                        <td>${board.mdfyDt}</td>
		                    </tr>
		                </c:forEach>
			        </c:when>
			        <c:otherwise>
			            <tr>
			                <td colspan="6">등록된 게시글이 없습니다.</td>
			            </tr>
			        </c:otherwise>
			    </c:choose>
			</tbody>
		</table>
		
		<div>
			<div>
				<ul class="page-nav">
				    <c:if test="${not empty boardList.boardList}">
					    <!-- 이전 페이지 그룹 -->
					    <c:if test="${searchBoardVO.hasPrevGroup}">
					        <li>
					            <a href="javascript:void(0)" onclick="movePage(0)">처음</a>
					        </li>
					        <li>
					            <a href="javascript:void(0)" onclick="movePage(${searchBoardVO.prevGroupStartPageNo})">이전</a>
					        </li>
					    </c:if>
					    
	                    <c:forEach begin="${searchBoardVO.groupStartPageNo}"
	                               end="${searchBoardVO.groupEndPageNo}"
	                               step="1"
	                               var="p">
							<li class="${searchBoardVO.pageNo eq p ? "active": ""}">
								<a href="javascript:void(0)" onclick="movePage(${p})">${p + 1}</a>
							</li>
	                    </c:forEach>
	                    
	                    <!-- 다음 페이지 그룹 -->
	                    <c:if test="${searchBoardVO.hasNextGroup}">
	                        <li>
	                            <a href="javascript:void(0)" onclick="movePage(${searchBoardVO.nextGroupStartPageNo})">다음</a>
	                        </li>
	                        <li>
	                            <a href="javascript:void(0)" onclick="movePage(${searchBoardVO.pageCount-1})">마지막</a>
	                        </li>
	                    </c:if>
                    </c:if>
				</ul>
			</div>
		</div>

		<c:if test="${not empty sessionScope._LOGIN_USER_}">
			<div class="right-align">
			    <a href="/board/excel/download">엑셀 다운로드</a>
				<a href="/board/write">게시글 등록</a>
			</div>
		</c:if>
	</div>
	
<jsp:include page="../layout/footer.jsp" />
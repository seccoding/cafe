<!-- Page Directive -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Taglib Directive -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<style type="text/css">
	a:link, a:hover, a:active, a:visited {
		color: #333;
		text-decoration: none;
	}

	table.table {
		border-collapse: collapse;
		border: 1px solid #DDD;
	}

	table.table > thead > tr {
		background-color: #FFF;
	}

	table.table > thead th {
		padding: 10px;
		color: #333;
	}

	table.table th, table.table td {
		border-right: 1px solid #F0F0F0;
	}

	table.table th:last-child,
	table.table td:last-child {
		border-right: none;
	}

	table.table > tbody > tr:nth-child(odd) {
		background-color: #F5F5F5;
	}

	table.table > tbody tr:hover {
		background-color: #FAFAFA;
	}

	table.table > tbody td {
		padding: 10px;
		color: #333;
	}

	div.grid {
		display: grid;
		grid-template-columns: 1fr;
		grid-template-rows: 28px 28px 1fr 28px 28px;
		row-gap: 10px;
	}
	div.grid div.right-align {
		text-align: right;
	}
	ul.horizontal-list {
	   padding: 0px;
	   margin: 0px;
	}
	ul.horizontal-list li {
	   display: inline;
    }

	ul.page-nav {
		margin: 0px;
		padding: 0px;
		text-align: center;
	}

	ul.page-nav > li {
		display: inline-block;
		padding: 10px;
		color: #333;
	}

	ul.page-nav > li.active > a {
		color: #F00;
		font-weight: bold;
	}
</style>
</head>
<body>
	
	<div class="grid">
	    
	    <jsp:include page="../member/membermenu.jsp"></jsp:include>
	    
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
				
				    <!-- 이전 페이지 그룹 -->
				    <c:if test="${searchBoardVO.hasPrevGroup}">
				        <li>
				            <a href="/board/list?pageNo=0">처음</a>
				        </li>
				        <li>
				            <a href="/board/list?pageNo=${searchBoardVO.prevGroupStartPageNo}">이전</a>
				        </li>
				    </c:if>
				    
                    <c:forEach begin="${searchBoardVO.groupStartPageNo}"
                               end="${searchBoardVO.groupEndPageNo}"
                               step="1"
                               var="p">
						<li class="${searchBoardVO.pageNo eq p ? "active": ""}">
							<a href="/board/list?pageNo=${p}">${p + 1}</a>
						</li>
                    </c:forEach>
                    
                    <!-- 다음 페이지 그룹 -->
                    <c:if test="${searchBoardVO.hasNextGroup}">
                        <li>
                            <a href="/board/list?pageNo=${searchBoardVO.nextGroupStartPageNo}">다음</a>
                        </li>
                        <li>
                            <a href="/board/list?pageNo=${searchBoardVO.pageCount-1}">마지막</a>
                        </li>
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

</body>
</html>
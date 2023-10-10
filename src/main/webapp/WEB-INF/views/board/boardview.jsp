<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 내용 조회</title>
<style type="text/css">
	* {
		color: #333;
		font-size: 17px;
	}

	a:link, a:hover, a:visited, a:active {
		color: #333;
		text-decoration: none;
	}

	div.grid {
		display: grid;
		grid-template-columns: 80px 1fr;
		grid-template-rows: 28px 28px 28px 28px 28px 28px 320px 1fr;
		row-gap: 10px;
	}

	div.grid > div.btn-group {
		display: grid;
		grid-column: 1 / 3;
	}

	div.grid div.right-align {
		text-align: right;
	}

	label {
		padding-left: 10px;
	}

	button, input, textarea {
		padding: 10px;
	}

	div.grid > div.replies {
		display: grid;
		grid-column: 1 / 3;
	}

	div.replies > .write-reply {
		margin-top: 10px;
		display: grid;
		grid-template-columns: 1fr 80px;
		grid-template-rows: 1fr 40px;
		row-gap: 10px;
		column-gap: 10px;
		align-items: center;
	}

	div.replies > .write-reply > textarea {
		height: 150px;
		display: grid;
		grid-column: 1 / 3;
	}

	div.replies > .reply-items {
		display: grid;
		grid-template-columns: 1fr;
		grid-template-rows: 1fr;
		row-gap: 10px;
	}

	pre.content {
		margin: 0px;
	}
</style>
<script type='text/javascript' src="/js/lib/jquery-3.7.1.js"></script>
<script type='text/javascript'>
	$().ready(function() {
		// 댓글 수정
		var modifyReply = function(event) {
			// event.currentTarget => 이벤트가 발생한 대상.
			// 클릭한 대상.
			var reply = $(event.currentTarget).closest(".reply")
			var replyId = reply.data("reply-id")

			// 작성되어있던 댓글의 내용
			var content = reply.find(".content").text()
			$("#txt-reply").val(content)
			$("#txt-reply").focus()

			// 수정모드로 변경.
			$("#txt-reply").data("mode", "modify")
			$("#txt-reply").data("target", replyId)
		}

		// 댓글 삭제
		var deleteReply = function(event) {
			// event.currentTarget => 이벤트가 발생한 대상.
			// 클릭한 대상.
			var reply = $(event.currentTarget).closest(".reply")
			var replyId = reply.data("reply-id")

			// txt-reply에 할당된 data-변수를 제거.
			$("#txt-reply").removeData("mode")
			$("#txt-reply").removeData("target")

			if ( confirm("댓글을 삭제하시겠습니까?") ) {
				// ajax 요청
				$.get(`/board/reply/delete/\${replyId}`, function(response) {
					// 삭제 성공 여부를 받아온다.
					var result = response.result

					// 삭제에 성공했다면
					if (result) {
						// 댓글을 다시 조회한다.
						loadReplies()
						// 댓글 입력 칸은 비워준다.
						$("#txt-reply").val("")
					}
				})
			}

		}

		// 대댓글 등록
		var reReply = function(event) {
			// event.currentTarget => 이벤트가 발생한 대상.
			// 클릭한 대상.
			var reply = $(event.currentTarget).closest(".reply")
			var replyId = reply.data("reply-id")

			$("#txt-reply").data("mode", "re-reply")
			$("#txt-reply").data("target", replyId)
			$("#txt-reply").focus()
		}

		// 댓글 추천
		var recommendReply = function(event) {
			// event.currentTarget => 이벤트가 발생한 대상.
			// 클릭한 대상.
			var reply = $(event.currentTarget).closest(".reply")
			var replyId = reply.data("reply-id")

			$("#txt-reply").removeData("mode")
			$("#txt-reply").removeData("target")

			// 추천 ajax 요청.
			$.get(`/board/reply/recommend/\${replyId}`, function(response) {
				var result = response.result

				if (result) {
					loadReplies();
					$("#txt-reply").val("")
				}
			})
		}

		// 댓글 조회
		var loadReplies = function() {
			// 댓글 목록 삭제.
			$(".reply-items").html("")

			// 댓글 조회.
			// `` <- backtick template literal 변수 바인딩 \${}
			$.get("/board/reply/${boardVO.id}", function(response) {
				var replies = response.replies // <-- Java: List, JS: Array
				for (var i = 0; i < replies.length; i++) {
					// 배열에서 댓글 하나를 가져온다.
					var reply = replies[i]

					// 댓글이 표현될 template을 만들어준다.
					var replyTemplate = 
						`<div class="reply"
						      data-reply-id="\${reply.replyId}"
							  style="padding-left: \${(reply.level - 1) * 40}px">
							<div class="author">
								\${reply.memberVO.name} (\${reply.email})
							</div>
							<div class="recommend-count">
								추천수: \${reply.recommendCnt}
							</div>
							<div class="datetime">
								<span class="crtdt">등록: \${reply.crtDt}</span>
								\${reply.mdfyDt != reply.crtDt ? 
									`<span class="mdfydt">수정: \${reply.mdfyDt}</span>`
									: ""}
							</div>
							<pre class="content">\${reply.content}</pre>
							\${reply.email == "${sessionScope._LOGIN_USER_.email}" ? 
								`<div>
									<span class="modify-reply">수정</span>
									<span class="delete-reply">삭제</span>
									<span class="re-reply">답변하기</span>
								</div>`
								: `<div>
									<span class="recommend-reply">추천하기</span>
									<span class="re-reply">답변하기</span>
								</div>`}
						</div>`

						var replyDom = $(replyTemplate)
						replyDom.find(".modify-reply").click(modifyReply)
						replyDom.find(".delete-reply").click(deleteReply)
						replyDom.find(".recommend-reply").click(recommendReply)
						replyDom.find(".re-reply").click(reReply)

						$(".reply-items").append(replyDom)
				}
			})
		}

		loadReplies()

		// 등록버튼 클릭
		$("#btn-save-reply").click(function() {})

		// 취소버튼 클릭
		$("#btn-cancel-reply").click(function() {})
	});
</script>
</head>
<body>
    
    <jsp:include page="../member/membermenu.jsp"></jsp:include>

	<h1>게시글 조회</h1>
	<div class="grid">
		<label for="subject">제목</label>
		<div>${boardVO.subject}</div>

		<label for="email">이메일</label>
		<div>${boardVO.memberVO.name} (${boardVO.email}) / ${boardVO.ipAddr}</div>

		<label for="viewCnt">조회수</label>
		<div>${boardVO.viewCnt}</div>
		
		<label for="originFileName">첨부파일</label>
        <div>
            <a href="/board/file/download/${boardVO.id}">
                ${boardVO.originFileName}
            </a>
        </div>

		<label for="crtDt">등록일</label>
		<div>${boardVO.crtDt}</div>

		<label for="mdfyDt">수정일</label>
		<div>${boardVO.mdfyDt}</div>

		<label for="content">내용</label>
		<div>${boardVO.content}</div>
        
        <c:if test="${not empty sessionScope._LOGIN_USER_ && sessionScope._LOGIN_USER_.email eq boardVO.memberVO.email}">
			<div class="replies">
				<div class="reply-items"></div>
				<div class="write-reply">
					<textarea id="txt-reply"></textarea>
					<button id="btn-save-reply">등록</button>
					<button id="btn-cancel-reply">취소</button>
				</div>
			</div>

			<div class="btn-group">
				<div class="right-align">
					<a href="/board/modify/${boardVO.id}">수정</a>
					<a href="/board/delete/${boardVO.id}">삭제</a>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>
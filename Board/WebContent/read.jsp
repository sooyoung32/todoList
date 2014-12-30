<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 읽기</title>
<link type="text/css" rel="stylesheet" type="text/css" href="/Board_psy/css/board.css" media="all" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$('#comment').click(function() {
			if ($('#commentText').val() != null) {
				var data2 = {
					boardNo : $('#boardNo').val(),
					email : $('#email').val(),
					content : $('#commentText').val()
				};

				$.ajax({
					type : "post",
					url : "/Board_psy/insertComment.do",
					data : data2,
					success : function(result) {
						if (result == "Y") {
							alert("댓글이 입력되었습니다");
							location.reload(true);
						} else if (result == "N") {
							alert("댓글 입력시 오류가 발생하였습니다");
							location.reload(true);
						}
					},
					error : function() {
						location.reload(true);
						alert("코멘트 ajax 에러");
					}

				});
			} else {
				alert("내용을 입력해주세요");
			}
		});

		$('#reply').click(function() {
			location.href = "/Board_psy/replyForm.do?boardNo=${board.boardNo}";
		});

		$('.cDelete').click(function() {

			$.ajax({
				type : "post",
				url : "/Board_psy/deleteComment.do",
				data : {
					commentNo : $('#commentNo').val()
				},
				success : function(result) {
					if (result == "Y") {
						alert("댓글이 삭제되었습니다");
						location.reload(true);
					} else if (result == "N") {
						alert("댓글 삭제시 오류가 발생하였습니다");
						location.reload(true);
					}
				},
				error : function() {
					location.reload(true);
					alert("코멘트 삭제 ajax 에러");
				}

			});
		});

	});
</script>


</head>
<body>
<table>
<tr>
	<td class="back_layout"><a href="/Board_psy/boardList.do">◀게시판 목록</a></td>
</tr>	

<tr><td class="read_layout">
	<table border="1" style="border-collapse: collapse;" width="800px" height="500%">
		<tr>
			<td id="read_td">작성자</td>
			<td id="read_td2">${board.writer.name}<c:if
					test="${sessionScope.name eq board.writer.name}">
					<a href="/Board_psy/updateForm.do?boardNo=${board.boardNo}"><input
						type="button" id="modify" name="modify" value="수정" align="right"></a>
					<a href="/Board_psy/deleteForm.do?boardNo=${board.boardNo}"><input
						type="button" id="delete" name="delete" value="삭제" align="right"></a>
				</c:if>
			</td>
		</tr>

		<tr>
			<td id="read_td">제목</td>

			<td id="read_td2"><c:if test="${board.flag==1}">
					${board.title}
				</c:if> <c:if test="${board.flag==0}">
					본 글은 삭제되었습니다
				</c:if></td>
		</tr>
		<tr>
			<td colspan="2" id="read_td">내용</td>
		</tr>
		<tr>
			<c:if test="${board.flag==1}">
				<td colspan="2" id="read_td2">${board.content}</td>
			</c:if>
			<c:if test="${board.flag==0}">
				<td colspan="2" id="read_td2">본 글은 삭제되었습니다</td>
			</c:if>
		</tr>
	</table>
	
</td></tr>	

<tr><td style="padding: 0.5em;"></td></tr>

<tr><td class="file_layout">	
	<c:if test="${board.flag==1 }">
		<table border="1" style="border-collapse: collapse;" width="800px" height="500%">
			<tr>
				<td id="file_td">첨부파일</td>
			</tr>
			<tr>
				<c:if test="${empty board.files}">
					<td id="file_td2">첨부파일이 없습니다
					<td>
				</c:if>
				<c:if test="${!empty board.files}">
					<c:forEach items="${board.files}" var="file">
						<c:if test="${file.flag ==1 && !empty sessionScope.email}">
							<tr>
								<td id="file_td2"><a
									href="/Board_psy/download.do?savedPath=${file.savedPath}">${file.originalName}</a>
								<td>
							</tr>
						</c:if>
						<c:if test="${empty sessionScope.email}">
							<tr>
								<td id="file_td2" >${file.originalName}<td>
							</tr>
						</c:if>

					</c:forEach>
				</c:if>
			</tr>
		</table>
	</c:if>
</td></tr>

	<c:if test="${board.flag==0}">
	
		<span id="file_td2" >첨부파일이 없습니다</span>
	
	</c:if>

<tr><td style="padding:0.5em;"></td></tr>
<tr><td class="reply_btn">
	<table  width="800px" height="500%">
		<tr>
			<c:if test="${!empty sessionScope.name}">
				<td style="text-align: right; font-size: medium;"><input type="button" id="reply" name="reply" value="Reply"></td>
			</c:if>
			<c:if test="${empty sessionScope.name}">
				<td style="padding-left:1em; font-style: italic; color: gray; font-size:12pt;" >로그인 후 답글*댓글을 작성할 수 있습니다 </td>
			</c:if>
		</tr>
	</table>
	
</td></tr>	

<tr><td style="padding:0.5em;"></td></tr>

<tr><td class="comment_layout">
	<table border="1" style="border-collapse: collapse;" width="800px" height="500%">
		<tr>
			<c:if test="${!empty sessionScope.name}">
				<td id="comment_td">${sessionScope.name }</td>
				<td><textarea rows="3" cols="88" id="commentText"></textarea></td>
				<td ><input type="button" id="comment" name="comment"
					value="Comment"></td>
			</c:if>
			<c:if test="${empty sessionScope.name}"></c:if>
		</tr>
	</table>
	
</td></tr>	

<tr><td style="padding:0.5em;"></td></tr>

<tr><td class="comment_list">
	<table border="1" style="border-collapse: collapse;" width="800px" height="500%">
		<c:choose>
			<c:when test="${empty board.comments}">
				<tr>
					<td style="padding-left: 1em; font-style: italic; color: gray;">댓글이 없습니다.</td>
				</tr>
			</c:when>

			<c:when test="${!empty board.comments}">
				<c:forEach items="${board.comments }" var="comment">
					<c:if test="${comment.flag==1 }">
						<tr>
							<td id ="comment_td">${comment.writer.name}</td>
							<td id="comment_td2">${comment.content}
							
							<c:if test="${sessionScope.name eq comment.writer.name}">
								
								<input type="hidden" id="commentNo" name="commentNo" value="${comment.commentNo}"> 
								<input type="button"  id="cDelete" class="cDelete" name="cDelete" value="Delete">
								
							</c:if>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>

		</c:choose>
	</table>
</td></tr>		
		
</table>		
	<input type="hidden" name="boardNo" id="boardNo"
		value="${board.boardNo}">
	<input type="hidden" name="email" id="email"
		value="${sessionScope.email}">
</body>
</html>
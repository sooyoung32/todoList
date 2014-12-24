<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 읽기</title>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(function(){
	$('#comment').click(function(){
		if($('#commentText').val()!=null){
			var data2 = {
				boardNo : $('#boardNo').val(),
				email :$('#email').val(),
				content: $('#commentText').val()
			};
			
			$.ajax({
				type:"post",
				url:"/Board_psy/insertComment.do",
				data: data2,
				success:function(result){
					if(result == "Y"){
						alert("댓글이 입력되었습니다");
						location.reload(true);
					}else if(result == "N"){
						alert("댓글 입력시 오류가 발생하였습니다");
						location.reload(true);
					}
				},
				error:function(){
					location.reload(true);
					alert("코멘트 ajax 에러");
				}
				
			});
		}else{
			alert("내용을 입력해주세요");
		}
	});
	
	$('#reply').click(function() {
		location.href = "/Board_psy/replyForm.do?boardNo=${board.boardNo}";
	});
});
</script>


</head>
<body>
<body>

	<a href="/Board_psy/boardList.do">게시판 목록</a>
	<table border="1" style="border-collapse: collapse;" width="50%"
		height="50%">
		<tr>
			<td>작성자</td>
			<td>${board.writer.name}
		<c:if test="${sessionScope.name eq board.writer.name}">
			<input type="button" id="modify" name="modify" value="수정" align="right"> 
			<input type="button" id="delete" name="delete" value="삭제" align="right">
		</c:if>
			</td>
		</tr>

		<tr>
			<td>제목</td>
			<td>${board.title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${board.content}</td>
		</tr>
	</table>
	<table border="1" style="border-collapse: collapse;" width="50%"
		height="50%">
		<tr>
			<td>첨부파일</td>
		</tr>
		<tr>
			<c:if test="${empty board.files}">
				<td>첨부파일이 없습니다
				<td>
			</c:if>
			<c:if test="${!empty board.files}">
				<c:forEach items="${board.files}" var="file">
					<tr>
						<td><a href="/Board_psy/download.do?savedPath=${file.savedPath}">${file.originalName}</a><td>
					</tr>
				</c:forEach>
			</c:if>
		</tr>
	</table>
	
	<input type="button" id="reply" name="reply" value="답글쓰기">
	
	<table  border="1" style="border-collapse: collapse;" width="50%" height="50%" >
		<tr>
			<c:if test="${empty sessionScope.name}">
				<td colspan="3">로그인 후 댓글을 남겨주세요!</td>
			</c:if>
			<c:if test="${!empty sessionScope.name}">
				<td>${sessionScope.name }</td>
				<td><textarea rows="3" cols="100" id="commentText"></textarea></td>
				<td><input type="button" id="comment" name="comment" value="댓글달기"></td>
			</c:if>
		</tr>
	</table>
	<table  border="1" style="border-collapse: collapse;" width="50%"	height="50%">
		<c:choose>
			<c:when test="${empty board.comments}">
				<tr>
					<td>댓글이 없습니다.</td>
				</tr>
			</c:when>

			<c:when test="${!empty board.comments}">
				<c:forEach items="${board.comments }" var="comment">
					<tr comment="${comment}">
						<td>${comment.writer.name}</td>
						<td>${comment.content}</td>
					</tr>

				</c:forEach>
			</c:when>

		</c:choose>
	</table>
	<input type="hidden" name="boardNo" id="boardNo"  value="${board.boardNo}">
	<input type="hidden" name="email" id="email" value="${sessionScope.email}"> 
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정</title>
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

		$('#addFile').click(function(){
			var fileIndex = $('#fileTable tr').children().length;
			$('#fileTable').append('<tr><td><input type="file" name="fileList['+fileIndex+']"></td></tr>');
			
		});

		$('.deleteFile').click(function() {
				var fileNo = $(this).prev().val();
				alert(fileNo +"//파일번호");
				$(this).remove();
				$('#deletedFile').append('<tr><td><input type="hidden" name="deletedFileList" value="'+fileNo+'"></td></tr>');
		});
	});
</script>


</head>
<body>

	<form action="/Board_psy/update.do" method="post"	enctype="multipart/form-data">

		<a href="/Board_psy/boardList.do">게시판 목록</a>

		<table border="1" style="border-collapse: collapse;" width="30%"
			height="50%">
			<tr>
				<td>작성자</td>
				<td>${board.writer.name}
					<c:if test="${sessionScope.name eq board.writer.name}"> <a href="/Board_psy/updateForm.do?boardNo=${board.boardNo}">
						<input type="submit" id="modify" name="modify" value="수정" align="right"></a>
					</c:if>
				</td>
			</tr>

			<tr>
				<td>제목</td>
				<td><input type="text" id="title" name="title" size="50" value="${board.title}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="10" cols="50" name="content" id="content">${board.content}</textarea></td>
			</tr>

		</table>


		<table border="1" style="border-collapse: collapse;" width="30%" height="50%">
			<tr>
				<td>첨부파일</td>
			</tr>
			<tr>
				<c:if test="${empty board.files}">
					<td>첨부파일이 없습니다<td>
				</c:if>
			</tr>
				<c:if test="${!empty board.files}">
					<td>
					<c:forEach items="${board.files}" var="file">
						<c:if test="${file.flag==1}">
						<li id="deleteTr"> fn:${file.fileNo} - ${file.originalName}
						<input type="hidden" id="fileNo" value="${file.fileNo}">
						<input type="button" class="deleteFile" value="삭제">
						 </li></c:if>
					</c:forEach>
						<table id="deletedFile">
					
						</table>
					</td>
			</c:if>
			</tr>
		</table>
				
	<table border="1" style="border-collapse:collapse;" width="30%" height="30" id="fileTable">
		<tr>
			<td><input type="file" id="file" name="fileList[0]"></td>
		</tr>
	</table>
			<input type="button" id="addFile" name="addFile" value="파일추가">

		<h3></h3>
		<table border="1" style="border-collapse: collapse;" width="30%"
			height="50%">
			<c:choose>
				<c:when test="${empty board.comments}">
					<tr>
						<td>댓글이 없습니다.</td>
					</tr>
				</c:when>

				<c:when test="${!empty board.comments}">
					<c:forEach items="${board.comments }" var="comment">
						<tr>
							<td>${comment.writer.name}</td>
							<td>${comment.content}</td>
						</tr>

					</c:forEach>
				</c:when>

			</c:choose>
		</table>
		<input type="hidden" name="boardNo" id="boardNo" value="${board.boardNo}"> 
		<input type="hidden" name="email"  id="email" value="${sessionScope.email}">
	</form>
</body>
</html>
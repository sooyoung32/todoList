<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정</title>
<link type="text/css" rel="stylesheet" type="text/css"
	href="/Board_psy/css/board.css" media="all" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {

		$('#addFile').click(function() {
			var fileIndex = $('#fileTable tr').children().length;
			$('#fileTable').append('<tr fileIndex="' + fileIndex + '"><td><input type="file" name="fileList" id="fileList">'
				+ '<input class="fileDelete" type="button" value="파일삭제" ></td></tr>');
			
			$('.fileDelete').unbind("click").bind("click",function() {
				this.parentElement.parentElement.remove();
			});
		});
	
		$('.deleteFile').unbind("click").bind("click",function() {
			var fileNo = $(this).prev().val();
			alert(fileNo+"-----파일번호");
			this.parentElement.parentElement.remove();
			$('#deletedFile').append('<tr><td><input type="hidden" name="deletedFileList" value="'+fileNo+'"></td></tr>');
			
		});
		
// 		$('#modify2').click(function(){
// 			if(showFileSize()){
// 				alert("버튼 클릭?");
// 				$('#modify2').attr('disabled', true);
// 				$('#modify2').val('Sending..');
// 				$('#form').submit();
// // 				$('#form').attr("onsubmit", "return false").submit(); 
// 				alert("서브밋?");
// 			}
// 		});
		
		
});
	
		
	function showFileSize() {

		var input;

		if (!window.FileReader) {
			alert("파일 API가 이 브라우저에서 지원되지 않습니다");
			return false;
		}

		input = document.getElementsByName('fileList');
		//files라는 객체가 file 안에 존재함. files또한 리스트여서 그 중에 0번째를 가져오는 것. 
		for(var i =0; i<input.length ; i++){
			
			if(!input[i].files[0]){
				alert("파일을 다시 확인해 주세요");
				return false;
			}
			
			if (input[i].files[0].size == 0) {
				alert("파일명 " + input[i].files[0].name + " 을 다시 확인해 주세요");
				return false;
			}
		}
		return true;
		$('#form').submit();
	}
	
// 	function save(){
// 		if(showFileSize()){
// // 			alert("버튼 클릭?");
// 			$('#form').submit();
// 			return true;
// 	}
	
</script>


</head>
<body>

	<form action="/Board_psy/update.do" id="form" name="form"
		onsubmit="return showFileSize();" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td class="back_layout"><a href="/Board_psy/boardList.do">◀게시판
						목록</a></td>
			</tr>

			<tr>
				<td style="text-align: right"><a
					href="/Board_psy/updateForm.do?boardNo=${board.boardNo}"> <input
						type="submit" id="modify2" name="modify" value="Modify"
						align="right" onclick="save();"></a></td>
			</tr>


			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="read_layout">

					<table border="1" style="border-collapse: collapse;" width="650px"
						height="500%">
						<tr>
							<td id="read_td">작성자</td>
							<td>${board.writer.name}</td>
						</tr>

						<tr>
							<td id="read_td">제목</td>
							<td><input type="text" id="title" name="title" size="80"
								value="${board.title}"></td>
						</tr>
						<tr>
							<td id="read_td">내용</td>
							<td><textarea rows="10" cols="80" name="content"
									id="content">${board.content}</textarea></td>
						</tr>

					</table>
				</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="read_layout">
					<table border="1" style="border-collapse: collapse;" width="650px"
						height="500%">
						<tr>
							<td id="read_td">첨부파일</td>
						</tr>

						<tr>
							<c:if test="${empty board.files}">
								<td>첨부파일이 없습니다</td>
							</c:if>
						</tr>

						<c:if test="${!empty board.files}">
							<c:forEach items="${board.files}" var="file">
								<tr>
									<td><c:if test="${file.flag==1}">
							   ${file.originalName} -- ${file.fileNo}
							<input type="hidden" id="fileNo" value="${file.fileNo}">
											<input type="button" class="deleteFile" value="삭제">
										</c:if></td>
								</tr>
							</c:forEach>

							<table id="deletedFile">

							</table>

						</c:if>

					</table>
				</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td>
					<table border="1" style="border-collapse: collapse;" width="650px"
						height="500%" id="fileTable">
						<!-- 		<tr> -->
						<!-- 			<td><input type="file" id="file" name="fileList[0]"></td> -->
						<!-- 		</tr> -->
					</table> <input type="button" id="addFile" name="addFile" value="파일추가">

				</td>
			</tr>




			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="comment_list">
					<table border="1" style="border-collapse: collapse;" width="650px"
						height="500%">
						<c:choose>
							<c:when test="${empty board.comments}">
								<tr>
									<td>댓글이 없습니다.</td>
								</tr>
							</c:when>

							<c:when test="${!empty board.comments}">
								<c:forEach items="${board.comments }" var="comment">
									<tr>
										<td id="comment_td">${comment.writer.name}</td>
										<td id="comment_td2">${comment.content}</td>
									</tr>

								</c:forEach>
							</c:when>

						</c:choose>
					</table>

				</td>
			</tr>
		</table>
		<input type="hidden" name="boardNo" id="boardNo"
			value="${board.boardNo}"> <input type="hidden" name="email"
			id="email" value="${sessionScope.email}">
	</form>
</body>
</html>
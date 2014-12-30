<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<link type="text/css" rel="stylesheet" type="text/css" href="/Board_psy/css/board.css" media="all" />
</head>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$('#addFile')
				.click(
						function() {
							var fileIndex = $('#fileTable tr').children().length;
							$('#fileTable')
									.append(
											'<tr><td><input type="file" name="fileList['+fileIndex+']"></td></tr>');

						});

	});
</script>
<body>
	<form action="/Board_psy/reply.do" method="post"
		enctype="multipart/form-data">
		
<table>
<tr><td style="font-style: italic; color: gray;" >
		${boardNo} 글의 답글</td></tr>
		
<tr><td style="padding: 0.5em;"></td></tr>

<tr><td class="reply" style="text-align: right;"><input type="submit" id="reply"	name="reply" value="WRITE"></td></tr>

<tr><td style="padding: 0.5em;"></td></tr>

<tr><td class="write_layout">		
		<table border="1" style="border-collapse: collapse;" width="650px"
			height="30">
			<tr>
				<td>작성자</td>
				<td>${sessionScope.name}</td>
			</tr>

			<tr>
				<td>제목</td>
				<td><input type="text" id="title" name="title" size="80"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="10" cols="80" name="content" id="content"></textarea></td>
			</tr>
		</table>
		
</td></tr>
<tr><td style="padding: 0.5em;"></td></tr>

<tr><td>
		첨부파일
		<table border="1" style="border-collapse: collapse;" width="650px"
			height="30" id="fileTable">
			<tr>
				<td><input type="file" id="file" name="fileList[0]"></td>
			</tr>
		</table>

</td></tr>

<tr><td style="padding: 0.5em;"></td></tr>


</table>
		<input type="button" id="addFile" name="addFile" value="파일추가">
		<input type="hidden" id="boardNo" name="boardNo" value="${boardNo }">
		<input type="hidden" value="${sessionScope.email}" name="email" id="email ">
	</form>

</body>
</html>
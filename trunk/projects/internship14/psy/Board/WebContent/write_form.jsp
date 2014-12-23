<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
</head>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(function(){
	$('#addFile').click(function(){
		var fileIndex = $('#fileTable tr').children().length;
		$('#fileTable').append('<tr><td><input type="file" name="fileList['+fileIndex+']"></td></tr>');
		
	});
	
});
</script>
<body>
<form action="/Board_psy/write.do" method="post" enctype="multipart/form-data">
<table border="1" style="border-collapse:collapse;" width="30%" height="30">
	
	<tr>
		<td>작성자</td>
		<td>${sessionScope.name}
			<input type="submit" id="write" name="write" value="저장" ></td>
	</tr>
	
	<tr>
		<td>제목</td>
		<td><input type="text" id="title" name="title" size="50"></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea rows="10" cols="50"></textarea></td>
	</tr>
</table>
	
		첨부파일
<table border="1" style="border-collapse:collapse;" width="30%" height="30" id="fileTable">
	<tr>
		<td><input type="file" id="file" name="fileList[0]"></td>
	</tr>
	</table>
		<input type="button" id="addFile" name="addFile" value="파일추가">

	<input type="hidden" value="${sessionScope.email}" name="email" id="email ">
</form>

</body>
</html>
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
$(function(){
	$('#addFile').click(function(){
		var fileIndex = $('#fileTable tr').children().length;
		$('#fileTable').append('<tr><td><input type="file" name="fileList['+fileIndex+']"></td></tr>');
		
	});
	
});
</script>
<body>
<form action="/Board_psy/write.do" method="post" enctype="multipart/form-data">

<table>
<tr><td style="text-align: right;" ><input type="submit" id="write" name="write" value="WRITE" ></td></tr>

<tr><td style="padding: 0.5em;"></td></tr>

<tr><td class="write_layout">
<table border="1" style="border-collapse:collapse;" width="650px" height="30">
	
	<tr>
		<td>작성자</td>
		<td>${sessionScope.name}
			</td>
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
<tr><td style="padding: 0.5em; font-size: x-small;font-style: italic;color: gray;"> 파일은 최대 50M까지 업로드 가능합니다</td></tr>
<tr><td>
	<span style="color: gray;">첨부파일</span></td></tr>
	
<tr><td>	
<table border="1" style="border-collapse:collapse;" width="650px" height="30" id="fileTable">
	<tr>
		<td><input type="file" id="file" name="fileList[0]"></td>
	</tr>
	</table><td></td>
		<td><input type="button" id="addFile" name="addFile" value="파일추가"></td>

	
</table>	

	<input type="hidden" value="${sessionScope.email}" name="email" id="email ">

</form>

</body>
</html>
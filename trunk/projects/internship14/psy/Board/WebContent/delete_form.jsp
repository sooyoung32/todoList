<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 삭제</title>
</head>
<body>

<form action="/Board_psy/delete.do?boardNo=${board.boardNo }" method="post">
<table border="1" style="border-collapse: collapse;">
	<tr>
		<td>비밀번호를 입력해주세요</td>
	</tr>
	<tr>
		<td><input type="password" name="password" size="20"></td>
	</tr>

</table>
	<input type="submit" name="delete" value="삭제하기">
</form>	
	
	<input type="hidden" name="boardNo" value="${board.boardNo}">
</body>
</html>
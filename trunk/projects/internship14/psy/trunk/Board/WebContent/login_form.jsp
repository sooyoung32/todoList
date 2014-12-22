<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="/Board_psy/loginSuccess.do" id="login" method="post">
	<table border="1">
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email" id="email"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="password" id="password"></td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="submit" id="button" value="로그인">
<!-- 				<a href="#" onclick="window.open('/Board/joinForm.do', '회원가입', 'width=400,height=500')">회원가입</a> -->
			<a href="/Board_psy/joinForm.do"> 회원가입 </a>
			</td>
		</tr>
	
	</table>

</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<link type="text/css" rel="stylesheet" type="text/css"
	href="<c:url value ="/css/board.css"/>" media="all" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>


</head>
<body>
	<!-- 	<form action="./loginSuccess.do" method="post"> -->
	<table border="1" style="border-collapse: collapse;">
		<tr bgcolor="#F6CEE3">
			<td>이메일</td>
			<td><input type="text" name="email" id="email"></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td>비밀번호</td>
			<td><input type="password" name="password" id="password"></td>
		</tr>
		<tr>
			<td colspan="2"><div id="msg" style="font-style: oblique;color: red;background-color: #F6CEE3;"></div> </td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td colspan="2" style="text-align: center;"><input type="button"
				id="login" value="Login"> <a href="#"
				onclick="window.open('<c:url value ="/joinForm.do" />', '회원가입', 'width=400,height=500')">
					Join </a></td>
		</tr>

	</table>

	<!-- 	</forSm> -->

	<input type="hidden" id="uri" value="${preAddr}">

<script type="text/javascript">
	$(function() {
		$('#login').click(function() {

			var data2 = {
				email : $('#email').val(),
				password : $('#password').val(),
				preAddr : $('#uri').val()
			};
			$.ajax({
				type : "post",
				url : "<c:url value ="/login.do" />",
				data : data2,
				success : function(result) {
					
					if (result == 'noID') {
						$('#msg').html("등록되지 않은 ID입니다");
					} else if (result == 'noPW') {
						$('#msg').html("비밀번호가 올바르지 않습니다");
					} else if(result == 'success'){
						// $('#login').submit();
						alert("정상적으로 로그인되었습니다.");
						window.opener.location.reload();
						window.close();
					} else if(result == 'success2'){
						alert("정상적으로 로그인되었습니다.");
						location.reload();
						window.close();
					}
				},
				error : function() {
					alert("왜 에러야ㅠㅠ");
				}
			});
		});
		$('#email').focus();
		$(opener.document).find('#email').append('<td><input type="text" name="email" id="email" value="#email"></td>');
	});
</script>
</body>
</html>
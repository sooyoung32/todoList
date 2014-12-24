<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#login').click(function() {

			var data2 = {
				email : $('#email').val(),
				password : $('#password').val()
			};
			$.ajax({
				type : "post",
				url : "/Board_psy/login.do",
				data : data2,
				success : function(result) {
					var msg = "";
					if (result == 'noID') {
						msg = "등록되지 않은 ID입니다";
						alert(msg);
					} else if (result == 'noPW') {
						msg = "비밀번호가 올바르지 않습니다";
						alert(msg);
					} else if(result == 'success'){
						// $('#login').submit();
						alert("정상적으로 로그인되었습니다.");
						window.opener.location.reload();
						window.close();
					}
				},
				error : function() {
					alert("왜 에러야ㅠㅠ");
				}
			});
		});
	});
</script>

</head>
<body>
<!-- 	<form action="/Board_psy/loginSuccess.do" method="post"> -->
		<table border="1">
			<tr>
				<td>이메일</td>
				<td><input type="text" name="email" id="email"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password" id="password"></td>
			</tr>
			<tr><td colspan="2"><div id="msg"></div></td></tr>
			<tr>
				<td colspan="2"><input type="button" id="login" value="로그인">
					<a href="#"
					onclick="window.open('/Board_psy/joinForm.do', '회원가입', 'width=400,height=500')">
						회원가입 </a></td>
			</tr>

		</table>

<!-- 	</forSm> -->
</body>
</html>
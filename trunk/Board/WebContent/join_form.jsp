<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(function(){
	$('#dupCheck').click(function(){
		if($('#email').val()!=null){
			$.ajax({
				type:"post",
				url:"/Board_psy/dupCheck.do",
				data:{email:$('#email').val()},
				success:function(result){
					var msg="";
					if(result=="unusable"){
						msg="이미 등록된 이메일입니다";
						$('#hidden').val(0);
					}else{
						msg="사용가능한 이메일입니다";
						$('#hidden').val(1);
					}
					$('#msg').html(msg);	
				},
				error:function(){
					alert("dupCheck Ajax 에러ㅠㅠ");
				}
				
			});
		}else{
			alert("이메일을 입력해주세요");
		}
	});
	$('#button').click(function(){
		if($('#hidden').val()==0){
			alert("이메일 중복확인을 해주세요");
		}
		if()
		
		if($('#password').val()!=$('#checkPassword').val()){
			alert("비밀번호가 같지 않습니다");
		}else{
			alert("회원가입이 완료되었습니다");
			document.form.submit();
			//$('#button').submit();
		}
			
	});
	
});


</script>
<title>회원가입 화면</title>
</head>
<body>
<form action="/Board_psy/join.do" method="post" name="joinForm">
	<table border="1">
		<tr><td>이름</td><td><input type="text" name="name"></td></tr>
		<tr><td>이메일</td><td><input type="text" name="email" id="email"><input type="button" name="dupCheck" id="dupCheck" value="중복확인"></td></tr>
		<tr><td colspan="2"><div id="msg"></div></td></tr>
		
		<tr><td>비밀번호</td><td><input type="password" name="password" id="password"></td></tr>
		<tr><td>비밀번호확인</td><td><input type="password" name="checkPassword" id="checkPassword"></td></tr>
		
		<tr>
			<td colspan="2"><input type="submit" name="button" id="button" value="회원가입">
			<input type="hidden" value="0" name="hidden" id="hidden">
			</td>
		</tr>
	</table>
</form>

</body>
</html>
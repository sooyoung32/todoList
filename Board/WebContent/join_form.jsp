<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
					alert("이메일 중복체크 Ajax 에러ㅠㅠ");
				}
				
			});
		}else{
			alert("이메일을 입력해주세요");
		}
	});
	$('#join').click(function(){
		if ( formCheck() ) {
			var data2 = {
					email : $('#email').val(),
					password : $('#password').val(),
					name : $('#name').val()
				};
				$.ajax({
					type:"post",
					url:"/Board_psy/join.do",
					data:data2,
					success:function(result){
						if(result=="Y"){
							alert("회원가입 성공");
							$("#email" , opener.document).val($('#email').val());
							window.close();
						}else{
							alert("회원가입 실패");
						}
					},
					error:function(){
						alert("회원가입 AJAX 에러");
					}
				});
		}
	});

});

var formCheck = function(){
	if($('#hidden').val()==0){
		alert("이메일 중복확인을 해주세요");
		return false;
	}else if($('#password').val() != $('#checkPassword').val()){
		alert("비밀번호가 같지 않습니다");
		return false;
	}	
	return true;
};




</script>
<title>회원가입</title>
<link type="text/css" rel="stylesheet" type="text/css" href="/Board_psy/css/board.css" media="all" />
</head>
<body>
<!-- <form action="/Board_psy/joinSuccess.do"  method="post" name="joinForm" > -->
	<table border="1">
		<tr bgcolor="#F6CEE3"><td>이름</td><td><input type="text" name="name" id="name"></td></tr>
		<tr bgcolor="#F6CEE3"><td>이메일</td><td><input type="text" name="email" id="email"><input type="button" name="dupCheck" id="dupCheck" value="중복확인"></td></tr>
		<tr bgcolor="#F6CEE3"><td colspan="2"><div id="msg"></div></td></tr>
		
		<tr bgcolor="#F6CEE3"><td>비밀번호</td><td><input type="password" name="password" id="password"></td></tr>
		<tr bgcolor="#F6CEE3"><td>비밀번호확인</td><td><input type="password" name="checkPassword" id="checkPassword"></td></tr>
		
		<tr>
			<td colspan="2" style="text-align: center;"><input type="button" name="button" id="join" value="Join">
			<input type="hidden" value="0" name="hidden" id="hidden">
			</td>
		</tr>
	</table>
<!-- </form> -->

</body>
</html>
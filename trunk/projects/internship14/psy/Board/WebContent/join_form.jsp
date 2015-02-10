<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$('#dupCheck').click(function() {

			var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

			if (exptext.test($('#email').val()) == false) {
				//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
				alert("이메일형식이 올바르지 않습니다.");
				$('#email').focus();
				exit;
			}

			if ($('#email').val() != null) {
				$.ajax({
					type : "post",
					url : "<c:url value ="/dupCheck.do"/>",
					data : {
						email : $('#email').val()
					},
					success : function(result) {
						var msg = "";
						if (result == "unusable") {
							msg = "이미 등록된 이메일입니다";
							$('#hidden').val(0);
						} else {
							msg = "사용가능한 이메일입니다";
							$('#hidden').val(1);
						}
						$('#msg').html(msg);
					},
					error : function() {
						alert("이메일 중복체크 Ajax 에러ㅠㅠ");
					}

				});
			} else {
				alert("이메일을 입력해주세요");
			}
		});
		$('#join').click(
				function() {
					if (formCheck()) {
						var data2 = {
							email : $('#email').val(),
							password : $('#password').val(),
							name : $('#name').val()
						};
						$.ajax({
							type : "post",
							url : "<c:url value =/join.do" />",
							data : data2,
							success : function(result) {
								if (result == "Y") {
									alert("회원가입 성공");
									$("#email", opener.document).val($('#email').val());
									window.close();
								} else {
									alert("회원가입 실패");
								}
							},
							error : function() {
								alert("회원가입 AJAX 에러");
							}
						});
					}
				});

	});

	var formCheck = function() {
		$('#name').val($('#name').val().trim()); // javascript를 이용해서 trim() 구현하기 바로가기
		if (!chkName($('#name').val())) {
			alert('이름을 확인하세요.(한글 2~4자 이내)');
			$('#name').focus();
			return false;
		}

		if ($('#hidden').val() == 0) {
			alert("이메일 중복확인을 해주세요");
			return false;
		}

		if (!pwCheck()) {
			return false;
		}

		if ($('#password').val() != $('#checkPassword').val()) {
			alert("비밀번호가 같지 않습니다");
			return false;
		}

		return true;
	};

	var pwCheck = function() {

		var chk_num = $('#password').val().search(/[0-9]/g);
		var chk_eng = $('#password').val().search(/[a-z]/ig);
		var strSpecial = $('#password').val().search(
				/[`~!@#$%^&*|\\\'\";:\/?]/gi);
		var isCapslock = false;

		if ($('#password').val().length < 8) {
			alert("8자 이상의 비밀번호만 입력 가능 합니다.");
			return false;
		}

		if ($('#password').val().length > 12) {
			alert("12자 이하의 비밀번호만 입력 가능 합니다.");
			return false;
		}

		if ($('#password').val().length < 8 || $('#password').val().length > 12) {
			alert("비밀번호를 8자리 이상 12자리 이하로 입력 가능 합니다.");
			return false;
		}

		if (chk_num < 0 || chk_eng < 0 || strSpecial < 0) {
			alert('비밀번호는 문자,숫자,특수문자가 조합 되어야 합니다.');
			return false;
		}

		return true;

	};

	function chkName(str) {
		var reg_name = /^[가-힣]{2,4}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/;
		if (!reg_name.test(str)) {
			return false;
		}
		return true;
	}
</script>
<title>회원가입</title>
<link type="text/css" rel="stylesheet" type="text/css"
	href="<c:url value ="/css/board.css" /> media="all" />
</head>
<body>
	<!-- <form action="./joinSuccess.do"  method="post" name="joinForm" > -->
	<table border="1">
		<tr bgcolor="#F6CEE3">
			<td>이름</td>
			<td><input type="text" name="name" id="name" maxlength="4"></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td>이메일</td>
			<td><input type="text" name="email" id="email"><input
				type="button" name="dupCheck" id="dupCheck" value="중복확인"></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td colspan="2"><div id="msg"></div></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td>비밀번호</td>
			<td><input type="password" name="password" id="password"></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td>비밀번호확인</td>
			<td><input type="password" name="checkPassword"
				id="checkPassword"></td>
		</tr>
		<tr bgcolor="#F6CEE3">
			<td colspan="2"
				style="font-style: italic; color: red; font-size: x-small; padding-left: 1em; padding-right: 1em;">비밀번호는
				8~12 자리, 문자/숫자/특수문자가 조합되야합니다</td>
		</tr>

		<tr>
			<td colspan="2" style="text-align: center;"><input type="button"
				name="button" id="join" value="Join"> <input type="hidden"
				value="0" name="hidden" id="hidden"></td>
		</tr>
	</table>
	<!-- </form> -->

</body>
</html>
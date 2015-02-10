<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
</head>
<link type="text/css" rel="stylesheet" href="<c:url value ="/css/board.css" />" media="all" />
<link type="text/css" rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/blitzer/jquery-ui.css"  />
<link type="text/css" rel="stylesheet" href="<c:url value ="/css/loginBpopup.css" />" media="all" />

<script src=js/jquery-1.11.2.min.js></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="js/jquery.bpopup.js"></script>
<script src="js/jquery.bpopup.min.js"></script>
<script src="js/jquery.easy-confirm-dialog.js"></script>
<script src="js/object.js"></script>
<script src="js/login.js"></script>
<body>
	<form action="<c:url value ="/reply.do" />" id="form" name="form" method="post" enctype="multipart/form-data">
<div id="popup" class="Pstyle">
		<span class="b-close">X</span>
			<div class="content" style="height: auto; width: auto;">
      			<div class="fb-login-button" 
      				data-scope="public_profile,email"
      				data-max-rows="1" 
      				data-size="large" 
      				data-show-faces="false" 
      				data-auto-logout-link="false" 
      				data-default-audience = "friends"
      				onlogin="facebookLogin();"
      		></div>
				<div id="status"></div>
			<p></p>
			<div><input type="button" name="login" id="login" value="KwareLogin" onclick="fn_loginOpen()"></div>
				<br>케이웨어 계정으로 로그인
			</div>
	</div>
		<table>
			<tr>
				<td style="font-style: italic; color: gray;">${articleNo} 글의 답글</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="reply" style="text-align: right;"><input
					type="button" id="reply" onclick="save();" name="reply"
					value="WRITE"></td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="write_layout">
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
							<td><textarea rows="10" cols="80" name="content"
									id="content"></textarea>
								<DIV class=remaining>
									남은 글자수: <SPAN class="count">4000</SPAN>
								</DIV></td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td
					style="padding: 0.5em; font-size: x-small; font-style: italic; color: gray;">
					파일은 최대 50M까지 업로드 가능합니다</td>
			</tr>

			<tr>
				<td>첨부파일
					<table border="1" style="border-collapse: collapse;" width="650px"
						height="30" id="fileTable">
					</table>
			<tr>
				<td><input type="button" id="addFile" name="addFile"
					value="파일추가"></td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>


		</table>
		<input type="hidden" id="articleNo" name="articleNo" value="${articleNo }">
		<input type="hidden" value="${sessionScope.email}" name="email"
			id="email ">
	</form>
<script type="text/javascript">
	$(function() {
		$('#addFile').click(function() {
			var fileIndex = $('#fileTable tr').children().length;
			$('#fileTable').append('<tr fileIndex="' + fileIndex + '"><td><input type="file" name="fileList" id="fileList">'
				+ '<input class="fileDelete" type="button" value="파일삭제" ></td></tr>');
			$('.fileDelete').unbind("click").bind("click",function() {
				this.parentElement.parentElement.remove();
			});

		});
});
	
	function showFileSize() {

		var input;

		if (!window.FileReader) {
			alert("파일 API가 이 브라우저에서 지원되지 않습니다");
			return;
		}

		input = document.getElementsByName('fileList');
		//files라는 객체가 file 안에 존재함. files또한 리스트여서 그 중에 0번째를 가져오는 것. 
		for(var i =0; i<input.length ; i++){
			if(!input[i].files[0]){
				alert("파일을 다시 확인해 주세요");
				return false;
			}
			if (input[i].files[0].size == 0) {
				alert("파일명 " + input[i].files[0].name + " 을 다시 확인해 주세요");
				return false;
			}else if(input[i].files[0].size > 5242880){
				alert("파일명 " + input[i].files[0].name + "의 용량이 초과되었습니다"+"\n"+"다시 선택해 주세요");
				return false;
			}
		}
		return true;
			
	}
	
	function save(){
		$.ajax({
			type : "post",
			url : "<c:url value ="/ajaxLoginCheck.do" />",
			data : {ajaxYn: "Y"},
			success : function(result) {
				if(result == "E"){
					alert("먼저 로그인을 해주세요"); 
//						location.replace("./articleList.do");
// 					loginOpen = window.open('./loginForm.do', '로그인', 'width=300, height=200');
					go_popup();
				}else if (result == "GO_TO") {
					if($('input[name*=title]').val()==""){
						alert("제목을 입력해 주세요");
						return false;
					}
					
					if($('input[name*=content]').val()==""){
						alert("내용을 입력해 주세요");
						return false;
					}
					
					if(showFileSize()){
						$('#reply').attr('disabled', true);
						$('#reply').val('Sending..');
						$('#form').submit();
					}

				}
			},
			error : function() {
				window.location.reload(true);
				alert("writeCheck ajax 에러");
			}

		});
		
		
	}
	
	$(function() {
	    $('.remaining').each(function() {
	        var $count = $('.count', this);
	        var $input = $(this).prev();
	        var maximumCount = $count.text() * 1;
	        var update = function() {
	            var before = $count.text() * 1;
	            var now = maximumCount - $input.val().length;
	            if (now < 0) {
	                var str = $input.val();
	                alert('글자 입력수가 초과하였습니다.');
	                $input.val(str.substr(0, maximumCount));
	                now = 0;
	            }
	            if (before != now) {
	                $count.text(now);
	            }
	        };
	        $input.bind('input keyup paste', function() {
	            setTimeout(update, 0);
	        });
	        update();
	    });
	});
	
/////////////////////////////////////////////////////////////////////////
	function facebookLogin(){
		FB.login(function(response){
			FB.api('/me',function(user) {
				console.log('Successful login for: '+ user.name);
				console.log(JSON.stringify(user));
				document.getElementById('status').innerHTML = 'Thanks for logging in, '	+ user.name	+ ' , '	+ user.email+ ' ! ';
				$.ajax({
					url : '<c:url value="/fbLogin.do" />',
					data : {
						fbUserId : response.authResponse.userID,
						fbToken : response.authResponse.accessToken,
						name : user.name,
						email : user.email
					},
					success : function(result) {
						if (result == "success") {
							alert("로긴석세스 : "+result);
							location.reload();
						} else {
							alert("페북 로그인 중 오류 발생");
						}
					  },
		  	    	error : function() {
	  			      alert("페북 로그인 에이젝스 처리중 에러가 발생했습니다");
		    		  location.reload();
					}
				});
			});
		});
	}

		window.fbAsyncInit = function() {
			FB.init({
				appId : '829852567056773',
				cookie : true, // 쿠키가 세션을 참조할 수 있도록 허용
				xfbml : true, // 소셜 플러그인이 있으면 처리
				version : 'v2.1' // 버전 2.1 사용
			});

			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
	};
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 읽기</title>
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

</head>
<body>
<form action="<c:url value="/articleList.do" />" id="form" name="form" method="post">
	<input type="hidden" name="page"> 
	<input type="hidden" name="articleNo" id="articleNo" value="${article.articleNo}"> 
	<input type="hidden" name="email"	id="email" value="${sessionScope.email}"> 
	<input type="hidden" name="searchValue" id="searchValue" value="${searchValue}"> 
	<input type="hidden" name="searchKey" id="searchKey" value="${searchKey}">
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
			<td class="back_layout">
			<a href="javascript:fn_pageMove(${page})">◀게시판 목록</a></td>
		</tr>

		<tr>
			<td class="read_layout">
				<table border="1" style="border-collapse: collapse;" width="800px" height="500%">
					<tr>
						<td id="read_td">작성자</td>
						<td id="read_td2">${article.writer.name}
							<c:if test="${sessionScope.name eq article.writer.name}">
									<input	type="button" id="modify" name="modify" value="수정" align="right">
									<input type="button" id="delete" name="delete" value="삭제"	align="right">
							</c:if>
						</td>
					</tr>

					<tr>
						<td id="read_td">제목</td>
						<td id="read_td2">
							<c:if test="${article.deletionStatus == 'PRESENT'}">${article.title}</c:if> 
							<c:if test="${article.deletionStatus=='DELETED'}">본 글은 삭제되었습니다</c:if>
						</td>
					</tr>
						
					<tr>
						<td colspan="2" id="read_td">내용</td>
					</tr>
						
					<tr>
						<c:if test="${article.deletionStatus == 'PRESENT'}">
							<td colspan="2" id="read_td2">${article.content}</td>
						</c:if>
						<c:if test="${article.deletionStatus=='DELETED'}">
							<td colspan="2" id="read_td2">본 글은 삭제되었습니다</td>
						</c:if>
					</tr>
				</table>
			</td>
		</tr>

		<tr><td style="padding: 0.5em;"></td></tr>

		<tr>
			<td class="file_layout">
				<c:if test="${article.deletionStatus == 'PRESENT'}">
					<table border="1" style="border-collapse: collapse;" width="800px"	height="500%">
						<tr>
							<td id="file_td">첨부파일</td>
						</tr>
						<tr>
							<c:if test="${empty article.files}">
								<td id="file_td2">첨부파일이 없습니다<td>
							</c:if>
							<c:if test="${!empty article.files}">
								<c:forEach items="${article.files}" var="file">
									<c:if test="${file.deletionStatus == 'PRESENT' && !empty sessionScope.email}">
										<tr>
											<td id="file_td2">
											<a	href="<c:url value ="/download.do?savedPath=${file.savedPath}" />">▶ &nbsp; ${file.originalName}</a><td>
										</tr>
									</c:if>
									<c:if test="${empty sessionScope.email}">
										<tr>
											<td id="file_td2">${file.originalName}<td>
										</tr>
									</c:if>

								</c:forEach>
							</c:if>
						</tr>
					</table>
				</c:if>
			</td>
		</tr>

		<tr>
			<c:if test="${article.deletionStatus=='DELETED'}">
				<td><span id="file_td2">첨부파일이 없습니다</span></td>
			</c:if>
		</tr>
		
		<tr><td style="padding: 0.5em;"></td></tr>
					
	<c:if test="${not empty sessionScope.email}">
		<tr>
			<td><div id="fb-root">
				<div class="fb-like" 
					data-href="<c:url value ="/read.do?articleNo=${article.articleNo}&isHitCount=true&page=1&searchKey=&searchValue=" />"
					data-layout="standard" 
					data-action="like" 
					data-colorscheme="light"
					data-show-faces="false" 
					data-share="true"></div>
				<div id="fb-root"></div>
			</td>
		</tr>
						
		<tr>
			<td>
				<div class="fb-comments" 
					 data-href="<c:url value ='/read.do?articleNo=${article.articleNo}&isHitCount=true&page=1&searchKey=&searchValue=' />" 
					 data-numposts="5" 
					 data-colorscheme="light">
				</div>
				<div id="fb-root"></div>
			</td>
		</tr>
	</c:if>
					
	<tr><td style="padding: 0.5em;"></td></tr>
	<tr>
		<td class="reply_btn">
			<table width="800px" height="500%">
				<tr>
					<c:if test="${!empty sessionScope.name && article.deletionStatus == 'PRESENT'}">
						<td style="text-align: right; font-size: medium;">
							<input	type="button" id="reply" name="reply" value="Reply"></td>
					</c:if>
					<c:if test="${empty sessionScope.name}">
						<td	style="padding-left: 1em; font-style: italic; color: gray; font-size: 12pt;">
							로그인 후 답글*댓글을 작성할 수 있습니다</td>
					</c:if>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td style="padding: 0.5em;"></td></tr>

	<tr>
		<td class="comment_layout">
			<table border="1" style="border-collapse: collapse;" width="800px"	height="500%">
		      <tr>
				<c:if test="${!empty sessionScope.name  && article.deletionStatus == 'PRESENT'}">
					<td id="comment_td">${sessionScope.name }</td>
					<td>
						<textarea rows="3" cols="88" id="commentText"></textarea>
						<DIV class=remaining>남은 글자수: <SPAN class="count">2000</SPAN></DIV>
					</td>
					<td><input type="button" id="comment" name="comment" value="Comment"></td>
				</c:if>
			  </tr>
			</table>
		</td>
	</tr>

	<tr><td style="padding: 0.5em;"></td></tr>

	<tr>
		<td class="comment_list">
			<div style="width: 800px; height: 350px; overflow: scroll;">
				<table border="1" style="border-collapse: collapse;" width="800px">
					<c:choose>
						<c:when test="${empty article.comments}">
							<tr>
								<td	style="padding-left: 1em; font-style: italic; color: gray;">
									댓글이	없습니다.
								</td>
							</tr>
						</c:when>

						<c:when test="${!empty article.comments}">
							<c:forEach items="${article.comments }" var="comment">
								<c:if test="${comment.deletionStatus == 'PRESENT'}">
									<tr>
										<td id="comment_td">${comment.writer.name}</td>
										<td id="comment_td2" class="comment_td2">
											<div id="content" class="content">${comment.content}</div> 
											<c:if test="${sessionScope.name eq comment.writer.name}">
												<input type="hidden" name="commentNo"value="${comment.commentNo}" />
												<input type="button" class="cDelete" name="cDelete"	value="덧글삭제" />
												<input type="button" class="cModify" name="cModify" value="덧글수정" />														
												<input type="hidden" name="cContent" value="${comment.content}" />
											</c:if>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:when>
					</c:choose>
				</table>
			</div>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">

//FB좋아요
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
 
//FB코멘트
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&appId=829852567056773&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
	
	var defaultAjaxDataSet = {
		type : "post",
		success : function(result) {
			if (result == "E") {
				alert("먼저 로그인을 해주세요");
// 				loginOpen = window.open('./loginForm.do', '로그인','width=300, height=200');
				go_popup();
			} else if (result == "GO_TO") {
				location.href = "<c:url value ='/replyForm.do?articleNo=${article.articleNo}'/>";
			} else if (result == "C_WRITE_SUCCESS") {
				alert("댓글이 입력되었습니다");
				location.reload(true);
			} else if (result == "C_WRITE_FAIL") {
				alert("댓글 입력시 오류가 발생하였습니다");
				location.reload(true);
			} else if (result == "C_DELETE_SUCCESS") {
				alert("댓글이 삭제되었습니다");
				location.reload(true);
			} else if (result == "C_DELETE_FAIL") {
				alert("댓글 삭제시 오류가 발생하였습니다");
				location.reload(true);
			} else if (result == "C_UPDATE_SUCCESS") {
				alert("덧글이 수정되었습니다");
				location.reload(true);
			} else if (result == "C_UPDATE_FAIL") {
				alert("덧글 수정시 오류가 발생하였습니다");
				location.reload(true);
			}
		},
		error : function() {
			alert("ajax 처리 중 에러가 발생하였습니다");
			window.location.reload(true);
		}
	};

	$(function() {
		$('#comment').click(function() {
			if ($('#commentText').val() == "") {
				alert("덧글을 작성해주세요!");
				return false;
			}
			if ($('#commentText').val() != null) {
				var data2 = {
					articleNo : $('#articleNo').val(),
					email : $('#email').val(),
					content : $('#commentText').val(),
					ajaxYn : 'Y',
				};

				defaultAjaxDataSet.data = data2;
				defaultAjaxDataSet.url = "<c:url value ="/insertComment.do"/>";
				$.ajax(defaultAjaxDataSet);

			} else {
				alert("내용을 입력해주세요");
			}
		});

		$('#reply').click(function() {
			defaultAjaxDataSet.data = {
				ajaxYn : "Y",
				articleNo : $('#articleNo').val()
			};
			defaultAjaxDataSet.url = "<c:url value ="/ajaxLoginCheck.do" />";
			$.ajax(defaultAjaxDataSet);
		});

		
	var updateAjaxDataSet = {
			type : "post",
			url : "<c:url value ="/ajaxUpdateLoginCheck.do" />",
			data : {
				ajaxYn : "Y",
				articleNo : $('#articleNo').val()
			},
			error : function() {
				alert("왜 에러야ㅠㅠ");
			}
		};	
		
		$('#modify').click(function() {		
			updateAjaxDataSet.success = function(result) {
					if (result.isAjax == 'E') {
						alert("먼저 로그인을 해주세요");
// 						loginOpen = window.open(./loginForm.do', '로그인', 'width=300, height=200');
						go_popup();
					} else {
						location.href = "./updateForm.do?articleNo=" + result.articleNo;
					}
				};
			$.ajax(updateAjaxDataSet);	
		});
			
		
		
		$('#delete').click(	function() {
			updateAjaxDataSet.success =  function(result) {
				if (result.isAjax == 'E') {
					alert("먼저 로그인을 해주세요");
					go_popup();
				} else {
					var $result = confirm("삭제 하시겠습니까?");
						if ($result) {
							location.replace("<c:url value = '/delete.do?articleNo=${article.articleNo}'/>");
						}
				}
			};
			
			$.ajax(updateAjaxDataSet);		
		});
			
		$('.cDelete').click(function() {
			// alert($(this).prev().val() + "//삭제 코멘트번호");
			defaultAjaxDataSet.data = {commentNo : $(this).prev().val(),ajaxYn : 'Y'};
			defaultAjaxDataSet.url = "<c:url value = "/deleteComment.do" />";
			$.ajax(defaultAjaxDataSet);

		});

		$('.cModify').click(
			function() {
				$(this).hide();
				var cContent = $(this).next().val();
				var commentNo = $(this).prev().prev().val();
				// alert(cContent + "//코멘트내용//  " + commentNo + "  //코멘트번호");
				var $test = $(this).prev().prev().prev();
				$test.text("");

				var $here = $("<div/>");
				$here.attr("id", "here");

				var $textarea = $("<textarea rows='3' cols='88'/>");
				$textarea.val(cContent);

				var $cancleBtn = $('<button  style="font-weight: bold;"/>');
				$cancleBtn.text('수정취소');

				$cancleBtn.click(function() {
					$test.text(cContent);
					$here.remove();
					window.location.href = window.location.href;
				});

				var $button = $('<button  style="font-weight: bold; vertical-align: top;"/>');
				$button.text('덧글수정');

				$button.click(function() {
					if ($textarea.val() != null) {
						defaultAjaxDataSet.data = {
						commentNo : commentNo,
						content : $textarea.val(),
						ajaxYn : 'Y'
					};
						defaultAjaxDataSet.url = "<c:url value ="/updateComment.do" />";
						$.ajax(defaultAjaxDataSet);

					} else {
					alert("코멘트 내용이 없어요~~");
						window.location.reload(true);
					}
				});

				$here.append($textarea);
				$here.append($button);
				$here.append($cancleBtn);
				$(this).parent().append($here);
		});

	});

	function fn_pageMove(page) {
		$("input[name='page']").val(page);
		!document.form.submit();
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
//////////////////////////////////////////////////////////////////////////////////////////////
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
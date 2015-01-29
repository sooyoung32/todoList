<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" type="text/css" href="/Board_psy/css/board.css" media="all" />


<title>게시판 목록 </title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/blitzer/jquery-ui.css" type="text/css" />

<script src="http://code.jquery.com/jquery-1.10.3.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->

<script src="http://cfs.tistory.com/custom/blog/173/1736984/skin/images/jquery.bpopup.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

<script src="js/jquery.easy-confirm-dialog.js"></script>
<!-- <script type="text/javascript" id="facebook-jssdk" src="http://connect.facebook.net/en_US/sdk.js"></script> -->
</head>
<script type="text/javascript">

function go_popup() {
		$('#popup').bPopup();
};
</script>


<style type="text/css">

.Pstyle {
	opacity: 0;
	display: none;
	position: relative;
	width: auto;
	border: 5px solid #fff;
	padding: 20px;
	background-color: #fff;
}

.b-close {
	position: absolute;
	right: 5px;
	top: 5px;
	padding: 5px;
	display: inline-block;
	cursor: pointer;
}
</style>

<body>
	<fmt:requestEncoding value="UTF-8" />
	<form action="/Board_psy/boardList.do" id="form" name="form" method="post">
		<input type="hidden" name="totalBoardCount" value="${boardPage.totalBoardCount}"> 
		<input type="hidden" name="page"> <input type="hidden" name="prePage" value="${boardPage.prePage}">
		<input type="hidden" name="nextPage" value="${boardPage.nextPage}">

		<table>
			<tr>
				<td class="title_layout"><h1>KWARE 게시판</h1></td>
			</tr>
			<tr>
				<td class="back_layout"><a href="/Board_psy/boardList.do">◀처음으로</a></td>
			</tr>

			<tr>
				<td class="user_layout">
					
					<span id="signinButton">
 						 <span class="g-signin"
   							 data-callback="signinCallback"
    				    	 data-clientid="13608227291-010l8ejltdeajig3d9ardslq6uo3ng5a.apps.googleusercontent.com"
   							 data-cookiepolicy="single_host_origin"
   							 data-requestvisibleactions="http://schemas.google.com/AddActivity"
    						 data-scope="https://www.googleapis.com/auth/plus.login">
 					 	</span>
					</span>
				
<!-- 				<div class="fb-login-button" data-max-rows="1" data-size="medium" data-show-faces="false" data-auto-logout-link="true" onclick="fbLogin();"></div> -->
					<c:choose>
					
					<c:when test="${empty sessionScope.email}">
						<input type="button" value="로그인" onclick="go_popup()">
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
<!-- 						<input type="button" name="login" id="login" value="Login" onclick="fn_loginOpen()"> -->
					</c:when>
					<c:when test="${!empty sessionScope.email}">
							${sessionScope.name} 님 환영합니다! <input type="button" name="logout" id="logout" value="Logout"/>
						<div id="dialog-confirm"></div>
<!-- 																<a href="#" onclick="FB.logout();" id="FBLogout">[Facebook logout]</a><br> -->
					</c:when>
					</c:choose>
				</td>
			</tr>

			<tr>
				<td class="search_layout">
					<table class="search_table">
						<tr>
							<td class="search_td"><select name="searchKey" class="combobox">
								<option value="ALL" <c:if test="${searchKey=='ALL'}">selected</c:if>>전체</option>
								<option value="CONTENT" <c:if test="${searchKey=='CONTENT'}">selected</c:if>>내용</option>
								<option value="TITLE" <c:if test="${searchKey=='TITLE'}">selected</c:if>>제목</option>
								<option value="NAME" <c:if test="${searchKey=='NAME'}">selected</c:if>>작성자</option>
							</select> 
							<input type="text" name="searchValue" value="${searchValue}" class="txtbox" size="25" /> 
							<!-- button onclick="fn_pageMove(1)">검색</button --> 
							<input type="button" name="searchBtn" value="검색" /></td>
						</tr>
					</table>
				</td>
			</tr>


			<tr>
				<td class="totalBoard_layout">총게시글 수 : ${boardPage.totalBoardCount}</td>

			</tr>

			<tr>
				<td class="list_layout">
					<table border="1" style="border-collapse: collapse;width : 1024px;" class="boardList">
						<c:if test="${empty boardPage.boardList }">
							<tr>
								<td>게시글이 없습니다.</td>
							</tr>
						</c:if>
						<c:if test="${!empty boardPage.boardList }">

							<tr align="center">
								<td width="80px">글번호</td>
								<td width="100px">작성자</td>
								<td width="300px">제목</td>
								<td>첨부</td>
								<td width="50px">댓글</td>
								<td width="80px">조회수</td>
							</tr>

							<c:forEach items="${boardPage.boardList}" var="board">
								<tr>
									<td align="center">${board.boardNo} </td>
									<td align="center" width="100px">${board.writer.name}</td>

									<td id="boardList_title" width="300px">
										<c:if test="${board.indent == 0 && board.flag==1}">
											<a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true&page=${boardPage.pageNo}&searchKey=${searchKey}&searchValue=${searchValue}">${board.title}</a>
										</c:if>
										<c:if test="${board.indent == 0 && board.flag==0}">
											<span style="font-style: italic; color: gray; font-size: x-small;">
												<a	href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true&page=${boardPage.pageNo}&searchKey=${searchKey}&searchValue=${searchValue}">삭제된 글입니다</a>
											</span>
										</c:if>
										<c:if test="${board.indent > 0 && board.flag ==1}">
											<c:forEach begin="1" end="${board.indent}">&nbsp;&nbsp;</c:forEach>
											<a href="javascript:fn_gotoBoard('${board.boardNo}')">ㄴ[Re]&nbsp;${board.title}</a>
										</c:if> 
										<c:if test="${board.indent > 0 && board.flag ==0}">
											<c:forEach begin="1" end="${board.indent}">&nbsp;&nbsp;</c:forEach>
											<span style="font-style: italic; color: gray; font-size: x-small;"> 
												<a	href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true&page=${boardPage.pageNo}&searchKey=${searchKey}&searchValue=${searchValue}"> ㄴ&nbsp;삭제된 글입니다</a>
											</span>
										</c:if>
									</td>
									<td align="center" width="100px">
										<c:choose>
											<c:when test="${!empty board.files }">
												<c:forEach items="${board.files}" var="file">
													<c:choose>
														<c:when test="${!empty sessionScope.email  && board.flag == 1}">
															<a href="/Board_psy/download.do?savedPath=${file.savedPath}"> <img alt="${file.originalName}" src="/Board_psy/img/file.jpg" width="20px" height="20px"></a>
														</c:when>
														<c:otherwise>
															<img alt="${file.originalName}" src="/Board_psy/img/file.jpg" width="20px" height="20px">
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>


											<c:otherwise>
												${fn:length(board.files)}
											</c:otherwise>
										</c:choose>
									</td>
									<td align="center" width="50px">${fn:length(board.comments)}</td>
									<td align="center" width="80px">${board.hitCount}</td>
								</tr>

							</c:forEach>
							
							
							
							<tr>
								<td colspan="6" align="center"><a href="javascript:fn_pageMove(1)"> &nbsp; << &nbsp; </a> 
									<a href="javascript:fn_pageMove(${boardPage.prePage})"> &nbsp; < &nbsp;</a> 
									<c:forEach var="num" begin="${boardPage.startPage}" end="${boardPage.endPage}">
										<c:choose>
											<c:when test="${num eq boardPage.pageNo}">
												<a href="javascript:fn_pageMove(${num})" class="choice" style="font-weight: bold;">[${num}]</a>
											</c:when>
											<c:otherwise>
												<a href="javascript:fn_pageMove(${num})">[${num}]</a>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
									<a href="javascript:fn_pageMove(${boardPage.nextPage})">&nbsp; > &nbsp;</a>
									<a href="javascript:fn_pageMove(${boardPage.totalPage})">&nbsp; >> &nbsp;</a>
								</td>
							</tr>
						</c:if>
					</table>
				</td>
			</tr>

			<tr>
				<td class="footer_layout">
					<input type="hidden" value="${sessionScope.email }" name="email"> 
					<input type="button" id="writeForm"	name="writeForm" value="Write" style="color: navy;">
					<c:if test="${empty sessionScope.email}">
						로그인 후 게시글을 작성하세요!
					</c:if>
				</td>
			</tr>

		</table>
		
		<input type="hidden" name="isHitCount" /> <input type="hidden" name="boardNo" />
		
	</form>

<div id="fb-root" class=" fb_reset"></div>
         



<script type="text/javascript">
	
;(function($) {
    $(function() {
        var $p1 = $('#popup'),
            $p2 = $('#popup2');
            // i = 0;

        $('body').on('click', '.small', function(e) {
            e.preventDefault();
            var popup = $(this).hasClass('pop1') ? $p1 : $p2,
                content = $('.content'),
                self = $(this);

            popup.bPopup(self.data('bpopup') || {});
     });

 });
})(jQuery);

////////////////////////////////////////////////////////////////////google login 
    
    //자바 스크립트 파일 비동기 로드 
    (function() {
       var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
       po.src = 'https://apis.google.com/js/client:plusone.js';
       var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
     })();

	//로그인 처리
    function signinCallback(authResult) {
    	  if (authResult['access_token']) {
    	    // 승인 성공
    	    // 사용자가 승인되었으므로 로그인 버튼 숨김. 예:
    	    document.getElementById('signinButton').setAttribute('style', 'display: none');
    	  } else if (authResult['error']) {
    	    // 오류가 발생했습니다.
    	    // 가능한 오류 코드:
    	    //   "access_denied" - 사용자가 앱에 대한 액세스 거부
    	    //   "immediate_failed" - 사용자가 자동으로 로그인할 수 없음
    	    // console.log('오류 발생: ' + authResult['error']);
    	  }
    	}

    //액세스 토큰 취소 및 앱 연결 해제
    function disconnectUser(access_token) {
    	  var revokeUrl = 'https://accounts.google.com/o/oauth2/revoke?token=' +
    	      access_token;

    	  // 비동기 GET 요청을 수행합니다.
    	  $.ajax({
    	    type: 'GET',
    	    url: revokeUrl,
    	    async: false,
    	    contentType: "application/json",
    	    dataType: 'jsonp',
    	    success: function(nullResponse) {
    	      // 사용자가 연결 해제되었으므로 작업을 수행합니다.
    	      // 응답은 항상 정의되지 않음입니다.
    	    },
    	    error: function(e) {
    	      // 오류 처리
    	      // console.log(e);
    	      // 실패한 경우 사용자가 수동으로 연결 해제하게 할 수 있습니다.
    	      // https://plus.google.com/apps
    	    }
    	  });
    	}
    	// 버튼 클릭으로 연결 해제를 실행할 수 있습니다.
    	$('#revokeButton').click(disconnectUser);

//This is called with the results from from FB.getLoginStatus().

	function statusChangeCallback(response) {
		console.log('statusChangeCallback');
		console.log(response);

		if (response.status === 'connected') {

		} else if (response.status === 'not_authorized') {
// 			document.getElementById('status').innerHTML = 'Please log '
		} else {
// 			document.getElementById('status').innerHTML = 'Please log '	+ 'into Facebook.';
		
		}
	}


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
			// 				location.reload();
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

	function checkLoginState() {
		FB.getLoginStatus(function(response) {
			statusChangeCallback(response);
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
		
		
	FB.Event.subscribe('auth.logout', function() {
			
// 			var result = confirm('현재 Facebook으로 로그인 된 모든 사이트에서 로그 아웃하시겠습니까?'
// 								+ 'Yes면 모두 로그아웃 No면 Kware Board에서만 로그아웃');

// 			if (result) {
// 				//yes
// 				location.href = "/Board_psy/logout.do";
// 			} else {
				$.ajax({
					url : '<c:url value="/fbLogout.do" />',
					success : function(result) {
						if (result == "logout") {
							alert("로그아웃 성공 : " + result);
							location.reload();

						} else {
							alert("페북 로그아웃 중 오류 발생");
							location.reload();
						}
					},
					error : function() {
						alert("페북 로그아웃 에이젝스 처리중 에러가 발생했습니다");
						location.reload();
					}

				});
				location.replace('index.php');
				
// 			}

		});
	};
	
	

	// 로그인이 성공한 다음에는 간단한 그래프API를 호출한다.
	// 이 호출은 statusChangeCallback()에서 이루어진다.

	function testAPI() {
		console.log('Welcome!  Fetching your information.... ');
		FB.api(	'/me',	function(response) {
			console.log('Successful login for: '+ response.name);
						document.getElementById('status').innerHTML = 'Thanks for logging in, '	+ response.name + '!';
			});
	}

	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/en_US/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

	
	function fnOpenNormalDialog() {
		 $("#dialog-confirm").html("FB으로 로그인된 모든 계정에서 로그아웃 하시겠습니까?");
		$("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
// 	        title: "Logout",
	        height: 250,
	        width: 400,
	        buttons: {
	            "Yes": function () {
	                callback(true);
	                $(this).dialog('close');
	            },
	            "No": function () {
	                callback(false);
	                $(this).dialog('close');
	                
	            }
	        }
	    });

	    // Define the Dialog and its properties.
	}
	
	
	function callback(value) {
	    if (value) {
	    	FB.logout();
	        alert("Confirmed");
	    } else {
	    	location.href = "/Board_psy/logout.do";
	        alert("Rejected");
	    }
	}
	
// 	$('FBLogout').click(fnOpenNormalDialog());
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	$(function() {
		
		$('#logout').click(function(){
			 $("#dialog-confirm").html("FB으로 로그인된 모든 계정에서 로그아웃 하시겠습니까?");
				$("#dialog-confirm").dialog({
			        resizable: false,
			        modal: true,
		 	        title: "Logout",
			        height: 250,
			        width: 400,
			        buttons: {
			            "Yes": function () {
			                callback(true);
			                $(this).dialog('close');
			            },
			            "No": function () {
			                callback(false);
			                $(this).dialog('close');
			                
			            }
			        }
			    });
		});
		
		
			
		$('#writeForm').click(
				function() {
					$.ajax({
						type : "post",
						url : "/Board_psy/ajaxLoginCheck.do",
						data : {
							ajaxYn : "Y"
						},
						success : function(result) {
							if (result == "E") {
								alert("먼저 로그인을 해주세요");
								loginOpen = window.open('/Board_psy/loginForm.do', '로그인','width=300, height=200');

							} else if (result == "GO_TO") {
								location.replace("/Board_psy/writeForm.do");
							}
						},
						error : function() {
							window.location.reload(true);
							alert("writeCheck ajax 에러");
						}
					});
				});
		$("input[name='searchBtn']").click(function() {
			$("input[name='page']").val(1);
			document.form.submit();
		});
		$("input.txtbox").unbind("keydown").bind("keydown", function(e) {
			if (e.keyCode == 13)
				$("#form input[name='searchBtn']").click();
		});
		$("input.txtbox").unbind("keyup").bind("keyup", function(e) {
			if (e.keyCode == 13)
				$("#form input[name='searchBtn']").click();
		});
	});
	function fn_pageMove(page) {
		$("input[name='page']").val(page);
		document.form.submit();
	}

	function fn_gotoBoard(boardNo) {
		$("input[name='page']").val('${boardPage.pageNo}');
		$("input[name='isHitCount']").val('true');
		$("input[name='boardNo']").val(boardNo);
		document.form.action = "/Board_psy/read.do";
		document.form.submit();
	}

	var loginOpen = null;
	function fn_loginOpen() {
		if (loginOpen == null || loginOpen.closed) {

			loginOpen = window.open('/Board_psy/loginForm.do', '로그인',
					'width=300, height=200');
		} else {
			loginOpen.focus();
		}
	}
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pageTag" uri="/tlds/custom-taglib"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" type="text/css" href="./css/board.css" media="all" />


<title>게시판 목록 </title>
<link type="text/css" rel="stylesheet" href="./css/board.css" media="all" />
<link type="text/css" rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/blitzer/jquery-ui.css"  />
<link type="text/css" rel="stylesheet" href="./css/loginBpopup.css" media="all" />

<script src=js/jquery-1.11.2.min.js></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="js/jquery.bpopup.js"></script>
<script src="js/jquery.easy-confirm-dialog.js"></script>
<script src="js/object.js"></script>
<script src="js/login.js"></script>
</head>

<body>
	<fmt:requestEncoding value="UTF-8" />
	<form action="<c:url value="/articleList.do" />" id="form" name="form" method="post">
		<input type="hidden" name="totalArticleCount" value="${articlePage.totalRecodeCount}"> 
		<input type="hidden" name="page"> <input type="hidden" name="prePage" value="${articlePage.prePage}">
		<input type="hidden" name="nextPage" value="${articlePage.nextPage}">

		<table>
			<tr>
				<td class="title_layout"><h1>KWARE 게시판</h1></td>
			</tr>
			<tr>
				<td class="back_layout"><a href="<c:url value="/articleList.do" />">◀처음으로</a></td>
			</tr>

			<tr>
				<td class="user_layout">
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
							${sessionScope.name} 님 환영합니다! 
								<input type="button" name="logout" id="logout" value="Logout"/>
								<div id="dialog-confirm"></div>
								<p></p>
								<input type="button" name="modifyPW" id="modifyPW" value="비밀번호변경" >
								<div id="modifyPwPopup" class="Pstyle">
								<span class="b-close">X</span>
									현재 비밀번호 : <input type="password" name="originPW" id="originPW" ><br>
									새 비밀번호 : <input type="password" name="newPW" id="newPW" ><br>
									새 비밀번호 확인 :<input type="password" name="confirmNewPW" id="confirmNewPW" ><br>
									<input type="button" name="changePW" id="changePW" value="변경하기">
								</div>
								<input type="button" name="withdrawUser" id="withdrawUser" value="회원탈퇴" >
								<input type="button" name="myWriting" id="myWriting" value="내가쓴글조회" >
					</c:when>
					</c:choose>
				</td>
			</tr>

			<tr>
				<td class="search_layout">
					<table class="search_table">
						<tr>
							<td class="search_td"><select name="searchKey" class="combobox">
								<option value="ALL" <c:if test="${searchKey eq 'ALL'}">selected</c:if>>전체</option>
								<option value="CONTENT" <c:if test="${searchKey eq 'CONTENT'}">selected</c:if>>내용</option>
								<option value="TITLE" <c:if test="${searchKey eq 'TITLE'}">selected</c:if>>제목</option>
								<option value="NAME" <c:if test="${searchKey eq 'NAME'}">selected</c:if>>작성자</option>
							</select> 
							<input type="text" name="searchValue" value="${searchValue}" class="txtbox" size="25" /> 
							<!-- button onclick="fn_pageMove(1)">검색</button --> 
							<input type="button" name="searchBtn" value="검색" /></td>
						</tr>
					</table>
				</td>
			</tr>


			<tr>
				<td class="totalBoard_layout">총게시글 수 : ${articlePage.totalRecodeCount}</td>

			</tr>

			<tr>
				<td class="list_layout">
					<table border="1" style="border-collapse: collapse;width : 1024px;" class="boardList">
						<c:if test="${empty articlePage.dataList }">
							<tr>
								<td>게시글이 없습니다.</td>
							</tr>
						</c:if>
						<c:if test="${!empty articlePage.dataList }">

							<tr align="center">
								<td width="80px">글번호</td>
								<td width="100px">작성자</td>
								<td width="300px">제목</td>
								<td>첨부</td>
								<td width="50px">댓글</td>
								<td width="80px">조회수</td>
							</tr>

							<c:forEach items="${articlePage.dataList}" var="article">
								<tr>
									<td align="center">${article.articleNo} </td>
									<td align="center" width="100px">${article.writer.name}</td>

									<td id="boardList_title" width="300px">
										<c:if test="${article.indent == 0 && article.deletionStatus == 'PRESENT'}">
											<a href="<c:url value ="/read.do?articleNo=${article.articleNo}&isHitCount=true&page=${articlePage.currentPageNo}&searchKey=${searchKey}&searchValue=${searchValue}"/>">${article.title}</a>
										</c:if>
										<c:if test="${article.indent == 0 && article.deletionStatus=='DELETED'}">
											<span style="font-style: italic; color: gray; font-size: x-small;">
												<a	href="<c:url value="/read.do?articleNo=${article.articleNo}&isHitCount=true&page=${articlePage.currentPageNo}&searchKey=${searchKey}&searchValue=${searchValue}"/>">삭제된 글입니다</a>
											</span>
										</c:if>
										<c:if test="${article.indent > 0 && article.deletionStatus =='PRESENT'}">
											<c:forEach begin="1" end="${article.indent}">&nbsp;&nbsp;</c:forEach>
											<a href="<c:url value ="/read.do?articleNo=${article.articleNo}&isHitCount=true&page=${articlePage.currentPageNo}&searchKey=${searchKey}&searchValue=${searchValue}"/>">ㄴ[Re]&nbsp;${article.title}</a>
										</c:if> 
										<c:if test="${article.indent > 0 && article.deletionStatus =='DELETED'}">
											<c:forEach begin="1" end="${article.indent}">&nbsp;&nbsp;</c:forEach>
											<span style="font-style: italic; color: gray; font-size: x-small;"> 
												<a	href="<c:url value ="/read.do?articleNo=${article.articleNo}&isHitCount=true&page=${articlePage.currentPageNo}&searchKey=${searchKey}&searchValue=${searchValue}"/>"> ㄴ&nbsp;삭제된 글입니다</a>
											</span>
										</c:if>
									</td>
									<td align="center" width="100px">
										<c:choose>
											<c:when test="${not empty article.files }">
												<c:forEach items="${article.files}" var="file">
													<c:choose>
														<c:when test="${not empty sessionScope.email  && article.deletionStatus =='PRESENT'}">
															<a href="<c:url value ="/download.do?savedPath=${file.savedPath}"/>"> <img alt="${file.originalName}" src="<c:url value="/img/file.jpg" />" width="20px" height="20px"></a>
														</c:when>
														<c:otherwise>
															<img alt="${file.originalName}" src="<c:url value="/img/file.jpg" />" width="20px" height="20px">
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>


											<c:otherwise>
												${fn:length(article.files)}
											</c:otherwise>
										</c:choose>
									</td>
									<td align="center" width="50px">${fn:length(article.comments)}</td>
									<td align="center" width="80px">${article.hitCount}</td>
								</tr>

							</c:forEach>
							
							
							
							<tr>
							
<!-- 								<td colspan="6" align="center"> -->
<!-- 								<a onclick="javascript:fn_pageMove(1)"> &nbsp; << &nbsp; </a>  -->
<%-- 									<a onclick="javascript:fn_pageMove(${articlePage.prePage})"> &nbsp; < &nbsp;</a>  --%>
<%-- 									<c:forEach var="num" begin="${articlePage.startPage}" end="${articlePage.endPage}"> --%>
<%-- 										<c:choose> --%>
<%-- 											<c:when test="${num eq articlePage.currentPageNo}"> --%>
<%-- 												<a onclick="javascript:fn_pageMove(${num})" class="choice" style="font-weight: bold;" >[${num}]</a> --%>
<%-- 											</c:when> --%>
<%-- 											<c:otherwise> --%>
<%-- 												<a onclick="javascript:fn_pageMove(${num})">[${num}]</a> --%>
<%-- 											</c:otherwise> --%>
<%-- 										</c:choose> --%>
<%-- 									</c:forEach>  --%>
<%-- 									<a onclick="javascript:fn_pageMove(${articlePage.nextPage})">&nbsp; > &nbsp;</a> --%>
<%-- 									<a onclick="javascript:fn_pageMove(${articlePage.finalPage})">&nbsp; >> &nbsp;</a> --%>
<!-- 								</td> -->
								<td colspan="6" align="center">
								<c:url var="searchUri" value="/articleList.do?s=${searchval}&page=##" />
								<pageTag:paging maxLinks="10" currPage="${articlePage.currentPageNo}" totalPages="${articlePage.totalRecodeCount}" uri="${searchUri}" />
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
		
		<input type="hidden" name="isHitCount" /> <input type="hidden" name="articleNo" />
		
	</form>

<script type="text/javascript">

$('.paging a').each(function() {
    if ($(this).attr('href') != '#') {
        var hrefURI = $(this).attr('href');
        var params = hrefURI.substring(hrefURI.indexOf('?'));
         
        $(this).click(function(event) {
            event.preventDefault();
            $.ajax({
                url: '/articlellist.do' + params,
                type: 'post',
                dataType: 'html',
                success: function(data) {
                    $('#resultArea').html(data);
                }
            });
        });
    }
});



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
		
		
	FB.Event.subscribe('auth.logout', function() {
		$.ajax({
			url : '<c:url value="/fbLogout.do" />',
			success : function(result) {
				if (result == "logout") {
					alert("로그아웃 성공 : " + result);
					location.reload();
				} else {
					lert("페북 로그아웃 중 오류 발생");
					location.reload();
				}
			},
			error : function() {
				alert("페북 로그아웃 에이젝스 처리중 에러가 발생했습니다");
				location.reload();
			}
		});
	});
};

	$(function() {
		
		$('#logout').click(function(){
			 $("#dialog-confirm").html("<h5>어떤 계정에서 로그아웃 하시겠습니까?<h5>");
				$("#dialog-confirm").dialog({
			        resizable: false,
			        modal: true,
		 	        title: "Logout",
			        height: 250,
			        width: 400,
			        buttons: {
			            "Facebook": function () {
			            	FB.getLoginStatus(function(response) {
			            	if(response.status == 'connected'){
			            		FB.logout();
			    	       		alert("모든 페이스북 계정에서 로그아웃되었습니다");
			                	$(this).dialog('close');
			            	}else if(response.status === 'unknown'){
			            		alert("페이스북 로그인이 되어있지 않습니다. Kware에서 로그아웃 해주세요")
			            	}
			        		});
			            },
			            "Kware": function () {
			            	location.href = "<c:url value ="/logout.do" />";
			    	        alert("KwareBoard에서 로그아웃되었습니다");
			                $(this).dialog('close');
			            }
			        }
			    });
		});
		
		$('#writeForm').click(
				function() {
					$.ajax({
						type : "post",
						url : "<c:url value="/ajaxLoginCheck.do" />",
						data : {
							ajaxYn : "Y"
						},
						success : function(result) {
							if (result == "E") {
								alert("먼저 로그인을 해주세요");
// 								loginOpen = window.open('./loginForm.do', '로그인','width=300, height=200');
								go_popup();
								
							} else if (result == "GO_TO") {
								location.replace("<c:url value ="/writeForm.do" />");
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
		
		$('#modifyPW').click(modifyPwPopup);
	});//end $(function()
	
	function fn_pageMove(page) {
		$("input[name='page']").val(page);
		document.form.submit();
	}

	function fn_gotoBoard(articleNo) {
		$("input[name='page']").val('${articlePage.currentPageNo}');
		$("input[name='isHitCount']").val('true');
		$("input[name='articleNo']").val(articleNo);
		document.form.action = "<c:url value ="/read.do"/>";
		document.form.submit();
	}
	
	function fn_modifyPwPopup(){
		$('#modifyPwPopup').bPopup();
	}

</script>
</body>
</html>
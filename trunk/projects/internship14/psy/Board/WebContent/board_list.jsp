<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" type="text/css" href="/Board_psy/css/board.css" media="all" />


<title>게시판 목록</title>
<script type="text/javascript" 	src="http://code.jquery.com/jquery-1.10.2.js"></script>
</head>


<body>
<form action="/Board_psy/boardList.do" id="form" name="form" method="post">
<input type="hidden" name="totalBoardCount" value="${boardPage.totalBoardCount}">
<input type="hidden" name="page"> 
<table>
	<tr>
		<td class="title_layout"><h1>KWARE 게시판</h1></td>
	</tr>
	<tr>
	<td class="user_layout" >
		<c:choose>
			<c:when test="${empty sessionScope.email}">
				<input type="button" name="login" id="login" value="Login"
					onclick="fn_loginOpen()">
			</c:when>
			<c:when test="${!empty sessionScope.email}">
			${sessionScope.name} 님 환영합니다! <input type="button" name="logout"
					id="logout" value="Logout">
			</c:when>
		</c:choose>

	</td>
</tr>

<tr>
	<td class="search_layout">
		<table class="search_table">
   			 <tr>
       			 <td class="search_td">
           		<select name="searchKey" class="combobox">
            	<option value="ALL" <c:if test="${searchKey=='ALL'}">selected</c:if>>전체</option>
                <option value="CONTENT" <c:if test="${searchKey=='CONTENT'}">selected</c:if>>내용</option>
                <option value="TITLE"  <c:if test="${searchKey=='TITLE'}">selected</c:if>>제목</option>                 
                <option value="NAME"  <c:if test="${searchKey=='NAME'}">selected</c:if>>작성자</option> 
            	</select>
            	<input type="text" name="searchValue" value="${searchValue}" class="txtbox" size="25" />
            	<!-- button onclick="fn_pageMove(1)">검색</button --> 
            	<input type="button" name="searchBtn" value="검색" />         
        		</td>
    		</tr>
		</table>
	</td>
</tr>
	
	
<tr>
	<td class="totalBoard_layout">총게시글 수 : ${boardPage.totalBoardCount}</td>
		
</tr>

<tr>
	<td class="list_layout">
	
	<table border="1" style="border-collapse: collapse;" width="1024px"
		height="500%" class="boardList">
		<c:if test="${empty boardPage.boardList }">
			<tr>
				<td>게시글이 없습니다.</td>
			</tr>
		</c:if>
		<c:if test="${!empty boardPage.boardList }">
		
			<tr align="center">
				<td>글번호</td>
				<td>작성자</td>
				<td>제목</td>
				<td>첨부</td>
				<td>댓글</td>
				<td>조회수</td>
			</tr>
			<c:forEach items="${boardPage.boardList}" var="board">
				<tr>
					<td align="center">${board.boardNo}
					<td align="center">${board.writer.name}</td>
					
					<td id="boardList_title">
					<c:if test="${board.indent == 0 && board.flag==1}">
						<a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">${board.title}</a>
						
					</c:if>
					
					<c:if test="${board.indent == 0 && board.flag==0}">
						<span style="font-style: italic; color: gray;">삭제된 글 입니다</span>
					</c:if>
					<c:if test="${board.indent > 0 && board.flag ==1}">
						<c:forEach begin="1" end="${board.indent}" >&nbsp;&nbsp;</c:forEach>
							<a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">ㄴ&nbsp;${board.title}</a>
						
					</c:if>
					
					<c:if test="${board.indent > 0 && board.flag ==0}">
						<c:forEach begin="1" end="${board.indent}" >&nbsp;&nbsp;</c:forEach>
							<span style="font-style: italic; color: gray;">ㄴ&nbsp;삭제된 글입니다 </span>
						
					</c:if>
					</td>
					<td align="center" width="100px">
					<c:choose>
						<c:when test="${!empty board.files }">
							<c:forEach items="${board.files }" var="file">
								
							<a href="/Board_psy/download.do?savedPath=${file.savedPath}">
								<img alt="${file.originalName}" src="/Board_psy/img/file.jpg" width="20px" height="20px"></a> 
							</c:forEach>
						</c:when>
						<c:when test="${empty board.files }">
							${fn:length(board.files)}
						</c:when>
					</c:choose>
					</td>
					<td align="center">${fn:length(board.comments)}</td>
					<td align="center">${board.hitCount}</td>
				</tr>

			</c:forEach>
			<tr>
				<td colspan="6" align="center"> 
				<c:if test="${boardPage.startPage>1}">
						<a href="/Board_psy/boardList.do?page=${boardList.startPage}">[이전]</a>
				</c:if> 
					<c:forEach var="num" begin="${boardPage.startPage}" end="${boardPage.endPage}">
						<a href="javascript:fn_pageMove(${num})">[${num}]</a>
					</c:forEach>
				<c:if test="${boardPage.endPage < boardPage.totalPage}">
						<a href="/Board_psy/boardList.do?page=${boardPage.endPage+2}">[다음]</a>
				</c:if>
				
				</td>
			</tr>


		</c:if>

	</table>
	</td>
</tr>	
	
<tr>
	<td class="footer_layout">	
	<input type="hidden" value="${sessionScope.email }" name="email">
	<c:if test="${!empty sessionScope.email}">
		<input type="button" id="writeForm" name="writeForm" value="Write"
			style="color: navy;">
	</c:if>
	<c:if test="${empty sessionScope.email }">
		로그인 후 게시글을 작성하세요!
	</c:if>

	</td>
</tr>

</table>
</form>
</body>
<script type="text/javascript">
	$(function() {
		$('#logout').click(function() {
			location.href = "/Board_psy/logout.do";
		});

		$('#writeForm').click(function() {
			location.href = "/Board_psy/writeForm.do";
		});
		$("input[name='searchBtn']").click(function(){
			$("input[name='page']").val(1);
			document.form.submit();
		});	
		$("input.txtbox").unbind("keydown").bind("keydown",function(e){
			if (e.keyCode == 13) $("#form input[name='searchBtn']").click();
		});
		$("input.txtbox").unbind("keyup").bind("keyup",function(e){
		    if (e.keyCode == 13) $("#form input[name='searchBtn']").click();
		});
	});
	function fn_pageMove(page){
		$("input[name='page']").val(page);
		document.form.submit();
	}
	var loginOpen = null;
	function fn_loginOpen(){
		if ( loginOpen == null || loginOpen.closed) {
			loginOpen = window.open('/Board_psy/loginForm.do', '로그인', 'width=300, height=200');
		} else {
			loginOpen.focus();
		}
	}
	
</script>
</html>
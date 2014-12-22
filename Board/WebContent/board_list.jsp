<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>게시판 목록</title>
</head>
<body>
<h1>KWARE 게시판</h1>
	<table border="1" style="border-collapse: collapse;" width="50%"  height="500%">
	<c:if test="${empty boardPage.boardList }">
		<tr>
			<td>
				게시글이 없습니다. 
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty boardPage.boardList }">
		<tr>
			<td>글번호</td>
			<td>작성자</td>
			<td>제목</td>
			<td>첨부</td>
			<td>댓글</td>
			<td>조회수</td>
		</tr>
		<c:forEach items="${boardPage.boardList}" var="board">
		<tr>
			<td>${board.boardNo}<td>${board.writer.name}</td>
			<td><a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">${board.title}</a></td>
			<td>${board.files}</td>
			<td>${fn:length(board.comments)}</td><td>${ board.hitCount}</td>
		</tr>
	
		</c:forEach>	
		<tr>
				<td colspan="6" align="center">
					<c:if test="${boardPage.startPage>1}">
						<a href="/Board_psy/boardList.do?page=${boardList.startPage-1}">[이전]</a>
					</c:if>
					<c:forEach var="num" begin="${boardPage.startPage}" end="${boardPage.endPage}">
						<a href="/Board_psy/boardList.do?page=${num}">[${num}]</a>						
					</c:forEach>
					<c:if test="${boardPage.endPage < boardPage.totalPage}">
						<a href="/Board_psy/boardList.do?page=${boardPage.endPage+1}">[다음]</a>
					</c:if>
				</td>
			</tr>


	</c:if>
	
	</table>


</table>



</body>
</html>
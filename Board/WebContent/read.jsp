<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>
	<table border="1" style="border-collapse: collapse;" width="50%" height="50%">
		<tr>
			<td>작성자</td> 
			<td>${board.writer.name}
				<input type="button" id="modify" name="modify" value="수정" align="right">
				<input type="button" id="delete" name="delete" value="삭제" align="right"> 
			</td>
		</tr>
		
		<tr><td>제목</td><td>${board.title}</td></tr>
		<tr><td>첨부파일</td><td>${board.files}</td></tr>
		<tr><td>내용</td><td>${board.content}</td></tr>
	</table>
	<table>
		<tr><td>댓글(로그인된사람)</td><td><input type="text" size="30"> </td></tr>
		<c:choose>
			<c:when test="${empty board.comments}">
				<tr>
					<td>
						댓글이 없습니다.
					</td>
				</tr>
			</c:when>
			
			<c:when test="${!empty board.comments}">
				<c:forEach items="${board.comments }" var="comment">
					<tr comment="${comment}">
						<td>${comment.writer.name}</td>
						<td>${comment.content}</td>
					</tr>
				
				</c:forEach>
			</c:when>
		
		</c:choose>
	</table>
</body>
</html>
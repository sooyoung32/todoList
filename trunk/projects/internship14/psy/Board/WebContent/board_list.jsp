<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$('#logout').click(function() {
			location.href = "/Board_psy/logout.do";
		});

		$('#writeForm').click(function() {
			location.href = "/Board_psy/writeForm.do";
		});

	});
</script>
<title>�Խ��� ���</title>
</head>
<body>
	<h1>KWARE �Խ���</h1>
	<div align="left">
		<c:choose>
			<c:when test="${empty sessionScope.email}">
				<input type="button" name="login" id="login" value="�α���"
					onclick="window.open('/Board_psy/loginForm.do', '�α���', 'width=300, height=200')">
			</c:when>
			<c:when test="${!empty sessionScope.email}">
			${sessionScope.name} �� ȯ���մϴ�! <input type="button" name="logout"
					id="logout" value="�α׾ƿ�">
			</c:when>
		</c:choose>

	</div>
	
	<div>�ѰԽñ� �� : ${boardPage.totalBoardCount}</div>
	
	<table border="1" style="border-collapse: collapse;" width="50%"
		height="500%">
		<c:if test="${empty boardPage.boardList }">
			<tr>
				<td>�Խñ��� �����ϴ�.</td>
			</tr>
		</c:if>
		<c:if test="${!empty boardPage.boardList }">
		
			<tr>
				<td>�۹�ȣ</td>
				<td>�ۼ���</td>
				<td>����</td>
				<td>÷��</td>
				<td>���</td>
				<td>��ȸ��</td>
			</tr>
			<c:forEach items="${boardPage.boardList}" var="board">
				<tr>
					<td>${board.boardNo}
					<td>${board.writer.name}</td>
					
					<c:if test="${board.indent == 0 && board.flag==1}">
						<td><a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">${board.title}</a>
						</td>
					</c:if>
					
					<c:if test="${board.indent == 0 && board.flag==0}">
						<td><a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">������ �� �Դϴ�</a></td>
					</c:if>
					<c:if test="${board.indent > 0 && board.flag ==1}">
						<td><c:forEach begin="1" end="${board.indent}" >&nbsp;&nbsp;</c:forEach>
							<a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">��&nbsp;${board.title}</a>
						</td>
					</c:if>
					
					<c:if test="${board.indent > 0 && board.flag ==0}">
						<td><c:forEach begin="1" end="${board.indent}" >&nbsp;&nbsp;</c:forEach>
							<a href="/Board_psy/read.do?boardNo=${board.boardNo}&isHitCount=true">��&nbsp;������ ���Դϴ�</a>
						</td>
					</c:if>
					
					<td>${fn:length(board.files)}</td>
					<td>${fn:length(board.comments)}</td>
					<td>${ board.hitCount}</td>
				</tr>

			</c:forEach>
			<tr>
				<td colspan="6" align="center"><c:if
						test="${boardPage.startPage>1}">
						<a href="/Board_psy/boardList.do?page=${boardList.startPage}">[����]</a>
					</c:if> <c:forEach var="num" begin="${boardPage.startPage}"
						end="${boardPage.endPage}">
						<a href="/Board_psy/boardList.do?page=${num}">[${num}]</a>
					</c:forEach> <c:if test="${boardPage.endPage < boardPage.totalPage}">
						<a href="/Board_psy/boardList.do?page=${boardPage.endPage+1}">[����]</a>
					</c:if></td>
			</tr>


		</c:if>

	</table>
	<input type="hidden" value="${sessionScope.eamil }" name="email">
	<c:if test="${!empty sessionScope.email}">
		<input type="button" id="writeForm" name="writeForm" value="���ۼ�"
			style="color: navy;">
	</c:if>
	<c:if test="${empty sessionScope.email }">
		�α��� �� �Խñ��� �ۼ��� �ּ���! 
	</c:if>

</body>
</html>
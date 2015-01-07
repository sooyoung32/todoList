<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 읽기</title>
<link type="text/css" rel="stylesheet" type="text/css"
	href="/Board_psy/css/board.css" media="all" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		$('#comment').click(function() {
			if($('#commentText').val() == ""){
				alert("덧글을 작성해주세요!");
				return false;
			}
			
			
			if ($('#commentText').val() != null) {
				var data2 = {
					boardNo : $('#boardNo').val(),
					email : $('#email').val(),
					content : $('#commentText').val(),
					ajaxYn: 'Y',
				};
				
				$.ajax({
					type : "post",
					url : "/Board_psy/insertComment.do",
					data : data2,
					success : function(result ,response) {
						if(result == "E"){
							alert("먼저 로그인을 해주세요"); 
							location.replace("/Board_psy/boardList.do");
						}else if (result == "Y") {
							alert("댓글이 입력되었습니다");
							location.reload(true);
						} else if (result == "N") {
							alert("댓글 입력시 오류가 발생하였습니다");
							location.reload(true);
						}
					},
					error : function() {
						location.reload(true);
						alert("코멘트 ajax 에러");
					}

				});
			} else {
				alert("내용을 입력해주세요");
			}
		});

		$('#reply').click(function() {
			location.href = "/Board_psy/replyForm.do?boardNo=${board.boardNo}";
		});
		
		
		$('#delete').click(function(){
			
			var $result =confirm("삭제 하시겠습니까?"); 
			
			if($result){
				location.replace("/Board_psy/delete.do?boardNo=${board.boardNo}");
			}else{
				
			}
			
		});
		
		

		$('.cDelete').click(function() {
			alert($(this).prev().val() + "//삭제 코멘트번호");
			$.ajax({
				type : "post",
				url : "/Board_psy/deleteComment.do",
				data : {
					commentNo : $(this).prev().val()
				},
				success : function(result) {
					if (result == "Y") {
						alert("댓글이 삭제되었습니다");
						location.reload(true);
					} else if (result == "N") {
						alert("댓글 삭제시 오류가 발생하였습니다");
						location.reload(true);
					}
				},
				error : function() {
					location.reload(true);
					alert("코멘트 삭제 ajax 에러");
				}

			});
		});

		$('.cModify').click(function() {
			$(this).hide();
			var cContent = $(this).next().val();
			var commentNo = $(this).prev().prev().val();
			alert(cContent + "//코멘트내용//  " + commentNo + "  //코멘트번호");
			
			var $test = $(this).prev().prev().prev();
			$test.text("");
			
			
			var $here = $("<div/>");
			$here.attr("id", "here");

			var $textarea = $("<textarea rows='3' cols='88'/>");
			$textarea.val(cContent);
			
			var $cancleBtn = $('<button  style="font-weight: bold;"/>');
			$cancleBtn.text('수정취소');
			
			$cancleBtn.click(function(){
				$test.text(cContent);
				$here.remove();
				window.location.href=window.location.href;
			});
			
			
			var $button = $('<button  style="font-weight: bold; vertical-align: top;"/>');
			$button.text('덧글수정');
			
			$button.click(function() {
				if ($textarea.val() != null) {
					alert("수정 코멘트번호--" + commentNo+"수정 content//"+$textarea.val());
					var data2 = {
						commentNo : commentNo,
						content : $textarea.val()
					};

					$.ajax({
						type : "post",
						url : "/Board_psy/updateComment.do",
						data : data2,
						success : function(result) {
							if (result == "Y") {
								alert("덧글이 수정되었습니다");
// 								window.location.reload(true);
// 								history.go(this);
								window.location.href=window.location.href;

							} else if (result == "N") {
								alert("덧글 입력시 오류가 발생하였습니다");
								window.location.reload(true);
							}
						},
						error : function() {
							window.location.reload(true);
							alert("코멘트수정 ajax 에러");
						}

					});
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
	        // count 정보 및 count 정보와 관련된 textarea/input 요소를 찾아내서 변수에 저장한다.
	        var $count = $('.count', this);
	        var $input = $(this).prev();
	        // .text()가 문자열을 반환하기에 이 문자를 숫자로 만들기 위해 1을 곱한다.
	        var maximumCount = $count.text() * 1;
	        // update 함수는 keyup, paste, input 이벤트에서 호출한다.
	        var update = function() {
	            var before = $count.text() * 1;
	            var now = maximumCount - $input.val().length;
	            // 사용자가 입력한 값이 제한 값을 초과하는지를 검사한다.
	            if (now < 0) {
	                var str = $input.val();
	                alert('글자 입력수가 초과하였습니다.');
	                $input.val(str.substr(0, maximumCount));
	                now = 0;
	            }
	            // 필요한 경우 DOM을 수정한다.
	            if (before != now) {
	                $count.text(now);
	            }
	        };
	        // input, keyup, paste 이벤트와 update 함수를 바인드한다
	        $input.bind('input keyup paste', function() {
	            setTimeout(update, 0);
	        });
	        update();
	    });
	});
</script>


</head>
<body>
	<form action="/Board_psy/boardList.do" id="form" name="form"
		method="post">
		<input type="hidden" name="page">
		<input type="hidden" name="boardNo" value="${board.boardNo}"/>
		<table>
			<tr>
				<td class="back_layout"><a
					href="javascript:fn_pageMove(${page})">◀게시판 목록</a></td>
			</tr>

			<tr>
				<td class="read_layout">
					<table border="1" style="border-collapse: collapse;" width="800px"
						height="500%">
						<tr>
							<td id="read_td">작성자</td>
							<td id="read_td2">${board.writer.name}<c:if
									test="${sessionScope.name eq board.writer.name}">
									<a href="/Board_psy/updateForm.do?boardNo=${board.boardNo}"><input
										type="button" id="modify" name="modify" value="수정"
										align="right"></a>
<%-- 									<a href="/Board_psy/deleteForm.do?boardNo=${board.boardNo}"> --%>
									<input type="button" id="delete" name="delete" value="삭제"
										align="right">
<!-- 										</a> -->
								</c:if>
							</td>
						</tr>

						<tr>
							<td id="read_td">제목</td>

							<td id="read_td2"><c:if test="${board.flag==1}">
					${board.title}
				</c:if> <c:if test="${board.flag==0}">
					본 글은 삭제되었습니다
				</c:if></td>
						</tr>
						<tr>
							<td colspan="2" id="read_td">내용</td>
						</tr>
						<tr>
							<c:if test="${board.flag==1}">
								<td colspan="2" id="read_td2"><pre>${board.content}</pre></td>
							</c:if>
							<c:if test="${board.flag==0}">
								<td colspan="2" id="read_td2">본 글은 삭제되었습니다</td>
							</c:if>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="file_layout"><c:if test="${board.flag==1 }">
						<table border="1" style="border-collapse: collapse;" width="800px"
							height="500%">
							<tr>
								<td id="file_td">첨부파일</td>
							</tr>
							<tr>
								<c:if test="${empty board.files}">
									<td id="file_td2">첨부파일이 없습니다
									<td>
								</c:if>
								<c:if test="${!empty board.files}">
									<c:forEach items="${board.files}" var="file">
										<c:if test="${file.flag ==1 && !empty sessionScope.email}">
											<tr>
												<td id="file_td2"><a
													href="/Board_psy/download.do?savedPath=${file.savedPath}"> ▶ &nbsp; ${file.originalName}</a>
												<td>
											</tr>
										</c:if>
										<c:if test="${empty sessionScope.email}">
											<tr>
												<td id="file_td2">${file.originalName}
												<td>
											</tr>
										</c:if>

									</c:forEach>
								</c:if>
							</tr>
						</table>
					</c:if></td>
			</tr>

			<c:if test="${board.flag==0}">

				<tr><td><span id="file_td2">첨부파일이 없습니다</span></td></tr>

			</c:if>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>
			<tr>
				<td class="reply_btn">
					<table width="800px" height="500%">
						<tr>
							<c:if test="${!empty sessionScope.name}">
								<td style="text-align: right; font-size: medium;"><input
									type="button" id="reply" name="reply" value="Reply"></td>
							</c:if>
							<c:if test="${empty sessionScope.name}">
								<td
									style="padding-left: 1em; font-style: italic; color: gray; font-size: 12pt;">로그인
									후 답글*댓글을 작성할 수 있습니다</td>
							</c:if>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			<tr>
				<td class="comment_layout">
					<table border="1" style="border-collapse: collapse;" width="800px"	height="500%">


						<tr>
							<c:if test="${!empty sessionScope.name}">
								<td id="comment_td">${sessionScope.name }</td>
								<td><textarea rows="3" cols="88" id="commentText"></textarea>
								<DIV class=remaining>남은 글자수: <SPAN class="count">2000</SPAN></DIV></td>
								<td><input type="button" id="comment" name="comment"
									value="Comment"></td>
							</c:if>
							<c:if test="${empty sessionScope.name}"></c:if>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>

			
			
			
			<tr>
				<td class="comment_list">
					
					<div style="width: 100%; height:350px; overflow:scroll;" >
					<table border="1" style="border-collapse: collapse;" width="800px">
						<c:choose>
							<c:when test="${empty board.comments}">
								<tr>
									<td style="padding-left: 1em; font-style: italic; color: gray;">댓글이
										없습니다.</td>
								</tr>
							</c:when>
					
							<c:when test="${!empty board.comments}">
								<c:forEach items="${board.comments }" var="comment">
									<c:if test="${comment.flag==1 }">
										<tr>
											<td id="comment_td">${comment.writer.name}</td>
											<td id="comment_td2" class="comment_td2"><div id="content" class="content">${comment.content}
													</div> <c:if
													test="${sessionScope.name eq comment.writer.name}">
													<input type="hidden" name="commentNo"
														value="${comment.commentNo}" />
													<input type="button" class="cDelete" name="cDelete"
														value="덧글삭제" />
													<input type="button" class="cModify" name="cModify"
														value="덧글수정" />
													<input type="hidden" name="cContent"
														value="${comment.content}" />
												</c:if></td>
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
		<input type="hidden" name="boardNo" id="boardNo"
			value="${board.boardNo}"> <input type="hidden" name="email"
			id="email" value="${sessionScope.email}"> <input
			type="hidden" name="searchValue" id="searchValue"
			value="${searchValue}"> <input type="hidden" name="searchKey"
			id="searchKey" value="${searchKey}">
	
	
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<link type="text/css" rel="stylesheet" type="text/css"
	href="/Board_psy/css/board.css" media="all" />
</head>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
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
			}
		}
		return true;
			
	}
	
	function save(){
		$.ajax({
			type : "post",
			url : "/Board_psy/ajaxLoginCheck.do",
			data : {ajaxYn: "Y"},
			success : function(result) {
				if(result == "E"){
					alert("먼저 로그인을 해주세요"); 
//						location.replace("/Board_psy/boardList.do");
					loginOpen = window.open('/Board_psy/loginForm.do', '로그인', 'width=300, height=200');
				
				}else if (result == "Y") {
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
<body>
	<form action="/Board_psy/reply.do" id="form" name="form" method="post"
		enctype="multipart/form-data">

		<table>
			<tr>
				<td style="font-style: italic; color: gray;">${boardNo} 글의 답글</td>
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
						<!-- 			<tr> -->
						<!-- 				<td><input type="file" id="file" name="fileList[0]"></td> -->
						<!-- 			</tr> -->
					</table>
			<tr>
				<td><input type="button" id="addFile" name="addFile"
					value="파일추가"></td>
			</tr>

			<tr>
				<td style="padding: 0.5em;"></td>
			</tr>


		</table>
		<input type="hidden" id="boardNo" name="boardNo" value="${boardNo }">
		<input type="hidden" value="${sessionScope.email}" name="email"
			id="email ">
	</form>

</body>
</html>
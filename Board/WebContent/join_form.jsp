<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(function(){
	$('#dupCheck').click(function(){
		if($('#email').val()!=null){
			$.ajax({
				type:"post",
				url:"/Board_psy/dupCheck.do",
				data:{email:$('#email').val()},
				success:function(result){
					var msg="";
					if(result=="unusable"){
						msg="�̹� ��ϵ� �̸����Դϴ�";
						$('#hidden').val(0);
					}else{
						msg="��밡���� �̸����Դϴ�";
						$('#hidden').val(1);
					}
					$('#msg').html(msg);	
				},
				error:function(){
					alert("dupCheck Ajax �����Ф�");
				}
				
			});
		}else{
			alert("�̸����� �Է����ּ���");
		}
	});
	$('#button').click(function(){
		if($('#hidden').val()==0){
			alert("�̸��� �ߺ�Ȯ���� ���ּ���");
		}
		if()
		
		if($('#password').val()!=$('#checkPassword').val()){
			alert("��й�ȣ�� ���� �ʽ��ϴ�");
		}else{
			alert("ȸ�������� �Ϸ�Ǿ����ϴ�");
			document.form.submit();
			//$('#button').submit();
		}
			
	});
	
});


</script>
<title>ȸ������ ȭ��</title>
</head>
<body>
<form action="/Board_psy/join.do" method="post" name="joinForm">
	<table border="1">
		<tr><td>�̸�</td><td><input type="text" name="name"></td></tr>
		<tr><td>�̸���</td><td><input type="text" name="email" id="email"><input type="button" name="dupCheck" id="dupCheck" value="�ߺ�Ȯ��"></td></tr>
		<tr><td colspan="2"><div id="msg"></div></td></tr>
		
		<tr><td>��й�ȣ</td><td><input type="password" name="password" id="password"></td></tr>
		<tr><td>��й�ȣȮ��</td><td><input type="password" name="checkPassword" id="checkPassword"></td></tr>
		
		<tr>
			<td colspan="2"><input type="submit" name="button" id="button" value="ȸ������">
			<input type="hidden" value="0" name="hidden" id="hidden">
			</td>
		</tr>
	</table>
</form>

</body>
</html>
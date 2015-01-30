	function statusChangeCallback(response) {
		console.log('statusChangeCallback');
		console.log(response);
		if (response.status === 'connected') {
		} else if (response.status === 'not_authorized') {
		} else {
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
							alert("�α伮���� : "+result);
							location.reload();
						} else {
							alert("��� �α��� �� ���� �߻�");
						}
					  },
		  	    	error : function() {
	  			      alert("��� �α��� �������� ó���� ������ �߻��߽��ϴ�");
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
				cookie : true, // ��Ű�� ������ ������ �� �ֵ��� ���
				xfbml : true, // �Ҽ� �÷������� ������ ó��
				version : 'v2.1' // ���� 2.1 ���
			});

			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
			
			
		FB.Event.subscribe('auth.logout', function() {
			$.ajax({
				url : '<c:url value="/fbLogout.do" />',
				success : function(result) {
					if (result == "logout") {
						alert("�α׾ƿ� ���� : " + result);
						location.reload();
					} else {
						lert("��� �α׾ƿ� �� ���� �߻�");
						location.reload();
					}
				},
				error : function() {
					alert("��� �α׾ƿ� �������� ó���� ������ �߻��߽��ϴ�");
					location.reload();
				}
			});
				location.replace('index.php');
		});
	};

	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	
	function go_popup() {
		$('#popup').bPopup();
	};
	var loginOpen = null;
	function fn_loginOpen() {
		if (loginOpen == null || loginOpen.closed) {
			loginOpen = window.open('/Board_psy/loginForm.do', '�α���','width=300, height=200');
		} else {
			loginOpen.focus();
		}
	}
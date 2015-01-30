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
			loginOpen = window.open('/Board_psy/loginForm.do', '로그인','width=300, height=200');
		} else {
			loginOpen.focus();
		}
	}
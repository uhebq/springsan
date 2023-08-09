<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>Signin Template · Bootstrap v5.2</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
      html,
      body {
        height: 100%;
      }

      body {
        display: flex;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 330px;
        padding: 15px;
      }

      .form-signin .form-floating:focus-within {
        z-index: 2;
      }

	  .middle{
	    border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
        margin-bottom: -1px;
        
	  }
      .start  {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
      }
	  
      .end  {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
      }

    </style>
    <script>

      window.addEventListener('load',function(){
        
    	// 로그인 폼 출력
        btnSigninView.addEventListener('click',function(){
          signupForm.style.display='none';
          signinForm.style.display='';
        })
		
        // 회원가입 폼 출력
        btnSignupView.addEventListener('click',function(){
          signupForm.style.display='';
          signinForm.style.display='none';
        })
        
        // 로그인
        btnSignin.addEventListener('click', function(e){
    		// 기본이벤트 제거
    		e.preventDefault();
    		
    		// 파라메터 수집`
    		let obj={
    			email : document.querySelector('#email').value
    			, pw : document.querySelector('#pw').value 
    		}
    			
    		console.log(obj);
    			
    		// 요청
    		fetchPost('/loginAction', obj, loginCheck)
    	})
    	
    	signUpEmail.addEventListener('blur', function(){
    		
    		// 입력체크
			if(!signUpEmail.value){
				signupMsg.innerHTML = '이메일을 입력해 주세요.';
				return;
			}
			
			// 파라메터 세팅
			let obj={ email : signUpEmail.value };
			console.log("이메일 체크", obj);
			
			// 아이디 체크 -> 서버에 다녀와야 해요
			fetchPost('/emailCheck', obj, (map)=>{
		    	  if(map.result == 'success'){
		    		  emailCheckRes.value='1'; // 이메일 사용 가능
		    		  signUpPw.focus();
		    	  } else {
		    		  emailCheckRes.value='0'; // 이메일 사용 불가능
		    		  signUpEmail.focus();
		    		  signUpEmail.value='';
		    	  }
		   		  signupMsg.innerHTML = map.msg; // 메세지 출력
		    });
    	});
    	
		signUpNickname.addEventListener('blur', function(){
    		
    		// 입력체크
			if(!signUpNickname.value){
				signupMsg.innerHTML = '닉네임을 입력 해주세요';
				return;
			}
			
			// 파라메터 세팅
			let obj={ nickname : signUpNickname.value };
			console.log("닉네임 체크", obj);
			
			// 닉네임 체크 -> 서버에 다녀와야 해요
			fetchPost('/nicknameCheck', obj, (map)=>{
		    	  if(map.result == 'success'){
		    		  nicknameCheckRes.value='1'; // 닉네임 사용 가능
		    		  signUpPNum.focus();
		    	  } else {
		    		  nicknameCheckRes.value='0'; // 닉네임 사용 불가능
		    		  signUpNickname.focus();
		    		  signUpNickname.value='';
		    	  }
		   		  signupMsg.innerHTML = map.msg; // 메세지 출력
		    });
    	});
    	
    	pwCheck.addEventListener('blur', function(){
    		
    		if(!signUpPw.value){
				signupMsg.innerHTML = '비밀번호를 입력해주세요';
				return;
			}
			if(!pwCheck.value){
				signupMsg.innerHTML = '비밀번호 확인을 입력해주세요';
				return;
			}
			if(signUpPw.value == pwCheck.value){
				pwCheckRes.value=1;
				signupMsg.innerHTML='';
			} else{
				signupMsg.innerHTML = '비밀번호가 일치하지 않습니다.';
				pwCheckRes.value=0;
				signUpPw.focus();
				pwCheck.value='';
				signUpPw.value='';
			}
    	});
    	
    	btnSignup.addEventListener('click', function(e){
        	// 기본 이벤트 초기화
        	e.preventDefault();
        	
        	let email = signUpEmail.value;
        	let pw = signUpPw.value;
        	let name = signUpName.value;
        	let nickname = signUpNickname.value;
        	let pNum = signUpPNum.value;
        	
        	
        	if(!email){
        		signupMsg.innerHTML = '이메일을 입력해주세요';
        		return;
        	}
        	if(!pw){
        		signupMsg.innerHTML = '비밀번호를 입력해주세요';
        		return;
        	}
        	if(!name){
        		signupMsg.innerHTML = '이름을 입력해주세요';
        		return;
        	}
        	if(!nickname){
        		signupMsg.innerHTML = '닉네임을 입력해주세요';
        		return;
        	}
        	if(!pNum){
        		signupMsg.innerHTML = '휴대폰 번호를 입력해주세요';
        		return;
        	}
        	
        	// 아이디 중복체크 확인
        	if(emailCheckRes.value != 1){
        		signupMsg.innerHTML = '아이디 중복체크를 해주세요';
        		signUpEmail.focus();
        		return;
        	}
        	
        	// 닉네임 중복체크 확인
        	if(nicknameCheckRes.value != 1){
        		signupMsg.innerHTML = '닉네임 중복체크를 해주세요';
        		signUpNickname.focus();
        		return;
        	}
        	
        	// 비밀번호 일치 확인
        	if(pwCheckRes.value != 1){
        		signupMsg.innerHTML = '비밀번호가 일치하는지 확인 해주세요';
        		pwCheck.focus();
        		return;
        	}
        	
        	obj = {
        			email : email
        			, pw : pw
        			, name : name
        			, nickname : nickname
        			, pnum : pNum
        	}
        	
        	console.log('회원가입 obj : ', obj);
        	
        	// 회원가입 요청
        	fetchPost('/register'
        				, obj
        				, (map)=>{
					        if(map.result == 'success'){
					        	location.href='/login?msg='+map.msg;
					        } else {
					        	signupMsg.innerHTML = map.msg;
					        }
					  });
        })
		
		function confirmEmailCheck() {
	        var email = $('#signUpEmail').val();
	
	        $.ajax({
	            type: "POST",
	            url: "/confirmEmail",
	            data: { email: email },
	            success: function(response) {
	                if (response.success) {
	                    alert('인증번호가 이메일로 발송되었습니다.');
	                } else {
	                    alert('가입되지 않은 이메일입니다.');
	                }
	            },
	            error: function() {
	                alert('요청 처리 중 오류가 발생했습니다.');
	            }
	        });
    	}
        
    	function loginCheck(map){
    		// 로그인 성공 -> list 로 이동
    		// 실패 -> 메세지 처리
    		if(map.result == 'success'){
    			location.href=map.url;
    		} else {
    			msg.innerHTML=map.msg;
    		}
    		console.log(map);
    	}
      })
    </script> 
    <script type="text/javascript" src="/resources/js/common.js"></script>
  </head>
	<body class="text-center">
	<main class="form-signin w-100 m-auto">
	
	<!-- 로그인 폼 -->
  <form name='signinForm'>
    <img class="mb-4" src="/resources/css/unnamed.png" alt="" width="200" height="150">
    <h1 class="h3 mb-3 fw-normal">로그인</h1>
	<div id="msg"></div>

    <div class="form-floating">
      <input value="kkk@naver.com" type="text" class="form-control start" required="required" id="email" placeholder="email">
      <label for="email">Email</label>
    </div>
    <div class="form-floating">
      <input value="1234" type="password" class="form-control end" id="pw" placeholder="Password">
      <label for="pw">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-email"> 이메일 저장 
        <input type="checkbox" value="auto-login"> 자동로그인
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit" id='btnSignin'>로그인</button>
    
    <a href = "/findEmailForm">이메일 찾기</a>
    <a href = "/findPwForm">비밀번호 찾기</a>
    <a href = "/sendPwForm">임시 비밀번호 발급</a>
    
    
  </form>
  <!-- 회원가입 폼 -->
  <form name='signupForm' style='display:none'>
    <img class="mb-4" src="/resources/css/unnamed.png" alt="" width="200" height="150">
    <h1 class="h3 mb-3 fw-normal">회원가입</h1>

	<div id="signupMsg"></div>
    <div class="form-floating">
      <input type="text" class="form-control start" id="signUpEmail" placeholder="email">
      <button onclick="confirmEmailCheck()" type="button">인증번호 전송</button>
      <label for="email">Email</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control middle" id="confirmEmail2" placeholder="email">
      <button id='confirmcheck' type="button">인증번호 확인</button>
      <label for="email">ConfirmEmail</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control middle" id="signUpPw" placeholder="Password">
      <label for="pw">Password</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control middle" id="pwCheck" placeholder="Password">
      <label for="pwCheck">PasswordCheck</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control middle" id="signUpName" placeholder="name">
      <label for="name">Name</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control middle" id="signUpNickname" placeholder="nickname">
      <label for="nickname">Nickname</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control end" id="signUpPNum" placeholder="pNum">
      <label for="pNum">Phone without ' - '</label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" id='btnSignup' type="submit">회원가입</button>
    
    <input type="text" value="0" id="emailCheckRes">
    <input type="text" value="0" id="nicknameCheckRes">
    <input type="text" value="0" id="pwCheckRes">
    
    
  </form>

  <button id='btnSignupView'>회원가입</button>
  <button id='btnSigninView'>로그인</button>
</main>


   	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>


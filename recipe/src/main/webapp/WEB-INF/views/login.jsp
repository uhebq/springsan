<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
    
<!doctype html>
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
		
        // 회원가입폼 출력
        btnSignupView.addEventListener('click',function(){
          signupForm.style.display='';
          signinForm.style.display='none';
        })
        
        // 로그인
        btnSignin.addEventListener('click', function(e){
			// 기본이벤트 재거
			e.preventDefault();
			
			// 파라메터 수집
			let obj={
				id : document.querySelector('#id').value
				, pw : document.querySelector('#pw').value 
			}
			
			console.log(obj);
			
			// 요청
			fetchPost('/loginAction', obj, loginCheck)
		})
		
		signUpId.addEventListener('blur', function(){
			
			// 입력체크
			if(!signUpId.value){
				signupMsg.innerHTML = '아이디를 입력 해주세요';
				return;
			}
			
			// 파라메터 세팅
			let obj={ id : signUpId.value };
			console.log("아이디 체크", obj);
			
			// 아이디 체크 -> 서버에 다녀와야 해요
			fetchPost('/idCheck', obj, (map)=>{
		    	  if(map.result == 'success'){
		    		  idCheckRes.value='1'; // 아이디 사용 가능
		    		  signUpName.focus();
		    	  } else {
		    		  idCheckRes.value='0'; // 아이디 사용 불가능
		    		  signUpId.focus();
		    		  signUpId.value='';
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
        	
        	let id = signUpId.value;
        	let pw = signUpPw.value;
        	let name = signUpName.value;
        	
        	if(!id){
        		signupMsg.innerHTML = '아이디를 입력해주세요';
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
        	
        	// 아이디 중복체크 확인
        	if(idCheckRes.value != 1){
        		signupMsg.innerHTML = '아이디 중복체크를 해주세요';
        		signUpId.focus();
        		return;
        	}
        	
        	// 비밀번호 일치 확인
        	if(idCheckRes.value != 1){
        		signupMsg.innerHTML = '비밀번호가 일치하는지 확인 해주세요';
        		pwCheck.focus();
        		return;
        	}
        	
        	obj = {
        			id : id
        			, pw : pw
        			, name : name
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

      })
      
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
      
      
    </script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
  </head>
  <body class="text-center">
    
<main class="form-signin w-100 m-auto">

  <!-- 로그인폼 -->
  <form name='signinForm'>
    <img class="mb-4" src="/resources/css/bootstrap-logo.svg" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
	<div id="msg">${param.msg }</div>
	
    <div class="form-floating">
      <input type="text" class="form-control start" required="required" id="id" placeholder="id">
      <label for="id">id</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control end" required="required" id="pw" placeholder="Password">
      <label for="pw">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me"> Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit" id='btnSignin'>로그인</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
  </form>
  
  <!-- 회원가입폼 -->
  <form name='signupForm' style='display:none'>
  	<img class="mb-4" src="/resources/css/bootstrap-logo.svg" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">Please sign up</h1>
	<div id="signupMsg"></div>
    <div class="form-floating">
      <input type="text" class="form-control start" id="signUpId" placeholder="id">
      <label for="signUpId">id</label>
    </div>
    <div class="form-floating">
      <input type="text" class="form-control middle" id="signUpName" placeholder="name">
      <label for="signUpName">name</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control middle" id="signUpPw" placeholder="Password">
      <label for="signUpPw">Password</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control end" id="pwCheck" placeholder="Password">
      <label for="pwCheck">PasswordCheck</label>
    </div>
    
    <button class="w-100 btn btn-lg btn-primary" id='btnSignup' type="submit">회원가입</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
    
    
    <input type="text" value="0" id="idCheckRes">
  	<input type="text" value="0" id="pwCheckRes">
  
  </form>

  <button id='btnSignupView'>회원가입</button>
  <button id='btnSigninView'>로그인</button>
  <%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
  <%
    String clientId = "K4dBcaR2392POv2SFnSD";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    
    // 요청URL -> 네이버로그인및 사용자정보제공 동의 -> 콜백으로 코드를 제공
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
  <a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
  
</main>

   	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    
  </body>
</html>
 
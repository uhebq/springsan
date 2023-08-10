
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더</title>

	<!-- 파비콘 설정 -->
	<link rel="icon" href="/resources/images/favicon.ico">

	<!-- 부트스트랩 아이콘 (MI, 2023/07/26)-->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
	
	<!-- 폰트어썸 아이콘 (MI, 2023/07/26) -->
	<script src="https://kit.fontawesome.com/bc0f5040fb.js" crossorigin="anonymous"></script>
	
	
	<!-- 템플릿 css (MI, 2023/07/26) -->
	<link rel="stylesheet" href="/resources/assets/css/main.css" />
	
	<!-- 템플릿 js (MI, 2023/07/26) -->
	<script src="/resources/assets/js/jquery.dropotron.min.js"></script>
	<script src="/resources/assets/js/browser.min.js"></script>
	<script src="/resources/assets/js/breakpoints.min.js"></script>
	
		
	<!-- 공통  css (recipe 플젝) (MI, 2023/07/26) -->	
	<link rel="stylesheet" href="/resources/recipe_css/common.css">
	
	<!-- 공통  js (MI, 2023/07/26) -->	
    <script src='/resources/recipe_js/common.js'></script> 

</head>
<body>
<!-- Header -->
	<section id="header">
		<div class="container">

			<!-- Logo -->
				<h1 id="logo">오늘 뭐먹조 ?</h1>

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a class="icon solid fa-home" href="main"><span>홈페이지</span></a></li>
						<li><a class="icon solid bi bi-chat-dots-fill" href="com_list"><span>커뮤니티</span></a></li>
						<li><a class="icon solid bi-gear-fill" href="admin"><span>관리자페이지</span></a></li>
						<li><a class="icon solid bi bi-key-fill" href="login"><span>로그인</span></a></li>
						<li><a class="icon solid bi-person-plus-fill" href="register"><span>회원가입</span></a></li>
						<!-- <li>
							<a href="#" class="icon fa-chart-bar"><span>Dropdown</span></a>
							<ul>
								<li><a href="#">Lorem ipsum dolor</a></li>
								<li><a href="#">Magna phasellus</a></li>
								<li><a href="#">Etiam dolore nisl</a></li>
								<li>
									<a href="#">Phasellus consequat</a>
									<ul>
										<li><a href="#">Magna phasellus</a></li>
										<li><a href="#">Etiam dolore nisl</a></li>
										<li><a href="#">Phasellus consequat</a></li>
									</ul>
								</li>
								<li><a href="#">Veroeros feugiat</a></li>
							</ul>
						</li> -->
					</ul>
				</nav>
				
			<!-- 검색 -->
			<br>
			<div class="search-wrapper active">
			    <div class="input-holder">
			    <form action="./list" name="searchFormHeader">
			        <input type="text" name="sWord" class="search-input" placeholder="레시피를 검색하세요." />
			        <button class="search-icon" onclick="searchToggle(this, event);"><span><i class="bi bi-search"></i></span></button>
			      </form>
			    </div>
			    <span class="searchClose" onclick="searchToggle(this, event);"><i class="bi bi-x-circle"></i></span>
			</div>
		</div>
	</section>
	
	<script>
		// 검색 버튼 액션
		function searchToggle(obj, evt){
		    var container = $(obj).closest('.search-wrapper');
		        if(!container.hasClass('active')){
		            container.addClass('active');
		            evt.preventDefault();
		        }
		        else if(container.hasClass('active') && $(obj).closest('.input-holder').length == 0){
		            container.removeClass('active');
		            // clear input
		            container.find('.search-input').val('');
		        }
		}
	</script>
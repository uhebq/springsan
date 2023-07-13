<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>Fixed top navbar example · Bootstrap v5.2</title>

	<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbar-fixed/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

	<!-- CSS -->
	<link href="/resources/css/style.css" rel="stylesheet">
	
	<!-- JS -->
	<script src="/resources/js/reply.js"></script>

</head>
<body>

<%@include file="../common/header.jsp" %>
<script type="text/javascript">

window.addEventListener('load',function(){
	
	// 수정페이지로 이동
	btnEdit.addEventListener('click',function(){
		viewForm.action='/board/edit';
		viewForm.submit();
	});
	
	// 삭제 처리후 리스트 페이지로 이동
	btnDelete.addEventListener('click', function(){
		viewForm.action='/board/delete';
		viewForm.submit();
	});
	
	// 리스트 페이지로 이동
	btnList.addEventListener('click', function(){
		viewForm.action='/board/list';
		viewForm.submit();
	});
	
	// 답글등록 버튼
	btnReplyWrite.addEventListener('click', function(){
		replyWrite();
	});
	
	// 댓글목록 조회및 출력
	getReplyList();
	
});

</script>
<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판 상세보기</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" 
    	href="#" id="btnList" 
    	role="button">리스트 &raquo;</a>
  </div>
  <p></p>
  <!-- 상세보기 -->
  <div class="list-group w-auto">
    <form method="get" name="viewForm">
    
    <!-- 파라메터 -->
    <input type="text" name="pageNo" value="${param.pageNo }">
    <input type="text" name="searchField" value="${param.searchField }">
    <input type="text" name="searchWord" value="${param.searchWord }">
    <input type="text" name="bno" id="bno" value="${board.bno }">
    
    
	<div class="mb-3">
	  <label for="title" class="form-label">제목</label>
	  <input name="title" id="title" 
	  type="text" readonly class="form-control" value='${board.title }'>
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">내용</label>
	  <textarea class="form-control" id="content" readonly
	  			 name="content" rows="3">${board.content }</textarea>
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">작성자</label>
	  <input type="text" class="form-control" readonly
	  			value='${board.writer }' id="writer" name="writer">
	</div>
	
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
		<button type="button" id="btnEdit" 
				class="btn btn-primary btn-lg">수정</button>
				
		<button type="button" id="btnDelete" 
				class="btn btn-primary btn-lg">삭제</button>
	</div>
	
	</form>
    
  </div>
  <p></p>
  <input type="text" id="replyer" value="작성자">
  <div class="input-group">
  	<span class="input-group-text">답글작성</span>
  	<input type="text" 
  				aria-label="First name" 
  				class="form-control"
  				id="reply">
  	<input type="button" 
			  	id="btnReplyWrite" 
			  	aria-label="Last name" 
			  	class="input-group-text" 
			  	value="등록하기">
  </div>
  <!-- 댓글 리스트 -->
  <div id="replyDiv"></div>
  
</main>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

</body>
</html>

















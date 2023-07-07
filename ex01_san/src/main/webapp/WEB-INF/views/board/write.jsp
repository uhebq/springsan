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
	<link href="/resources/css/style.css" rel="stylesheet">
	<!-- <link href="../assets/dist/css/bootstrap.min.css" rel="stylesheet">-->
	
</head>
<body>

<%@include file="../common/header.jsp" %>

<script type="text/javascript">
	// 메세지 처리
	let msg = '${msg}';
	if(msg != ''){
		alert(msg);
	}
</script>

<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" href="/board/list" role="button">리스트 &raquo;</a>
  </div>
  <p></p>
  <!-- 글쓰기 -->
  <div class="list-group w-auto">
  <form method="post" action="/board/write">
  
	<div class="mb-3">
	  <label for="title" class="form-label">제목</label>
	  <input type="text" class="form-control" id="title" name="title">
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">내용</label>
	  <textarea class="form-control" id="content" name="content" rows="3"></textarea>
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">작성자</label>
	  <input type="text" class="form-control" id="writer" name="writer">
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
	<button type="submit" class="btn btn-primary btn-lg">글쓰기</button>
	<button type="reset" class="btn btn-secondary btn-lg">초기화</button>
	</div>
 </form>
</div>
</main>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>
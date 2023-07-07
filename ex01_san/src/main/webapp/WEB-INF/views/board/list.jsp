<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
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
		
		window.onload= function(){
			
			if(msg != ''){
				
				// 메세지 출력
				document.querySelector(".modal-body").innerHTML = msg;
				// 버튼 출력제어
				document.querySelector("#btnModalSave").style.display='none'
				
				const myModal = new bootstrap.Modal('#myModal', {
					  keyboard: false
					});	
				// 모달 보여주기
				myModal.show();
			}
		}
	
</script>

<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" href="/board/write" role="button">글쓰기 &raquo;</a>
  </div>
  <p></p>
  <!-- 리스트 출력 -->
  <div class="list-group w-auto">
  
  <c:forEach items="${list }" var="vo">
    <a href="#" class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true">
      <img src="https://github.com/twbs.png" alt="twbs" width="32" height="32" class="rounded-circle flex-shrink-0">
      <div class="d-flex gap-2 w-100 justify-content-between">
        <div>
          <h6 class="mb-0">${vo.title }</h6>
          <p class="mb-0 opacity-75">${vo.writer }</p>
        </div>
        <small class="opacity-50 text-nowrap">${vo.regDate }</small>
      </div>
    </a>
   </c:forEach>
  </div>
</main>


<!-- Modal -->
<div id="myModal" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
       ... 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
        <button type="button" id="btnModalSave" class="btn btn-primary">저장</button>
      </div>
    </div>
  </div>
</div>

    <!--  <script src="../assets/dist/js/bootstrap.bundle.min.js"></script>-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

      
  </body>
</html>
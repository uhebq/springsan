<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<nav aria-label="navigation example">
	<ul class="pagination">

	<%-- 앞으로 가기 버튼 --%>
	<c:if test="${pageNo ne pageNumber}">	
    	<li class="page-item"><a class="page-link" href="/board/list_bs?searchField=${param.searchField}&searchWord=${param.searchWord}&pageNo=${pageNumber}">&lt;&lt;</a></li>
		<li class="page-item"><a class="page-link" href="/board/list_bs?searchField=${param.searchField}&searchWord=${param.searchWord}&pageNo=${pageNo+1}">&lt;</a></li>
	</c:if>
	
	<%-- 버튼 생성 --%>
	<c:forEach begin="1" end="5" var="i" step="1">
		<li class="page-item"><a class="page-link">${ i} </a></li>
	</c:forEach>
	
		<%-- 첫페이지면 뒤로가기 버튼 안보여주기 --%>
	<c:if test="${pageNo ne 1}" var="res">
    	<li class="page-item"><a class="page-link" href="/board/list_bs?searchField=${param.searchField}&searchWord=${param.searchWord}&pageNo=${pageNo-1}">&gt;</a></li>
	    <li class="page-item"><a class="page-link" href="/board/list_bs?searchField=${param.searchField}&searchWord=${param.searchWord}&pageNo=1">&gt;&gt;</a></li>
	</c:if>
	
	</ul>
	</nav>
	

</body>
</html>
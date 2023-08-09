<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>인증결과</title>
</head>
<body>
    <h1>인증결과</h1>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
</body>
</html>

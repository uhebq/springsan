<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>�������</title>
</head>
<body>
    <h1>�������</h1>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
</body>
</html>

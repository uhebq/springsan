<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>인증번호 확인</title>
</head>
<body>
    <h1>인증번호 확인</h1>
    <form action="/checkVerificationCode" method="post">
        <label for="code">인증번호:</label>
        <input type="text" id="code" name="code" required>
        <button type="submit">확인</button>
    </form>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
</body>
</html>

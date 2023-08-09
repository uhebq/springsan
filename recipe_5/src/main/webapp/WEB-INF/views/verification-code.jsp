<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>인증번호 발급</title>
</head>
<body>
    <h1>인증번호 발급</h1>
    <form action="/sendVerificationCode" method="post">
        <label for="email">이메일:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">인증번호 발송</button>
    </form>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
    <a href="/verifyCode">인증번호 확인</a>
</body>
</html>

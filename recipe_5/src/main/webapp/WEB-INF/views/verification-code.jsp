<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>������ȣ �߱�</title>
</head>
<body>
    <h1>������ȣ �߱�</h1>
    <form action="/sendVerificationCode" method="post">
        <label for="email">�̸���:</label>
        <input type="email" id="email" name="email" required>
        <button type="submit">������ȣ �߼�</button>
    </form>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
    <a href="/verifyCode">������ȣ Ȯ��</a>
</body>
</html>

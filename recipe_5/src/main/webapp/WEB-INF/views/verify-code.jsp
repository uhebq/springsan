<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>������ȣ Ȯ��</title>
</head>
<body>
    <h1>������ȣ Ȯ��</h1>
    <form action="/checkVerificationCode" method="post">
        <label for="code">������ȣ:</label>
        <input type="text" id="code" name="code" required>
        <button type="submit">Ȯ��</button>
    </form>
    <div th:if="${message}">
        <p th:text="${message}"></p>
    </div>
</body>
</html>

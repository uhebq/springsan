<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>비밀번호 찾기</title>
</head>
<body>

	<h1>임시 비밀번호 발급</h1>
    <form action="/recipe/sendPw" method="post">
        <label for="email">이메일:</label>
        <input type="text" id="email" name="email" required>
        <label for="name">이름:</label>
        <input type="text" id="name" name="name" required>
        <button type="submit">임시 비밀번호 발송</button>
    
    ${msg }    
    ${error }
    </form>
</body>
</html>
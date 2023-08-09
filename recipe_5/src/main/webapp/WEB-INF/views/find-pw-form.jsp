<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    
</head>
<body>
    <div class="container">
        <h2>비밀번호 찾기</h2>
        <form action="/findPw" method="post">
            <div class="form-group">
                <label for="email">아이디 : </label>
                <input type="text" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="name">이름 : </label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <input type="submit" value="찾기">
            </div>
            
            ${foundPw }
            ${error }
        </form>
    </div>
</body>
</html>
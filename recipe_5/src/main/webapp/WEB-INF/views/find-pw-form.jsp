<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>��й�ȣ ã��</title>
    
</head>
<body>
    <div class="container">
        <h2>��й�ȣ ã��</h2>
        <form action="/findPw" method="post">
            <div class="form-group">
                <label for="email">���̵� : </label>
                <input type="text" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="name">�̸� : </label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <input type="submit" value="ã��">
            </div>
            
            ${foundPw }
            ${error }
        </form>
    </div>
</body>
</html>
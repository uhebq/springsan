<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이메일 찾기</title>
    
</head>
<body>
    <div class="container">
        <h2>이메일 찾기</h2>
        <form action="/recipe/findEmail" method="post">
            <div class="form-group">
                <label for="name">이름 : </label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="pNum">전화번호 : </label>
                <input type="text" id="pnum" name="pnum" required>
            </div>
            <div class="form-group">
                <input type="submit" value="찾기">
            </div>
            
            ${foundEmail }
            ${error }
            <%-- <c:if var=foundEmail test="${foundEmail }"></c:if>
            <c:if var=error test="${error }"></c:if> --%>
            
        </form>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>�̸��� ã��</title>
    
</head>
<body>
    <div class="container">
        <h2>�̸��� ã��</h2>
        <form action="/recipe/findEmail" method="post">
            <div class="form-group">
                <label for="name">�̸� : </label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="pNum">��ȭ��ȣ : </label>
                <input type="text" id="pnum" name="pnum" required>
            </div>
            <div class="form-group">
                <input type="submit" value="ã��">
            </div>
            
            ${foundEmail }
            ${error }
            <%-- <c:if var=foundEmail test="${foundEmail }"></c:if>
            <c:if var=error test="${error }"></c:if> --%>
            
        </form>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣ ã��</title>
</head>
<body>

	<h1>�ӽ� ��й�ȣ �߱�</h1>
    <form action="/sendPw" method="post">
        <label for="email">�̸���:</label>
        <input type="text" id="email" name="email" required>
        <label for="name">�̸�:</label>
        <input type="text" id="name" name="name" required>
        <button type="submit">�ӽ� ��й�ȣ �߼�</button>
    
    // ��ũ��Ʈ �����
    	��ũ��Ʈ�ȿ� ���� ���� alert �޽��� ����
    	Ȯ�� ������ location.href �� �α��� �������� �̵�
    ${msg }    
    ${error }
    </form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SimpleMDE Example</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
</head>
<body>

    <!-- Your content goes here -->
    <textarea id="my-textarea"></textarea>
    <button onclick="sendToServer()">Send to Server</button>

    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script>
        var simplemde = new SimpleMDE({ element: document.getElementById("my-textarea") });

        function sendToServer() {
            // 클라이언트에서 SimpleMDE로 작성된 문서를 얻어옴
            var documentContent = simplemde.value();

            // 서버로 전송
            fetch('/simpleMDE/simpleMDEAction', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ content: documentContent }),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // 서버의 응답 처리
                console.log(data.message);
                simplemde.value(data.content+data.content);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
        }
    </script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SimpleMDE Image Upload Example</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/dropzone/dist/min/dropzone.min.css">
</head>
<body>
    <!-- Your content goes here -->
    <form action="/uploadImage" class="dropzone" id="myDropzone">
        <!-- 이미지를 드래그 앤 드롭하거나 파일 선택을 위한 영역 -->
    </form>
    <textarea id="editor"></textarea>

    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/dropzone/dist/min/dropzone.min.js"></script>
    <script>
        var simplemde = new SimpleMDE({ element: document.getElementById("editor") });

        // Dropzone.js와 SimpleMDE를 연동하여 이미지 업로드 처리
        Dropzone.options.myDropzone = {
            url: "/uploadImage", // 이미지 업로드를 처리할 서버 엔드포인트로 변경해야 합니다.
            acceptedFiles: "image/*",
            init: function () {
                this.on("success", function (file, response) {
                    var imageUrl = response.imageUrl;
                    var markdownImage = "![" + file.name + "](" + imageUrl + ")";
                    simplemde.codemirror.replaceSelection(markdownImage);
                });
            }
        };
    </script>
</body>
</html>

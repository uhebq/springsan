<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	window.addEventListener('load', function(){
	
		// 리스트 조회
		btnList.addEventListener('click', function(){
			getFileList();
		})
		
		// 파일 업로드
		btnFileupload.addEventListener('click', function(){
			// 웹 개발에서 HTML 폼 데이터를 
			// JavaScript로 쉽게 조작하고 전송하는 방법을 제공하는 API입니다
			let formData = new FormData(fileuploadForm);
			formData.append('name', 'momo');
			
			console.log("formData : ", formData);
			// FormData값 확인
			for(var pair of formData.entries()){
				if(typeof(pair[1]) == 'object'){
					let fileName = pair[1].name;
					let fileSize = pair[1].fileSize;
					// 파일 확장자, 크기 체크
					// 서버에 전송 가능한 형식인지 확인
					// 최대 전송가능한 용량을 초과 하지 않는지
					if(!checkExtension(fileName, fileSize)){
						return false;
					}
					console.log('fileName', fileName);
					console.log('fileSize', fileSize);
				}
			}
			
			fetch('/file/fileuploadActionFetch'
					, {
						method:'post'
						, body : formData
					})
					// 요청결과 json문자열을 javascript 객체로 반환
					.then(response => response.json())
					// 콜백함수 실행
					.then(map => fileuploadRes(map));
			
				
			});
	})

	function checkExtension(fileName, fileSize){
		let maxSize = 1024 * 1024 * 1;
		// .exe, .sh, .zip, .alz 끝나는 문자열
		// 정규표현식 : 특정 규칙을 가진 문자열을 검색 하거나 치환 할때 사용
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		if(maxSize <= fileSize){
			alert("파일 사이즈 초과");
			return false;
		}
		
		// 문자열에 정규식 패턴을 만족하는 값이 있으면 true, 없으면 false를 리턴한다
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.")
			return false;
		}
		
		return true;
	}
	
	function fileuploadRes(map){
		if(map.result == 'success'){
			divFileuploadRes.innerHTML = map.msg;
			// 게시글등록
		} else {
			alert(map.msg);
			
		}
	}
	
	function getFileList(){
		///file/list/{bno}
		let bno = document.querySelector("#bno").value;
		fetch('/file/list/'+bno)
			.then(response => response.json())
			.then(map => viewFileList(map));
		
	}
	
	function attachFileDelete(e){
		let bno = e.dataset.bno;
		let uuid = e.dataset.uuid;
		// 값이 유효하지 않은 경우 메세지 처리
		// fetch 요청
		//fetch('/file/delete/'+uuid+'/'+bno)
		// el 표현식 -> \${ } (el 표현식으로 처리 하지 않음)
		fetch(`/file/delete/\${uuid}/\${bno}`)
			.then(response => response.json())
			.then(map => fileDeleteRes(map));
		
	}
	
	// 삭제 결과 처리
	function fileDeleteRes(map){
		if(map.result == 'success'){
			console.log(map.msg);
			// 리스트 조회
			getFileList();
		} else {
			alert(map.msg);
		}
	}
	
	function viewFileList(map){
		console.log(map);
		let content = '';
		if(map.list.length > 0){
			map.list.forEach(function(item, index){
			let savePath = encodeURIComponent(item.savePath);
			content += "<a href='/file/download?fileName=" 
				    + savePath+"'>"
					+ item.filename + '</a>'
					+ ' <i onclick="attachFileDelete(this)" '
					+ '   data-bno="'+item.bno+'" data-uuid="'+item.uuid+'" '
					+ '   class="fa-regular fa-square-minus"></i>'
					+ '<br>';
			})
		} else {
			content = '등록된 파일이 없습니다.';
		}
		divFileupload.innerHTML = content;
		
	}
	
</script>
<script src="https://kit.fontawesome.com/ba30180671.js" crossorigin="anonymous"></script>
</head>
<body>
	<h2>파일 업로드</h2>
	<form method="post" enctype="multipart/form-data" 
			action="/file/fileuploadAction" name="fileuploadForm">
			
	<h2>파일선택</h2>
	bno : <input type="text" name="bno" id="bno" value="83"><br>
	<input type="file" name="files" multiple="multiple"><br>		
	
	<br>
	<button type="submit">파일업로드</button>		
	<button type="button" id="btnFileupload">Fetch파일업로드</button>
	
	<div id="divFileuploadRes">
	res : ${param.msg }
	</div>
	
	</form>
	
	<h2>파일 리스트 조회</h2>
	<button type="button" id="btnList">리스트 조회</button>
	<div id="divFileupload">
	
	
	</div>
	

</body>
</html>















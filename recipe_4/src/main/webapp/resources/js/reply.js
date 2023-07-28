console.log('reply.js=========')

// get방식 요청
function fetchGet(url, callback){
	try{
		// url 요청
		fetch(url)
			// 요청결과 json문자열을 javascript 객체로 반환
			.then(response => response.json())
			// 콜백함수 실행
			.then(map => callback(map));			
	}catch(e){
		console.log('fetchGet',e);
	}
}

// post방식 요청
function fetchPost(url, obj, callback){
	try{
		// url 요청
		fetch(url
				, {
					method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : JSON.stringify(obj)
				})
			// 요청결과 json문자열을 javascript 객체로 반환
			.then(response => response.json())
			// 콜백함수 실행
			.then(map => callback(map));			
	}catch(e){
		console.log('fetchPost', e);
	}
	
}


// 댓글 조회및 출력
function getReplyList(page){
	
	/**
	 * falsey : false, 0, "", NaN, undefined, null
	 * falsey한 값 이외의 값이 들어 있으면  true를 반환
	 * 
	 * page에 입력된 값이 없으면 1을 세팅
	 */
	if(!page){
		page = 1;
	}
	
	let bno = document.querySelector('#bno').value;
	console.log('bno : ', bno);
	
	console.log('/reply/list/' + bno + '/' + page);
	console.log(`/reply/list/${bno}/${page}`);
	
	// url : 요청경로
	// callback : 응답결과를 받아 실행시킬 함수
	fetchGet(`/reply/list/${bno}/${page}`, replyView)
	
}

// 리스트 결과를 받아서 화면에 출력
function replyView(map){
	let list = map.list;
	let pageDto = map.pageDto;
	console.log(list);
	console.log('pageDto=============', pageDto);
	
	// 리스트 사이즈를 확인하여 메세지 처리
	if(list.length == 0){
		replyDiv.innerHTML = '등록된 댓글이 없어요~!'
	} else {
		
		
		let replyDivStr = 
			'댓글목록'
			+ '<table class="table text-break text-center"">                       '
			+ '  <thead>                                   '
			+ '    <tr>                                    '
			+ '      <th scope="col" class="col-1">#</th>                '
			+ '      <th scope="col" class="col-9">댓글</th>            '
			+ '      <th scope="col" class="col-2">작성자</th>             '
			+ '    </tr>                                   '
			+ '  </thead>                                  '
			+ '  <tbody>                                   ';
		
		// 리스트를 돌며 댓글목록을 생성
		list.forEach(reply => {
			replyDivStr +=  
			  '    <tr id="tr'+reply.rno+'" data-value="'+reply.reply+'">                                    '
			+ '      <th scope="row">' + reply.rno + '</th>                '
			+ '      <td class="text-start">' + reply.reply; 

			// replyer.value : 로그인한 아이디
			// reply.replyer : 답글 작성자
			if(replyer.value == reply.replyer){
				replyDivStr +=
				  ' 		<i class="fa-regular fa-pen-to-square"'
				+ '				onclick="replyEdit('+ reply.rno +')"></i>'
				+ '			<i class="fa-regular fa-trash-can" '
				+ '				onclick="replyDelete('+ reply.rno +')"></i>';		
			}
			
			replyDivStr +=
			  '		 </td>                         '
			+ '      <td>' + reply.replyer
			+ '			<br>' + reply.replydate		
			+ '		 </td>                         '
			+ '    </tr>									';    	
		})
			                               
			
		replyDivStr += '  </tbody>                           '
						+ '</table>                          ';

		// 화면에 출력
		replyDiv.innerHTML = replyDivStr;
		
		// 페이지 블럭 생성
		let pageBlock = 
				  `<nav aria-label="...">                                                 `
				+ `  <ul class="pagination justify-content-center">                       `;
			
		if(pageDto.prev){
			// prev 버튼
			pageBlock += `    <li class="page-item disabled"`
				+ ` 					onclick="getReplyList(${pageDto.startNo-1})">                                    `
				+ `      <a class="page-link">Previous</a>                                `
				+ `    </li>                                                              `;
		}
		
		for(let i=pageDto.startNo; i<=pageDto.endNo; i++){
			let active = pageDto.cri.pageNo == i ? 'active' : '';
			// 페이지 버튼 startNo ~ endNo
			pageBlock += `    <li class="page-item ${active}" onclick="getReplyList(${i})">`
						+ `		<a class="page-link" href="#">`
						+ `		${i}`
						+ `		</a></li>     `;
		}
		
		if(pageDto.next){
			// next 버튼
			pageBlock += `    <li class="page-item" `
				+ `						onclick="getReplyList(${pageDto.endNo+1})">                                             `
				+ `      <a class="page-link" href="#">Next</a>                           `
				+ `    </li>                                                              `;
		}

				
		pageBlock += `  </ul>                                                                `
				+ `</nav>                                                                 `;
			                                                                      
		replyDiv.innerHTML += pageBlock;	
	}
		
//	if(!list){
//	} else {
//		replyDiv.innerHTML = '등록된 댓글이 없어요~!'
//	}                              
}

// 답글 등록하기
function replyWrite(){
	
	// 답글 작성시 필요한 데이터 수집 - bno, reply, replyer
	let bno = document.querySelector('#bno').value;
	let reply = document.querySelector('#reply').value;
	let replyer = document.querySelector('#replyer').value;
	
	// 전달할 객체로 생성
	let obj = {bno : bno
			, reply : reply
			, replyer : replyer};
	
	console.log(obj);
	
	// url : 요청경로
	// obj : JSON형식으로 전달할 데이터
	// callback : 콜백함수(응답결과를 받아서 처리할 함수)
	fetchPost('/reply/insert', obj, replyRes);
}

// 답글 등록, 수정, 삭제의 결과를 처리하는 함수
function replyRes(map){
	console.log(map);
	if(map.result == 'success'){
		// 성공 : 리스트 조회및 출력
		getReplyList();
	} else {
		// 실패 : 메세지 출력
		alert(map.message);
	}
		
}

// 답글 삭제하기
function replyDelete(rno){
	console.log('rno', rno )
	fetchGet('/reply/delete/' + rno, replyRes);
}

function replyEdit(rno){
	let tr = document.querySelector('#tr'+rno);
	let replyTxt = tr.dataset.value;
	console.log('tr',tr);
	tr.innerHTML = '<td colspan="3">'                                       
					+ '	<div class="input-group">                           '
					+ '	  	<span class="input-group-text">답글수정</span>  '
					+ '	  	<input type="text"                              '
					+ '	  				aria-label="First name"             '
					+ '	  				class="form-control"                '
					+ '	  				id="reply'+rno+'" value="'+ replyTxt +'">                         '
					+ '	  	<input type="button"                            '
					+ '				  	onclick="replyEditAction('+ rno +')" '
					+ '				  	aria-label="Last name"              '
					+ '				  	class="input-group-text"            '
					+ '				  	value="수정하기">                   '
					+ '	  </div>                                            '
					+ '</td>';
	
}


function replyEditAction(rno){
	// 파라메터 수집
	let reply = document.querySelector('#reply'+rno).value;
	
	// 전송할 데이터를 JS 객체로 생성
	let obj = {
			rno : rno
			, reply : reply
	}
		
	// 서버에 요청
	fetchPost('/reply/editAction', obj, replyRes);
	
}














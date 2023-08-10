console.log('연결 확인 ===================================================') 
	
		// swal 커스텀함수
		function alertCustomizing(){
			
			// 성공swal 커스텀
			console.log('왜 때문에 실행이 되지 않을까 ?? =============================');
			document.querySelector('.swal-button').style.backgroundColor= '#F7863B';
			
			const elements = document.querySelectorAll('.swal-icon--success__line');
			
			elements.forEach(element => {
			    element.style.backgroundColor = '#F7863B';
			});
			
			document.querySelector('.swal-icon--success').style.borderColor ='#F7863B';
			
			
			//
		};
		
		
		
		function initializeStarRatings() {
   			 $(".test-score1").score();
  		}

		function openModalAndInitializeStars() {
		    const modal = document.getElementById("photoReviewModal");
		    modal.style.display = "flex";
		    initializeStarRatings(); // 모달창의 댓글에 대한 별점 초기화
		  }
		
		
		document.addEventListener("click", function (event) {
		    if (event.target && event.target.className == "fa-circle-info") {
		      // "fa-circle-info" 클래스 이름을 가진 요소를 클릭할 때 모달을 열어 별점을 초기화합니다.
		      const i_no = event.target.getAttribute("onclick").match(/\d+/)[0];
		      goModal(i_no);
		      openModalAndInitializeStars();
		    }
		  });
		
		function getFileList(){
		
		
		let bno = document.querySelector('#b_no').value;
		
		// fetch 사용
		// 정보를 가져와서 함수에 파라메터로 넣는다.
		fetch('/file/list/' + bno)
			.then(response => response.json())
			.then(map => viewFileList(map));
			
		}
		
		// 상단 img
		// ======================================================================
		
		// 최근 본 페이지로 이동하기 위해 Controller 에 b_no 값 전달
		function redirectToView(b_no) {
		    window.location.href = './view?b_no=' + b_no;
		}
		
		
		/**
		 * 최근 본 페이지를 로컬스토리지에서 꺼내 올 수 있는 함수입니다.
		 * @returns
		 */
		function recentPage() {
		    const recentlyViewed = document.getElementById('recentlyViewed');
		    const recentArray = JSON.parse(localStorage.getItem('recentArray')) || [];

		    
		    
		    
		    const uniqueItems = Array.from(new Set(recentArray.map(item => JSON.stringify(item))))
		        .map(itemString => JSON.parse(itemString));
		    let imgTag =''; 
		    uniqueItems.forEach((item, index) => {
		      
		    	
		    	let savePath = item.savePath;
		    	let b_no = item.b_no;
		    	let title = item.title;
		    	
		    	console.log('item.b_no : ', item.b_no);
		        console.log('item.savePath : ', item.savePath);
		        console.log('item.title : ', item.title);
		        
		        imgTag += 
		        	  '<div class="recentPageDiv" onclick="redirectToView(' + b_no + ')">'		        		
		        	+   '<img style="max-width:180px; max-height:180px;" src="/display?fileName=' + savePath +'">'
		        	+   '<div>'+title+'</div>'
		        	+ '</div>';
		        
		    });
		    
		    recentlyViewed.innerHTML = imgTag;
		}
		
		
		function viewFileList(map) {
		    let bno = document.querySelector('#b_no').value;
		    let title= document.querySelector('#title').value;
		    console.log('map 출력:', map.list);
		    let content = '';

		    if (map.list.length > 0) {
		        const recentArray = JSON.parse(localStorage.getItem('recentArray')) || [];

		        map.list.forEach(function (item, index) {
		            let savePath = encodeURIComponent(item.savePath);

		            content +=
		                '<a><img style="max-width:500px; max-height:500px;" src="/display?fileName=' + savePath + '"></a>';

		            // 데이터가 이미 recentArray에 존재하는지 확인
		            const isDuplicate = recentArray.some(existingItem =>
		                existingItem.b_no === bno && existingItem.savePath === savePath
		            );

		            if (!isDuplicate) {
		                const recent = {
		                    b_no: bno,
		                    savePath: savePath,
		                    title: title
		                };

		                recentArray.push(recent);
		            }
		        });

		        // 업데이트된 recentArray를 localStorage에 저장
		        localStorage.setItem('recentArray', JSON.stringify(recentArray));
		    } else {
		        content = '등록된 파일이 없습니다.';
		    }

		    headImgDiv.innerHTML = content;
		}
			
		// 요리완성 사진 불러오는 함수 // 
		
		function getFinishImg(){
			
			let bno = document.querySelector('#b_no').value;
			
			// fetch 사용
			// 정보를 가져와서 함수에 파라메터로 넣는다.
			fetch('/file/getFinishImg/' + bno)
				.then(response => response.json())
				.then(map => viewFinishImg(map));
			
		}
		
		
		

		/// =======================================================================================================================
		
		
		// 재료정보출력
		// ========================================================================
		
		function getMaterial(){
			
			let bno = document.querySelector('#b_no').value;
	   		
	   		// fetch 사용
	   		// 정보를 가져와서 함수에 파라메터로 넣는다.
	   		fetch('/recipe/material/' + bno)
	   			.then(response => response.json())
	   			.then(map => viewMaterialList(map));
	   			
	   		}
		
		
		function viewMaterialList(map){
				
			
			// item을 매개변수로 받아 변수 생성 , html 을 return 하는 함수 작성
			function materialItemHtml(item) {
			    let i_no = item.i_no; // 재료번호
			    let i_name = item.i_name; // 재료이름
			    let materialCnt = item.materialCnt; // 총 개수
			 
			    // Let's take it to the input hidden and make it available when
				// clicking on it.
			    // We need to get the picture associated with the comment later.
			    return (
			    		
			    		'<input type="hidden" value="'+i_no+'">'
		   				+'<li>'+ i_name +'&nbsp;<i class="fa-solid fa-circle-info" style="color: #b36b44;" onclick="goModal(' + i_no + ',\''+i_name+'\')"></i>'
		   				
		   				+'<span id="roketBtn"><i class="fa-solid fa-rocket" style="color: #588adf;" onclick=goRoket("'+i_name+'")></i></span>'
		   				+'<span class="material-span">'+materialCnt+'</span></li>'
			    );
			    
			  }
			
			
			
			// console.log('재료정보 map 출력 : ', map);
			let content = '<ul><b class="materialB">[재료]</b>';
							
			// Controller 에서 map 에 put 할때 지정한 값이 있다면..
			
			if(map.materialList.length > 0){
				
				map.materialList.forEach(function(item, index){
					
				//console.log('map.materialList : ', map.materialList);
				
					let i_name = item.i_name;
					let materialCnt = item.materialcnt;
					// input hidden 으로 가지고 간다음 클릭하면 넘어갈때 가져갈 수 있도록 해보자
					var i_no = item.i_no;
					
						
				content +=   						
					
					'<input type="hidden" value="'+i_no+'">'
				
   				+'<li style="border-bottom: 1px solid; border-color: #65361778;"><span class="materialName">'+i_name+'</span>&nbsp;<i class="fa-solid fa-circle-info" style="color: #F7863B;" onclick="goModal(' + i_no + ',\''+i_name+'\')"></i>'
   				+'<span id="roketBtn"><i class="fa-solid fa-rocket roket" style="color: #588adf;" onclick=goRoket("'+i_name+'")></i></span>'
   				+'<span class="material-span">'+materialCnt+'</span></li>'
   				
					
				})
				
				content += '</ul>'
				
			}else{
				content='재료 정보를 불러오지 못했습니다.';
			}
			
			
			materialContentDiv.innerHTML = content;
		}
		
		
		
		// 재료 출력 테스트
		function viewMaterialList_Test(map) {
			  // 항목을 담은 HTML 컨테이너를 반환하는 함수
			console.log('map.materialList : ', map.materialList)
			
			function materialItemHtml(item) {
			    let i_no = item.i_no; // 재료번호
			    let i_name = item.i_name; // 재료이름
			    let materialCnt = item.materialcnt; // 총 개수

			    // 클릭 시 사용할 숨은 입력(hidden)을 만듭니다.
			    // 나중에 댓글과 연관된 사진을 가져오기 위해 필요합니다.
			    return (
			      '<input type="hidden" value="' +
			      i_no +
			      '">' +
			      '<li>' +
			      i_name +
			      ' <i class="fa-solid fa-circle-info" style="color: #374967;" onclick="goModal(' +
			      i_no +
			      ', \'' +
			      i_name +
			      '\')"></i>' +
			      '<span class="material-span">' +
			      materialCnt +
			      '</span>' +
			      '<span id="rocketBtn"><i class="fa-solid fa-rocket" style="color: #588adf;" onclick="goRocket(\'' +
			      i_name +
			      '\')"></i></span></li>'
			    );
			  }

			  let content = '<ul><b class="materialB">[재료]</b>';
			  // 지도 컨트롤러에서 map에 값을 설정한 경우에만...
			  if (map.materialList.length > 0) {
			    // 처음 5개 항목만 표시합니다.
			    for (let index = 0; index < Math.min(5, map.materialList.length); index++) {
			      const item = map.materialList[index];
			      content += materialItemHtml(item);
			    }
			    
			    // 만약 5개보다 더 많은 항목이 있다면, 새로운 <ul>을 생성하여 나머지 항목을 표시합니다.
			    if (map.materialList.length > 5) {
			      content += '</ul><span> </span><ul>';
			      for (let index = 5; index < map.materialList.length; index++) {
			        const item = map.materialList[index];
			        content += materialItemHtml(item);
			      }
			    }
			  }

			  content += '</ul>';
			  materialContentDiv.innerHTML = content;
			}
		
		
		
		// 버튼을 클릭하면 모달창 출력
		function goModal(i_no, i_name){
			
// console.log('goMoal 출력 : ', i_no);
// console.log('goMoal 출력 : ', i_name);
			
			
		// fetch 사용
	   		// 정보를 가져와서 함수에 파라메터로 넣는다.
	   		fetch('/recipe/modal/ingredientModal/' + i_no +'/'+ i_name)
	   			.then(response => response.json())
	   			.then(map => viewIngredientsModal(map));
	   			
	   		}
			
		
		
		
		
		function viewIngredientsModal(map) {
			  // console.log('viewIngredientsModal: ', map.ingredient);

			  const modal = document.getElementById("ingredientModal");
			  const ingredientInfoDiv = document.getElementById("ingredientInfoDiv");
			  const ingredientTop = document.getElementById("ingredientTop");

			  if (map.ingredient == null) {
			    const errorMessage = '<img style="width: 460px;" src="/resources/img/다운로드.jpeg">'
			    					+'<p>재료정보가 없습니다. 관리자에게 문의 후 재료정보를 추가해주세요!</p>';
			    ingredientTop.innerHTML = errorMessage;
			    ingredientInfoDiv.innerHTML =''; // 초기화
			    modal.style.display = "flex";
			    return;
			  }

			  
			  let contentImg = '';
			  let content = '';

			  if (map.ingredientImg) {
			    let savePath = encodeURIComponent(map.ingredientImg.savePath);
			    contentImg =
			      '<a><img class="ingreModal_img" src="/display?fileName=' + savePath + '"><span><b class="ingreModalName">' + map.ingredient.i_name + '</b></span></a>';
			  } else {
			    contentImg = '<a><span><b class="ingreModalName">' + map.ingredient.i_name + '</b></span></a>';
			  }

			  if (map.ingredient) {
			    content +=
			      '<table border="0" cellpadding="0" cellspacing="0">' +
			      '<tbody><tr>' +
			      '<th width="200">효능</th>' +
			      '<td>' + map.ingredient.i_power + '</td>' +
			      '</tr>' +
			      '<tr>' +
			      '<th width="200">적합 음식</th>' +
			      '<td>' + map.ingredient.i_friendfood + '</td>' +
			      '</tr>' +
			      '<tr>' +
			      '<th width="200">소장</th>' +
			      '<td>' + map.ingredient.i_repair + '</td>' +
			      '</tr>' +
			      '<tr>' +
			      '<th width="200">관련 요리</th>' +
			      '<td>' + map.ingredient.i_cook + '</td>' +
			      '</tr>' +
			      '</tbody></table>';
			  } else {
			    content = '원료 정보를 가져오는 데 실패했습니다.';
			  }

			  ingredientInfoDiv.innerHTML = content;
			  ingredientTop.innerHTML = contentImg;
			  modal.style.display = "flex";
			}

		
		function closeModal() {
		  const modal = document.getElementById("ingredientModal");
		  modal.style.display = "none";
		
		}
			
			
		
		
		
		// --------------------------------------------------------------------------------------
			
		// 요리 순서 출력
		function getRecipeStep(){
			
			
			let bno = document.querySelector('#b_no').value;
	   		
	   		// fetch 사용
	   		// 정보를 가져와서 함수에 파라메터로 넣는다.
	   		fetch('/recipe/recipeStep/' + bno)
	   			.then(response => response.json())
	   			.then(map => viewRecipeStep(map));
	   			
	   		}
		
		//---------------------------------------------------------------------------------------
			
		function viewRecipeStep(map) {
		    console.log('조리순서 map 출력 : ', map);
		    
			
			let content = '';

		    if (map.recipeStep.length > 0) {
		        map.recipeStep.forEach(function (item, index) {
		            let step_content = item.step_content;
		            var s_no = item.s_no;
		            let stepNumber = index + 1;

		            // Verifica si 'map.fileStepList' está definido y es una
					// matriz
		            if (Array.isArray(map.fileStepList)) {
		                // Utiliza el método 'find' solo si 'map.fileStepList'
						// es una matriz
		               // console.log("fileStepList
						// =======================================")
		                
		                let matchedImage = map.fileStepList.find(function (imageItem) {
		                    return imageItem.s_no === s_no;
		                });
		                

		                	content +=
			                    '<input type="hidden" value="' + s_no + '">'
			                    + '<div id="recipeContainer">'
			                    + '<div class="stepNumber stepFont-Family">' + stepNumber + '</div>' // Add the step number here
			                    + '<div class="stepContent stepFont stepFont-Family" id="stepContent"><p style="margin-top: 5px; margin-left: 20px" class="stepFont-Family">'+ step_content +'</p></div>';
		                	

		                if (matchedImage) {
		                   
		                	let savePath = encodeURIComponent(matchedImage.savePath);
		                    
		                	//console.log('savePath : ', savePath);
		                    
		                    
		                    content +=
		                        '<div class="stepContent" id="stepImg"><a><img onclick=stepModal(\"'+savePath+'\") class="stepImg" src="/display?fileName=' + savePath + '"></a></div>';
		                }

		                content += 
		                    		 '</div>';
		            } else {
		                // Maneja el caso cuando 'map.fileStepList' no es una
						// matriz
		                content +=
		                    '<input type="text" value="' + s_no + '">'
		                    + '<div>'
		                    + '<ul>'
		                    + '<li class="">' + step_content + '</li>'
		                    + '</ul>'
		                    + '</div>';
		            }
		        });
		    } else {
		        content = '재료 정보를 불러오지 못했습니다.';
		    }

		    
		    recipeStepDiv.innerHTML = content;
		
		    
		    // 요리 완성 이미지 출력부분 
  
		    console.log('map.finishImgs : ', map.finishImgs);
		    
		    let finishImg =
	    		
		    	'<div style="width: 500px;" id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">'
		    	+  '<div style="" class="carousel-inner">';
		    
		    map.finishImgs.forEach(function (item, index){
		    	
		    	console.log('index : ', index);	
		    	
		    	if(index == 0){
		    		
		    		finishImg +=
				    	
			    		'<div class="carousel-item active">'
			    	+		'<img style="height: 600px;" src="/display?fileName=' + encodeURIComponent(item.savePath) + '" class="d-block w-100" alt="...">'
			    	+    '</div>';
		    	
		    	}else{
		    		
		    		finishImg +=
				    	
			    		'<div  class="carousel-item">'
			    	+		'<img style="height: 600px;" src="/display?fileName=' + encodeURIComponent(item.savePath) + '" class="d-block w-100" alt="...">'
			    	+    '</div>';
		    	}
		    	
		    	    
		  	
		    /*	finishImg += max-height:700px; max-width:500px;
		    		
		    		'<img style="max-height:700px; max-width:500px;" class="stepImg" src="/display?fileName=' + encodeURIComponent(item.savePath) + '">';*/
		    	
		    });
				    
				    finishImg +=      
		    			
				    	'</div>'
		    	+ 	 '<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">'
		    	+   	 '<span class="carousel-control-prev-icon" aria-hidden="true"></span>'
		    	+    	'<span class="visually-hidden">Previous</span>'
		    	+  	'</button>'
		    	+ 	 '<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">'
		    	+   	 '<span class="carousel-control-next-icon" aria-hidden="true"></span>'
		    	+   	 '<span class="visually-hidden">Next</span>'
		    	+  	'</button>'
		    	+	'</div>'
		    
		    
		    	
		    recipeFinishImg.innerHTML = finishImg;
		
		}
		
		
		
		function stepModal(savePath){
			
			const modal = document.getElementById('stepModal');
			console.log("================================== stepModal(savePath) 실행 ");
			console.log("savePath : ", savePath);
			
			
			
			stepImgDiv.innerHTML = '<img class="stepImg" src="/display?fileName=' + savePath + '">';
			modal.style.display = "flex";	
			
		}
		

		
		
		// 댓글 정보 출력
		// =================================================================
		
		function getRecipeReply(){
			
			console.log("실행여부 확인 ========================");
			
				let bno = document.querySelector('#b_no').value;
	   		
		   		// fetch 사용
		   		// 정보를 가져와서 함수에 파라메터로 넣는다.
		   		fetch('/replyList/' + bno)
		   			.then(response => response.json())
		   			.then(map => viewReplyList_Test3(map));
				
		   		$(".test-score1").score();
		}
		
		
		

			function photoModal_re(savePath, replyJson, replyListJson, photoReviewJson) {
				  
				  const modal = document.getElementById("photoReviewModal");
				  const photoReviewImgDiv = document.getElementById("photoReviewImgDiv");
				  const photoReviewReplyDiv = document.getElementById("photoReviewReplyDiv");
				  const photoReviewModalList = document.getElementById("photoReviewModalList"); // Element
																								// to
																								// display
																								// all
																								// images

				  photoReviewImgDiv.innerHTML = '<img src="/display?fileName=' + savePath + '">';

				  // Parse the stringified JSON objects back into
					// arrays/objects
				  var photoReview = JSON.parse(decodeURIComponent(photoReviewJson));
				  var replyList = JSON.parse(decodeURIComponent(replyListJson));
				  var reply = JSON.parse(decodeURIComponent(replyJson));

				  // Display all images in the photoReview array
				  let photoList = '';
				  photoReview.forEach(function (item, index) {
				    let saveP = encodeURIComponent(item.savePath);
				    photoList += '<img src="/display?fileName=' + saveP + '">';
				  });

				  photoReviewModalList.innerHTML = photoList;

				  // Display reply information in the photoReviewReplyDiv
				  let content =
				    '<div id="modalContent">' +
				    '<div id="modalHead">' +
				    '<span>' + reply.writer + '</span> <div class="test-score2" data-max="5" data-rate="' + reply.star + '"></div>' +
				    '<span>' + reply.replydate + '</span>' +
				    '</div>' +
				    '<div id="modalContent">' + reply.reply + '</div>' +
				    '</div>';

				  photoReviewReplyDiv.innerHTML = content;
				  $(".test-score2").score();
				  modal.style.display = "flex";
				}
		
		
			/*=============================================================================================================*/
			
			function replyDelete(r_no){
				
				// fetch 사용
		   		// 정보를 가져와서 함수에 파라메터로 넣는다.
		   		fetch('/replyDelete/' + r_no)
		   			.then(response => response.json())
		   			.then(map => replyDeleteRes(map));
				
			}
			
			
			function replyDeleteRes(map){

				if(map.result =='success'){
					
					console.log("댓글삭제 성공");
					
					getRecipeReply();
					
				} else{
					
					console.log("댓글삭제 실패");
				}
					
			}
			
			
			
			function goReplyUpdate(r_no){
				
				event.preventDefault();
				
				let reply = document.querySelector('#reply'+r_no+'').value;
				console.log('reply :', reply);
				console.log('r_no : ', r_no);
				
				 let obj = {
						 reply : reply
						 ,r_no : r_no
						 
				 };
				 				
				fetchPost('/reply/edit' ,obj,replyEditRes);
				
			};
			
			
			// 수정 결과
			function replyEditRes(map){
				
				console.log("댓글이 수정되었습니다.");
				getRecipeReply();
				
			}
			
			
			 function replyUpdate(r_no){
				  
				  console.log("replyUpdate 실행 =================");
				  
				  document.querySelector('.cont'+r_no+'').style.display = 'none';
				  document.querySelector('.replyUpdate'+r_no+'').type = 'text';
				  document.querySelector('.editBtn'+r_no+'').style.display = 'block';  
				  
			  }
			
			function viewReplyList_Test2(map) {
				
				// viewReplyList_Test2 출력
				console.log('viewReplyList_Test2 map ========================: ', map);
				
				photoReviewCnt.innerHTML = map.photoReviewCnt +'건';	
				replyCnt.innerHTML = map.replyCnt + '건';
				
				  let content = '';
				  let showAll = false;

				  // Function to generate the HTML content for a single item
				  function generateItemHtml(item) {
				    let r_no = item.r_no; // comment number
				    let replydate = item.replydate; // Date Created
				    let reply = item.reply; // detail
				    let writer = item.writer; // Writer
				    let star = item.star;

				    // Let's take it to the input hidden and make it available when
					// clicking on it.
				    // We need to get the picture associated with the comment later.
				    return (
				    		
				      '<div class="media-body">' 
				     + '<h4 class="media-heading">'
				     + '<b class="info_name_f">' + writer + '</b>'
				     +'<span class="reply-regdate-star reply-regdate">' + replydate + '</span><div class="test-score1 reply-regdate-star reply-star" data-max="5" data-rate="' + star + '"></div>' 
				     + '<p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyDelete('+r_no+')">삭제</p><p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyUpdate('+r_no+')">수정</p></h4>' 
				     + '<p class="reply_list_cont cont'+r_no+'">' + reply + '</p><form name="editForm" onSubmit="return false;" style="display:flex;"><input type="hidden" value="'+r_no+'"><input style="width:400px;" type="hidden" id="reply'+r_no+'" class="replyUpdate'+r_no+'" name="replyUpdate" value="'+reply+'"><button class="editBtn'+r_no+'" style="display:none;"onclick="goReplyUpdate('+r_no+')">수정</button></form></div>'
				      
				    );
				    
				  }
				  
				  
				 

				  // Function to display all items in map.replyList
				  function displayAllItems() {
				    showAll = true;
				    content = '';
				    map.replyList.forEach(function (item) {
				      content += generateItemHtml(item);
				    });
				    generalCommentDiv.innerHTML = content;
				    $(".test-score1").score();
				  }

				 
				  
				  // If there is a value specified when putting it to the map in
					// the controller...
				  if (map.replyList.length > 0) {
				    
					 // Show the first five items initially
				    for (let index = 0; index < Math.min(5, map.replyList.length); index++) {
				      const item = map.replyList[index];
				      content += generateItemHtml(item);
				      
				      console.log('작성자 반복 출력', item.writer);
				    }
				    generalCommentDiv.innerHTML = content;
				    $(".test-score1").score();

				    // Add event listener to the '더보기' button
				    const moreButton = document.getElementById('moreButton');
				    moreButton.addEventListener('click', displayAllItems);
				  } else {
				    content = '댓글이 없습니다.. 첫번째 후기를 남길 기회에요!';
				    generalCommentDiv.innerHTML = content;
				  }

 
				  // 포토리뷰 출력 부 ---------------------------------------------------- 
				  
				  let photoReply = '';

				// console.log(map.photoReview);
				 // console.log(map.replyList);

				  if (map.photoReview.length > 0) {
				    map.photoReview.forEach(function (item, index) {
				    	
				    	//console.log('map.photoReview : ', map.photoReview);
				      let savePath = encodeURIComponent(item.savePath);
				      let r_no = item.r_no;
				     // console.log('savePath : ', savePath);

				      // Use encodeURIComponent for the entire JSON string to
						// handle quotes correctly
				      let photoReviewJson = encodeURIComponent(JSON.stringify(map.photoReview));
				      let replyListJson = encodeURIComponent(JSON.stringify(map.replyList));

				    // console.log('================================');
				    // console.log(map.replyList[index]);
				    // console.log(map.replyList);

				      let reply = map.replyList[index];
				      let replyJson = JSON.stringify(reply);
				   // console.log(replyJson);
				      

				      if (!showAll && index >= 10) {
				        
				    	  photoReply +=
				       
				        '<div class="w20p" style="display: none;"><p class="pclass"><a onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')">' +
				          '<img  src="/display?fileName=' + savePath + '"></a></p></div>';
				       
				        
				      } else {
				    	  
				    	  	photoReply +=
					          '<div style="cursor: pointer;" ><p class="pclass"><a onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')">' +
					          '<img class="w136" src="/display?fileName=' + savePath + '"></a></p></div>';
				    	 
				    	  if(index == 9){
				    	
				    		  photoReply +=
						          '<p class="moreP" style="width:100px; height:100px;" onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')"" >더보기</p>'
				    	  }
				        
				      }
				      
				    });
				    
				    photoReviewDiv.innerHTML = photoReply;
				  
				  }else{
					  
					  photoReply +=
						  '포토리뷰가 없습니다. 첫 사진 후기를 남겨주세요 !';
						  
						  photoReviewDiv.innerHTML = photoReply;
				  }
				
			}
			
// ======================================================================================================================
			
				function viewReplyList_Test3(map) {
						
						// viewReplyList_Test2 출력
						console.log('viewReplyList_Test2 map ========================: ', map);
						
						photoReviewCnt.innerHTML = map.photoReviewCnt +'건';	
						replyCnt.innerHTML = map.replyCnt + '건';
						
						  let content = '';
						  let showAll = false;
		
						  // 댓글 반복 출력 문단 생성
						  function generateItemHtml(item, photoReview) {
						    let r_no = item.r_no; // comment number
						    let replydate = item.replydate; // Date Created
						    let reply = item.reply; // detail
						    let writer = item.writer; // Writer
						    let star = item.star;
						    

						    let matchedImg = photoReview.find(item => item.r_no == r_no);
						    
						    if(matchedImg){
						    
						    	let savePath = encodeURIComponent(matchedImg.savePath);
							    
							    console.log('matchedImg ===============================: ', matchedImg);
							    // Let's take it to the input hidden and make it available when
								// clicking on it.
							    // We need to get the picture associated with the comment later.
							    return (
							    		
							      '<div class="media-body" style="position: relative;padding-bottom: 25px;">' 
							     + '<h4 class="media-heading">'
							     + '<b class="info_name_f">' + writer + '</b>'
							     +'<span class="reply-regdate-star reply-regdate">' + replydate + '</span><div class="test-score1 reply-regdate-star reply-star" data-max="5" data-rate="' + star + '"></div>' 
							     + '<p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyDelete('+r_no+')">삭제</p><p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyUpdate('+r_no+')">수정</p></h4>' 
							     + '<p class="reply_list_cont cont'+r_no+'" style="position: absolute; top: 20px;">' + reply + '</p><form name="editForm" onSubmit="return false;" style="display:flex;"><input type="hidden" value="'+r_no+'"><input style="width:400px;" type="hidden" id="reply'+r_no+'" class="replyUpdate'+r_no+'" name="replyUpdate" value="'+reply+'"><button class="editBtn'+r_no+'" style="display:none;"onclick="goReplyUpdate('+r_no+')">수정</button></form>'
							     + '<div class="replyIMGDiV" style="position: absolute; right: 0; top: 0;"><a><img onclick=stepModal(\"'+savePath+'\") style="width:80px; height:80px;" src="/display?fileName=' + savePath + '"></a></div></div>' 
							     
							    );
						    	
						    }else {
						    	
						    	
						    	 return (
								    		
									      '<div class="media-body">' 
									     + '<h4 class="media-heading">'
									     + '<b class="info_name_f">' + writer + '</b>'
									     +'<span class="reply-regdate-star reply-regdate">' + replydate + '</span><div class="test-score1 reply-regdate-star reply-star" data-max="5" data-rate="' + star + '"></div>' 
									     + '<p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyDelete('+r_no+')">삭제</p><p style="cursor: pointer; margin-left: 10px; margin-top: 2px;" onclick="replyUpdate('+r_no+')">수정</p></h4>' 
									     + '<p class="reply_list_cont cont'+r_no+'">' + reply + '</p><form name="editForm" onSubmit="return false;" style="display:flex;"><input type="hidden" value="'+r_no+'"><input style="width:400px;" type="hidden" id="reply'+r_no+'" class="replyUpdate'+r_no+'" name="replyUpdate" value="'+reply+'"><button class="editBtn'+r_no+'" style="display:none;"onclick="goReplyUpdate('+r_no+')">수정</button></form></div>'  
									     
									    );
						    }
						    
						    
						  }
						  
						 
						  // Function to display all items in map.replyList
						  function displayAllItems() {
						    showAll = true;
						    content = '';
						    map.replyList.forEach(function (item) {
						      content += generateItemHtml(item, map.photoReview);
						    });
						    generalCommentDiv.innerHTML = content;
						    $(".test-score1").score();
						  }
		
						 
						  
						  // If there is a value specified when putting it to the map in
							// the controller...
						  if (map.replyList.length > 0) {
						    
							 // Show the first five items initially
						    for (let index = 0; index < Math.min(5, map.replyList.length); index++) {
						      const item = map.replyList[index];
						      content += generateItemHtml(item, map.photoReview);
						      
						      console.log('작성자 반복 출력', item.writer);
						    }
						    generalCommentDiv.innerHTML = content;
						    $(".test-score1").score();
		
						    // Add event listener to the '더보기' button
						    const moreButton = document.getElementById('moreButton');
						    moreButton.addEventListener('click', displayAllItems);
						  } else {
						    content = '댓글이 없습니다.. 첫번째 후기를 남길 기회에요!';
						    generalCommentDiv.innerHTML = content;
						  }
		
		 
						  // 포토리뷰 출력 부 ---------------------------------------------------- 
						  
						  let photoReply = '';
		
						// console.log(map.photoReview);
						 // console.log(map.replyList);
		
						  if (map.photoReview.length > 0) {
						    map.photoReview.forEach(function (item, index) {
						    	
						    	//console.log('map.photoReview : ', map.photoReview);
						      let savePath = encodeURIComponent(item.savePath);
						      let r_no = item.r_no;
						     // console.log('savePath : ', savePath);
		
						      // Use encodeURIComponent for the entire JSON string to
								// handle quotes correctly
						      let photoReviewJson = encodeURIComponent(JSON.stringify(map.photoReview));
						      let replyListJson = encodeURIComponent(JSON.stringify(map.replyList));
		
						    // console.log('================================');
						    // console.log(map.replyList[index]);
						    // console.log(map.replyList);
		
						      let reply = map.replyList[index];
						      let replyJson = JSON.stringify(reply);
						   // console.log(replyJson);
						      
		
						      if (!showAll && index >= 10) {
						        
						    	  photoReply +=
						       
						        '<div class="w20p" style="display: none;"><p class="pclass"><a onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')">' +
						          '<img  src="/display?fileName=' + savePath + '"></a></p></div>';
						       
						        
						      } else {
						    	  
						    	  	photoReply +=
							          '<div style="cursor: pointer;" ><p class="pclass"><a onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')">' +
							          '<img class="w136" src="/display?fileName=' + savePath + '"></a></p></div>';
						    	 
						    	  if(index == 9){
						    	
						    		  photoReply +=
								          '<p class="moreP" style="width:100px; height:100px;" onclick="photoModal_TEST(\'' + savePath + '\', \'' + encodeURIComponent(replyJson) + '\' , \'' + replyListJson + '\' , \'' + photoReviewJson + '\', \'' + r_no + '\', \'' + index + '\')"" >더보기</p>'
						    	  }
						        
						      }
						      
						    });
						    
						    photoReviewDiv.innerHTML = photoReply;
						  
						  }else{
							  
							  photoReply +=
								  '포토리뷰가 없습니다. 첫 사진 후기를 남겨주세요 !';
								  
								  photoReviewDiv.innerHTML = photoReply;
						  }
						
					}
			
			
			

//-----------------------------------------------------------------------------------

			// gpt 코드
			function photoModal_rerere(savePath, replyJson, replyListJson, photoReviewJson, r_no) {
				 
				  const modal = document.getElementById("photoReviewModal");
				  const photoReviewImgDiv = document.getElementById("photoReviewImgDiv");
				  const photoReviewReplyDiv = document.getElementById("photoReviewReplyDiv");
				  const photoReviewModalList = document.getElementById("photoReviewModalList");

				  // Parse the stringified JSON objects back into arrays/objects
				  var photoReview = JSON.parse(decodeURIComponent(photoReviewJson));
				  var replyList = JSON.parse(decodeURIComponent(replyListJson));
				  var reply = JSON.parse(decodeURIComponent(replyJson));
				  
				  console.log('==================================');
				  console.log('savePath : ',savePath);
				  console.log('reply : ',reply);
				  console.log('replyList : ',replyList);
				  console.log('photoReview : ',photoReview);
				  console.log('r_no : ',r_no);
				  console.log('==================================');
				  
				  
				  // Find the correct comment that matches the clicked photo's r_no
				  let matchedReply = replyList.find((item) => item.r_no == r_no);

				  console.log('matchedReply', matchedReply);
				  console.log('r_no: ', r_no);

				  
				  // 모달창에 클릭한 사진의 이미지를 표시합니다.
			      photoReviewImgDiv.innerHTML = matchedReply
				    ? '<img style="max-width: 500px; max-height: 300px;" src="/display?fileName=' + savePath + '">'
				    : '일치하는 이미지가 없습니다.';
			      
			      
			      
			      if (matchedReply) {
				        let content =
				          '<div id="modalContent">' +
				          '<div id="modalHead">' +
				          '<span>' +
				          matchedReply.writer +
				          '</span> <div class="test-score2" data-max="5" data-rate="' +
				          matchedReply.star +
				          '"></div>' +
				          '<span>' +
				          matchedReply.replydate +
				          '</span>' +
				          '</div>' +
				          '<div id="modalContent">' +
				          matchedReply.reply +
				          '</div>' +
				          '</div>';
				        photoReviewReplyDiv.innerHTML = content;
				        $(".test-score2").score();
				      } else {
				        // 일치하는 댓글이 없는 경우, 댓글을 표시하는 영역에 메시지를 표시합니다.
				        photoReviewReplyDiv.innerHTML = '일치하는 댓글이 없습니다.';
				      }
			      
			      // ==========================================================================

				  // 포토리뷰 전체를 보여주는 부분
				  let photoList = '<ul>';

				  photoReview.forEach(function (item, index) {
				    let saveP = encodeURIComponent(item.savePath);

				    // 각 이미지마다 고유한 'data-r_no' 속성을 추가하여 중복 불러오기 문제를 해결합니다.
				    photoList += '<li style="width: 70px;" class="photoList"><img class="w100" name="photoList" src="/display?fileName=' + saveP + '" data-r_no="' + item.r_no + '" data-savePath="'+saveP+'"></li>';
				  });

				  photoList += '</ul>'
					  
				  photoReviewModalList.innerHTML = photoList;
				  
				  // 'photoList' 클래스를 가진 모든 이미지 요소를 가져옵니다.
				  const photoListImages = document.querySelectorAll('.photoList img');
				  console.log('photoListImages :', photoListImages);
				  
				  // 클릭한 사진의 이미지와 댓글 정보를 초기화합니다.
				 

				  // photoList의 각 이미지에 클릭 이벤트 리스너를 추가합니다.
				  photoListImages.forEach((image) => {
				    
					  image.addEventListener('click', function () {
				      // 클릭한 이미지에서 'data-r_no' 속성을 가져옵니다.
				      const clickedRNo = image.getAttribute('data-r_no');
				      const clickedSaveP = image.getAttribute('data-savePath');
				      console.log('clickedRNo : ', clickedRNo);	
				      console.log('clickedSaveP : ', clickedSaveP);
				      
				      // 클릭한 이미지의 'data-r_no' 속성과 일치하는 댓글을 'replyList'에서 찾습니다.
				      const matchedReply = replyList.find((item) => item.r_no == clickedRNo);
				      console.log('두번째 matchedReply : ', matchedReply)
				      
				      // 모달창에 클릭한 사진의 이미지를 표시합니다.
				      photoReviewImgDiv.innerHTML = matchedReply
					    ? '<img style="max-width: 500px; max-height: 300px;" src="/display?fileName=' + clickedSaveP + '">'
					    : '일치하는 이미지가 없습니다.';

				      // 댓글 정보를 'photoReviewReplyDiv'에 표시합니다.
				      if (matchedReply) {
				        let content =
				          '<div id="modalContent">' +
				          '<div id="modalHead">' +
				          '<span>' +
				          matchedReply.writer +
				          '</span> <div class="test-score2" data-max="5" data-rate="' +
				          matchedReply.star +
				          '"></div>' +
				          '<span>' +
				          matchedReply.replydate +
				          '</span>' +
				          '</div>' +
				          '<div id="modalContent">' +
				          matchedReply.reply +
				          '</div>' +
				          '</div>';
				        photoReviewReplyDiv.innerHTML = content;
				        $(".test-score2").score();
				      } else {
				        // 일치하는 댓글이 없는 경우, 댓글을 표시하는 영역에 메시지를 표시합니다.
				        photoReviewReplyDiv.innerHTML = '일치하는 댓글이 없습니다.';
				      }
				    });
				  });

				  modal.style.display = 'flex';
				}
			
			
			
//-----------------------------------------------------------------------------------

			function photoModal_rererere(savePath, replyJson, replyListJson, photoReviewJson, r_no) {
				 
				  const modal = document.getElementById("photoReviewModal");
				  const photoReviewImgDiv = document.getElementById("photoReviewImgDiv");
				  const photoReviewReplyDiv = document.getElementById("photoReviewReplyDiv");
				  const photoReviewModalList = document.getElementById("photoReviewModalList");

				  // Parse the stringified JSON objects back into arrays/objects
				  var photoReview = JSON.parse(decodeURIComponent(photoReviewJson));
				  var replyList = JSON.parse(decodeURIComponent(replyListJson));
				  var reply = JSON.parse(decodeURIComponent(replyJson));

				  console.log('==================================');
				  console.log('savePath : ',savePath);
				  console.log('reply : ',reply);
				  console.log('replyList : ',replyList);
				  console.log('photoReview : ',photoReview);
				  console.log('r_no : ',r_no);
				  console.log('==================================');
				  
				  
				  // Find the correct comment that matches the clicked photo's r_no
				  let matchedReply = replyList.find((item) => item.r_no == r_no);

				  console.log('matchedReply', matchedReply);
				  console.log('r_no: ', r_no);

				  
				  // 모달창에 클릭한 사진의 이미지를 표시합니다.
			      photoReviewImgDiv.innerHTML = matchedReply
				    ? '<img style="max-width: 500px; max-height: 300px;" src="/display?fileName=' + savePath + '">'
				    : '일치하는 이미지가 없습니다.';
			      
			      
			      
			      if (matchedReply) {
				        let content =
				          '<div id="modalContent">' +
				          '<div id="modalHead">' +
				          '<span>' +
				          matchedReply.writer +
				          '</span> <div class="test-score2" data-max="5" data-rate="' +
				          matchedReply.star +
				          '"></div>' +
				          '<span>' +
				          matchedReply.replydate +
				          '</span>' +
				          '</div>' +
				          '<div id="modalContent">' +
				          matchedReply.reply +
				          '</div>' +
				          '</div>';
				        photoReviewReplyDiv.innerHTML = content;
				        $(".test-score2").score();
				      } else {
				        // 일치하는 댓글이 없는 경우, 댓글을 표시하는 영역에 메시지를 표시합니다.
				        photoReviewReplyDiv.innerHTML = '일치하는 댓글이 없습니다.';
				      }
			      
			      // ==========================================================================

				  // 포토리뷰 전체를 보여주는 부분
				  let photoList = '<ul>';

				  photoReview.forEach(function (item, index) {
				    let saveP = encodeURIComponent(item.savePath);

				    // 각 이미지마다 고유한 'data-r_no' 속성을 추가하여 중복 불러오기 문제를 해결합니다.
				    photoList += '<li style="width: 70px;" class="photoList"><img class="w100" name="photoList" src="/display?fileName=' + saveP + '" data-r_no="' + item.r_no + '" data-savePath="'+saveP+'"></li>';
				  });

				  photoList += '</ul>'
					  
				  photoReviewModalList.innerHTML = photoList;
				  
				  // 'photoList' 클래스를 가진 모든 이미지 요소를 가져옵니다.
				  const photoListImages = document.querySelectorAll('.photoList img');
				  console.log('photoListImages :', photoListImages);
				  
				  // 클릭한 사진의 이미지와 댓글 정보를 초기화합니다.
				 

				  // photoList의 각 이미지에 클릭 이벤트 리스너를 추가합니다.
				  photoListImages.forEach((image) => {
				    
					  image.addEventListener('click', function () {
				      // 클릭한 이미지에서 'data-r_no' 속성을 가져옵니다.
				      const clickedRNo = image.getAttribute('data-r_no');
				      const clickedSaveP = image.getAttribute('data-savePath');
				      console.log('clickedRNo : ', clickedRNo);	
				      console.log('clickedSaveP : ', clickedSaveP);
				      
				      // 클릭한 이미지의 'data-r_no' 속성과 일치하는 댓글을 'replyList'에서 찾습니다.
				      const matchedReply = replyList.find((item) => item.r_no == clickedRNo);
				      console.log('두번째 matchedReply : ', matchedReply)
				      
				      // 모달창에 클릭한 사진의 이미지를 표시합니다.
				      photoReviewImgDiv.innerHTML = matchedReply
					    ? '<img style="max-width: 500px; max-height: 300px;" src="/display?fileName=' + clickedSaveP + '">'
					    : '일치하는 이미지가 없습니다.';

				      // 댓글 정보를 'photoReviewReplyDiv'에 표시합니다.
				      if (matchedReply) {
				        let content =
				          '<div id="modalContent">' +
				          '<div id="modalHead">' +
				          '<span>' +
				          matchedReply.writer +
				          '</span> <div class="test-score2" data-max="5" data-rate="' +
				          matchedReply.star +
				          '"></div>' +
				          '<span>' +
				          matchedReply.replydate +
				          '</span>' +
				          '</div>' +
				          '<div id="modalContent">' +
				          matchedReply.reply +
				          '</div>' +
				          '</div>';
				        photoReviewReplyDiv.innerHTML = content;
				        $(".test-score2").score();
				      } else {
				        // 일치하는 댓글이 없는 경우, 댓글을 표시하는 영역에 메시지를 표시합니다.
				        photoReviewReplyDiv.innerHTML = '일치하는 댓글이 없습니다.';
				      }
				    });
				  });

				  modal.style.display = 'flex';
				}
			
// ========================================================================================================================			

	function closeModal() {
	  const photoReviewModal = document.getElementById("photoReviewModal");
	  const ingredientModal = document.getElementById("ingredientModal");
	  const stepModal = document.getElementById("stepModal");
	  photoReviewModal.style.display = "none";
	  ingredientModal.style.display = "none";
	  stepModal.style.display = "none";
	  
	  
	}
		
		
		
	function goRoket(i_name){
		
		let options = "toolbar=no,scrollbars=no,resizable=yes,status=no,menubar=no,width=1200, height=800, top=0,left=0";
		let searchWord = encodeURIComponent(i_name);
		let url = 'https://www.coupang.com/np/search?component=&q=' + searchWord + '&channel=user';
		  window.open(url ,"_blank", options);
		}
		
	
	
// ==========================================================================================================================
	
	 function nextBtn(r_no){
   	  
   	  
   	  console.log('nextBtn 출력 ==========================');
   	  console.log('r_no : ', r_no);
	 };
     
     
     function prevBtn(r_no){
   	  
   	  console.log('prevBtn 출력 ==========================');
   	  console.log('r_no : ', r_no);
   	  
     };
	
	function photoModal_TEST(savePath, replyJson, replyListJson, photoReviewJson, r_no, index1) {
		 
		  const modal = document.getElementById("photoReviewModal");
		  const photoReviewImgDiv = document.getElementById("photoReviewImgDiv");
		  const photoReviewReplyDiv = document.getElementById("photoReviewReplyDiv");
		  const photoReviewModalList = document.getElementById("photoReviewModalList");

		  // Parse the stringified JSON objects back into arrays/objects
		  var photoReview = JSON.parse(decodeURIComponent(photoReviewJson));
		  var replyList = JSON.parse(decodeURIComponent(replyListJson));
		  var reply = JSON.parse(decodeURIComponent(replyJson));
		  	  
		  console.log('==================================');
		  console.log('savePath : ',savePath);
		  console.log('reply : ',reply);
		  console.log('replyList : ',replyList);
		  console.log('photoReview : ',photoReview);
		  console.log('r_no : ',r_no);
		  console.log('index :', index1);
		  console.log('==================================');
		  
		  //
		  
		  
		  
		  let matchedReply = replyList.find((item) => item.r_no == r_no);
		  
		  
		  let photoBtn = 
			  			'<div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="false">'
			  			+ '<div class="carousel-inner" style="overflow:inherit;">'	
		  				+ '<div class="carousel-indicators" style="display:none;">';
			  			
		  for(let i = 0; i < photoReview.length; i++){
        	 
        	 if(i == 1){
        	
        		 photoBtn +=
        		 
        		 '<button type="button" style="background-color: black; opacity: initial;" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="'+i+'" class="active" aria-current="true" aria-label="Slide '+i+'"></button>';
        		 	
        	 }else{
        	
        		 photoBtn +=
        		 
        		'<button type="button" style="background-color: black; opacity: initial;" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="'+i+'" aria-label="Slide '+i+'"></button>';
                 
        		 
        	 }
        	 
        	 
             
         }		  			
		  
		  photoBtn +=
     		 
     		 '</div>';			  
			  			
		if(matchedReply){
			
		
			  	
			  	photoReview.forEach(function(item, index){
			  	
			  		let sPath = encodeURIComponent(item.savePath);
			  		
			  		if(index == index1){
			  		
			  			photoBtn +=
		  					
			  				'<div class="carousel-item active photoBtn" data-bs-interval="1000000">'
		  					+	'<img style="height: 300px; max-width: 500px; margin: 0 auto;" src="/display?fileName=' + savePath + '" class="d-block photoBtn" data-rno="'+item.r_no+'" alt="...">'			  				
		  					+ '<div class="carousel-caption d-none d-md-block" style="top: 110%; color: black;">'
		  			        + '<h5>'+replyList[index1].writer+'</h5>'
		  			        + '<div class="test-score2" data-max="5" data-rate="' +replyList[index1].star +'" ></div><span style="margin-left: 10px;  font-size: 13px;  color: #ab8e7d;">'+replyList[index1].replydate+'</span>'
		  			        + '<p style="font-size:18px;">'+replyList[index1].reply+'</p>'
		  			        + '</div>'
		  					+'</div>';
			  			
			  			console.log('replyList[index1] : ',replyList[index1]);
			  			
			  		}else {
			  			
			  			photoBtn +=
			  				
			  				'<div  class="carousel-item photoBtn" data-bs-interval="1000000">'
					    	+		'<img style="height: 300px; max-width: 500px; margin: 0 auto;" src="/display?fileName=' + sPath + '" class="d-block photoBtn" data-rno="'+item.r_no+'" alt="...">'	
					    	+ '<div class="carousel-caption d-none d-md-block" style="top: 110%; color: black;">'
		  			        + '<h5>'+replyList[index].writer+'</h5>'
		  			        + '<div class="test-score2" data-max="5" data-rate="' +replyList[index].star +'" ></div><span style="margin-left: 10px;  font-size: 13px;  color: #ab8e7d;">'+replyList[index1].replydate+'</span>'
		  			        + '<p style="font-size:18px;">'+replyList[index].reply+'</p>'
		  			        + '</div>'
					    	+    '</div>';
			  			
			  			console.log('replyList[index] : ',replyList[index]);
			  			
			  		}

			  		
			  	});
		  
		}  			
		  				photoBtn +=
		  					
		  					'</div>'
			  			+		'<button style="box-shadow: none;" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" onclick="prevBtn('+(r_no-1)+')" data-bs-slide="prev" >'
						+	          '<span style="background-color: #F7863B;" class="carousel-control-prev-icon" aria-hidden="true" ></span>'
						+	          '<span class="visually-hidden">Previous</span>'
						+	        '</button>'	      
		  				+		'<button style="box-shadow: none;" class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" onclick="nextBtn('+(r_no+1)+')" data-bs-slide="next" >'
						+	          '<span style="background-color: #F7863B;" class="carousel-control-next-icon" aria-hidden="true" ></span>'
						+	          '<span class="visually-hidden">Next</span>'
						+	      '</button>'
						+   '</div>'
		  

		  
			
		  	photoReviewImgDiv.innerHTML = photoBtn;

			$(".test-score2").score();				
		  				
		  				
			const photoBtnImg = document.querySelectorAll('.photoBtn img');
			
			console.log('!!!!!!!! photoBtnImg : ', photoBtnImg);
			
			photoBtnImg.forEach((img) => {
				
			let checkRNO = img.getAttribute('data-rno');
			
			console.log('checkRNO : ', checkRNO);
			
			
			});
				
  	
	      
	      // ==========================================================================

		  // 포토리뷰 전체를 보여주는 부분
		  let photoList = 
			  	'<ul style="background-color: white;">';

		  photoReview.forEach(function (item, index) {
		    let saveP = encodeURIComponent(item.savePath);

		    // 각 이미지마다 고유한 'data-r_no' 속성을 추가하여 중복 불러오기 문제를 해결합니다.
		    photoList += '<li style="width: 70px;" class="photoList"><img data-bs-target="#carouselExampleCaptions" data-bs-slide-to="'+index+'" class="w100" name="photoList" src="/display?fileName=' + saveP + '" data-r_no="' + item.r_no + '" data-savePath="'+saveP+'"></li>';
		    			
		  });

		  photoList += '</ul>';
			  			
			  
		  photoReviewModalList.innerHTML = photoList;
		  
		  // 'photoList' 클래스를 가진 모든 이미지 요소를 가져옵니다.
		  const photoListImages = document.querySelectorAll('.photoList img');
		  console.log('photoListImages :', photoListImages);
		  
		  // 클릭한 사진의 이미지와 댓글 정보를 초기화합니다.
		 

		  // photoList의 각 이미지에 클릭 이벤트 리스너를 추가합니다.
		  /*photoListImages.forEach((image) => {
		    
			  image.addEventListener('click', function () {
		    
			  // 클릭한 이미지에서 'data-r_no' 속성을 가져옵니다.
		      const clickedRNo = image.getAttribute('data-r_no');
		      const clickedSaveP = image.getAttribute('data-savePath');
		      console.log('clickedRNo : ', clickedRNo);	
		      console.log('clickedSaveP : ', clickedSaveP);
		      
		      // 클릭한 이미지의 'data-r_no' 속성과 일치하는 댓글을 'replyList'에서 찾습니다.
		      const matchedReply = replyList.find((item) => item.r_no == clickedRNo);
		      console.log('두번째 matchedReply : ', matchedReply)
		      
		      // 모달창에 클릭한 사진의 이미지를 표시합니다.
		      photoReviewImgDiv.innerHTML = matchedReply
			    ? '<img style="max-width: 500px; max-height: 300px;" src="/display?fileName=' + clickedSaveP + '">'
			    : '일치하는 이미지가 없습니다.';

		      // 댓글 정보를 'photoReviewReplyDiv'에 표시합니다.
		       if (matchedReply) {
		        let content =
		          '<div id="modalContent">' +
		          '<div id="modalHead">' +
		          '<span>' +
		          matchedReply.writer +
		          '</span> <div class="test-score2" data-max="5" data-rate="' +
		          matchedReply.star +
		          '"></div>' +
		          '<span>' +
		          matchedReply.replydate +
		          '</span>' +
		          '</div>' +
		          '<div id="modalContent">' +
		          matchedReply.reply +
		          '</div>' +
		          '</div>';
		        photoReviewReplyDiv.innerHTML = content;
		        $(".test-score2").score();
		      } else {
		        // 일치하는 댓글이 없는 경우, 댓글을 표시하는 영역에 메시지를 표시합니다.
		        photoReviewReplyDiv.innerHTML = '일치하는 댓글이 없습니다.';
		      }
		   
			  });
		  });*/

		  modal.style.display = 'flex';
		}
	

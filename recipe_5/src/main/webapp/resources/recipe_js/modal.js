console.log("modal.js 연결 완료");
var action = '';
var url = '';
var type = '';
var nno = '';

//공지 클릭 > 상세 
function dis(element){
 
    var $item = $(element).closest('li');
    if($item.find('.show').is(':hidden')){
      $item.find('.show').show();
    }else{
      $item.find('.show').hide();
    }
  }
$(document).ready(function(){

	// 수정하기 버튼 클릭
	$("button[name='modify']").click(function(){
		action='modify';
		type = 'post';
		
		nno = this.value;
		console.log(' 수정하기 버튼 클릭 : nno : ' , nno);
		// content 담기
		var row = $(this).parent().parent().parent();
		var tr = row.children();
		
		var nwriterN = tr.eq(0).text(); // 작성자
		console.log("작성자", nwriterN);
		var ntitleN = tr.eq(1).text();  // 제목
		console.log("제목", ntitleN);
		var ncontentN = tr.eq(2).text(); // 내용
		console.log("내용", ncontentN);
		var gubunN = tr.eq(3).text(); // 구분
		console.log("구분", gubunN);
		
		if(gubunN == '일반'){
			$("#gubunN").val(gubunN);
		}else if(gubunN == '이벤트'){
			$("#gubunN").val(gubunN);	
		}else{
			$("#gubunN").val(gubunN);
		}
		
		$("#modal-title").text("공지사항 수정📝");
		
		$("#nwriterN").val(nwriterN);
		$("#ntitleN").val(ntitleN);
		$("#ncontentN").val(ncontentN);

		
		$("#myModal2").modal();
	});
	
	// Modal의 Submit 버튼 클릭
	$("#modalSubmit").click(function(){
		
		if(action == 'modify'){
			url = '/recipe/noticeUpdate';
			console.log("submit 버튼 실행 : ", url);
		}else{
			alert('수정 오류 발생');
		}

		var data = {
			"nno" : nno,
			"nwriter" : $("#nwriterN").val(),
			"ntitle" : $("#ntitleN").val(),
			"ncontent" : $("#ncontentN").val(),
			"gubun" : $("#gubunN").val()
		};
		
		$.ajax({
			url : url,
			type : type,
			data : data
		})
	
		location.reload();
	});
	
});

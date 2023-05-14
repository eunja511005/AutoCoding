var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

$(document).ready(function() {

	$('form#projectForm').one('submit', function(event) {

		event.preventDefault(); // 이벤트 중지

		var id = $('#id').val();
		var name = $('#name').val();
		var description = $('#description').val();
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var status = $('#status').val();
		var manager = $('#manager').val();
		var participants = $('#participants').val();

		var data = {
			"id": id,
			"name": name,
			"description": description,
			"startDate": startDate,
			"endDate": endDate,
			"status": status,
			"manager": manager,
			"participants": participants
		};
		
		callAjax("/project/create", "POST", data, createCallback);
	});



	$(document).on('click', '.delete-button', function() {
		var button = $(this);
		var id = button.data('id');
		button.prop('disabled', true);

		swal({
			title: "Are you sure you want to delete it?",
			text: "Your information is safely managed.",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
			.then((willDelete) => {
				if (willDelete) {
					callAjax("/project/delete/"+id, "DELETE", null, deleteCallback);
				} else {

				}
			});

	});
	
	debugger;
	var participantsArray = parseParticipants($('#project-participants').val());
	console.log(participantsArray); // ["User1"]
	if ($('#project-participants').val() == null || $('#project-participants').val() == '') {
	    //신규 페이지
	    initSelectBox('participants', '/commonCode/PARTICIPANTS', false);
	} else {
	    // 수정 페이지 로직
	    initSelectBox('participants', '/commonCode/PARTICIPANTS', false, participantsArray);
	}
}); 

function createCallback(response){
	console.log(response.errorMessage);
	loadDynamicContent("/project/listForm");
}

function deleteCallback(response){
	console.log(response.errorMessage);
	loadDynamicContent("/project/listForm");
}

function parseParticipants(participantsStr) {
	  // 대괄호 제거
	  participantsStr = participantsStr.replace('[', '').replace(']', '');
	  
	  // 쉼표로 구분된 문자열을 배열로 변환
	  const participantsArray = participantsStr.split(',').map(participant => participant.trim());
	  
	  // 배열이 null 또는 undefined인 경우 빈 배열 반환
	  if (!participantsArray) {
	    return [];
	  }
	  
	  return participantsArray;
}
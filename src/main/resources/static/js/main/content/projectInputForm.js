var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

$(document).ready(function() {
	
    // 이미지 선택 시 미리 보기 호출
    $('#image').on('change', function(event) {
        previewImage(event);
    });

	$('form#projectForm').one('submit', function(event) {
		event.preventDefault(); // 이벤트 중지
		debugger;
		var formData = new FormData(this);
		
		formData.set('endDate', $('#endDate').val());
		formData.set('startDate', $('#startDate').val());
		
		const fileInput = $('#formFile')[0];
		const file = fileInput.files[0];
		if (file) {
			const resizedImage = compressImage(file, 300, 0.9);
			formData.append('file', resizedImage);
		}
		
		saveProject(formData);
		
	});

	$('#description').summernote({
  	    placeholder: 'Hello Summernote lite',
  	    tabsize: 2,
        height: 300,                 // set editor height
        minHeight: null,             // set minimum height of editor
        maxHeight: null,             // set maximum height of editor
        focus: true,                  // set focus to editable area after
										// initializing summernote
        toolbar: [
  		    ['fontname', ['fontname']],
  		    ['fontsize', ['fontsize']],
  		    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
  		    ['color', ['forecolor','color']],
  		    ['table', ['table']],
  		    ['para', ['ul', 'ol', 'paragraph']],
  		    ['height', ['height']],
  		    ['insert',['picture','link','video']],
  		    ['view', ['codeview','fullscreen', 'help']]
  		  ],		  
		  callbacks: {
		      onImageUpload: onImageUpload
		  },  		  
  		// 추가한 글꼴
  		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
  		 // 추가한 폰트사이즈
  		fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
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
	
	var participantsArray = parseParticipants($('#project-participants').val());
	console.log(participantsArray); // ["User1"]
	var pictureUrl = $('#project-picture').val();
	console.log(pictureUrl);
	if ($('#project-participants').val() == null || $('#project-participants').val() == '') {
	    //신규 페이지
	    initSelectBox('participants', '/commonCode/PARTICIPANTS', false);
	} else {
	    // 수정 페이지 로직
	    initSelectBox('participants', '/commonCode/PARTICIPANTS', false, participantsArray);
	    
		var preview = $('#imagePreview'); // 미리보기 이미지가 표시될 엘리먼트 선택
		// 이미지 URL이 유효한 경우에만 미리보기 표시
		if (pictureUrl) {
			preview.html('<img src="' + pictureUrl + '" alt="Preview Image" class="img-fluid">');
		}
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

// 이미지 미리 보기 함수
function previewImage(event) {
    var input = event.target;
    var preview = document.getElementById("imagePreview");

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            preview.innerHTML = '<img src="' + e.target.result + '" alt="Preview Image" class="img-fluid">';
        }

        reader.readAsDataURL(input.files[0]);
    } else {
        preview.innerHTML = '';
    }
}

function handleDragOver(event) {
    event.preventDefault();
    event.stopPropagation();
    document.getElementById("dragDropArea").classList.add("drag-drop-over");
    document.querySelector(".drag-drop-message").textContent = "이미지를 여기에 놓아 업로드하세요";
}

function handleDragLeave(event) {
    event.preventDefault();
    event.stopPropagation();
    document.getElementById("dragDropArea").classList.remove("drag-drop-over");
    document.querySelector(".drag-drop-message").textContent = "이미지를 드래그 앤 드롭하세요";
}

function handleFileDrop(event) {
    event.preventDefault();
    event.stopPropagation();
    var files = event.dataTransfer.files;
    var input = document.getElementById("formFile");

    if (files.length > 0) {
        input.files = files;
        previewImage({ target: input });
    }
    document.getElementById("dragDropArea").classList.remove("drag-drop-over");
    document.querySelector(".drag-drop-message").textContent = "이미지를 드래그 앤 드롭하세요";
}

function saveProject(formData) {
    $.ajax({
        url: '/project/create',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend : function(xhr){   
			xhr.setRequestHeader(csrfheader, csrftoken);
        },
        success: function(response) {
        	createCallback(response);
        },
        error: function(response) {
        	debugger;
            alert(response.responseText);
        }
    });
}

function onImageUpload(files) {
	  for (var i = 0; i < files.length; i++) {
		    // 이미지 파일을 압축합니다. (압축: 70% 수준 유지)
		    compressImage(files[i], 300, 0.7).then(function (compressedImage) {
		      // 압축된 이미지 파일을 서버에 업로드합니다.
		      const formData = new FormData();
		      formData.append('file', compressedImage);
		      $.ajax({
		        type: 'POST',
		        url: '/posts/uploadImage',
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrfheader, csrftoken);
				},
		        data: formData,
		        processData: false,
		        contentType: false,
		        success: function (data) {
		          // 서버로부터 이미지 파일의 경로를 받아와 HTML 본문에 삽입합니다.
		          const imgNode = $('<img>').attr('src', data.createdFilePath);
		          $('#description').summernote('insertNode', imgNode[0]);
		        },
		        error: function () {
		          alert('이미지 업로드에 실패했습니다.');
		        }
		      });
		    });
		  }
}
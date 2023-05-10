var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	
var table;
$(document).ready(function() {
    table = $('#example').DataTable({
        "serverSide": true,
        "processing": true,
        "searching": false, // 검색 기능 비활성화
        scrollX: true,
        "ajax": {
            "url": "/posts/list",
            "type": "POST",
            contentType : 'application/json; charset=utf-8',
            beforeSend : function(xhr){   
				xhr.setRequestHeader(csrfheader, csrftoken);
            },
            "data": function (d) {
            	
            	var searchParams = {
                	title     : {value : $('#title').val(), regex : false},	
                	isSecret  : {value : $('#is_secret').val(), regex : false},
                	startDate : {value : $('#start_date').val(), regex : false},
                	endDate   : {value : $('#end_date').val(), regex : false}
            	};
            	
                d.search = searchParams;
                
                var orderColumnIndex = d.order[0].column;
                d.orderColumnName = d.columns[orderColumnIndex].data;
                d.orderDirection = d.order[0].dir.toUpperCase();
                
                return JSON.stringify(d);
            }
        },
        "columns": [
            {"data": "title"},
            {"data": "secret"},
            {"data": "created_at",
	            "render": function(data) {
	            	return moment(data, "YYYY-MM-DDTHH:mm:ss").format("YYYY년 MM월 DD일 HH:mm");
	            }
            },
            {"data": "id",
         	   "render": function(data, type, row, meta){
         		   // return '<button type="button" class="btn
					// btn-primary">View Content</button>';
         		   return '<i class="fa-solid fa-magnifying-glass-chart"></i>';
         	   }                       
            },
            {"data": "",
         	   "render": function(data, type, row, meta){
                    // return '<button type="button" class="btn
					// btn-danger">Delete</button>';
                    return '<i class="fa-solid fa-trash-can"></i>';
                }
            }
        ],
        order: [[2, 'desc']]
    });

    $('#search_btn').on('click', function () {
    	table.ajax.reload();
    });
    
    
    $('#newPostModalButton').click(function() {
    	// 댓글 입력창 숨기기
		$("#comment-form").hide(); 
		$("#commentButton").hide();
		
		// 모달 창 보이기
        $('#newPostModal').modal('show');
    });   
    
	$('#modalContent').summernote({
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
    
    // Attach click event to "Save Changes" button
    $('#newPostModal .modal-footer button.btn-primary').on('click', function(event) {
        var form = $('#postForm')[0];
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        form.classList.add('was-validated');
        if (form.checkValidity()) {
            save(form);
        }
    });
    
    $('#newPostModal').on('hidden.bs.modal', function () {
        $(this).find(':input').val('');
        $(this).find('form').trigger('reset');
        $(this).find('#modalContent').summernote('reset');
        $(this).find('#comment-list').empty().hide();
        $(this).find('#comment-count').text('0');
    });
    
    // Bind click event to content button
    $(document).on('click', '.fa-magnifying-glass-chart', function(){ 
        var $btn=$(this);
        var $tr=$btn.closest('tr');
        var dataTableRow=$("#example").DataTable().row($tr[0]); // get the DT
																// row so we can
																// use the API
																// on it
        var rowData=dataTableRow.data();
        var id = rowData.id;
          $.ajax({
              url: '/posts/list/'+ id,
              type: 'POST',
              beforeSend : function(xhr){   
  				xhr.setRequestHeader(csrfheader, csrftoken);
              },
              success: function(response) {
              	if(response.postList != undefined && response.postList != ""){
              		$('#modalId').val(response.postList.id);
              		$('#modalContent').summernote('code', response.postList.content);
			       	$('#modaTitle').val(response.postList.title);
			       	$('#modaSecret').prop('checked', response.postList.secret);
			       	
					// 댓글 리스트 보여 주기
					loadComments();
			       	
			       	if (response.login === true) {
			       		// 댓글 입력창 보이기
						$("#commentArea").show(); 
						$("#commentButton").show();
			       	}else{
			       		// 댓글 입력창 숨기기
						$("#comment-form").hide(); 
						$("#commentButton").hide(); 
			       	}
			       	
			       	$('#newPostModal').modal('show');
              	}else{
                  	swal({
                		  title: response.result,
                		  text: "You are not authorized to view this content.",
                		  icon: "warning",
                		  button: "OK",
                		})
              	}
              },
              error : function (jqXHR){
          		console.log(jqXHR);  // 응답 메시지
          	}
          });	                
        
	});    
    
    // Bind click event to delete button
    $('#example').on('click', '.fa-trash-can', function() {
    	
    	swal({
    		  title: "Are you sure you want to delete it?",
    		  text: "Your information is safely managed.",
    		  icon: "warning",
    		  buttons: true,
    		  dangerMode: true,
    		})
    		.then((willDelete) => {
    		  if (willDelete) {
    			var data = table.row($(this).parents('tr')).data();
	                var id = data.id;
	                $.ajax({
	                    url: '/posts/delete/'+ id,
	                    type: 'DELETE',
	                    beforeSend : function(xhr){   
	        				xhr.setRequestHeader(csrfheader, csrftoken);
	                    },
	                    success: function(response) {
	                    	if(response.redirectUrl != undefined && response.redirectUrl != ""){
	                    		table.ajax.reload();
	                    	}else{
	                    		swal({
		                  		  title: response.result,
		                  		  text: "You are not authorized to delete this content.",
		                  		  icon: "warning",
		                  		  button: "OK",
		                  		})	      	                    		
	                    	}
	                    },
	                    error : function (jqXHR){
	                		console.log(jqXHR);  // 응답 메시지
	                	}
	                });
    		  } else {
    		    
    		  }
    	});	

    });    
    
    // 댓글 달기 버튼 클릭시
    $("#commentButton").click(function() {
    	  // 입력된 댓글 내용 가져오기
    	  var commentContent = $("#commentContent").val();
    	  
    	  // 댓글 내용이 비어있으면 알림창 띄우기
    	  if(commentContent == '') {
  			swal({
				title: "Registration failed",
				text: "댓글을 입력해 주세요.",
				icon: "warning",
				button: "OK",
			})
    	    return false;
    	  }
    	  
  		  var params = {
  			 "postId": $("#modalId").val(),
  			 "content": commentContent
  		  };
    	  
    	  // 서버로 댓글 추가 요청 보내기
    	  $.ajax({
    	    type: "POST",
    	    url: "/posts/comment",
    	    contentType: 'application/json; charset=utf-8',
    	    beforeSend : function(xhr){   
				xhr.setRequestHeader(csrfheader, csrftoken);
            },
            data: JSON.stringify(params),
    	    success: function(response) {
    	      if(response.result != undefined && response.result != "comment save success"){
      	  	      swal({
      	    		  title: "Fail",
      	    		  text: response.result,
      	    		  icon: "warning",
      	    		  button: "OK",
      	    		})  
    	      }else{
      	  	      swal({
      	    		  title: "Success",
      	    		  text: response.result,
      	    		  icon: "success",
      	    		  button: "OK",
      	    		})
    	      }
  	    		// 댓글 추가 후 입력 폼 내용 지우기
  	    		$("#commentContent").val("");
    	        
  	  	        // 댓글 목록을 다시 불러오기
    	        loadComments();
    	    },
    	    error: function(error) {
    	      // 서버에서 응답을 받지 못하면 에러 메시지 출력
    	      console.log(error);
  	  	      swal({
  	    		  title: "Error",
  	    		  text: "Please check browser console",
  	    		  icon: "warning",
  	    		  button: "OK",
  	    		})
    	    }
    	  });
    	});
    
    // 댓글 리스트 접기/펼치기
    // 댓글 목록 버튼 클릭 시
    $("#comment-header").on("click", function() {
      // 댓글 목록 보이기/숨기기
      $("#comment-list").slideToggle();
      // 버튼 아이콘 변경
      $("#comment-header button i").toggleClass("fa-chevron-down fa-chevron-up");
    });
    
    // 삭제 버튼 클릭 이벤트
    $(document).on("click", ".delete-comment", function() {
    	swal({
  		  title: "Are you sure you want to delete it?",
  		  text: "Your information is safely managed.",
  		  icon: "warning",
  		  buttons: true,
  		  dangerMode: true,
  		})
  		.then((willDelete) => {
  		  if (willDelete) {
  			  var commentId = $(this).data("commentid");

	  	      $.ajax({
	  	    	  url: '/posts/comment/'+ commentId,
	  	          type: 'DELETE',
	  	          beforeSend : function(xhr){   
	  					xhr.setRequestHeader(csrfheader, csrftoken);
	  	          },
	  	        success: function(response) {
	  	        	if(response.result != undefined && response.result == "delete success"){
	  	        		loadComments();
	  	        	}else{
	  	        		swal({
	  	          		  title: "No Auth",
	  	          		  text: "You are not authorized to delete this content.",
	  	          		  icon: "warning",
	  	          		  button: "OK",
	  	          		})	
	  	        	}
	  	        },
	  	        error: function (error) {
	  	  	      console.log(error);	
	  	  	      swal({
	  	    		  title: "No Auth",
	  	    		  text: "You are not authorized to delete this content.",
	  	    		  icon: "warning",
	  	    		  button: "OK",
	  	    		})
	  	  	    },
	  	      });
  		  }else{
  			  
  		  }
    	});	
    });
    
    
    // textarea 엘리먼트에서 엔터키 눌렀을 때
    $('textarea').keydown(function(e) {
      if (e.keyCode == 13 && !e.shiftKey) { // 엔터키이고 shift키가 눌리지 않았을 때
        e.preventDefault(); // 기본 동작 취소
        $('#commentButton').click(); // 댓글 추가 버튼 클릭
      }
    });
    
    
});

function save(form){
	// Get form data
	var formData = new FormData(form);

	// Convert FormData object to JSON object
	var json = {};
	formData.forEach(function(value, key) {
      // If the key is "enable", set the value as a boolean
      if (key === 'secret') {
        json[key] = $("#secret").is(':checked');
      } else {
        json[key] = value;
      }
	});

	// Send AJAX request to save data
	$.ajax({
		url: '/posts/save',
		method: 'POST',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
		headers: {
			'Content-Type': 'application/json; charset=UTF-8'
		},
		data: JSON.stringify(json),
		success: function(response) {
			
			swal({
	        	title: response.result,
	        	text: "Your changes have been saved.",
	        	icon: "success",
	        	button: "OK",
	        	})
	        	.then((result) => {
	        	if (result) {
					// Close the modal
					var modal = $('#newPostModal');
					var modalInstance = bootstrap.Modal.getInstance(modal);
					modalInstance.hide();
					
					table.ajax.reload();
	        	}
	            });			
		},
		error: function(jqXHR) {
			console.log(jqXHR);  // 응답 메시지
			swal({
				title: "Registration failed",
				text: "Error : " + jqXHR.responseJSON.message,
				icon: "warning",
				button: "OK",
			})
				.then((result) => {
					if (result) {
						// Close the modal
						var modal = $('#newPostModal');
						var modalInstance = bootstrap.Modal.getInstance(modal);
						modalInstance.hide();
					}
				});
		}		
	});	
}

// onImageUpload callback 함수 정의
function onImageUpload(files) {
    for (var i = 0; i < files.length; i++) {
    	
    	// 이미지 파일을 압축합니다. (압축률: 50%)
    	compressImage(files[i], 200, 0.5).then(function (compressedImage) {
    	    // 압축된 이미지 파일을 써머노트 본문에 삽입합니다.
    	    const reader = new FileReader();
    	    reader.onload = function (e) {
    	      const imgNode = $('<img>').attr('src', e.target.result);
    	      $('#modalContent').summernote('insertNode', imgNode[0]);
    	    }
    		reader.readAsDataURL(compressedImage);
    	});
    }
}

// 이미지 파일을 압축하는 함수
function compressImage(imageFile, maxSize, quality) {
  return new Promise((resolve, reject) => {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');

    const reader = new FileReader();
    reader.readAsDataURL(imageFile);
    reader.onload = function (e) {
    const img = new Image();
    img.src = e.target.result;
    img.onload = function () {
     	// Calculate new dimensions based on max size parameter
    	let newWidth = img.width;
    	let newHeight = img.height;
    	if (newWidth > newHeight) {
    	   if (newWidth > maxSize) {
    	        newHeight *= maxSize / newWidth;
    	        newWidth = maxSize;
    	   }
    	} else {
    	   if (newHeight > maxSize) {
    	        newWidth *= maxSize / newHeight;
    	        newHeight = maxSize;
    	      }
    	}
    	
    	// 캔버스에 이미지를 그립니다.
        canvas.width = newWidth;
        canvas.height = newHeight;
        ctx.drawImage(img, 0, 0, newWidth, newHeight);

        // 캔버스에서 이미지 데이터를 가져옵니다.
        canvas.toBlob((blob) => {
        	console.log("Original Size:",imageFile.size);
            console.log("Compressed image size:", blob.size);
          // 압축된 이미지 파일을 반환합니다.
          resolve(blob);
        }, imageFile.type, quality);
      }
    };
    reader.onerror = reject;
  });
}

function loadComments() {
	debugger;
	  var postId = $("#modalId").val();
	  $.ajax({
	    type: "POST",
	    url: "/posts/comment/" + postId,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
	    success: function (response) {
	      var currentUserId = response.currentUserId;
	      var commentsHtml = "";
	      if (response.commentList.length == 0) {
	    	  commentsHtml += "<p>No comments</p>";
	      } else {
	    	  for (var i = 0; i < response.commentList.length; i++) {
	    		    commentsHtml += "<div class='card mt-3'>";
	    		    commentsHtml += "<div class='card-body'>";
	    		    commentsHtml += "<h5 class='card-title'>" + response.commentList[i].createId + "</h5>";
	    		    commentsHtml += "<h6 class='card-subtitle mb-2 text-muted'>" + moment(response.commentList[i].createdAt, "YYYY-MM-DDTHH:mm:ss").format("YYYY년 MM월 DD일 HH:mm") + "</h6>";
	    		    commentsHtml += "<p class='card-text'>" + response.commentList[i].content + "</p>";

	    		    // 삭제 아이콘 추가
	    		    if (response.commentList[i].createId === currentUserId) {
	    		        commentsHtml += "<i class='fas fa-trash-alt float-end delete-comment' data-commentid='" + response.commentList[i].id + "'></i>";
	    		    }

	    		    commentsHtml += "</div>";
	    		    commentsHtml += "</div>";
	    	  }
	      }

	      $("#comment-list").html(commentsHtml);
	      
	      // 댓글 갯수를 업데이트
    	  $("#comment-count").text(response.commentList.length);

	    },
	    error: function (error) {
	      console.log(error);
	    },
	  });
	}

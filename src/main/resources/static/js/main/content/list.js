var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	
var table;
$(document).ready(function() {
	debugger;
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
         		   //return '<button type="button" class="btn btn-primary">View Content</button>';
         		   return '<i class="fa-solid fa-magnifying-glass-chart"></i>';
         	   }                       
            },
            {"data": "",
         	   "render": function(data, type, row, meta){
                    //return '<button type="button" class="btn btn-danger">Delete</button>';
                    return '<i class="fa-solid fa-trash-can"></i>';
                }
            }
        ],
        order: [[3, 'desc']]
    });

    $('#search_btn').on('click', function () {
    	table.ajax.reload();
    });
    
    
    $('#newPostModalButton').click(function() {
        $('#newPostModal').modal('show');
    });
    
	$('#modalContent').summernote({
  	    placeholder: 'Hello Summernote lite',
  	    tabsize: 2,
        height: 300,                 // set editor height
        minHeight: null,             // set minimum height of editor
        maxHeight: null,             // set maximum height of editor
        focus: true,                  // set focus to editable area after initializing summernote
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
    	$(this).find('#postForm input[type=hidden]').val('');
    	$(this).find('#postForm').trigger('reset');
    	$(this).find('#modalContent').summernote('reset');
    });
    
    // Bind click event to content button
    $(document).on('click', '.fa-magnifying-glass-chart', function(){ 
        var $btn=$(this);
        var $tr=$btn.closest('tr');
        var dataTableRow=$("#example").DataTable().row($tr[0]); // get the DT row so we can use the API on it
        var rowData=dataTableRow.data();
        var id = rowData.id;
          $.ajax({
              url: '/posts/list/'+ id,
              type: 'POST',
              beforeSend : function(xhr){   
  				xhr.setRequestHeader(csrfheader, csrftoken);
              },
              success: function(response) {
              	debugger;
              	if(response.postList != undefined && response.postList != ""){
              		$('#modalId').val(response.postList.id);
              		$('#modalContent').summernote('code', response.postList.content);
			       	$('#modaTitle').val(response.postList.title);
			       	$('#modaSecret').prop('checked', response.postList.secret);
              		
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
          		console.log(jqXHR);  //응답 메시지
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
	                debugger;
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
	                		console.log(jqXHR);  //응답 메시지
	                	}
	                });
    		  } else {
    		    
    		  }
    	});	

    });    
    
});

function save(form){
	debugger;
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
			console.log(jqXHR);  //응답 메시지
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


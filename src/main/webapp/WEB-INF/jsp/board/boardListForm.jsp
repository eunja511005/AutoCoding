<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
<title>Insert title here</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="/assets/myfavicon.ico" />
    
	<!-- Required dataTable -->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">

		
    <!-- Required jquery -->
    <script type="text/javascript" language="javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script type="text/javascript" language="javascript" src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>

	<!-- Required bootstrap -->
	<!--<link href="/assets/dist/css/bootstrap.min.css" rel="stylesheet">-->
	
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

	<!-- Required summernote -->
  	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

</head>
<body>
<div class="container-fluid">
	<h2>Board Lists</h2>
</div>
<hr>

<div class="container-fluid">
    <table id="example" class="display" style="width:100%">
        <thead>
		    <tr>
		        <th colspan="6">
					<form id="my-form" action="/board/saveForm" method="post">
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					  <button type="submit">New</button>
					</form>
		        </th>
		    </tr>       
	        <tr>
	            <th>Title</th>
	            <th>Creator</th>
	            <th>Update</th>
	            <th>Secret</th>
	            <th>Contents</th>
	            <th>Delete</th>
	        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>



<!-- Modal -->
<div class="modal fade" id="contentModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Board Detail</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
		<form id="boardForm">
			  <div class="row my-4">
			    <div class="col-md-12">
			      <div class="form-check">
			        <input type="checkbox" class="form-check-input" id="isSecret">
			        <label class="form-check-label" for="isSecret">Is Secret</label>
			      </div>
			    </div>
			  </div>	
			  <div class="row my-4">
			    <div class="col-md-12">
			      <div>
			        <label for="title" class="form-label">Title</label>
			        <input type="text" class="form-control" id="title" name="title" placeholder="title">
			      </div>
			    </div>
			  </div>
			  <div class="row">
			    <div class="col-md-12">
			      <div>
			        <label for="content" class="form-label">Content</label>
			        <textarea class="form-control" id="summernote" name="content" rows="10"></textarea>
			      </div>  
			    </div>
			  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" id="updateContents" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>



<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>

<!-- Required bootstrap -->
<!--<script type="text/javascript" language="javascript" src="/assets/dist/js/bootstrap.bundle.min.js"></script> -->

	<script>
				var nowPage = 1;
				var pageSize = 1;
				
				var csrfheader = $("meta[name='_csrf_header']").attr("content");
		    	var csrftoken = $("meta[name='_csrf']").attr("content");
		    	
		    	var currentBoradId;
		    	
	            $(document).ready(function() {
	            	
	            	debugger;
	                var aParams = {
	                		nowPage : nowPage,
	                		pageSize : pageSize
	                    };
	            	
	            	
	                $('#example').DataTable({
	                   lengthChange: false,
	                   displayLength: 20,
	                   scrollX: true,
	                   ajax: {
	                       url: '/board/list',
	                	   type: 'POST',
	                	   data: aParams,
		               	   beforeSend: function(xhr) {
		            	   		xhr.setRequestHeader(csrfheader, csrftoken);
		            	   },
	                       dataSrc: 'boardList'
	                   },
	                   columns: [
	                       { data: 'title'},
	                       { data: 'createId'},
	                       { data: 'updateTime', 
	                    	   "render": function(data, type, row, meta){
	                    		   data = data;
	                    		   return data;
	                    	   }
	                       },
	                       { data: 'secret', 
	                    	    "render": function(data, type, row, meta) {
	                    	        if (data == true) {
	                    	            return '<i class="fa-solid fa-lock" aria-hidden="true"></i>';
	                    	        } else {
	                    	            return '<i class="fa-solid fa-unlock" aria-hidden="true"></i>';
	                    	        }
	                    	    }
	                    	},
	                       { data: 'content',
	                    	   "render": function(data, type, row, meta){
	                    		   //return '<button type="button" class="btn btn-primary">View Content</button>';
	                    		   return '<i class="fa-solid fa-magnifying-glass-chart"></i>';
	                    	   }                       
	                       },
	                       { data: 'boardId',
	                    	   "render": function(data, type, row, meta){
	                               //return '<button type="button" class="btn btn-danger">Delete</button>';
	                               return '<i class="fa-solid fa-trash-can"></i>';
	                           }
	                       }
	                   ],
		               columnDefs: [
		            	   //{ targets: 0, visible: false }
		            	      {"className": "text-center", "targets": [3, 4]},
		            	],
	                   order: [ [2, 'desc'] ]
	                });
	                
	                
	                $('#summernote').summernote({
	                    placeholder: 'write your idea',
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
	                
	                
	            } );
	            
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
	            			var data = $('#example').DataTable().row($(this).parents('tr')).data();
	      	                
	      	                var id = data.boardId;
	      	                
	      	                $.ajax({
	      	                    url: '/board/delete/'+ id,
	      	                    type: 'DELETE',
	      	                    beforeSend : function(xhr){   
	      	        				xhr.setRequestHeader(csrfheader, csrftoken);
	      	                    },
	      	                    success: function(response) {
	      	                    	if(response.redirectUrl != undefined && response.redirectUrl != ""){
	      	                    		window.location.href = response.redirectUrl;
	      	                    	}else{
	      	                    		swal({
	        		                  		  title: response.result,
	        		                  		  text: "You are not authorized to delete this content.",
	        		                  		  icon: "warning",
	        		                  		  button: "OK",
	        		                  		})	      	                    		
	      	                    	}
	      	                    },
	      	                    error : function (jqXHR, textStatus, errorThrown){
	      	                		console.log(jqXHR);  //응답 메시지
	      	                	}
	      	                });
	            		  } else {
	            		    
	            		  }
	            	});	

	            });
	            
	            /*
	            function deleteRow(data) {
	                // Get the datatable
	                var table = $('#example').DataTable();
	
	                // Find the row with the matching data and remove it
	                table.rows().eq(0).each(function(index) {
	                    var row = table.row(index);
	                    var rowData = row.data();
	                    if (rowData === data) {
	                        row.remove().draw();
	                    }
	                });
	            }
	            */
	            
	            $(document).on('click', '.fa-magnifying-glass-chart', function(){ 
	                var $btn=$(this);
	                var $tr=$btn.closest('tr');
	                var dataTableRow=$("#example").DataTable().row($tr[0]); // get the DT row so we can use the API on it
	                var rowData=dataTableRow.data();
					
	                currentBoradId = rowData.boardId;
	                
  	                $.ajax({
  	                    url: '/board/list/'+ currentBoradId,
  	                    type: 'POST',
  	                    beforeSend : function(xhr){   
  	        				xhr.setRequestHeader(csrfheader, csrftoken);
  	                    },
  	                    success: function(response) {
  	                    	
  	                    	//var response = JSON.parse(response);
  	                    	
  	                    	if(response.boardList != undefined && response.boardList != ""){
  	                    		$('#summernote').summernote('code', response.boardList.content);
  	      			       	    $('#title').val(response.boardList.title);
  	      			       		$('#isSecret').prop('checked', response.boardList.secret);
  	                    		$('#contentModal').modal('show');
  	                    	}else{
  		                    	swal({
  		                  		  title: response.result,
  		                  		  text: "You are not authorized to view this content.",
  		                  		  icon: "warning",
  		                  		  button: "OK",
  		                  		})
  	                    	}
  	                    },
  	                    error : function (jqXHR, textStatus, errorThrown){
  	                		console.log(jqXHR);  //응답 메시지
  	                	}
  	                });	                
	                
	                
	                
	                
	        	});
	            
	            $(document).on('click', '.btn-secondary', function(){ 
	            	$('#contentModal').modal('hide');
	        	});
	            
	            $(document).on('click', '#updateContents', function(){ 
	                var data = {
	                	boardId : currentBoradId,
	                	title : $('#title').val(),
	                	secret : $('#isSecret').is(':checked'),
	                    content : $('#summernote').summernote('code')
	                };
	            	
	                $.ajax({
	                    type : 'PUT',
	                    url : '/board/update',
	                    beforeSend : function(xhr){   
	        				xhr.setRequestHeader(csrfheader, csrftoken);
	                    },
	                    contentType : 'application/json; charset=utf-8',
	                    data : JSON.stringify(data),
	                    processData: false,
	                    dataType : 'text',
	                    success: function(response) {
	                    	debugger;
	                    	
	                    	//var response = eval( '(' + response + ')' );
	                    	var response = JSON.parse(response);
	                    	
	                    	swal({
	                  		  title: response.result,
	                  		  text: "Your changes have been saved.",
	                  		  icon: "success",
	                  		  button: "OK",
	                  		})
	                  		.then((result) => {
	                  		  if (result) {
	                  			  window.location.href = response.redirectUrl;
	                  		  }
	                  		});
	                    },
	                	error : function (jqXHR, textStatus, errorThrown){
	                		console.log(jqXHR);  //응답 메시지
	                		console.log(textStatus); //"error"로 고정인듯함
	                		console.log(errorThrown);
	                	}
	                })
	            	
	        	});
	</script>

	<!-- fontawesome -->
	<script src="https://kit.fontawesome.com/64b5a4efc1.js" crossorigin="anonymous"></script>
	
	<!-- https://sweetalert.js.org/guides/ -->
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> 

</body>
</html>
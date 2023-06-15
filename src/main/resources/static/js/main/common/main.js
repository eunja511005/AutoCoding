var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

// 카드 리스트 페이징 CBO 처리를 위한 변수
const MAX_PAGES_DISPLAYED = 10; // 10개 페이지씩 보여줌
const MID_PAGES_DISPLAYED = 8; // 중간 페이지
    	
$(document).ready(function(){
	// 첫번째 페이지
	$('#dynamic-content').load("/content1");
	
	// 로그인 여부를 체크합니다.
	checkLoginStatus();
	
	// 좌측 메뉴
	loadNav();
	
	// 로그 아웃 이벤트 등록(위임을 통해 상위에 이벤트 등록하여 dynamic-content 부분만 바뀌어도 이벤트 유지도록 함)
	addLogoutEvent();
	
});

function handleClick(event) {
	debugger;
    event.preventDefault();

    // 페이지를 로드합니다.
    var url = $(this).attr('href');
    if (url !== undefined) {
    	loadDynamicContent(url);
        
        //$('.a-menu').off('click');
        //$('.a-menu').on('click', handleClick);
        
        //$('nav').off('click', '.a-menu', handleClick);
        //$('nav').on('click', '.a-menu', handleClick);
        //$('nav').off('click', '.a-menu').on('click', '.a-menu', handleClick);

    }
    
}

function loadDynamicContent(url) {
	  // 기존 모든 이벤트를 제거
	  $('#dynamic-content').off();
	  //$("#menuContainer").off();
	  
	  // 이전에 로딩된 동적 요소를 삭제
	  $('#dynamic-content').empty();
	  //$("#menuContainer").empty();

	  // 새 페이지 로딩
	  $('#dynamic-content').load(url, function(response, status, xhr) {
		  if (status === "error" && xhr.status === 403) {
		    $('#dynamic-content').html("403 Forbidden - Access Denied");
		  }
	  });
	  
	  //$("#menuContainer").html(navLoad);
}

function checkLoginStatus() {
	$.ajax({
		url: "/login-status",
		type: "GET",
		dataType: "json",
		success: function(response) {
			if(response.result != undefined && response.result == "login") {
				// 로그인 되어 있을 경우
				$('#login-btn').hide();
				$('#edit-btn').show();
				$('#message-btn').show();
				$('#logout-btn').show();
				
			    // 로그인되어있으면 이미지를 보여준다.
				$('#navbarDropdown').append('<img src="'+response.userProfileImg+'" class="rounded-circle" width="25" height="25" alt="User Profile">');
				$('#loggedInUser').text(response.userName);
			} else {
				// 로그인 되어 있지 않을 경우
				$('#login-btn').show();
				$('#edit-btn').hide();
				$('#message-btn').hide();
				$('#logout-btn').hide();
				
			    // 로그인되어있지 않으면 아이콘을 보여준다.
			    const i = document.createElement('i');
			    i.classList.add('fas', 'fa-user', 'fa-fw');
			    $('#navbarDropdown').append(i);
			}
		},
		error: function(xhr) {
			console.error(xhr);
		}
	});
}

function addLogoutEvent(){
	$(document).on('click', '#logout-btn', function(event) {
	    event.preventDefault();
		swal({
			title: "Are you sure want to log out?",
			text: "Your information is safely managed.",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
			.then((willDelete) => {
				debugger;
				if (willDelete) {
				    $.ajax({
				      type: 'POST',
				      url: '/signout',
				      beforeSend: function(xhr) {
				        xhr.setRequestHeader(csrfheader, csrftoken);
				      },
				      success: function() {
				    	  window.location.href = '/main'; // 로그아웃 성공 후 /main 페이지로 이동
				    	  location.reload(); // 로그 아웃 성공후 csrf 토큰 리프레쉬를 위해 페이지 리로드
				      }
				    });
				} else {

				}
			});		    

	  }); 	
}

function initSelectBox(selectBoxId, url, includeAll, selectedValues) {
	  var selectBox = $('#' + selectBoxId);
	  selectBox.empty();
	  
	  if (includeAll) {
	    selectBox.append($('<option>').val('').text('ALL'));
	  }

	  callAjax(url, "GET", null, function(response) {
	      if (response.success) {
		        $.each(response.data, function(index, commonCode) {
		          var option = $('<option>').val(commonCode.code).text(commonCode.value);
		          if (Array.isArray(selectedValues) && selectedValues.indexOf(commonCode.code) !== -1) {
		            option.prop('selected', true);
		          }
		          selectBox.append(option);		         
		        });
		  }
	  });
	  
}


function callAjax(url, method, data, successCallback){
	  // 입력값이 form 요소인 경우
	  if (data instanceof FormData) {
		// Convert FormData object to JSON object[(ex) var formData = new FormData(this);]
		var json = {};
		data.forEach(function(value, key) {
	      // If the key is "enable", set the value as a boolean
	      if (key === 'enable') {
	        json[key] = $("#enable").is(':checked');
	      } else {
	        json[key] = value;
	      }
		});
	    
		data = JSON.stringify(json);
	  }
	  
	  if(typeof data === 'object'){
		  data = JSON.stringify(data);
	  }
	  
	  // AJAX 호출 수행
	  $.ajax({
	    url: url,
	    type: method,
	    beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
	    data: data,
	    contentType: 'application/json; charset=UTF-8',
	    success: function(response) {
			if (response.success) {
				successCallback(response);
		    } else {
		    	swal({
		      		  title: "Application Error",
		      		  text: response.errorMessage,
		      		  icon: "warning",
		      		  button: "OK",
		      	})
		    }
		},
		error: function(error) {
	  	    swal({
	    		  title: error.responseJSON.status + ", " +error.responseJSON.code,
	    		  text: error.responseJSON.message,
	    		  icon: "warning",
	    		  button: "OK",
	    	})
		},
	  });
}

function loadNav(){
    // 클릭 이벤트를 등록합니다.(꼭 클릭 이벤트는 한번만 등록 할것. 여러번 등록하면 중복 호출 이슈 있으니 주의)
	$('nav').off('click', '.a-menu').on('click', '.a-menu', handleClick);
	
    // leftNav 구성(서버에서 메뉴 HTML을 가져와서 menuContainer에 삽입)
    $.ajax({
        url: "/menu/loadMenu",
        type: "GET",
        success: function(response) {            
            // 받은 HTML을 동적으로 추가합니다.
            $("#menuContainer").html(response);
        },
        error: function(xhr, status, error) {
            console.log("Failed to load menu: " + error);
        }
    });
}

//이미지 파일을 압축하는 함수
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
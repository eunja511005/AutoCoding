var nowPage = 1;
var pageSize = 1;
 
var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	

//로딩바 타임아웃 설정
const LOADING_TIMEOUT = 5000; // 타임아웃 시간 (10초)
let loadingTimer; // 타임아웃 타이머
		
$(document).ready(function() {
	
	debugger;
    
    $('form#uploadForm').submit(function(event) {
    	
    	showLoadingBar();
    	
    	event.preventDefault();
    	
        if (!this.checkValidity()) {
            event.stopPropagation();
            $(this).addClass('was-validated');
            hideLoadingBar();
            return;
        }
        
        $(this).addClass('was-validated');
    	
        var form = document.getElementById('uploadForm'); //id of form
        var formData = new FormData(form);
        
        // 민감 정보(이메일) 암호화
        debugger;
        var publicKey = $('#publicKeyString').val();
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        var dataToEncrypt = document.getElementById('email').value;
        var encryptedData = encrypt.encrypt(dataToEncrypt);
        formData.set('email', encryptedData);
        
        callAjax("/recoveryPassword", "POST", formData, recoveryCallback);

    });
});

function callAjax(url, method, data, successCallback, jwtToken){
	
	//showLoadingBar();
	
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
	  
	  var ajaxOptions = {
		url: url,
		type: method,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
			
            // jwtToken 값이 존재하는 경우에만 Authorization 헤더 설정
            if (jwtToken) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
            }
		},
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
				});
			}
		},
		error: function(error) {
			swal({
				title: error.responseJSON.data,
				text: error.responseJSON.errorMessage,
				icon: "warning",
			 	button: "OK",
			});
		},
	};

	// data가 존재하면서 비어있지 않을 때만 data 필드 추가
	if (!isBlank(data)) {
		ajaxOptions.data = data;
	}

	$.ajax(ajaxOptions);
}

function recoveryCallback(response){
	hideLoadingBar();
	
	swal({
		title: "Success",
		text: response.data,
		icon: "success",
		button: "OK",
	});
}

//로딩바 표시 함수
function showLoadingBar() {
  var loadingContainer = document.getElementById('loadingContainer');
  loadingContainer.style.display = 'flex';
  loadingTimer = setTimeout(hideLoadingBar, LOADING_TIMEOUT);
}

// 로딩바 숨김 함수
function hideLoadingBar() {
  var loadingContainer = document.getElementById('loadingContainer');
  loadingContainer.style.display = 'none';
  clearTimeout(loadingTimer); // 타임아웃 타이머 제거
}

//null, 빈 문자열, undefined 체크하는 공통 메서드
function isBlank(data) {
  return data === null || data === "" || data === "null" || typeof data === "undefined";
}
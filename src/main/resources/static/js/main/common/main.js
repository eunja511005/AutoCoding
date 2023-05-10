var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
    	
$(document).ready(function(){
	// 첫번째 페이지
	$('#dynamic-content').load("/content1");
	
    // 클릭 이벤트를 등록합니다.
    $('nav a').on('click', handleClick);
    
	// 로그인 여부를 체크합니다.
	checkLoginStatus();
	
	// 로그 아웃 이벤트 등록(위임을 통해 상위에 이벤트 등록하여 dynamic-content 부분만 바뀌어도 이벤트 유지도록 함)
	// $(document).on('click', '#logout-btn', function(event) {
	addLogoutEvent();
	
});

function handleClick(event) {
    event.preventDefault();

    // 페이지를 로드합니다.
    var page = $(this).attr('href');
    if (page !== undefined) {
    	$('#dynamic-content').load(page);
        
        $('nav a').off('click');
        $('nav a').on('click', handleClick);
    }
    
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
				$('#navbarDropdown').append('<img src="'+response.userProfileImg+'" class="rounded-circle" width="20" height="20" alt="User Profile">');
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
				    	  location.reload(); // 로그 아웃 성공후 csrf 토큰 리프레쉬를 위해 페이지 리로드
				      }
				    });
				} else {

				}
			});		    

	  }); 	
}
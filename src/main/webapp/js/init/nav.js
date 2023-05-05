$(document).ready(function() {

	var csrfheader = $("meta[name='_csrf_header']").attr("content");
	var csrftoken = $("meta[name='_csrf']").attr("content");

	$.ajax({
		url: '/getUserProfile',
		type: 'POST',
		contentType: false,
		processData: false,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
		success: function(response) {
			$('#userProfileImg').attr('src', response.userProfileImg);
			$('#userSpan').text(response.userName);
		},
		error: function(response) {
			alert(response.responseText);
		}
	});
});

function logout() {
	swal({
		title: "Are you sure want to log out?",
		text: "Your information is safely managed.",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				logoutForm.submit();
			} else {

			}
		});
}
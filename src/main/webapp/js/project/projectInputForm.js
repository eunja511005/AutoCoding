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

		$.ajax({
			url: "/project/create",
			type: "POST",
			data: JSON.stringify(data),
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			success: function(response) {
				swal({
					title: response.result,
					text: "Your changes have been saved.",
					icon: "success",
					button: "OK",
				})
					.then((result) => {
						if (result) {
							if (response.redirectUrl != undefined && response.redirectUrl != "") {
								window.location.href = response.redirectUrl;
							}
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

						}
					});
			}
		});
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

					$.ajax({
						url: '/project/delete/' + id,
						type: 'DELETE',
						beforeSend: function(xhr) {
							xhr.setRequestHeader(csrfheader, csrftoken);
						},
						success: function(response) {
							if (response.redirectUrl != undefined && response.redirectUrl != "") {
								window.location.href = response.redirectUrl;
							} else {
								swal({
									title: response.result,
									text: "You are not authorized to delete this content.",
									icon: "warning",
									button: "OK",
								})
							}
						},
						error: function(jqXHR) {
							console.log(jqXHR);  //응답 메시지
						},
						complete: function() {
							button.prop('disabled', false);
						}
					});
				} else {

				}
			});

	});


}); 
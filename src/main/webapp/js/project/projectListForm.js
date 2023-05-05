var currentPage = 1;
var itemsPerPage = 6;
const MAX_PAGES_DISPLAYED = 10; // 10개 페이지씩 보여줌
const MID_PAGES_DISPLAYED = 6; // 중간 페이지

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

var currentCodeId;

$(document).ready(function() {

	function displayProjects(data) {
		var projectList = $('#project-list');
		projectList.empty();

		$.each(data, function(index, project) {
			var col = $('<div class="col mb-4"></div>');
			var card = $('<div class="card h-100"></div>');
			var cardBody = $('<div class="card-body"></div>');

			var titleText = project.name.length > 10 ? project.name.substring(0, 10) + '...' : project.name;
			var title = $('<h5 class="card-title">' + titleText + '</h5>');
			cardBody.append(title);

			var descriptionText = project.description.length > 10 ? project.description.substring(0, 10) + '...' : project.description;
			var description = $('<p class="card-text">' + descriptionText + '</p>');
			cardBody.append(description);

			var viewBtn = $('<a href="#" class="btn btn-primary">View Project</a>');
			viewBtn.attr('data-project-id', project.id);
			cardBody.append(viewBtn);

			card.append(cardBody);
			col.append(card);
			projectList.append(col);
		});
	}


	$(document).on('click', '.btn-primary', function() {
		var id = $(this).data('project-id');

		window.location.href = "/project/inputForm/" + id;

		/*$.ajax({
			url: '/project/list/' + id,
			type: 'POST',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			success: function(response) {

				if (response.projectList != undefined && response.projectList != "") {
					window.location.href = "/project/inputForm?id=" + id;
				} else {
					swal({
						title: response.result,
						text: "You are not authorized to view this content.",
						icon: "warning",
						button: "OK",
					})
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);  //응답 메시지
			}
		});*/


	});


	function displayPagination(totalItems) {
		var totalPages = Math.ceil(totalItems / itemsPerPage);
		var pagination = $('#pagination');
		pagination.empty();

		if (totalPages > 1) {
			var startPage, endPage;
			if (totalPages <= MAX_PAGES_DISPLAYED) {
				startPage = 1;
				endPage = totalPages;
			} else {
				var half = Math.floor(MAX_PAGES_DISPLAYED / 2);
				if (currentPage <= half) {
					startPage = 1;
					endPage = MAX_PAGES_DISPLAYED;
				} else if (currentPage + half >= totalPages) {
					startPage = totalPages - (MAX_PAGES_DISPLAYED - 1);
					endPage = totalPages;
				} else {
					startPage = currentPage - half;
					//endPage = currentPage + (MAX_PAGES_DISPLAYED - half - 1);
					endPage = currentPage + half;
				}
			}

			var paginationList = $('<ul class="pagination justify-content-center"></ul>');
			var prevBtn = $('<li class="page-item"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
			if (currentPage === 1) {
				prevBtn.addClass('disabled');
			} else {
				prevBtn.find('a').on('click', function() {
					currentPage--;
					loadProjects();
				});
			}
			paginationList.append(prevBtn);

			for (var i = startPage; i <= endPage; i++) {
				var pageLink = $('<li class="page-item"><a class="page-link" href="#">' + i + '</a></li>');
				if (i === currentPage) {
					pageLink.addClass('active');
				} else {
					pageLink.find('a').on('click', function() {
						currentPage = parseInt($(this).text(), 10);
						loadProjects();
					});
				}
				paginationList.append(pageLink);
			}

			var nextBtn = $('<li class="page-item"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
			if (currentPage === totalPages) {
				nextBtn.addClass('disabled');
			} else {
				nextBtn.find('a').on('click', function() {
					currentPage++;
					loadProjects();
				});
			}
			paginationList.append(nextBtn);

			pagination.append(paginationList);
		}
	}



	function loadProjects() {

		var params = {
			"page": currentPage,
			"size": itemsPerPage
		};

		$.ajax({
			url: '/project/list',
			type: 'POST',
			data: JSON.stringify(params),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			success: function(data) {

				if (data.projects != undefined && data.projects != "") {
					displayProjects(data.projects);
					displayPagination(data.totalElements);
				}
			},
			error: function(jqXHR) {
				console.log(jqXHR);  //응답 메시지
			}
		});
	}

	loadProjects();

});


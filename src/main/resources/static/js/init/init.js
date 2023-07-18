var currentPage = 1;
var itemsPerPage = 6;

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

var currentCodeId;

$(document).ready(function() {

	debugger;
	
	$('#dynamic-content').on('click', '.a-btn-projectListForm', function(event) {
		  event.preventDefault();
		  var id = $(this).data('project-id');
		  loadDynamicContent("/project/inputForm/" + id);
	});	
	
	$('#dynamic-content').on('click', '#submitButton', function(event) {
		event.preventDefault();
		
		// Form 내용 가져오기
		const name = document.getElementById('name').value;
		const email = document.getElementById('email').value;
		const phone = document.getElementById('phone').value;
		const message = document.getElementById('message').value;

		// 이메일 본문 생성
		const mailBody = `Name: ${name}\nEmail: ${email}\nPhone: ${phone}\nMessage: ${message}`;

		
	    const data = {
			to: 'ywbest.park@gmail.com',
			subject: '[중요] AutoCoding 컨택 메일',
			body: mailBody
		};
		
		callAjax("/sendEmail", "POST", data, emailCallback);
	});	

	loadProjects();

});

function emailCallback(response){
	swal({
		title: "Success",
		text: response.data,
		icon: "success",
		button: "OK",
	});
	
    // 이메일 입력 필드 초기화
    document.getElementById("email").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("message").value = "";
}

function listCallback(data) {

	if (data.projects != undefined && data.projects != "") {
		displayProjects(data.projects);
		displayPagination(data.totalElements);
	}
}

function displayProjects(data) {
	var projectList = $('#project-list');
	projectList.empty();

	$.each(data, function(index, project) {
		var col = $('<div class="col mb-3"></div>');
		var card = $('<div class="card h-100"></div>');
		var cardBody = $('<div class="card-body"></div>');
		
		var image = $('<img class="card-img-top mb-3">');
		image.attr('src', project.picture);
		cardBody.append(image);

		var titleText = project.name.length > 10 ? project.name.substring(0, 10) + '...' : project.name;
		var title = $('<h5 class="card-title">' + titleText + '</h5>');
		cardBody.append(title);

		var descriptionText = project.description.length > 20 ? project.description.substring(0, 20) + '...' : project.description;
		var description = $('<p class="card-text">' + descriptionText + '</p>');
		cardBody.append(description);
		
		var viewBtnWrapper = $('<div class="d-flex justify-content-center"></div>');
		var viewBtn = $('<a class="btn btn-secondary a-btn-projectListForm">View Project</a>');
		viewBtn.attr('data-project-id', project.id);
		viewBtnWrapper.append(viewBtn);
		cardBody.append(viewBtnWrapper);

		card.append(cardBody);
		col.append(card);
		projectList.append(col);
	});
}

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
	
	callAjax("/project/list", "POST", params, listCallback);
}
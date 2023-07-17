/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	
	$('#sendEmailButton').on('click', sendEmail);
	
	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
});

function sendEmail(event) {
	event.preventDefault();
	
    const data = {
		to: document.getElementById("to").value,
		subject: document.getElementById("subject").value,
		body: document.getElementById("body").value
	};
	
	callAjax("/sendEmail", "POST", data, emailCallback);
	
}

function emailCallback(response){
	swal({
		title: "Success",
		text: response.data,
		icon: "success",
		button: "OK",
	});
	
    // 이메일 입력 필드 초기화
    document.getElementById("to").value = "";
    document.getElementById("subject").value = "";
    document.getElementById("body").value = "";
}


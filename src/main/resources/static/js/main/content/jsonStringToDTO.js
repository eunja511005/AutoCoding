/**
 * This software is protected by copyright laws and international copyright
 * treaties. The ownership and intellectual property rights of this software
 * belong to the developer. Unauthorized reproduction, distribution,
 * modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences. This software is licensed to the user
 * and must be used in accordance with the terms of the license. Under no
 * circumstances should the source code or design of this software be disclosed
 * or leaked. The developer shall not be liable for any loss or damages. Please
 * read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var showButton = true; // 이 부분을 원하는 조건에 따라 true 또는 false로 변경하세요.

$(document).ready(function() {
	debugger;
	
	// 유저에 따라 버튼 보이고 안보이게 컨트롤
	buttonControl();
	
	// 버튼 클릭 이벤트 등록
	$('#conversionButton').on('click', function(event) {
		event.preventDefault();
		conversion();
	    $(this).blur(); // 버튼 클릭 후 포커스 해제
	});

	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
});

function buttonControl(){
    if (showButton) {
        $("#conversionButton").show();
    } else {
        $("#conversionButton").hide();
    }
}

function conversion(){
	var jsonStringInput = $("#jsonString").val();
	
	var data = {
		jsonString: jsonStringInput,
	};
	
	callAjax('/jsonStringToDTO/conversion', 'POST', data, conversionCallback)
}

function conversionCallback(response) {
	var data = response.data;
	const sourceList = $('#sourceList');

	// Clear the source list
	sourceList.empty();

	// Create list items for each source code
	for (var autoCoding of data) {
	  const li = $('<li>').addClass('list-group-item d-flex flex-column gap-2');
	  const row = $('<div>').addClass('row');
	  const col = $('<div>').addClass('col-md-12 d-flex justify-content-between');

	  const nameElement = $('<span>').addClass('mr-4').text(autoCoding.sourceName);

	  const copyButton = $('<button>').addClass('btn btn-sm btn-dark');
	  const copyIcon = $('<i>').addClass('fas fa-copy');
	  const buttonText = $('<span>').text('Copy');
	  const separator = $('<span>').addClass('mx-2 separator').text('|'); // Add
																			// separator
																			// between
																			// icon
																			// and
																			// button
																			// text
	  copyButton.append(copyIcon);
	  copyButton.append(separator);
	  copyButton.append(buttonText);
	  copyButton.on('click', function() {
	      copyToClipboard(codeElement.text());
	      buttonText.text('Copied!');
	      copyIcon.removeClass('fa-copy').addClass('fa-check'); // Change the
																// icon to check
																// mark
	      setTimeout(function() {
	          buttonText.text('Copy');
	          copyIcon.removeClass('fa-check').addClass('fa-copy'); // Change
																	// the icon
																	// back to
																	// copy
	      }, 2000);
	  });


	  col.append(nameElement);
	  col.append(copyButton);
	  row.append(col);
	  li.append(row);

	  const codeElement = $('<pre>').css('padding-left', '20px').css('padding-top', '20px');
	  codeElement.addClass('bg-dark');

	  if (autoCoding.sourceName.endsWith('.java')) {
	  	codeElement.addClass('language-java').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.java));
	  } else if (autoCoding.sourceName.endsWith('.xml')) {
	  	codeElement.addClass('language-xml').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.xml));
	  } else if (autoCoding.sourceName.endsWith('.sql')) {
	  	codeElement.addClass('language-sql').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.sql));
	  } else if (autoCoding.sourceName.endsWith('.jsp')) {
	  	codeElement.addClass('language-markup').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.sql));
	  } else if (autoCoding.sourceName.endsWith('.js')) {
	  	codeElement.addClass('language-javascript').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.sql));
	  } else {
	  	codeElement.text(autoCoding.sourceCode);
	  }


	  li.css('padding', '20px');
	  li.append(codeElement);
	  sourceList.append(li);
	}
}

function copyToClipboard(text) {
	const textarea = $('<textarea>').val(text);
	$('body').append(textarea);
	textarea.select();
	document.execCommand('copy');
	textarea.remove();
}
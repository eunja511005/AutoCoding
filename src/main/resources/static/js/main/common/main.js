$(document).ready(function(){
	//첫번째 페이지
	$('#dynamic-content').load("/content1");
	
    // 클릭 이벤트를 등록합니다.
    $('nav a').on('click', handleClick);
});

function handleClick(event) {
    event.preventDefault();

    // 페이지를 로드합니다.
    var page = $(this).attr('href');
    $('#dynamic-content').load(page);
    
    $('nav a').off('click');
    $('nav a').on('click', handleClick);
}


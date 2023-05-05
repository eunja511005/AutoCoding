$(document).ready(function(){
	loadContent("/content1");
	
    $('a').click(function(event) {
        event.preventDefault();
        var page = $(this).attr('href');
        loadContent(page);
    });
});

function loadContent(page){
	$('#dynamic-content').load(page);
}

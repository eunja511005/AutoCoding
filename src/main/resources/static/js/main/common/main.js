$(document).ready(function(){
	loadContent("/content1");
	
	$('nav a').on('click', function(event) {
        event.preventDefault();
        var page = $(this).attr('href');
        loadContent(page);
    });
});

function loadContent(page){
	$('#dynamic-content').load(page);
}

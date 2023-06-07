$(document).ready(function() {
	debugger;
	callAjax("/userRequestHistory/requestDataAnlyze", "GET", null, getPieCallback);
});

function getPieCallback(response){
    var labels = [];
    var data = [];

    for (var i = 0; i < response.data.length; i++) {
        var item = response.data[i];
        labels.push(item.REQUEST_URL);
        data.push(item.REQUEST_COUNT);
    }

    var ctx = document.getElementById('myPieChart');
    var myChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745', '#ff0000'],
            }]
        },
    });
}

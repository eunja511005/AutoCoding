$(document).ready(function() {
	debugger;
	callAjax("/errorHist/errorData", "GET", null, getPieCallback);
});

function getPieCallback(response){
    var labels = [];
    var data = [];

    for (var i = 0; i < response.data.length; i++) {
        var item = response.data[i];
        labels.push(item.ERROR_DATE);
        data.push(item.ERROR_COUNT);
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

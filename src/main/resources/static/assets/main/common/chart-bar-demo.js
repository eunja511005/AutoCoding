$(document).ready(function() {
	debugger;
	callAjax("/errorHist/errorData", "GET", null, getBarCallback);
});

function getBarCallback(response){
    var labels = [];
    var data = [];

    for (var i = 0; i < response.data.length; i++) {
        var item = response.data[i];
        labels.push(item.ERROR_DATE);
        data.push(item.ERROR_COUNT);
    }

    var ctx = document.getElementById('myBarChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Error Count',
                data: data,
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    stepSize: 1 // y축 간격 설정 (오류 갯수가 정수인 경우)
                }
            }
        }
    });

}

$(document).ready(function() {
	debugger;
	
    document.querySelector('#updateTime1 span').textContent = getCurrentTime();
    document.querySelector('#updateTime2 span').textContent = getCurrentTime();
    document.querySelector('#updateTime3 span').textContent = getCurrentTime();
    
	callAjax("/errorHist/errorData", "GET", null, getLineCallback);
});

function getLineCallback(response){
    var labels = [];
    var data = [];

    for (var i = 0; i < response.data.length; i++) {
        var item = response.data[i];
        labels.push(item.ERROR_DATE);
        data.push(item.ERROR_COUNT);
    }

    var ctx = document.getElementById('myAreaChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
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

function getCurrentTime() {
    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();
    var timeString = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0');
    return timeString;
}
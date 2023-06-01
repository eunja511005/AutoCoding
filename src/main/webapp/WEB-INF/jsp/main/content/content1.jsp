<div class="container-fluid px-4">
    <h1 class="mt-4">Charts</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
        <li class="breadcrumb-item active">Charts</li>
    </ol>
    <div class="card mb-4">
        <div class="card-body">
            Chart.js is a third party plugin that is used to generate the charts in this template. The charts below have been customized - for further customization options, please visit the official
            <a target="_blank" href="https://www.chartjs.org/docs/latest/">Chart.js documentation</a>
            .
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-chart-area me-1"></i>
            Area Chart Example
        </div>
        <div class="card-body"><canvas id="myAreaChart" width="100%" height="30"></canvas></div>
        <div class="card-footer small text-muted" id="updateTime1">Updated at <span></span></div>
    </div>
    <div class="row">
        <div class="col-lg-6">
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-chart-bar me-1"></i>
                    Bar Chart Example
                </div>
                <div class="card-body"><canvas id="myBarChart" width="100%" height="50"></canvas></div>
                <div class="card-footer small text-muted" id="updateTime2">Updated at <span></span></div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-chart-pie me-1"></i>
                    Pie Chart Example
                </div>
                <div class="card-body"><canvas id="myPieChart" width="100%" height="50"></canvas></div>
                <div class="card-footer small text-muted" id="updateTime3">Updated at <span></span></div>
            </div>
        </div>
    </div>
</div>
<script src="/assets/main/common/chart-area-demo.js"></script>
<script src="/assets/main/common/chart-bar-demo.js"></script>
<script src="/assets/main/common/chart-pie-demo.js"></script>

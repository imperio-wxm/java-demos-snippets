var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {
    title: {
        text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
        data: ['销量']
    },
    xAxis: {
        data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }]
};

axios.get('/getData')
    .then(function (response) {
        var array = response.data;
        for (var index in array) {
            console.log(index, array[index]);
        }
        option.series[0].data = response.data;
        console.log(option.series[0].data);
        console.log(response);
    })
    .catch(function (error) {
        console.log(error);
    })
    .then(function (value) {
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    });
axios.get('/getData')
    .then(function (response) {
        var str = "";
        for (var i = 0; i < response.data.length; i++) {
            str += "<div id=" + i + " style='width: 1300px; height:400px;'>" + i + "</div>";
        }
        document.body.innerHTML = str;

        for (var j = 0; j < response.data.length; j++) {
            (function () {
                var myChart = echarts.init(document.getElementById(j));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: 'ECharts 入门示例'
                    },
                    tooltip: {
                        trigger: 'axis',
                        formatter: function (params) {
                            console.log(params);
                            var text = "<strong>Time: " + params[0].name + "</strong><br/>";
                            for (var i = 0; i < params.length; i++) {
                                text += params[i].seriesName + ": " + params[i].value + " ms";
                                text += "<br/>";
                            }
                            var diff = params[1].value - params[0].value;
                            text += "<span style='color: lightpink'> 差值： " + diff + " ms</span>"
                            return text;
                        },
                        axisPointer: {
                            animation: false
                        }
                    },
                    legend: {
                        data: ['销量']
                    },
                    xAxis: {
                        data: []
                    },
                    yAxis: {},
                    series: []
                };
                option.title.text = response.data[j][0].name;
                option.xAxis.data = response.data[j][0].attr;
                for (var k = 0; k < response.data[j].length; k++) {
                    var item = {
                        name: response.data[j][k].name,
                        type: 'line',
                        data: response.data[j][k].data,
                        showSymbol: false,
                        hoverAnimation: false
                    };
                    option.series.push(item);
                }
                myChart.setOption(option);
            })(j);
        }
    })
    .catch(function (error) {
        console.log(error);
    });
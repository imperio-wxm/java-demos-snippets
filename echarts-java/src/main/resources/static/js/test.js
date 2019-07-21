axios.get('/getData')
    .then(function (response) {
        var str = "";
        for (var i = 0; i < response.data.length; i++) {
            str += "<div id=" + i + " style='width: 1340px; height:400px;'>" + i + "</div>";
            str += "<hr style='width: 100%;color: black;size:50px'/>";
        }
        document.body.innerHTML = str;

        for (var j = 0; j < response.data.length; j++) {
            (function () {
                var myChart = echarts.init(document.getElementById(j));
                var option = {
                    title: {
                        text: ''
                    },
                    tooltip: {
                        trigger: 'axis',
                        formatter: function (params) {
                            var text = "<strong>Time: " + params[0].name + "</strong><br/>";
                            if (params.length > 1) {
                                for (var i = 0; i < params.length; i++) {
                                    text += params[i].seriesName + ": " + params[i].value + " ms";
                                    text += "<br/>";
                                }
                                var diff = params[1].value - params[0].value;
                                text += "<span style='color: lightpink'> 差值： " + diff + " ms</span>";
                            } else {
                                text += params[0].seriesName + ": " + params[0].value + " ms";
                            }
                            return text;
                        },
                        axisPointer: {
                            animation: false
                        }
                    },
                    legend: {
                        data: []
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
                    option.legend.data.push(response.data[j][k].name);
                }
                myChart.setOption(option);
            })(j);
        }
    })
    .catch(function (error) {
        console.log(error);
    });
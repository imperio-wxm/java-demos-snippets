function getData(path) {
    var type = document.getElementById("type").value;
    var partDate = document.getElementById("partDate").value;
    path += "?type=" + type + "&partDate=" + partDate;
    axios.get(path)
        .then(function (response) {
            var resData = response.data;
            var i = 0;
            var str = '';
            for (var item in resData) {
                str += "<hr style='color: black;size:50px'/>" + "<h3>" + resData[item][0].taskName + "</h3>";
                str += "<div id=" + i + " style='width: 1400px; height:400px;'>" + i + "</div>";
                i++;
            }
            document.getElementById("main").innerHTML = str;

            var j = 0;
            for (var itemData in resData) {
                (function () {
                    var dataList = resData[itemData];
                    var myChart = echarts.init(document.getElementById(j));
                    var option = {
                        title: {
                            text: ''
                        },
                        tooltip: {
                            trigger: 'axis',
                            formatter: function (params) {
                                var text = "<strong>任务时间: " + params[0].name + "</strong><br/>";
                                if (params.length > 1) {
                                    for (var i = 0; i < params.length; i++) {
                                        text += params[i].seriesName + " 耗时 : " + params[i].value + " s";
                                        text += "<br/>";
                                    }
                                    var diff = params[1].value - params[0].value;
                                    text += "<span style='color: lightpink'> 差值： " + diff + " s</span>";
                                } else {
                                    text += params[0].seriesName + ": " + params[0].value + " s";
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
                    //option.title.text = dataList[0].taskName;
                    var prodName = [];
                    var prodData = [];
                    var testData = [];
                    for (var k = 0; k < dataList.length; k++) {
                        if (dataList[k].type === 'prod') {
                            prodName.push(dataList[k].time);
                            prodData.push(parseInt(dataList[k].diff) / 1000);
                        }
                    }
                    for (var l = 0; l < dataList.length; l++) {
                        if (dataList[l].type === 'test') {
                            testData.push(parseInt(dataList[l].diff) / 1000);
                        }
                    }
                    var prod = {
                        name: 'Prod',
                        type: 'line',
                        data: prodData,
                        showSymbol: false,
                        hoverAnimation: false
                    };
                    var test = {
                        name: 'Test',
                        type: 'line',
                        data: testData,
                        showSymbol: false,
                        hoverAnimation: false
                    };
                    option.xAxis.data = prodName;
                    option.series.push(prod);
                    option.series.push(test);
                    option.legend.data.push('Prod');
                    option.legend.data.push('Test');
                    myChart.setOption(option);
                    j++;
                })(j);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}
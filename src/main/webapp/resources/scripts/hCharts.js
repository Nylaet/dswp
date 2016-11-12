/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {

                $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function (data) {
                    // Create the chart
                    Highcharts.stockChart('container', {
                        rangeSelector: {
                            selected: 1
                        },
                        title: {
                            text: 'AAPL Stock Price'
                        },
                        series: [{
                                name: 'AAPL',
                                data: data,
                                tooltip: {
                                    valueDecimals: 2
                                }
                            }]
                    });
                });

            });
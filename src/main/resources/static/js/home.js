$pieChartForm = $('#pieChartForm');
var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'250px'});
var plot;

/**
 * Setup date range picker
 */
var start = moment().startOf('month');
var end = moment().endOf('month');

var $daterangepicker =  $pieChartForm.find('#daterange');
$daterangepicker.daterangepicker({
    startDate: start,
    endDate: end,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'This Year': [moment().startOf('year').startOf('month'), moment()]
    }
}, cb);

/**
 * Initialize daterangepicker with default start and end dates.
 */
function initDaterangepicker() {
    cb(start, end);
}

/**
 * Sets the given start and end dates to the range picker.
 * @param start the start date
 * @param end the end date
 */
function cb(start, end) {
    $daterangepicker.text(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
}

/**
 * Initialize pie chart.
 */
function initPieChart() {
    $('.easy-pie-chart.percentage').each(function(){
        var $box = $(this).closest('.infobox');
        var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
        var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
        var size = parseInt($(this).data('size')) || 50;
        $(this).easyPieChart({
            barColor: barColor,
            trackColor: trackColor,
            scaleColor: false,
            lineCap: 'butt',
            lineWidth: parseInt(size/10),
            animate: ace.vars['old_ie'] ? false : 1000,
            size: size
        });
    });

    var data = [];
    placeholder.data('chart', data);
    placeholder.data('draw', drawPieChart);


    //pie chart tooltip example
    var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
    var previousPoint = null;

    placeholder.on('plothover', function (event, pos, item) {
        if(item) {
            if (previousPoint != item.seriesIndex) {
                previousPoint = item.seriesIndex;
                var series = item.series;
                var percent = parseFloat(series.percent).toFixed(2);
                var tip = series['label'] + " : " + percent+'%' + ' (' + series['total'] + series['symbol'] + ')';
                $tooltip.show().children(0).text(tip);
            }
            $tooltip.css({top:pos.pageY + 10, left:pos.pageX + 10});
        } else {
            $tooltip.hide();
            previousPoint = null;
        }
    });
}


/**
 * Loads pie chart data via ajax.
 */
function loadPieChartData() {
    $.ajax({
        type : "POST",
        url : "dashboard/pieChartData",
        data: $pieChartForm.serializeArray(),
        success : function(response) {
            var result = response.result;
            var list = result.list;
            var chartData = [];

            for (var i = 0; i < list.length; i++) {
                var tr = list[i];
                var catTransaction = {};
                catTransaction.label = tr.category;
                catTransaction.data = tr.percent;
                catTransaction.total = tr.totalStr;
                catTransaction.symbol = tr.symbol;
                catTransaction.color = tr.categoryColor;
                chartData.push(catTransaction);
            }
            $('#pieChartTotal').text(result.totalStr);

            if (chartData.length > 0) {
                try {
                    drawPieChart(placeholder, chartData);
                } catch (e) {
                }
            } else {
                placeholder.empty();
            }
        }
    });
}

/**
 * Draws pie chart based on given data.
 * @param placeholder the pie chart placeholder
 * @param data the ice chart data
 * @param position the position
 */
function drawPieChart(placeholder, data, position) {
    plot = $.plot(placeholder, data, {
        series: {
            pie: {
                show: true,
                tilt:1,
                highlight: {
                    opacity: 0.25
                },
                stroke: {
                    color: '#fff',
                    width: 2
                },
                startAngle: 2
            }
        },
        legend: {
            show: true,
            container: '#legendholder',
            position: position || "ne",
            labelBoxBorderColor: null,
            margin:[-30,15],
            labelFormatter: function(label, series) {
                var percent= Math.round(series.percent);
                var number= series.data[0][1];
                return('&nbsp;<b>'+label+'</b>:&nbsp;'+ percent + '%' + ' (' + series['total'] + series['symbol'] + ')');
            }
        },
        grid: {
            hoverable: true,
            clickable: true
        }
    })
}

/**
 * Loads accounts review data.
 */
function loadAccountsOverview() {
    $.ajax({
        type : "POST",
        url : "dashboard/accountsOverview",
        data: $('#accountsForm').serializeArray(),
        success : function(response) {
            var result = response.result;
            var html = '';

            for (var i = 0; i < result.length; i++) {
                var acc = result[i];
                html += '<div class="infobox accountOverviewWidget">';
                html += '<div class="infobox-icon accountCircle" style="background-color: ' + acc.color +'";>';
                html += '<span class="fa-icon">' + acc.icon + '</span>';
                html += '</div>';
                html += '<div class="infobox-data">';
                html += '<span class="infobox-data-number">' + acc.balance + '' + acc.symbol +'</span>';
                html += '<div class="infobox-content">' + acc.name + '</div>';
                html += '</div>';
                html += '</div>';
            }
            $('#accountsBody').html(html);
        }
    });
}

/**
 * Downloads pie chart as PDF.
 */
function downloadPieChartData() {
    var canvas = plot.getCanvas();
    var src = canvas.toDataURL("image/png");
    var pdf = new jsPDF("p", "pt", "a4");
    pdf.addImage(src, 'PNG', 0, 0);
    pdf.fromHTML($('#legendholder').html(), 0, 250);
    pdf.save('transactions_chart.pdf');
}

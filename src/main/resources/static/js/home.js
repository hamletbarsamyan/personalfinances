$pieChartForm = $('#pieChartForm');

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
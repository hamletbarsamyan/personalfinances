/* Search for parameters */
var $prevSortCol;
var prevSortBy;
var prevSortClass;

var $searchForm = $('#searchForm');
var $sortBy = $searchForm.find('#sortBy');
var $asc = $searchForm.find('#asc');
var $pageSize = $('#pageSize');

/**
 * Setup date range picker
 */
var start = moment().startOf('year').startOf('month');
var end = moment().endOf('year').endOf('month');

var $daterangepicker =  $searchForm.find('#daterange');
$daterangepicker.daterangepicker({
    startDate: start,
    endDate: end,
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'This Year': [moment().startOf('year').startOf('month'), moment().endOf('year').endOf('month')]
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
 * Sets the sort by column and makes ajax call for sorting.
 * @param col the column to sort
 */
function sortBy(col) {
    var $sortCol = $(col);
    var sortBy = $sortCol.attr('sort');
    $sortBy.val(sortBy);
    var sortClass = '';
    var sortClassNew = '';

    if ($sortCol.hasClass('sorting')) {
        sortClass = 'sorting';
        sortClassNew = 'sorting_asc';
        $asc.val(true);
    } else if ($sortCol.hasClass('sorting_asc')) {
        sortClass = 'sorting_asc';
        sortClassNew = 'sorting_desc';
        $asc.val(false);
    } else if ($sortCol.hasClass('sorting_desc')) {
        sortClass = 'sorting_desc';
        sortClassNew = 'sorting_asc';
        $asc.val(true);
    }

    $sortCol.removeClass(sortClass);
    $sortCol.addClass(sortClassNew);

    if (prevSortBy && prevSortBy != sortBy) {
        $prevSortCol.removeClass(prevSortClass);
        $prevSortCol.addClass('sorting');
    }

    $prevSortCol = $sortCol;
    prevSortBy = sortBy;
    prevSortClass = sortClassNew;

    loadReminders(1);
}

/**
 * Searches using ajax method.
 */
function search() {
    if (prevSortBy) {
        $prevSortCol.removeClass(prevSortClass);
        $prevSortCol.addClass('sorting');
    }

    $prevSortCol = null;
    prevSortBy = null;
    prevSortClass = null;
    loadReminders(1);
}

/**
 * Resets search form and searches using ajax method.
 */
function resetSearch() {
    document.getElementById("searchForm").reset();

    $searchForm.find('#accountIcon').removeClass();
    $searchForm.find('#categoryIcon').removeClass();

    if (prevSortBy) {
        $prevSortCol.removeClass(prevSortClass);
        $prevSortCol.addClass('sorting');
    }

    $prevSortCol = null;
    prevSortBy = null;
    prevSortClass = null;

    $pageSize.find("option:first").prop("selected", "selected");

    initDaterangepicker();

    loadReminders(1);
}

/**
 * Toggles search form
 */
function toggleIcon() {
    $("#filterIcon").toggleClass('fa-chevron-down fa-chevron-up');
}

/**
 * Loads remidners by page.
 * @param page the page
 */
function loadReminders(page) {
    var data = $searchForm.serializeArray();
    data.push( { "name" : "page", "value" : page } );
    data.push( { "name" : "size", "value" : $pageSize.find('option:selected').val() } );

    $("#resultsBlock").html('<tr><td align="center" colspan="5"><div class="cp-spinner cp-skeleton"></div></td></tr>').load('loadReminders', data);
}

/**
 * Loads a single reminder by id into reminder list.
 * @param id the reminder id
 */
function reminderPageLoaded(id) {
    var data = $searchForm.serializeArray();
    data.push( { "name" : "page", "value" : '1' } );
    data.push( { "name" : "size", "value" : $pageSize.find('option:selected').val() } );

    if (id) {
        data.push( { "name" : "id", "value" : id} );
    }

    $("#resultsBlock").html('<tr><td align="center" colspan="5"><div class="cp-spinner cp-skeleton"></div></td></tr>').load('loadReminders', data);
}

/**
 * Loads add reminder template.
 */
function addReminder() {
    $('#remDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('add');
}

/**
 * Loads view reminder template.
 */
function viewReminder(reminderId) {
    $('#remDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('view?id=' + reminderId);
}

/**
 * Loads edit reminder template.
 */
function editReminder(reminderId) {
    $('#remDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('edit?id=' + reminderId);
}
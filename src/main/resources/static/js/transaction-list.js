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
var start = moment().startOf('month');
var end = moment().endOf('month');

var $daterangepicker =  $searchForm.find('#daterange');
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

    loadTransactions(1);
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
    loadTransactions(1);
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

    loadTransactions(1);
}

var categoriesLoaded = false;

/**
 * Loads html file containing category options.
 * @param callback
 */
function loadCategories(callback) {
    if (categoriesLoaded) {
        callback();
        return;
    }
    // categories
    var $category = $searchForm.find('#category');
    var categoryUrl = ctx + '/userhtml/' + currentUserId + '/category-lookup.html';
    $category.html('<i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>').load(categoryUrl, function () {
        callback();
    });

    categoriesLoaded = true;
}

var accountsLoaded = false;

/**
 * Loads html file containing account options.
 * @param callback
 */
function loadAccounts(callback) {
    if (accountsLoaded) {
        callback();
        return;
    }

    // accounts
    var $account = $searchForm.find('#account');
    var accountUrl = ctx + '/userhtml/' + currentUserId + '/account-lookup.html';
    $account.html('<i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>').load(accountUrl, function () {
        callback();
    });

    accountsLoaded = true;
}

var contactsLoaded = false;

/**
 * Loads html file containing contact options.
 * @param callback
 */
function loadSearchContacts(callback) {
    if (contactsLoaded) {
        callback();
        return;
    }
    // contacts
    var $contact = $searchForm.find('#contact');
    var contactUrl = ctx + '/userhtml/' + currentUserId + '/contact-lookup.html';
    $contact.html('<i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>').load(contactUrl, function () {
        callback();
    });

    contactsLoaded = true;
}

/**
 * Toggles search form
 */
function toggleSearch() {
    $("#filterIcon").toggleClass('fa-chevron-down fa-chevron-up');
    loadCategories(function(){});
    loadAccounts(function(){});
    loadSearchContacts(function(){});
}

/**
 * Loads transactions by page.
 * @param page the page
 */
function loadTransactions(page) {
    var data = $searchForm.serializeArray();
    data.push( { "name" : "page", "value" : page } );
    data.push( { "name" : "size", "value" : $pageSize.find('option:selected').val() } );

    $("#resultsBlock").html('<tr><td align="center" colspan="4"><div class="cp-spinner cp-skeleton"></div></td></tr>').load('loadTransactions', data);
}

/**
 * Loads add transaction template.
 */
function addTransaction() {
    $('#trDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('add');
}

/**
 * Loads add batch transactions template.
 */
function addTransactionBatch() {
    $('#trDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('addBatch');
}

/**
 * Loads view transaction template.
 */
function viewTransaction(transactionId) {
    $('#trDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('view?id=' + transactionId);
}

/**
 * Loads edit transaction template.
 */
function editTransaction(transactionId) {
    $('#trDialogContentDiv').html('<div class="cp-spinner cp-skeleton"></div>').load('edit?id=' + transactionId);
}
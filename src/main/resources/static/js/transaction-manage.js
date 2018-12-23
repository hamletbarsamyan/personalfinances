var $manageForm = $('#manageForm');
var $convertForm = $('#convertForm');
var isTransfer = $manageForm.find('#transfer').attr("checked");

/**
 * Initializes transaction form data.
 */
function initTransactionManage() {
    setCurrency($manageForm.find('.account:checked'));

    $manageForm.find(".numeric").number(true, 2);
    $manageForm.find(".exchangeRate").number(true, 4);

    $manageForm.find("#amount").change(function() {
        amountChanged();
    });

    $manageForm.find("#exchangeRate").change(function() {
        if (isTransfer) {
            convertWithRate();
        }
    });

    if(!ace.vars['old_ie']) $('.date-time-picker').datetimepicker({
        format: 'DD/MM/YYYY HH:mm',
        icons: {
            time: 'fa fa-clock-o',
            date: 'fa fa-calendar',
            up: 'fa fa-chevron-up',
            down: 'fa fa-chevron-down',
            previous: 'fa fa-chevron-left',
            next: 'fa fa-chevron-right',
            today: 'fa fa-arrows ',
            clear: 'fa fa-trash',
            close: 'fa fa-times'
        }
    }).next().on(ace.click_event, function(){
        $(this).prev().focus();
    });


    loadCategories(function() {
        // categories
        var $category = $manageForm.find('#category');
        $category.html($searchForm.find("#category").html());
        $category.find("option").eq(0).remove();

        if (categoryId != 0) {
            $category.find('option[value=' + categoryId + ']').attr('selected', 'selected');
        }
    });
}

/**
 * Changes transaction input
 * @param elementId the element id
 */
function checkTransactionType(elementId) {
    var $element = $manageForm.find("#" + elementId);
    $($element).prop("checked", true);

    if (elementId == 'transfer') {
        $manageForm.find('#transferDiv').show();
        isTransfer = true;
        $manageForm.find("input:radio[name=targetAccountId]:first").attr('checked', true);
        $manageForm.find("#exchangeRate").val('1');
        $manageForm.find("#convertedAmount").val($manageForm.find("#amount").val());
        $manageForm.find("#convertedCurrency").text($manageForm.find("#currency").text());
    } else {
        $manageForm.find('#transferDiv').hide();
        isTransfer = false;
    }
}

/**
 * Checks the source account and sets the converted amount for transfer type.
 * @param elementId the element id
 */
function accountChanged(elementId) {
    var $element = $manageForm.find("#" + elementId);
    $($element).prop("checked", true);
    setCurrency($element);

    if (isTransfer) {
        convert();
    }
}

/**
 * Checks the target account and sets the converted amount for transfer type.
 * @param elementId the element id
 */
function targetAccountChanged(elementId) {
    var $element = $manageForm.find("#" + elementId);
    $($element).prop("checked", true);

    var currencySymbol = $element.attr('data');
    if (currencySymbol) {
        $manageForm.find('#convertedCurrency').text(currencySymbol);
    }

    if (isTransfer) {
        convert();
    }
}

/**
 * Sets the currency symbol based on account radio.
 * @param radio the account radion
 */
function setCurrency(radio) {
    var currencySymbol = $(radio).attr('data');
    if (currencySymbol) {
        $manageForm.find('#currency').text(currencySymbol);
    }
}

var contcatsLoaded = false;

/**
 * Loads the contacts
 * @param contactId the contact to be selected
 */
function loadContacts(contactId) {
    if (!contcatsLoaded) {
        loadSearchContacts(function(){
            var $contact = $manageForm.find('#contact');
            $contact.html($searchForm.find("#contact").html());
            if (contactId != 0) {
                $contact.find('option[value=' + contactId + ']').attr('selected', 'selected');
            }
        });

        contcatsLoaded = true;
    }
}

/**
 * Saves transaction data via ajax post.
 */
function saveTransaction() {
    var amount = $manageForm.find('#amount').val();

    if (!amount || amount <= 0) {
        return;
    }

    $.ajax({
        type: "POST",
        url: "save",
        data: $manageForm.serialize(),
        success: function(data) {
            $('#trDialog').modal('hide');
            loadTransactions(1);
        }
    });
}

/**
 * Sets converted amount for transfer type.
 */
function amountChanged() {
    if (isTransfer) {
        convert();
    }
}

/**
 * Sets converted amount for transfer type.
 */
function convert() {
    var amount = $manageForm.find('#amount').val();

    if (!amount || amount <= 0) {
        return;
    }

    $convertForm.find('#convertAmount').val(amount);
    $convertForm.find('#from').val($manageForm.find('input[name="accountId"]:checked').attr('currency'));
    $convertForm.find('#to').val($manageForm.find('input[name="targetAccountId"]:checked').attr('currency'));

    $.ajax({
        type : "POST",
        url : "../currency/convert",
        data: $convertForm.serialize(),
        success : function(response) {
            $manageForm.find("#exchangeRate").val(response.result.key);
            $manageForm.find("#convertedAmount").val(response.result.value);
        }
    });
}

/**
 * Sets converted amount for transfer type.
 */
function convertWithRate() {
    $convertForm.find('#convertAmount').val($manageForm.find('#amount').val());
    $convertForm.find('#convertRate').val($manageForm.find('#exchangeRate').val());

    $.ajax({
        type : "POST",
        url : "../currency/convertWithRate",
        data: $convertForm.serialize(),
        success : function(response) {
            $manageForm.find("#convertedAmount").val(response.result);
        }
    });
}
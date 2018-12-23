/**
 * Returns the url parameter value by name.
 * @param name the url parameter name
 * @returns the url parameter value
 */
$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
        return null;
    }
    else{
        return results[1] || 0;
    }
};

/**
 * Loads reminders for current user id.
 */
function loadReminderAlerts() {
    try {
        var url = ctx + '/userhtml/' + currentUserId + '/reminder-alert.html';
        $('#reminderAlerts').html('<i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>').load(url, "",
            function(responseText, textStatus, XMLHttpRequest) {
                if(textStatus === 'error') {
                    $('#reminderAlerts').html('');
                }
            });
    } catch (e) {
    }
}
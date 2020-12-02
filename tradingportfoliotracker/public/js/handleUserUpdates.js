jQuery(document).ready(function($){
    /* Handles user settings update */
    $("#btn-update-settings").click(function (e) {
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: "update",
            data: $('#update-settings-form').serialize(),
            success: function (response) {
                $('#updateSettingsMessageContainer').html("");
                $('#updateSettingsMessageContainer').append("<div class='row mt-2'><div class='col-12'>Settings have been updated successfully</div></div>");
                $('#updateSettingsMessageContainer').css('color', "green");
            },
            error: function (data) {
                $('#updateSettingsMessageContainer').html("");
                // you can loop through the errors object and show it to the user
                $.each(data.responseJSON.errors, function ( key, value) {
                    $('#updateSettingsMessageContainer').append("<div class='row mt-2'><div class='col-12'>" + value[0] + "</div></div>");
                });
                $('#updateSettingsMessageContainer').css('color', "red");
            }
        });
    });
});
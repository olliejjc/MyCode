jQuery(document).ready(function($){
    /* Handles the form for adding a new trade */
    $("#btn-new-trade").click(function (e) {
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        var formData = new FormData(($('#trade-add-form').get(0)));
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: "newtrades",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                console.log(response);
                $('#addedTradeMessageContainer').html("");
                $('#addedTradeMessageContainer').append("<div class='row mt-2'><div class='col-12'>Trade has been added successfully</div></div>");
                $('#addedTradeMessageContainer').css('color', "green");
            },
            error: function (data) {
                console.log(data);
                $('#addedTradeMessageContainer').html("");
                $.each(data.responseJSON.errors, function ( key, value) {
                    $('#addedTradeMessageContainer').append("<div class='row mt-2'><div class='col-12'>" + value[0] + "</div></div>");
                });
                $('#addedTradeMessageContainer').css('color', "red");
            }
        });
    });
});
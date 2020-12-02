jQuery(document).ready(function($){
    /* Handles the form for calculating the risk of a trade */
    $("#btn-calculate-size").click(function (e) {
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: "calculate",
            data: $('#risk-calculator-form').serialize(),
            dataType: 'JSON',
            success: function (response) {
                var maxSharesToPurchase = response.maxSharesToPurchase;
                var positionSize = response.positionSize;
                var riskOfPosition = response.riskOfPosition;
                document.getElementById("max_shares_purchase").value = maxSharesToPurchase;
                document.getElementById("position_size").value = "$" + positionSize;
                document.getElementById("risk_of_position").value = "$" + riskOfPosition;
            },
            error: function (data) {
                $('#errorMessageCalculatorContainer').html("");
                console.log(data);
                /* you can loop through the errors object and show it to the user */
                $.each(data.responseJSON.errors, function ( key, value) {
                    $('#errorMessageCalculatorContainer').append("<div class='row mt-2'><div class='col-12'>" + value[0] + "</div></div>");
                });
            }
        });
    });
});
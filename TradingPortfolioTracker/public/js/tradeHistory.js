jQuery(document).ready(function($){
    jQuery(document).on('change','#selectmonth',function () {
        getTradesWithDateMatchingSelected();
        getTradeMonthlyTotals();
    });

    jQuery(document).on('change','#selectyear',function () {
        getTradesWithDateMatchingSelected();
        getTradeMonthlyTotals();
    });

    /* Gets all trades that match a specific month and year */
    function getTradesWithDateMatchingSelected(){
        $('#deletedTradeMessage').html("");
        $('#closedTradeMessageContainer').html("");
        var month = $('#selectmonth').find(":selected").text();
        var year = $('#selectyear').find(":selected").text();
        if(year == "Year" || month == "Month"){
            month = document.getElementById("tradeReportMonthIdentifier").innerHTML;
            year = document.getElementById("tradeReportYearIdentifier").innerHTML;
        }
        $.ajax({
            type: 'GET',
            url: '/changeMonthUpdateTradesDisplayed',
            data: {
                'month':month,
                'year':year,
            },
            dataType: 'JSON',
            success: function (data) {
                var tradeMonth = data[0];
                var tradeYear = data[1];
                var tradesWithMatchingDate = data[2];
                generateUpdatedTradeHistory(tradeMonth, tradeYear, tradesWithMatchingDate);
            },
            error: function (data) {
                console.log(data);
            }
        });
        
    }

    /* Get the total monthly balance and monthly profit loss*/
    function getTradeMonthlyTotals(){
        var month = $('#selectmonth').find(":selected").text();
        var year = $('#selectyear').find(":selected").text();
        if(year == "Year" || month == "Month"){
            month = document.getElementById("tradeReportMonthIdentifier").innerHTML;
            year = document.getElementById("tradeReportYearIdentifier").innerHTML;
        }
        $.ajax({
            type: 'GET',
            url: '/calculateMonthlyTotals',
            data: {
                'month':month,
                'year':year,
            },
            dataType: 'JSON',
            success: function (data) {
                var monthlyBalance = data[0];
                var monthlyProfitLoss = data[1];
                generateUpdatedTradeHistoryTotals(monthlyBalance, monthlyProfitLoss);
            },
            error: function (data) {
                console.log(data);
            }
        });

    }

    /* Handles closing an open trade*/
    $(document).on('click', '.closeActionButton', function(e) {
        var tradeRowID = $(this).parent().parent().attr('id');
        var id = tradeRowID.replace( /^\D+/g, '');
        /* Adds a hidden trade row identifier*/
        $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+id).append("<input name=tradeRowClosedIdentifier value=" + id + " hidden></input>");
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        var formData = new FormData(($('#closeTradeForm').get(0)));
        $.ajax({
            type: 'POST',
            url: "closetrades",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                getTradesWithDateMatchingSelected();
                getTradeMonthlyTotals();
                $('#closedTradeMessageContainer').html("");
                var result = "Trade has been closed successfully";
                $('#closedTradeMessageContainer').append("<div class='row mt-2'><div class='col-12'>" + result + "</div></div>");
                $('#closedTradeMessageContainer').css('color', "green");
            },
            error: function (data) {
                $('#closedTradeMessageContainer').html("");
                $.each(data.responseJSON.errors, function ( key, value) {
                    $('#closedTradeMessageContainer').append("<div class='row mt-2'><div class='col-12'>" + value[0] + "</div></div>");
                });
                $('#closedTradeMessageContainer').css('color', "red");
            }
        });
    });

    /* Handles deleting a trade */
    $(document).on('click', '.deleteActionButton', function(e) {
        var tradeRowID = $(this).parent().parent().attr('id');
        var id = tradeRowID.replace( /^\D+/g, ''); // replace all leading non-digits with nothing
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        $.ajax({
            url: "/deletetrades/"+id,
            type: 'DELETE',
            dataType: 'JSON',
            data:{
                'id': id,
            },
            success: function (response) {
                getTradesWithDateMatchingSelected();
                getTradeMonthlyTotals();
                $('#closedTradeMessageContainer').html("");
                var result = "Trade has been deleted successfully";
                $("#tradeHistoryTable #tradeHistoryTableBody #tradeRow"+id).remove();
                $('#deletedTradeMessage').html(result);
                $('#deletedTradeMessage').css('color', "green");
            },
            error: function (data) {
                $('#closedTradeMessageContainer').html("");
                var result = "Trade has not been deleted successfully, error occurred";
                $('#deletedTradeMessage').html(result);
                $('#deletedTradeMessage').css('color', "red");
            }
        });
    });

    /* Handles adding a new screenshot from the add screenshot modal */
    $(document).on('click', '#btn-new-screenshot', function(e) {
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
            }
        });
        var formData = new FormData(($('#uploadScreenshotForm').get(0)));
        var tradeID = $('#tradeRowHiddenIdentifier').text();

        $.ajax({
            type: 'POST',
            url: "tradehistory",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                $('#closedTradeMessageContainer').html("");
                var result = "Screenshots have been added successfully";
                $('#addedScreenshotMessage').html(result);
                $('#addedScreenshotMessage').css('color', "green");
                $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeID + " .span1").html("");
                $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeID + " .span1").append("<input type=image id=screenshotTradeID" + tradeID + " class=screenshotImage src=" + assetBaseUrl + "image/screenshot.png data-toggle=modal data-target=#screenshotDisplayModal>");
                
            },
            error: function (data) {
                $('#closedTradeMessageContainer').html("");
                var result = "Screenshots have not been added successfully, error occurred";
                $('#addedScreenshotMessage').html(result);
                $('#addedScreenshotMessage').css('color', "red");
            }
        });
    });

    /* Shows the display modal to view screenshots in trade history */
    $('#screenshotDisplayModal').on('show.bs.modal',function(event){
        var tradeRowID = event.relatedTarget.id;
        var id = tradeRowID.replace( /^\D+/g, '');
        generateScreenshotDisplayModal(id);
    });

    /* Shows the modal to upload screenshots in trade history */
    $('#screenshotUploadModal').on('show.bs.modal',function(event){
        var tradeRowID = event.relatedTarget.id;
        var id = tradeRowID.replace( /^\D+/g, '');
        $('#tradeRowHiddenIdentifier').text(id);
        generateScreenshotUploadModal();
    });
});

function generateScreenshotUploadModal(){
    document.getElementById("modalBodyScreenshotUpload").innerHTML = "";
    document.getElementById("modalFooterScreenshotUpload").innerHTML = "";
    $('#modalBodyScreenshotUpload').append("<form action='javascript:void(0)' id='uploadScreenshotForm' method='POST' enctype='multipart/form-data' files='true'><input type='hidden' name='csrfmiddlewaretoken' value=" + $('meta[name="csrf-token"]').attr('content') + "><input class='form-control' type='file' id='screenshots' name='screenshots[]' multiple><input class='classRowIdentifierUploadScreenshot' id='rowIdentifier' name='rowIdentifier' hidden></input>");
    $('#modalFooterScreenshotUpload').append("<span id='addedScreenshotMessage'></span><button class='btn btn-style-1 btn-primary' type='submit' id='btn-new-screenshot'>Upload Screenshots</button></form>");
    var tradeID = $('#tradeRowHiddenIdentifier').text();
    $('.classRowIdentifierUploadScreenshot').val(tradeID);
}

function generateScreenshotDisplayModal(id){
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
        }
    });
    $.ajax({
        url: "/tradescreenshots/"+id,
        type: 'GET',
        dataType: 'JSON',
        data:{
            'id': id,
        },
        success: function (response) {
            document.getElementById("modalBodyScreenshotDisplay").innerHTML = "";
            document.getElementById("modalFooterScreenshotDisplay").innerHTML = "";
            /* Adds a carousel to the screenshot display modal */
            $('#modalBodyScreenshotDisplay').append("<div id='carouselExample' class='carousel slide' data-ride='carousel'><div class='carousel-inner' id='carousel-inner-screenshotupload'></div></div>");
            /* Loops through all the screenshots associated with a trade and adds them to the carousel */
            for(var i = 0; i < response.length; i++){
                if(i == 0){
                    $('#carousel-inner-screenshotupload').append("<div class='carousel-item active'><img class='d-block w-100' src=/image/" + response[i] + "></div>");
                }
                else{
                    $('#carousel-inner-screenshotupload').append("<div class='carousel-item'><img class='d-block w-100' src=/image/" + response[i] + "></div>");
                }
            }
            $('#modalFooterScreenshotDisplay').append("<div class='col-sm'></div><div class='col-sm d-flex justify-content-center'><span id='numbertext'>1/" + (response.length) + "</span></div><div class='col-sm d-flex justify-content-end'><button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button></div>");
            /* If there is more than one screenshot associated with a trade than add previous and next buttons to view different screenshots */
            if(response.length>1){
                $('#carousel-inner-screenshotupload').append("<a class='carousel-control-prev' href='#carouselExample' role='button' data-slide='prev'><span class='carousel-control-prev-icon' aria-hidden='true'></span><span class='sr-only'>Previous</span></a>");
                $('#carousel-inner-screenshotupload').append("<a class='carousel-control-next' href='#carouselExample' role='button' data-slide='next'><span class='carousel-control-next-icon' aria-hidden='true'></span><span class='sr-only'>Next</span></a>");
            }
            
            /* Displays the screenshot you are currently looking at and the amount you can scroll through */
            $('#carouselExample').on('slid.bs.carousel', function() {
                var totalItems = $('.carousel-item').length;
                var currentIndex = $('div.active').index() + 1;
                $('#numbertext').html(''+currentIndex+'/'+totalItems+'');
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
}

function generateUpdatedTradeHistory(tradeMonth, tradeYear, tradesWithMatchingDate){
    document.getElementById("tradeReportDateIdentifier").innerHTML = "";
    $("#tradeHistoryTable #tradeHistoryTableBody tr").remove();
    document.getElementById("noReportDisplay").innerHTML = "";
    /* Check if there is no trade history available for a specific month */
    if (!Array.isArray(tradesWithMatchingDate) || !tradesWithMatchingDate.length) {
        $('#noReportDisplay').append("<div class=col-lg-12><h3>There is no trade history for the chosen month, please select a different one</h3></div>");
    }
    else{
        document.getElementById("tradeReportDateIdentifier").innerHTML = tradeMonth + " " + tradeYear + " Report";
        /* Loop through all trades that are associated with a specific month and year */
        for(i = 0; i < tradesWithMatchingDate.length; i++){ 
            var trade = tradesWithMatchingDate[i];
            var tradeIdentifier = trade.id;
            $('#tradeHistoryTable #tradeHistoryTableBody').append("<tr id=tradeRow" + tradeIdentifier + "></tr>");
            var closedTradeColumnsSet = true;
            /* Loops through the data associated with a specific trade */
            Object.keys(trade).forEach(function(k){
                if(k != "id" && k!="created_at" && k!="updated_at" && k!="symbol" && k!="trade_opened" && k!="user_id"){
                    /* Handles attributes of a trade that have a set value and generates columns for each */
                    if(trade[k]!=null){
                        /* Only displays the screenshots display button if screenshots exist*/
                        if(k == "has_screenshots" && trade[k] == 1){
                            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td class=screenshotColumn><div class=span1><input type=image id=screenshotTradeID" + tradeIdentifier + " class=screenshotImage src=" + assetBaseUrl + "image/screenshot.png data-toggle=modal data-target=#screenshotDisplayModal></div><div class=span2><button type='button' class='addNewScreenshotButton' id='uploadScreenshotTradeID" + tradeIdentifier + "' data-toggle='modal' data-target='#screenshotUploadModal'><span aria-hidden='true'>&#43;</span></button></div></td>");
                        }
                        else if(k == "has_screenshots" && trade[k] != 1){
                            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td class=screenshotColumn><div class=span1></div><div class=span2><button type='button' class='addNewScreenshotButton' id='uploadScreenshotTradeID" + tradeIdentifier + "' data-toggle='modal' data-target='#screenshotUploadModal'><span aria-hidden='true'>&#43;</span></button></div></td>");
                        }
                        else{
                            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td> " + trade[k] + "</td>");
                        }
                    }
                    /* Certain attributes of a trade won't be set on creation of a trade such as date_trade_closed and price_closed_at and need to be handled seperately. They are generated as
                       inputs to allow the user to submit data required to close the trade */
                    else{
                        closedTradeColumnsSet = false;
                        if(k == "date_trade_closed"){
                            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td><input type='date' id=" + k + "_id_" + tradeIdentifier + " name=" + k + "_id_" + tradeIdentifier + "></input></td>");
                        }
                        else{
                            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td><input id=" + k + "_id_" + tradeIdentifier + " name=" + k + "_id_" + tradeIdentifier + "></input></td>");
                        }
                    }
                }
            });
            /* Handles a closed trade, if its closed status will be "Closed". If trade is open it will show the close trade button */
            if(closedTradeColumnsSet){
                $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td class=closeActionColumn><span aria-hidden=true>Closed</span></td>");
            }
            else{
                $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td class=closeActionColumn><button type=button class=closeActionButton><span aria-hidden=true>&times;</span></button></td>");
            }
            $('#tradeHistoryTable #tradeHistoryTableBody #tradeRow'+tradeIdentifier).append("<td class=deleteActionColumn><button type=button class=deleteActionButton><span aria-hidden=true>&times;</span></button></td>");
        }
    }
}

/* Builds the display of total monthly balance and monthly profit and loss for the trade month selected */
function generateUpdatedTradeHistoryTotals(monthlyBalance, monthlyProfitLoss){
    document.getElementById("tradeHistoryTotalsTableBody").innerHTML = "";
    $('#tradeHistoryTotalsTableBody').append("<tr><td>$" + monthlyBalance + "</td><td>$" + monthlyProfitLoss + "</td></tr>");
}
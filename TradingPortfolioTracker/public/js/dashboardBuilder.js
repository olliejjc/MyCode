var chart;

jQuery(document).ready(function($){
    var timePeriod = $('#selecttimeperiod').find(":selected").text();
    var yearPeriod = $('#selectyearperiod').find(":selected").text();
    generatePortfolioChart(timePeriod, yearPeriod);

    /* handles switch between portfolio holdings view and portfolio performance view */
    jQuery(document).on('change','#selectPortfolioView',function () {
        var dashboardDisplayChoice = $('#selectPortfolioView').find(":selected").text()
        if(dashboardDisplayChoice !== "Portfolio Performance"){
            var oldcanv = document.getElementById('myChart');
            $('#timePeriodSelectContainer').hide();
            $('#yearPeriodSelectContainer').hide();
            $('#portfolioHoldingsContainer').show();
            $('#dashboardContentContainer').hide();
            generatePortfolioHoldings();
        }
        else{
            $('#timePeriodSelectContainer').show();
            $('#yearPeriodSelectContainer').show();
            $('#portfolioHoldingsContainer').hide();
            $('#dashboardContentContainer').show();
            $('#portfolioHoldingsTable #portfolioHoldingsTableBody').html("");
            $('#portfolioHoldingsTable thead tr').html("");
        }
    });

    /* handles changing between different time periods when viewing portfolio performance */
    jQuery(document).on('change','#selecttimeperiod',function () {
        var timePeriod = $('#selecttimeperiod').find(":selected").text();;
        var labels = getLabels(timePeriod);
        var dataSet = getChartDataSet(timePeriod, yearPeriod);
        var suggestedMinYAxis = getSuggestedMinYAxis(dataSet);
        var suggestedMaxYAxis = getSuggestedMaxYAxis(dataSet);
        updatePortfolioChart(labels, dataSet, suggestedMinYAxis, suggestedMaxYAxis);
        if(timePeriod != "Yearly View"){
            $('#yearPeriodSelectContainer').hide();
        }
        else{
            $('#yearPeriodSelectContainer').show();
        }
    });
    /* handles changing between different years when viewing portfolio performance */
    jQuery(document).on('change','#selectyearperiod',function () {
        var timePeriod = $('#selecttimeperiod').find(":selected").text();
        var yearPeriod = $('#selectyearperiod').find(":selected").text();
        var labels = getLabels(timePeriod);
        var dataSet = getChartDataSet(timePeriod, yearPeriod);
        var suggestedMinYAxis = getSuggestedMinYAxis(dataSet);
        var suggestedMaxYAxis = getSuggestedMaxYAxis(dataSet);
        updatePortfolioChart(labels, dataSet, suggestedMinYAxis, suggestedMaxYAxis);
    });
});


function generatePortfolioChart(timePeriod, yearPeriod){

    var ctx = document.getElementById('myChart').getContext('2d');
    var dataSet = getChartDataSet(timePeriod, yearPeriod);
    var suggestedMinYAxis = getSuggestedMinYAxis(dataSet);
    var suggestedMaxYAxis = getSuggestedMaxYAxis(dataSet);
    console.log(dataSet);
    var labels = getLabels(timePeriod);
    chart = new Chart(ctx, {
        /* The type of chart we want to create */
        type: 'line',

        /* The data for our dataset */
        data: {
            labels: labels,
            datasets: [{
                label: 'Portfolio ($)',
                backgroundColor: 'aliceblue',
                borderColor: 'steelblue',
                data: dataSet
            }]
        },

        options: {
            maintainAspectRatio: false,
            scales: {
                yAxes: [{
                    display: true,
                    ticks: {
                        suggestedMin: suggestedMinYAxis,
                        beginAtZero: true,
                        suggestedMax:suggestedMaxYAxis
                    }
                }]
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            }
        }
    });
}

/* update existing portfolio chart with new labels and dataset */
function updatePortfolioChart(labels, dataSet, suggestedMinYAxis, suggestedMaxYAxis){
    chart.destroy();
    var ctx = document.getElementById('myChart').getContext('2d');
    var chartData = {
        labels: labels,
        datasets: [{
            label: 'Portfolio ($)',
            backgroundColor: 'aliceblue',
            borderColor: 'steelblue',
            data: dataSet
        }]
    };

    var chartOptions = {
        maintainAspectRatio: false,
        scales: {
            yAxes: [{
                display: true,
                ticks: {
                    suggestedMin: suggestedMinYAxis,
                    beginAtZero: true,
                    suggestedMax:suggestedMaxYAxis
                }
            }]
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        }
    };

    chart = new Chart(ctx, { type: 'line', data: chartData, options: chartOptions });
}

function generatePortfolioHoldings(){
    $.ajax({
        type: 'GET',
        url: '/trades',
        dataType: 'JSON',
        success: function (data) {
            var tradesOpened = false;
            /* Displays all open trades which is equivalent to live portfolio holdings */
            $.each(data, function(i, item) {
                if(data[i].trade_opened == true){
                    console.log(tradesOpened);
                    if(tradesOpened == false){
                        $('#portfolioHoldingsTable thead tr').append("<th>Holding Name</th><th>Price Purchased At</th><th>Trade Size</th><th>Trade Value</th><th>Date Trade Opened</th>");
                    }
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody').append("<tr id=portfolioHoldingsRow" + data[i].id + "></tr>");
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody #portfolioHoldingsRow' + data[i].id).append("<td>" + data[i].holding_name + "</td>");
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody #portfolioHoldingsRow' + data[i].id).append("<td>$" + data[i].price_purchased_at + "</td>");
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody #portfolioHoldingsRow' + data[i].id).append("<td>" + data[i].trade_size + "</td>");
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody #portfolioHoldingsRow' + data[i].id).append("<td>$" + data[i].trade_value + "</td>");
                    $('#portfolioHoldingsTable #portfolioHoldingsTableBody #portfolioHoldingsRow' + data[i].id).append("<td>" + data[i].date_trade_opened + "</td>");
                    tradesOpened = true;
                }
            });
            if(tradesOpened == false){
                $('#portfolioHoldingsContainer').append("<h2>No Currently Open Trades</h2>");
            }                     
        },
        error: function (data) {
            console.log(data);
        }
    });
    
}

/* Generate chart data set for specific time period */
function getChartDataSet(timePeriod, yearPeriod){
    var chartDataSetString;

    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content')
        }
    });

    $.ajax({
        type: 'POST',
        url: '/generateTradeChartDataSets',
        data: {
            'timePeriod':timePeriod,
            'yearPeriod':yearPeriod
        },
        dataType: 'JSON',
        async: false,
        success: function (chartDataSet) {
            //chartDataSetString = chartDataSet.map(String);
            chartDataSetString = chartDataSet;
        },
        error: function (data) {
            console.log(data);
        }
    });
    return chartDataSetString;
}

/* Generate chart labels for different time periods */
function getLabels(timePeriod){
    var date = new Date();
    const formatter = new Intl.DateTimeFormat('en', { month: 'long' });

    if(timePeriod == "3 Month View"){
        date.setMonth(date.getMonth()-2);
        var month1 = formatter.format(date);

        date.setMonth(date.getMonth()+1);
        var month2 = formatter.format(date);

        date.setMonth(date.getMonth()+1);
        var month3 = formatter.format(date);

        return [month1, month2, month3];
    }
    else if(timePeriod == "6 Month View"){
        date.setMonth(date.getMonth()-5);
        var month1 = formatter.format(date);
        date.setMonth(date.getMonth()+1);
        var month2 = formatter.format(date);
        date.setMonth(date.getMonth()+1);
        var month3 = formatter.format(date);
        date.setMonth(date.getMonth()+1);
        var month4 = formatter.format(date);
        date.setMonth(date.getMonth()+1);
        var month5 = formatter.format(date);
        date.setMonth(date.getMonth()+1);
        var month6 = formatter.format(date);
        return [month1, month2, month3, month4, month5, month6];
    }
    else if(timePeriod == "Yearly View"){
        return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    }
    else{
        var yearsWithTrades = getYearsWithTrades();
        return yearsWithTrades;
    }
}

function getSuggestedMinYAxis(dataset){
    var smallestValue = 0;
    for (var i = 0; i < dataset.length; i++) {
        if(dataset[i] < smallestValue){
            smallestValue = dataset [i];
        }
    }
    if(smallestValue > 0){
        var maxYAxis = 0;
        return maxYAxis;
    }
    else{
        var maxYAxis = smallestValue * 2;
        return maxYAxis;
    }
}

function getSuggestedMaxYAxis(dataset){
    var largestValue = 0;
    for (var i = 0; i < dataset.length; i++) {
        if(dataset[i] > largestValue){
            largestValue = dataset [i];
        }
    }
    var maxYAxis = largestValue * 2;
    return maxYAxis;
}

/* Generates a list of years that trades have been made in */
function getYearsWithTrades(){
    var yearsWithTradesStringArray;
    $.ajax({
        type: 'GET',
        url: '/tradeYearsWithTrades',
        dataType: 'JSON',
        async: false,
        success: function (yearsWithTrades) {
            yearsWithTradesStringArray = yearsWithTrades.map(String);
        },
        error: function (data) {
            console.log(data);
        }
    });
    return yearsWithTradesStringArray;
}
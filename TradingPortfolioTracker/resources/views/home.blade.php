@extends('layouts.panelLayout')

@section('content')
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-4">
                        <h2 class="mt-4">Dashboard</h2>
                        <select class="form-control mt-4" id="selectPortfolioView" data-width="120px">
                            <option id="portfolioChartView" selected="selected" value="0"><h2 class="mt-4">Portfolio Performance</h2></option>
                            <option id="portfolioHoldingsView" value="1"><h2 class="mt-4">Portfolio Holdings</h2></option>
                        </select>
                    </div>
                    <div class="col-lg-8"></div>
                </div>
            </div>
            <div class="h-75 container-fluid" id="dashboardBodyContainer">
                <div class="row mt-5">
                    <div id="timePeriodSelectContainer" class="col-lg-2">
                        <select class="form-control" id="selecttimeperiod" data-width="120px">
                            <option id="3monthsChartView" value="3">3 Month View</option>
                            <option id="6monthsChartView" value="6">6 Month View</option>
                            <option id="optionTitle" selected="selected">Yearly View</option>
                            <option id="overallChartView" value="0">Overall View</option>
                        </select>
                    </div>
                    <div id="yearPeriodSelectContainer" class="col-lg-2">
                        @if(!empty($listOfTradeYears))
                        <select class="form-control" id="selectyearperiod" data-width="100px">
                            @foreach($listOfTradeYears as $tradeYearSelect)
                                <option class="tradeYear" value="">{{ $tradeYearSelect }}</option>
                            @endforeach 
                        </select>
                        @endif
                    </div>
                    <div class="col-lg-8">
                    </div>
                </div>
                <div id="dashboardContentContainer">
                    <canvas id="myChart"></canvas>
                </div>
                <div id="portfolioHoldingsContainer">
                    <table id="portfolioHoldingsTable" class="table table-bordered">
                        <thead>
                            <tr>
                            </tr>
                        </thead>
                        <tbody id="portfolioHoldingsTableBody">
                        </tbody>
                    </table>
                    <h2 id="noOpenTradesIdentifier"></h2>
                </div>
            </div>
        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright &copy; Oliver Campion</div>
                </div>
            </div>
        </footer>
    </div>
@endsection

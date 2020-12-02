@extends('layouts.panelLayout')

@section('content')
<div id="layoutSidenav_content">
    <main class="h-75">
        <!-- Modal -->
        <div class="container-fluid">
            <h1 class="mt-4">Trade History</h1>
        </div>
        <div class="h-75 container-fluid">
            @if(isset($listOfTradeYears))
                <div class="row mt-5">
                    <div class="col-lg-2">
                        <select class="form-control" id="selectmonth" data-width="120px">
                            <option class="optionTitle" selected="selected" disabled="disabled">Month</option>
                            <option class="tradeMonth" value="01">January</option>
                            <option class="tradeMonth" value="02">February</option>
                            <option class="tradeMonth" value="03">March</option>
                            <option class="tradeMonth" value="04">April</option>
                            <option class="tradeMonth" value="05">May</option>
                            <option class="tradeMonth" value="06">June</option>
                            <option class="tradeMonth" value="07">July</option>
                            <option class="tradeMonth" value="08">August</option>
                            <option class="tradeMonth" value="09">September</option>
                            <option class="tradeMonth" value="10">October</option>
                            <option class="tradeMonth" value="11">November</option>
                            <option class="tradeMonth" value="12">December</option>
                        </select>
                    </div>
                    <div class="col-lg-2">
                        {{-- Displays years with trades you can select from --}}
                        <select class="form-control" id="selectyear" data-width="100px">
                            <option class="optionTitle" selected="selected" disabled="disabled">Year</option>
                            @foreach($listOfTradeYears as $tradeYearSelect)
                                <option class="tradeYear" value="{{ $tradeYear }}">{{ $tradeYearSelect }}</option>
                            @endforeach
                        </select>
                    </div>
                    <div class="col-lg-8">
                    </div>
                </div>
            @endif
            {{-- Only displays trade report if trade history for that month and year is available --}}
            @if(isset($tradeMonth) && isset($tradeYear))
            <div class="row mt-5">
                <div class="col-lg-4">
                    <h2 id="tradeReportDateIdentifier"><span id="tradeReportMonthIdentifier">{{$tradeMonth}}</span> <span id="tradeReportYearIdentifier">{{$tradeYear}}</span> Report</h2>
                </div>
                <div class="col-lg-8">
                </div>
            </div>
            @else
            <div class="row mt-5">
                <div class="col-lg-4">
                    <h2 id="tradeReportDateIdentifier">No Trade History Available</h2>
                </div>
                <div class="col-lg-8">
                </div>
            </div>
            @endif
            <div id="noReportDisplay" class="row">
            </div>
            @if(isset($trades))
            <div class="row mt-5">
                <!-- Profile Settings-->
                <div class="col-lg-12 pb-5">
                    <form action='javascript:void(0)' id='closeTradeForm' method='POST' enctype='multipart/form-data' files='true'>
                    @csrf
                        <div class="table-responsive-sm">
                            <table id="tradeHistoryTable" class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Holding Name</th>
                                        <th>Price Purchased At</th>
                                        <th>Trade Size</th>
                                        <th>Trade Value</th>
                                        <th>Date Trade Opened</th>
                                        <th>Date Trade Closed</th>
                                        <th>Price Closed At</th>
                                        <th>Profit/Loss of Trade</th>
                                        <th>Screenshots</th>
                                        <th id="closeTradeHeader">Close Trade</th>
                                        <th id="deleteTradeHeader">Delete Trade</th>
                                    </tr>
                                </thead>
                                <tbody id="tradeHistoryTableBody">
                                    {{-- Loops through each trade in a specific month and year and displays the values of each trade attribute --}}
                                    @foreach($trades as $trade)
                                    <tr id="tradeRow{{$trade->id}}">
                                        <td>{{$trade->holding_name}}</td>
                                        <td>${{$trade->price_purchased_at}}</td>
                                        <td>{{$trade->trade_size}}</td>
                                        <td>${{$trade->trade_value}}</td>
                                        <td>{{$trade->date_trade_opened}}</td>
                                        @if($trade->date_trade_closed != null && $trade->price_closed_at != null && $trade->profit_loss != null)
                                            <td>{{$trade->date_trade_closed}}</td>
                                            <td>{{$trade->price_closed_at}}</td>
                                            <td>{{$trade->profit_loss}}</td>
                                        @else
                                            <td><input type="date" id="date_trade_closed_id_{{$trade->id}}" name="date_trade_closed_id_{{$trade->id}}"></input></td>
                                            <td><input id="price_closed_at_id_{{$trade->id}}" name="price_closed_at_id_{{$trade->id}}"></input></td>
                                            <td><input id="profit_loss_id_{{$trade->id}}" name="profit_loss_id_{{$trade->id}}"></input></td>
                                        @endif
                                        {{-- Only shows screeenshot display button if screenshots exist --}}
                                        @if($trade->has_screenshots == 1)
                                            <td class="screenshotColumn"><div class="span1"><input type="image" id="screenshotTradeID{{$trade->id}}" class="screenshotImage" src="{{URL::asset('/image/screenshot.png')}}" data-toggle="modal" data-target="#screenshotDisplayModal"></div><div class="span2"><button type="button" class="addNewScreenshotButton" id="uploadScreenshotTradeID{{$trade->id}}" data-toggle="modal" data-target="#screenshotUploadModal"><span aria-hidden="true">&#43;</span></button></div></td>
                                        @else
                                            <td class="screenshotColumn"><div class="span1"></div><div class="span2"><button type="button" class="addNewScreenshotButton" id="uploadScreenshotTradeID{{$trade->id}}" data-toggle="modal" data-target="#screenshotUploadModal"><span aria-hidden="true">&#43;</span></button></div></td>
                                        @endif
                                        <td class="closeActionColumn">
                                            {{-- Only show close trade button if trade is not closed --}}
                                            @if($trade->date_trade_closed == null && $trade->priced_closed_at == null && $trade->profit_loss == null)
                                                <button type="button" class="closeActionButton"><span aria-hidden="true">&times;</span></button>
                                            @else
                                                <span aria-hidden="true">Closed</span>
                                            @endif
                                        </td>
                                        <td class="deleteActionColumn"><button type="button" class="deleteActionButton"><span aria-hidden="true">&times;</span></button></td>
                                    </tr>
                                    @endforeach
                                </tbody>
                            </table>
                        </div>
                    </form>
                    <div id="closedTradeMessageContainer">
                    </div>
                    <div class="row mt-1">
                        <div class="col-12">
                            <span id="deletedTradeMessage"> </span>
                        </div>
                    </div>
                </div>
            </div>
            @endif
            {{-- Checks monthly balance and profit loss are set and displays them in the report --}}
            @if(isset($monthlyBalance) && isset($monthlyProfitLoss))
            <div class="row mt-5">
                    <div class="col-lg-4 col-sm-3"></div>
                    <div class="col-lg-4" id="tradeHistoryTotalContainer">
                        <table id="tradeHistoryTotalsTable" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>MONTHLY BALANCE</th>
                                    <th>MONTHLY PROFIT/LOSS</th>
                                </tr>
                            </thead>
                            <tbody id="tradeHistoryTotalsTableBody">
                                <tr>
                                    <td>${{$monthlyBalance}}</td>
                                    <td>${{$monthlyProfitLoss}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-lg-4"></div>
            </div>
            @endif
        </div>
        {{-- Modal for screenshot display --}}
        <div class="modal fade" id="screenshotDisplayModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Screenshots</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="modalBodyScreenshotDisplay">
                    </div>
                    <div class="modal-footer" id="modalFooterScreenshotDisplay">
                    </div>
                </div>
            </div>
        </div>
        {{-- Modal for screenshot upload --}}
        <div class="modal modal-success fade" tabindex="-1" id="screenshotUploadModal" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <span id="tradeRowHiddenIdentifier" hidden></span>
                        <h4 class="modal-title">Upload Screenshots</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="modalBodyScreenshotUpload">
                    </div>
                    <div class="modal-footer" id="modalFooterScreenshotUpload">
                            <button class="btn btn-style-1 btn-primary" type="submit" id="btn-new-screenshot">Upload Screenshots</button>
                    </div>
                </div>
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
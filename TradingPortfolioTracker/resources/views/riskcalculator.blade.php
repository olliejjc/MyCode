@extends('layouts.panelLayout')

@section('content')
<div id="layoutSidenav_content">
    <main class="h-75">
        <div class="container-fluid">
            <h1 class="mt-4">Risk Calculator</h1>
        </div>
        <div class="h-75 container-fluid">
            <div class="row mt-5">
                <!-- Profile Settings-->
                <div class="col-lg-12 pb-5">
                    <form class="row" id="risk-calculator-form" action="javascript:void(0)" method="POST">
                    @csrf
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Portfolio Size</label>
                                @if(isset($currentPortfolioSize))
                                    <input class="form-control" type="text" id="portfolio_size" name="portfolio_size" value="${{$currentPortfolioSize}}" readonly="readonly">
                                @else
                                    <input class="form-control" type="text" id="portfolio_size" name="portfolio_size" value="${{$user->portfolio_size}}" readonly="readonly">
                                @endif
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Percentage Risk Per Trade</label>
                                <input class="form-control" type="text" id="risk_percentage_per_trade" name="risk_percentage_per_trade" value="{{$user->risk_percentage_per_trade}}%" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Entry Price</label>
                                <input class="form-control" type="text" id="entry_price" name="entry_price" value="0">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Stop Loss</label>
                                <input class="form-control" type="text" id="stop_loss" name="stop_loss" value="0">
                            </div>
                        </div>
                        <div class="col-12">
                            <hr class="mt-2 mb-3">
                            <div class="d-flex flex-wrap justify-content-between align-items-center">
                                <button class="btn btn-style-1 btn-primary" type="submit" id="btn-calculate-size" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="Position size calculated.">Calculate</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">Max # of Shares to Purchase</label>
                            <input class="form-control" type="text" id="max_shares_purchase" name="max_shares_purchase" value="0" disabled="">
                        </div>
                </div>
                <div class="col-md-6">
                        <div class="form-group">
                            <label for="account-fn">Position Size</label>
                            <input class="form-control" type="text" id="position_size" name="position_size" value="$0" disabled="">
                        </div>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                        <div class="form-group">
                            <label for="account-fn">Risk of Position</label>
                            <input class="form-control" type="text" id="risk_of_position" name="risk_of_position" value="$0" disabled="">
                        </div>
                </div>
                <div class="col-md-4"></div>
            </div>
            <div id="errorMessageCalculatorContainer">
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
@extends('layouts.panelLayout')

@section('content')
<div id="layoutSidenav_content">
    <main class="h-75">
        <div class="container-fluid">
            <h1 class="mt-4">Add New Trade</h1>
        </div>
        <div class="h-75 container-fluid">
            <div class="row mt-5">
                <!-- Profile Settings-->
                <div class="col-lg-12 pb-5">
                    <form class="row" id="trade-add-form" action="javascript:void(0)" method="POST" enctype="multipart/form-data">
                    @csrf
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Asset Name</label>
                                <input class="form-control" type="text" id="asset_name" name="asset_name" value="">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Trade Size</label>
                                <input class="form-control" type="number" id="trade_size" name="trade_size" value="" step="0.01">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Trade Value</label>
                                <input class="form-control" type="number" id="trade_value" name="trade_value" value="" step="0.01">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Date Trade Opened</label>
                                <input class="form-control" type="date" id="date_trade_opened" name="date_trade_opened" value="">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Price Purchased At</label>
                                <input class="form-control" type="number" id="price_purchased_at" name="price_purchased_at" value="" step="0.01">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Screenshots</label>
                                <input class="form-control" type="file" id="screenshots" name="screenshots[]" multiple>
                            </div>
                        </div>
                        <div class="col-12">
                            <hr class="mt-2 mb-3">
                            <div class="d-flex flex-wrap justify-content-between align-items-center">
                                <button class="btn btn-style-1 btn-primary" type="submit" id="btn-new-trade" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="New Trade Submitted">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div id="addedTradeMessageContainer">
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
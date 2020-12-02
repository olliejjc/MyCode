@extends('layouts.panelLayout')

@section('content')
<div id="layoutSidenav_content">
    <main class="h-100">
        <div class="container-fluid">
            <h1 class="mt-4">User Settings</h1>
        </div>
        <div class="h-100 container-fluid">
            <div class="row mt-5">
                <!-- Profile Settings-->
                <div class="col-lg-12 pb-5">
                    <form class="row" id="update-settings-form" action="javascript:void(0)" method="POST">
                    @csrf
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-fn">Full Name</label>
                                <input class="form-control" type="text" id="name" name="name" value="{{ Auth::user()->name }}" disabled=""readonly="readonly">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-email">Username</label>
                                <input class="form-control" type="username" id="account-username" value="{{ Auth::user()->username }}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-email">E-mail Address</label>
                                <input class="form-control" type="email" id="email" name="email" value="{{ Auth::user()->email }}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-phone">Portfolio Size ($)</label>
                                <input class="form-control" type="text" id="portfolio_size" name="portfolio_size" value="{{$user->portfolio_size}}" required="">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-phone">Percentage Risk Per Trade</label>
                                <input class="form-control" type="text" id="risk_percentage_per_trade" name="risk_percentage_per_trade" value="{{$user->risk_percentage_per_trade}}" required="">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-pass">Binance API Key</label>
                                <input class="form-control" type="password" id="binance_apikey" name="binance_apikey" value="{{$user->binance_apikey}}">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="account-confirm-pass">Binance Secret Key</label>
                                <input class="form-control" type="password" id="binance_secretkey" name="binance_secretkey" value="{{$user->binance_secretkey}}">
                            </div>
                        </div>
                        <div class="col-12">
                            <hr class="mt-2 mb-3">
                            <div class="d-flex flex-wrap justify-content-between align-items-center">
                                <button class="btn btn-style-1 btn-primary" type="submit" id="btn-update-settings" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="Your profile updated successfuly.">Update Profile</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div id="updateSettingsMessageContainer">
            </div>
            <div class="row mt-1">
                <div class="col-12">
                    <span id="updatedSettingsMessage"> </span>
                </div>
            </div>
        </div>
    </main>
</div>
@endsection
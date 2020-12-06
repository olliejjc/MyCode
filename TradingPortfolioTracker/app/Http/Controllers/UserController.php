<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\User;
use Auth;
use App\Http\Controllers\TradesController;

class UserController extends Controller{
    
    /* Retrieve user details for use on risk calculator */
    public function showRiskCalculatorSettings(){
        $user = User::where('username', Auth::user()->username)->first();
        $currentPortfolioSize = TradesController::getCurrentPortfolioSize();
        return view('riskcalculator', ['user' => $user, 'currentPortfolioSize' => $currentPortfolioSize]);
    }

    public function showUserSettings(){
        $user = User::where('username', Auth::user()->username)->first();
        return view('settings', ['user' => $user]);
    }

    public function update(Request $req){
        $user = User::where('username', Auth::user()->username)->first();
        $validatedData = $req->validate([
            'portfolio_size' => 'required|numeric|min:0|max:100000000',
            'risk_percentage_per_trade' => 'required|numeric|min:0.00|max:100.00',
        ]);
        $user -> portfolio_size = $req->input('portfolio_size');
        $user -> risk_percentage_per_trade = $req->input('risk_percentage_per_trade');
        $user -> binance_apikey = $req->input('binance_apikey');
        $user -> binance_secretkey = $req->input('binance_secretkey');
        $user -> save();
    }

    public static function show($username){
        $user = User::where('username', $username)->first();
        return $user;
    }
}
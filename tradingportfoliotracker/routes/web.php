<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::group( ['middleware' => 'auth' ], function()
{
    Route::get('/', 'HomeController@index')->name('home');

    Route::get('/home', 'HomeController@index')->name('home');
    
    Route::get('/riskcalculator', 'UserController@showRiskCalculatorSettings');
    
    Route::get('/settings', 'UserController@showUserSettings');

    Route::post('update', 'UserController@update');
    
    Route::get('/newtrades', function() {
        return view('newtrades');
    });

    Route::get('/trades', 'TradesController@index');
    
    Route::get('/tradehistory', 'TradesController@generateTradeHistory');
    
    Route::get('/changeMonthUpdateTradesDisplayed', 'TradesController@changeMonthUpdateTradesDisplayed');
    
    Route::get('/calculateMonthlyTotals', 'TradesController@calculateMonthlyBalanceAndProfitLoss');
    
    Route::get('/tradeYearsWithTrades', 'TradesController@getListOfTradeYears');
    
    Route::post('newtrades', 'TradesController@addNewTrade');
    
    Route::post('closetrades', 'TradesController@closeTrade');
    
    Route::post('generateTradeChartDataSets', 'TradesController@generateTradeChartDataSets');
    
    Route::delete('/deletetrades/{id}', 'TradesController@delete');

    Route::post('calculate', 'RiskCalculatorController@calculate');

    Route::get('/tradescreenshots/{id}', 'ScreenshotsController@getScreenshotsByTrade');

    Route::post('tradehistory', 'ScreenshotsController@store');

    Route::get('/liveportfolio', 'LivePortfolioController@index')->name('liveportfolio');
});



Route::namespace('Auth')->group(function () {
    //Route::get('iblogin', 'IBLoginController@handlelogin');
    // Authentication Routes...
    Route::get('login', 'LoginController@showLoginForm')->name('login');
    Route::post('login', 'LoginController@login');
    Route::post('logout', 'LoginController@logout')->name('logout');

    // Registration Routes...
    Route::get('register', 'RegisterController@showRegistrationForm')->name('register');
    Route::post('register', 'RegisterController@register');

    // Password Reset Routes...
    Route::get('password/reset', 'ForgotPasswordController@showLinkRequestForm');
    Route::post('password/email', 'ForgotPasswordController@sendResetLinkEmail');
    Route::get('password/reset/{token}', 'ResetPasswordController@showResetForm');
    Route::post('password/reset', 'ResetPasswordController@reset');
});
<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use Illuminate\Support\Facades\Http;

use Symfony\Component\Process\Process;

use App\User;

use Auth;

use File;

use Image;

class LivePortfolioController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    protected $binanceTotalDollarHoldings = 0;

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Contracts\Support\Renderable
     */
    public function index(){
        $user = User::where('username', Auth::user()->username)->first();
        $binanceAPIKey  = $user -> binance_apikey;
        $binanceSecretKey  = $user -> binance_secretkey;
        //checks that the binance api keys are set, if they are will retrieve your binance exchange data
        if(isset($binanceAPIKey) && isset($binanceSecretKey)){
            $binanceData = LivePortfolioController::getBinanceData($binanceAPIKey, $binanceSecretKey);
            $binanceDataDecoded = json_decode($binanceData);
            //check that the binance api returns valid data about holdings, will return valid data if api on binance is not set up to allow retrieval of data from your account
            if(!isset( $binanceDataDecoded -> code)){
                $binanceHoldings = LivePortfolioController::getBinanceHoldings($binanceData);
                $interactiveBrokersData = "";
                return view('liveportfolio', ['binanceHoldings' => $binanceHoldings, 'binanceTotalDollarHoldings' => $this->binanceTotalDollarHoldings, 'interactiveBrokersData' => $interactiveBrokersData]);
            }
            else{
                return view('liveportfolio', ['binanceHoldings' => null]);
            }
        }
        else{
            return view('liveportfolio', ['binanceHoldings' => null]);
        }
        /* This gets data from the Interactive Brokers API *TO BE DONE* */
        //$interactiveBrokersData = LivePortfolioController::getInteractiveBrokersData();
    }

    /* Retrieves crypto token symbol for portfolio display for each crypto token holding */
    public function generateCryptoSymbol($cryptoType){
        $cryptoSymbol = "";

        /* Symbol exists already so don't need to retrieve it again from the cryptoicons api*/
        if (File::exists("symbols/" . strtolower($cryptoType) . ".png")) {
            $cryptoSymbol = "symbols/" . strtolower($cryptoType) . ".png";
        }
        else{
            $cryptoIconsSymbol = @file_get_contents("https://cryptoicons.org/api/icon/" . strtolower($cryptoType) . "/30");
            /* Check if the cryptoicons api returns data for specific crypto token */
            if($cryptoIconsSymbol !== FALSE){
                file_put_contents(public_path("symbols/" . strtolower($cryptoType) . ".png"), $cryptoIconsSymbol);
                $cryptoSymbol = "symbols/" . strtolower($cryptoType) . ".png";
            }
            /* When the cryptoicons api doesn't return data for specific crypto token we try and retrieve a generic crypto symbol from the api instead */
            else{
                if (File::exists("symbols/generic.png")) {
                    $cryptoSymbol = "symbols/generic.png";
                }
                else{
                    $cryptoIconsGenericSymbol = @file_get_contents("https://cryptoicons.org/api/icon/generic/30");
                    if($cryptoIconsGenericSymbol !== FALSE){
                        file_put_contents(public_path("symbols/generic.png"), $cryptoIconsGenericSymbol);
                        $cryptoSymbol = "symbols/generic.png";
                    }
                    else{
                        $cryptoSymbol = "";
                    }
                }
            }
        }        
        return $cryptoSymbol;
    }

    /* gets data to access binance account api */
    public function getBinanceData($binanceAPIKey, $binanceSecretKey){
        $timestampRequest = json_decode(Http::get('https://api.binance.com/api/v3/time'));
        $timestamp = $timestampRequest -> serverTime;
        $timestampString = "timestamp=" . $timestamp;
        $signature = LivePortfolioController::generateSignature($timestampString, $binanceSecretKey);
        $response = Http::withHeaders(['X-MBX-APIKEY' => $binanceAPIKey])->get('https://api.binance.com/api/v3/account', ['timestamp' => $timestamp, 'signature' => $signature]);
        return $response;
    }

    public function getBinanceHoldings($binanceData){
        $binanceDataDecoded = json_decode($binanceData);
        $balances = $binanceDataDecoded -> balances;
        $binanceHoldings = [];

        /* Loop through each possible binance token */
        foreach($balances as $balance){
            /* only check tokens where balance > 0 */
            if($balance->free > 0){
                $cryptoType = $balance->asset;
                $cryptoBalance = $balance->free;
                $dollarValueOfHolding = 0;
                $dollarPriceOfHolding = 0;
                $symbolImage;
                if($balance->asset !== "USDT"){
                    $symbol = $balance->asset . "USDT";
                    $dollarPriceOfHoldingResponse = json_decode(Http::get('https://api.binance.com/api/v3/ticker/price', ['symbol' => $symbol]));
                    if(isset($dollarPriceOfHoldingResponse -> price)){
                        $dollarPriceOfHolding = $dollarPriceOfHoldingResponse -> price;
                        $dollarValueOfHolding = $dollarPriceOfHolding * $cryptoBalance;
                    }
                }
                else{
                    $dollarValueOfHolding = $cryptoBalance;
                    $dollarPriceOfHolding = 1;
                }
                /* Builds list of binance holdings in the traders account with a value greater than 1 dollar*/
                if($dollarValueOfHolding > 1){
                    $this->binanceTotalDollarHoldings += number_format($dollarValueOfHolding, 2, '.', '');
                    $cryptoSymbol = LivePortfolioController::generateCryptoSymbol($cryptoType);
                    $binanceHoldings [] = [$cryptoSymbol, $cryptoType, $cryptoBalance, "$" . number_format($dollarPriceOfHolding, 2, '.', ''), "$" . number_format($dollarValueOfHolding, 2, '.', '')]; 
                }

            }
        }
        return $binanceHoldings;
    }

    public function getInteractiveBrokersData(){
        $process = new Process(['C:\laragon\www\tradingportfoliotracker\resources\IBC\StartGateway.bat']);
        // $process->setWorkingDirectory('C:\IBC');
        // $process->setEnv(getenv());
        // $process->start();
        // $process->waitUntil(function ($type, $output) {
        //     return $output === 'Ready. Waiting for commands...';
        // });        
        // echo $process->getOutput();
        $accountDataResponse = Http::withoutVerifying()->get('https://localhost:5000/v1/portal/portfolio/accounts');
        $response = Http::withoutVerifying()->get('https://localhost:5000/v1/portal/portfolio/U2479245/summary');
        $response = Http::withoutVerifying()->get('https://localhost:5000/v1/portal/iserver/accounts');
        return $response;
    }

    public function generateSignature($timestampString, $secretKey){
        return hash_hmac('sha256', $timestampString, $secretKey);
    }
}

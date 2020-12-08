<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Trade;
use App\User;
use Auth;
use App\Screenshot;
use App\Http\Controllers\ScreenshotsController;
use Carbon\Carbon;

class TradesController extends Controller{

    public static function index(){
        $user = User::where('username', Auth::user()->username)->first();
        $trades = Trade::where('user_id', $user->id)->get();
        return $trades;
    }

    public function delete($id){
        $trade = Trade::findOrFail($id);
        $screenshotsPathData = ScreenshotsController::getScreenshotsByTrade($id);
        if(!empty($screenshotsPathData)){
            foreach($screenshotsPathData as $screenshotPath){
                if (file_exists(public_path() . "//screenshots//" . $screenshotPath)) {
                    unlink(public_path() . "//screenshots//" . $screenshotPath);
                }
            }
        }
        $trade->delete();
        return $trade;
    }

    public function generateTradeHistory(){
        $trades = array();
        $trades = TradesController::getTradesByLatestMonth();
        if(!empty($trades)){
            $tradeMonth = TradesController::getTradeMonth($trades[0]);
            $tradeYear = TradesController::getTradeYear($trades[0]);
            $monthlyBalance = TradesController::getMonthlyBalance($tradeMonth, $tradeYear);
            $monthlyProfitLoss = TradesController::getMonthlyProfitLoss($tradeMonth, $tradeYear);
            $listOfTradeYears = TradesController::getListOfTradeYears();
            return view('tradehistory', ['trades' => $trades, 'listOfTradeYears' => $listOfTradeYears, 'tradeMonth' => $tradeMonth, 'tradeYear' => $tradeYear, 
        'monthlyBalance' => $monthlyBalance, 'monthlyProfitLoss' => $monthlyProfitLoss]);
        }
        else{
            return view('tradehistory', ['trades' => null, 'listOfTradeYears' => null, 'tradeMonth' => null, 'tradeYear' => null, 
        'monthlyBalance' => null, 'monthlyProfitLoss' => null]);
        }
    }

    public function calculateMonthlyBalanceAndProfitLoss(Request $request){
        $monthSelected = $request->month;
        $yearSelected = $request->year;
        $monthlyBalance = TradesController::getMonthlyBalance($monthSelected, $yearSelected);
        $monthlyProfitLoss = TradesController::getMonthlyProfitLoss($monthSelected, $yearSelected);
        $monthlyTotals = array($monthlyBalance, $monthlyProfitLoss);
        return json_encode($monthlyTotals);
    }

    public static function getMonthlyBalance($monthSelected, $yearSelected){
        $user = User::where('username', Auth::user()->username)->first();
        $portfolioSize = $user -> portfolio_size;
        $trades = TradesController::index();
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $date = new Carbon($tradeDate);
            $year = strval($date -> year);
            $month = $date->format('F');
            $tradeProfitLoss = $trade -> profit_loss;
            $monthValue = Carbon::parse($month)->month;
            /* checks if there's profit/loss on the trade and then add remove it from the portfolio balance */
            if($tradeProfitLoss != null){
                if($monthSelected != "All Months"){
                    if($year < $yearSelected){
                        $portfolioSize += $tradeProfitLoss;
                    }
                    else if($year == $yearSelected){
                        $monthSelectedValue = Carbon::parse($monthSelected)->month;
                        if($monthValue <= $monthSelectedValue){
                            $portfolioSize += $tradeProfitLoss;
                        }
                    }
                }
                else{
                    if($year < $yearSelected || $year == $yearSelected){
                        $portfolioSize += $tradeProfitLoss;
                    }
                }
            }
        }
        $portfolioSize = number_format((float)$portfolioSize, 2, '.', '');
        return $portfolioSize;
    }

    public function generateTradeChartDataSets(Request $request){
        $monthSelected;
        $yearSelected;
        $tradeChartDataSets = array();
        $timePeriod = $request->timePeriod;
        if($timePeriod == ("3 Month View") || $timePeriod == ("6 Month View")){
            $lastMonthChecked = "";
            $date = Carbon::now();
            $yearSelected = $date->year;
            $i = 0;
            $j = 0;
            if($timePeriod == ("3 Month View") ){
                /* check starts at 2 months ago */
                $i=2;
            }
            else if($timePeriod == ("6 Month View") ){
                /* check starts at 5 months ago */
                $i=5;
            }

            $currentMonth = $date->format('m');
            $earliestMonth = $date->subMonth($i)->format('m');
            /* if earliest month is greater than the current month it means the time period check has crossed multiple years and the earliestMonth belongs to the year before */
            if($earliestMonth > $currentMonth){
                $yearSelected--;
            }
            $date = Carbon::now();
            /* loop through each month to be checked depending on time period */
            for($i; $i >= 0; $i--){
                $monthSelected = $date->subMonth($i)->format('F');
                /* check has passed into the next year so increment the year, e.g December 2019 -> January 2020 */
                if($lastMonthChecked == "December" && $monthSelected == "January"){
                    $yearSelected++;
                }
                $lastMonthChecked = $monthSelected;
                $monthlyBalance = TradesController::getMonthlyBalance($monthSelected, $yearSelected);
                $tradeChartDataSets[$j] = $monthlyBalance;
                $date = Carbon::now();
                $j++;
            }
            return $tradeChartDataSets;
        }
        else if($timePeriod == ("Yearly View")){
            $monthsInYear = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
            $date = Carbon::now();
            $currentYear= $date->year;
            $currentMonth = $date->format('F');
            $yearSelected = $request->yearPeriod;
            //$tradeMonths = TradesController::getTradeMonthsByYear($yearSelected);
            foreach($monthsInYear as $month){
                if($currentYear == $yearSelected){
                    $monthlyBalance = TradesController::getMonthlyBalance($month, $yearSelected);
                    $tradeChartDataSets[] = $monthlyBalance;
                    /* If we reach the current month we can't get data for the months after that so we break the loop */
                    if($month == $currentMonth){
                        break;
                    }
                }
                else{
                    $monthlyBalance = TradesController::getMonthlyBalance($month, $yearSelected);
                    $tradeChartDataSets[] = $monthlyBalance;
                }
            }
            return $tradeChartDataSets;
        }
        else if($timePeriod == ("Overall View")){
            $tradeYears = TradesController::getListOfTradeYears();
            $user = User::where('username', Auth::user()->username)->first();
            $tradeChartDataSets[] = $user -> portfolio_size;
            /* Gets the balance for each year */
            foreach($tradeYears as $tradeYear){
                $tradeMonths = TradesController::getTradeMonthsByYear($tradeYear);
                $lastTradeMonth = $tradeMonths[count($tradeMonths)-1];
                $monthlyBalance = TradesController::getMonthlyBalance($lastTradeMonth, $tradeYear);
                $tradeChartDataSets[] = $monthlyBalance;
            }
            return $tradeChartDataSets;
        }
    }

    public function getMonthlyProfitLoss($monthSelected, $yearSelected){
        $totalMonthlyProfitLoss = 0;
        $tradesWithMatchingDate = TradesController::getTradesBySelectedDate($monthSelected, $yearSelected);
        foreach($tradesWithMatchingDate as $tradeWithMatchingDate){
            $tradeProfitLoss = $tradeWithMatchingDate -> profit_loss; 
            if($tradeProfitLoss != null){
                $totalMonthlyProfitLoss += $tradeProfitLoss;
            }
        }
        $totalMonthlyProfitLoss = number_format((float)$totalMonthlyProfitLoss, 2, '.', '');
        return $totalMonthlyProfitLoss;
    }

    public static function getTradesByLatestMonth(){
        $tradesFromLatestMonth = array();
        $trades = TradesController::index();
        $latestMonthChecked = 0;
        $latestYearChecked = 0;
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $date = new Carbon( $tradeDate );
            $year = $date -> year;
            $month = $date-> month;
            /* handles if there's only one trade in the latest year */
            if($year > $latestYearChecked){
                $tradesFromLatestMonth = array();
                $latestYearChecked = $year;
                $latestMonthChecked = $month;
                $tradesFromLatestMonth [] = $trade;
            }
            /* if the trade is in the same year as the latest year checked */
            else if($year == $latestYearChecked){
                /* If the month is greater than the latest month checked than reset tradesFromLatestMonth and start building it again */
                if($month > $latestMonthChecked){
                    $tradesFromLatestMonth = array();
                    $latestMonthChecked = $month;
                    $tradesFromLatestMonth [] = $trade;
                }
                /* If the trade is in the latest month checked add it to tradesFromLatestMonth */
                else if($month == $latestMonthChecked){
                    $tradesFromLatestMonth [] = $trade;
                }
            }
        }
        return $tradesFromLatestMonth;
    }

    public static function getListOfTradeYears(){
        $listOfYears = array();
        $trades = TradesController::index();
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $date = new Carbon( $tradeDate );
            $year = $date -> year;
            /* check so don't add same year twice */
            if(!in_array($year, $listOfYears)){
                $listOfYears [] = $year;
            }
        }
        sort($listOfYears);
        return $listOfYears;
    }

    public static function getListOfTradeYearsWithClosedTrades(){
        $listOfYears = array();
        $trades = TradesController::index();
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $isTradeOpened = $trade -> trade_opened;
            $date = new Carbon( $tradeDate );
            $year = $date -> year;
            if($isTradeOpened == false){
                if(!in_array($year, $listOfYears)){
                    $listOfYears [] = $year;
                }
            }
        }
        sort($listOfYears);
        return $listOfYears;
    }

    /* Builds a list of trades by a selected month and year*/
    public function getTradesBySelectedDate($monthSelected, $yearSelected){
        $tradesWithMatchingDate = array();
        $trades = TradesController::index();
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $date = new Carbon( $tradeDate );
            $year = strval($date -> year);
            $month = $date->format('F');
            if($monthSelected == "All Months"){
                if($year==$yearSelected){
                    $tradesWithMatchingDate [] = $trade;
                }
            }
            else{
                if($year==$yearSelected && $month==$monthSelected){
                    $tradesWithMatchingDate [] = $trade;
                }
            }
        }
        return $tradesWithMatchingDate;
    }

    public function addNewTrade(Request $req){
        $validatedData = $req->validate([
            'asset_name' => 'required',
            'trade_size' => 'required|numeric|min:0.00|max:100000.00',
            'trade_value' => 'required|numeric|min:0.00|max:10000000.00',
            'date_trade_opened' => 'required|date_format:Y-m-d|before:tomorrow|after_or_equal:2018-01-01',
            'price_purchased_at' => 'required|numeric|min:0.00|max:100000.00',
            'screenshots.*' => 'image|mimes:jpeg,png,jpg,gif,svg|max:2048',
        ]);
        $trade = new Trade();
        $trade->symbol = "";
        $trade->holding_name = $req->input('asset_name');
        $trade->trade_size = $req->input('trade_size');
        $trade->trade_value = $req->input('trade_value');
        $trade->date_trade_opened = $req->input('date_trade_opened');
        $trade->date_trade_closed = null;
        $trade->price_purchased_at = $req->input('price_purchased_at');
        $trade->price_closed_at = null;
        $trade->trade_opened = true;
        $trade->profit_loss = null;
        $trade->has_screenshots = false;
        $user = UserController::show(Auth::user()->username);
        // Handle multiple screenshot upload
        $images = $req->file('screenshots');
        if(is_array($images)){
            $trade->has_screenshots = true;
            $trade->save();
            $user->currentTrades()->save($trade);
            /* Loops through each screenshot uploaded and associates it with a trade */
            foreach($images as $key => $image){
                $name=$image->getClientOriginalName();
                $screenshot = new Screenshot();
                $screenshot->save();
                $screenshotNameWithId = str_replace(".png", "_" . $screenshot->id, $name);
                $screenshotFileName =  $screenshotNameWithId . ".png";
                $screenshot -> screenshot_image = $screenshotFileName;
                $screenshot->save();
                $image->move('screenshots', $screenshotFileName);
                $trade->existingScreenshots()->save($screenshot);
            }
        }
        /* Handles single screenshot upload */
        else{
            if (!empty($images)) {
                $trade->has_screenshots = true;
                $trade->save();
                $user->currentTrades()->save($trade);
                $oneImage = $images;
                $name=$oneImage->getClientOriginalName();
                $screenshot = new Screenshot();
                $screenshot->save();
                $screenshotNameWithId = str_replace(".png", "_" . $screenshot->id, $name);
                $screenshotFileName =  $screenshotNameWithId . ".png";
                $screenshot -> screenshot_image = $screenshotFileName;
                $screenshot->save();
                $image->move('screenshots', $screenshotFileName);
                $trade->existingScreenshots()->save($screenshot);
            }
        }
        $trade->save();
        $user->currentTrades()->save($trade);
    }

    public function closeTrade(Request $req){
        $tradeRowClosedIdentifier = $req->input('tradeRowClosedIdentifier');
        $dateTradeClosedInputName = 'date_trade_closed_id_' . $tradeRowClosedIdentifier;
        $priceClosedAtInputName = 'price_closed_at_id_' . $tradeRowClosedIdentifier;
        $profitLossInputName = 'profit_loss_id_' . $tradeRowClosedIdentifier;
        $trade = Trade::findOrFail($tradeRowClosedIdentifier);
        $dateTradeOpened = $trade->date_trade_opened;
        TradesController::validateCloseTradeInputData($req, $dateTradeClosedInputName, $priceClosedAtInputName, $profitLossInputName, $dateTradeOpened);
        $dateTradeClosed = $req->input($dateTradeClosedInputName);
        $priceClosedAt = $req->input($priceClosedAtInputName);
        $profitLossTotal = $req->input($profitLossInputName);
        $user= UserController::show(Auth::user()->username);
        $trade->date_trade_closed = $dateTradeClosed;
        $trade->price_closed_at = $priceClosedAt;
        $trade->profit_loss = $profitLossTotal;
        $trade->trade_opened = false;
        $trade->save();
        $user->currentTrades()->save($trade);
    }

    /* get all months with a trade in it in a specific year */
    public function getTradeMonthsByYear($yearSelected){
        $tradeMonthsWithMatchingYear = array();
        $trades = TradesController::index();
        foreach($trades as $trade){
            $tradeDate = $trade -> date_trade_opened;
            $date = new Carbon( $tradeDate );
            $year = strval($date -> year);
            $month = $date->format('F');
            if($year==$yearSelected){
                if(!in_array($month, $tradeMonthsWithMatchingYear)){
                    $tradeMonthsWithMatchingYear [] = $month;
                }
            }
        }
        return $tradeMonthsWithMatchingYear;
    }

    /* For trade history will update the trades displayed if month is changed*/
    public function changeMonthUpdateTradesDisplayed(Request $request){
        $monthSelected = $request->month;
        $yearSelected = $request->year;
        $tradesWithMatchingDate = TradesController::getTradesBySelectedDate($monthSelected, $yearSelected);
        $tradesAndDateData = array($monthSelected, $yearSelected, $tradesWithMatchingDate);
        return json_encode($tradesAndDateData);
    }

    public static function getCurrentPortfolioSize(){
        $trades = array();
        $trades = TradesController::getTradesByLatestMonth();
        if(!empty($trades)){
            $tradeMonth = TradesController::getTradeMonth($trades[0]);
            $tradeYear = TradesController::getTradeYear($trades[0]);
            $monthlyBalance = TradesController::getMonthlyBalance($tradeMonth, $tradeYear);
            return $monthlyBalance;
        }
    }

    public static function getTradeMonth($trade){
        $tradeDate = $trade -> date_trade_opened;
        $date = new Carbon( $tradeDate );
        $month = $date->format('F');
        return $month;
    }

    public static function getTradeYear($trade){
        $tradeDate = $trade -> date_trade_opened;
        $date = new Carbon( $tradeDate );
        $year = strval($date -> year);
        return $year;
    }

    public function validateCloseTradeInputData(Request $req, $dateTradeClosedInputName, $priceClosedAtInputName, $profitLossInputName,  $dateTradeOpened){
        $validatedData = $req->validate([
            $dateTradeClosedInputName => 'required|date_format:Y-m-d|before:tomorrow|after_or_equal:' . $dateTradeOpened,
            $priceClosedAtInputName => 'required|numeric|min:0.00|max:1000000.00',
            $profitLossInputName => 'required|numeric',
        ], 
        [
            $dateTradeClosedInputName . '.required' => 'The date trade closed field is required.',
            $dateTradeClosedInputName . '.date_format' => 'The date trade closed field does not match the format Y-m-d.',
            $dateTradeClosedInputName . '.before' => 'The date trade closed field must be a date before tomorrow.',
            $dateTradeClosedInputName . '.after' => 'The date trade closed field must be a date after ' . $dateTradeOpened,
            $priceClosedAtInputName . '.required' => 'The priced closed at field is required.',
            $priceClosedAtInputName . '.numeric' =>  'The price closed at field must be a number.',
            $priceClosedAtInputName . '.min' => 'The price closed at field must be at least 0.00.',
            $priceClosedAtInputName . '.max' =>  'The price closed at field may not be greater than 1000000.00.',
            $profitLossInputName . '.required' => 'The profit/loss field is required.',
            $profitLossInputName . '.numeric' => 'The profit/loss field must be a number.',
        ]
        );
    }
}
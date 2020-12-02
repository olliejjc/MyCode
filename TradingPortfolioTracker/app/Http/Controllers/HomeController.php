<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use Illuminate\Support\Facades\Http;

use Symfony\Component\Process\Process;

class HomeController extends Controller
{
   /*Generates the list of years that have trade history */
   public function index(){
       $listOfTradeYears = TradesController::getListOfTradeYears();
       rsort($listOfTradeYears);
       $latestYearAvailable;
       return view('home', ['listOfTradeYears' => $listOfTradeYears]);
   }
}

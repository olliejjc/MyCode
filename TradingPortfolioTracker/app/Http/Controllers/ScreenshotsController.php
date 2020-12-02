<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Trade;
use App\User;
use App\Screenshot;
use Auth;

class ScreenshotsController extends Controller
{

    public function store(Request $req){
        $images = $req->file('screenshots');
        $user= UserController::show(Auth::user()->username);
        $trade = Trade::findOrFail($req->rowIdentifier);
        /* Multiple screenshots uploaded*/
        if(is_array($images)){
            $trade->has_screenshots = true;
            $trade->save();
            $user->currentTrades()->save($trade);
            foreach($images as $key => $image){
                $name=$image->getClientOriginalName();
                $image->move('image',$name);
                $screenshot = new Screenshot();
                $screenshot -> screenshot_image = $name;
                $screenshot->save();
                $trade->existingScreenshots()->save($screenshot);
            }
        }
        /* One screenshot uploaded*/
        else{
            $trade->has_screenshots = true;
            $trade->save();
            $user->currentTrades()->save($trade);
            $oneImage = $images;
            $name=$oneImage->getClientOriginalName();
            $screenshot = new Screenshot();
            $screenshot -> screenshot_image = $name;
            $screenshot->save();
            $trade->existingScreenshots()->save($screenshot);
        }
        $trade->save();
        $user->currentTrades()->save($trade);
    }

    public function getScreenshotsByTrade($tradeID){
        $screenshotPathData = array();
        $screenshots = Screenshot::where('trade_id', $tradeID)->get();
        foreach($screenshots as $screenshot){
            $screenshotPathData [] = $screenshot->screenshot_image;
        }
        return $screenshotPathData;
    }
}
<?php

namespace App\Http\Controllers\Auth;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Symfony\Component\Process\Process;

class IBLoginController extends Controller{
    
    public function handleLogin(){
        $process = new Process(['StartIBC.bat']);
        $process->setWorkingDirectory('C:\laragon\www\tradingportfoliotracker\resources\IBC\scripts');
        $process->run();
        // executes after the command finishes
        if (!$process->isSuccessful()) {
            echo("Here");
            throw new ProcessFailedException($process);
        }
        echo $process->getOutput();
        return view('/home');
    }

}
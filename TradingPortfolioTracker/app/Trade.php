<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Trade extends Model
{
    protected $table = 'currenttrades';

    public function existingScreenshots(){
        return $this->hasMany('App\Screenshot');
    }

    public function users()
    {
        return $this->belongsTo('App\User');
    }
}

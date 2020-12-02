<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class Screenshots extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('screenshots', function (Blueprint $table) {
            $table->id();
            $table->timestamps();
            $table->binary('screenshot_image');
            $table->bigInteger('trade_id')->unsigned()->nullable();
        });

        Schema::table('screenshots', function (Blueprint $table) {
            $table->foreign('trade_id')->references('id')->on('currenttrades')->onDelete('cascade');
        });

    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('screenshots');
    }
}

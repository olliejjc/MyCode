@extends('layouts.panelLayout')

@section('content')
    <div id="layoutSidenav_content">
        <main>
            @if(isset($binanceHoldings))
            <div class="container-fluid">
                <h1 class="mt-4">Live Portfolio</h1>
                <h2 class="mt-4">Crypto Holdings Table</h1>
                <div class="row">
                    <table class="table table-striped mt-4">
                        <thead>
                            <tr>
                            <th scope="col">Symbol</th>
                            <th scope="col">Ticker</th>
                            <th scope="col">Holdings</th>
                            <th scope="col">Current Price</th>
                            <th scope="col">Dollar Value of Holdings</th>
                            </tr>
                        </thead>
                        <tbody>
                        {{-- Loops through data from binance API and shows the details for each Binance Crypto Holding on your binance account --}}
                        @foreach($binanceHoldings as $binanceHolding)
                            <tr>
                                @foreach($binanceHolding as $binanceDetails)
                                    @if ($loop->first)
                                        <td><img src="{{$binanceDetails}}"></td>
                                        @continue
                                    @endif
                                    <td>{{$binanceDetails}}</td>
                                @endforeach
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="col-md-6 text-right"><h3>Total Dollar Value Of Holdings</h3></div>
                    <div class="col-md-6"><h3>${{$binanceTotalDollarHoldings}}</h3></div>
                </div>
            </div>
            @else
            <div class="container-fluid">
                <h1 class="mt-4">Live Portfolio</h1>
                <h2 class="mt-4">No Live Portfolio Data Available - Check your Binance and IB API Settings</h1>
            </div>
            @endif
        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright &copy; Oliver Campion</div>
                </div>
            </div>
        </footer>
    </div>
@endsection
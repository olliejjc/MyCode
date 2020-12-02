<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <meta name="csrf-token" content="{{ csrf_token() }}" />
        <title>Dashboard - SB Admin</title>
        <link href="css/styles.css" rel="stylesheet" />
        <link href="css/portfoliotracker.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
        <script>
            var assetBaseUrl = "{{ asset('') }}";
        </script>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <div>
                <a class="navbar-brand" href="{{ url('/home') }}">Trading Portfolio Tracker</a>
            </div>
            <div class="order-1 order-lg-0" id="sidebarToggleContainer">
                <button class="btn btn-link btn-sm" id="sidebarToggle"><i class="fas fa-bars"></i></button>
            </div>
            <ul class="navbar-nav ml-auto ml-md-30">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="{{ url('/settings') }}">Settings</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="{{ route('logout') }}" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">Logout</a>
                        <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                            @csrf
                        </form>
                    </div>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Portfolio Status</div>
                            <a class="nav-link" href="{{ url('/home') }}">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                            <div class="sb-sidenav-menu-heading">Tools</div>
                            <a class="nav-link" href="{{ url('/riskcalculator') }}">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Risk Calculator
                            </a>
                            <a class="nav-link" href="{{ url('/newtrades') }}">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Add New Trade
                            </a>
                            <a class="nav-link" href="{{ url('/tradehistory') }}">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                View Trade History
                            </a>
                            <a class="nav-link" href="{{ url('/liveportfolio') }}">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Live Portfolio
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        {{ Auth::user()->name }}
                    </div>
                </nav>
            </div>
            @yield('content')
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
        <script src="js/handleUserUpdates.js"></script>
        <script src="js/riskCalculator.js"></script>
        <script src="js/newTrade.js"></script>
        <script src="js/tradeHistory.js"></script>
        <script src="js/dashboardBuilder.js"></script>
        <script src="js/portfolioTracker.js"></script>
    </body>
</html>


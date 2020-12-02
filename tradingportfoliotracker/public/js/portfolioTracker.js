jQuery(document).ready(function($){
    var isToggled = false;
    var toggledClicked = false;
    var sideBarToggleDisplay = document.getElementById("layoutSidenav_nav");
    var sideBarContentContainer = document.getElementById("layoutSidenav_content");
    var lastWindowSizeValue = window.innerWidth;
    if(window.innerWidth >= 991.99){
        sideBarToggleDisplay.style.transform = "translateX(0px)";
        sideBarContentContainer.style.marginLeft = "0px";
    }
    else{
        sideBarToggleDisplay.style.transform = "translateX(-225px)";
        sideBarContentContainer.style.marginLeft = "-225px";
    }
    

    /* Handles clicking on the toggle sidebar button, resizes main display and shows/hides the sidebar */
    $(document).on('click', '#sidebarToggle', function(e) {
        toggledClicked = true;
        if(isToggled == false){
            isToggled = true;
        }
        else{
            isToggled = false;
        }
        var sideBarToggleDisplay = document.getElementById("layoutSidenav_nav");
        var sideBarContentContainer = document.getElementById("layoutSidenav_content");
        if (sideBarToggleDisplay.style.transform == "translateX(-225px)") {
            sideBarToggleDisplay.style.transform = "translateX(0px)";
            sideBarContentContainer.style.marginLeft = "0px";
        } else {
            sideBarToggleDisplay.style.transform = "translateX(-225px)";
            sideBarContentContainer.style.marginLeft = "-225px";
        }
    });

    /* Handles the sidebar when the screen is resized, will only show the sidebar on a smaller screen if the sidebar toggle button is deliberately clicked on,
       otherwise the sidebar is automatically hidden */
    $(window).on('resize', function(event){
        var sideBarToggleDisplay = document.getElementById("layoutSidenav_nav");
        var sideBarContentContainer = document.getElementById("layoutSidenav_content");
        /* Handles smaller window size */
        if(window.innerWidth <= 991.98){
            /* Checks if the window has gone from big to small and the toggle has not been clicked
               Will hide the sidebar automatically */
            if (lastWindowSizeValue >= 991.99 && isToggled == false && toggledClicked == false) {
                var sideBarToggleDisplay = document.getElementById("layoutSidenav_nav");
                var sideBarContentContainer = document.getElementById("layoutSidenav_content");
                sideBarToggleDisplay.style.transform = "translateX(-225px)";
                sideBarContentContainer.style.marginLeft = "-225px";
            }
        }
        /* Handles large window size */
        else{
            /* Checks if the window has gone from small to big and the toggle has not been clicked
               Will display the sidebar automatically */
            if(lastWindowSizeValue <= 991.98 && isToggled == false && toggledClicked == false){
                var sideBarToggleDisplay = document.getElementById("layoutSidenav_nav");
                var sideBarContentContainer = document.getElementById("layoutSidenav_content");
                sideBarToggleDisplay.style.transform = "translateX(0px)";
                sideBarContentContainer.style.marginLeft = "0px";
            }
        }
        lastWindowSizeValue = window.innerWidth;
    });    
});
/* JS for most of the general functionality of the site.*/

/* Hide JS message when JavaScript is allowed. */
document.getElementById("js-message").style.display = "none";


/* Back to top button. Display when the user scrolls down and hide when back at top */
window.onscroll = function () {
    checkScrolling();
};

function checkScrolling() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("back-to-top-button").style.display = "block";
    } else {
        document.getElementById("back-to-top-button").style.display = "none";
    }
};

/* When the user clicks "Back to top" scroll to the top */
function backToTop() {
    document.body.scrollTop = 0; //Safari
    document.documentElement.scrollTop = 0;
};

/* Submit the search query and still route to /search */
function submitSearch() {
    document.getElementById("search-container").submit();
};

/* ---------------------------------------------------------------------------------------------------------------*/

/* ---- Login/Register Overlays ---- */
function registerVisible() {
    document.getElementById("register-overlay").style.display = "flex";
};

function registerInvisible() {
    document.getElementById("register-overlay").style.display = "none";
};

function loginVisible() {
    document.getElementById("login-overlay").style.display = "flex";
};

function loginInvisible() {
    document.getElementById("login-overlay").style.display = "none";
};

/* If a user clicks into the search field, set the tab active for that time. */
function setSearchTabActive() {
    document.getElementById("search-container").style.backgroundColor = "#F5F5F5";
};

/* When a user clicks on the overlay and outside the register dialog, close it. */
$('.register-overlay').on('click', function (e) {
    /* Make sure only parent reacts to event! */
    if (e.target !== this) {
        return;
    } else {
        document.getElementById("register-overlay").style.display = "none";
    }
});
/* When a user clicks on the overlay and outside the login dialog, close it. */
$('.login-overlay').on('click', function (e) {
    /* Make sure only parent reacts to event! */
    if (e.target !== this) {
        return;
    } else {
        document.getElementById("login-overlay").style.display = "none";
    }
});

/* When the search field gains focus, set the search-container visually active. */
$('.search-box').on('focus', function (e) {
    let searchContainer = document.getElementById("search-container");
    searchContainer.style.backgroundColor = "#F5F5F5";
    searchContainer.style.border = "1px solid black";
    searchContainer.style.borderBottom = "1.25px solid #F5F5F5";
    searchContainer.style.transitionDuration = "0.4s";
});

/* When the focus is lost, reset everything back to normal. */
$('.search-box').on('blur', function (e) {
    let searchContainer = document.getElementById("search-container");
    /* Only unfocus when your are not the search page. */
    if (window.location.pathname !== "/search") {
        searchContainer.style.backgroundColor = "#666666";
        searchContainer.style.border = "1px solid #505050";
        searchContainer.style.borderBottom = "1.25px solid black";
        searchContainer.style.transitionDuration = "unset";
    }
});

/* When the menu icon in mobile view is clicked, toogle visibility of top navigation and show the icon as clicked. */
let visibilityFlag = false;
function showMobileNavigation() {
    if (!visibilityFlag) {
        document.getElementById("navigation-items").style.display = "block";
        document.getElementById("menu-icon").style.backgroundColor = "#444444";
        visibilityFlag = true;
    } else {
        document.getElementById("navigation-items").style.display = "none";
        document.getElementById("menu-icon").style.backgroundColor = "#555555";
        visibilityFlag = false;
    }
}

/* If the search page is loaded, highlight the search box for tab behaviour. */
function highlightSearchbox() {
    let searchContainer = document.getElementById("search-container");
    if (window.location.pathname.includes("/search")) {
        searchContainer.style.backgroundColor = "#F5F5F5";
        searchContainer.style.border = "1px solid black";
        searchContainer.style.borderBottom = "1.25px solid #F5F5F5";
        searchContainer.style.transitionDuration = "unset";
    }
}

/* After 3 seconds, make alert success message invisible. */
setInterval(function () {
    let successAlert = document.getElementById("alert-success");
    if (successAlert != null || successAlert != undefined) {
        successAlert.style.display = "none";
    }
}, 3000);
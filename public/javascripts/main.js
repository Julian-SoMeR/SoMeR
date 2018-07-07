/* Hide JS message when JavaScript is allowed. */
window.onload = function () {
    document.getElementById("js-message").style.display = "none";
};

/* Back to top button. Display when the user scrolls down and hide when back at top */
window.onscroll = function () {
    checkScrolling()
};

function checkScrolling() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("back-to-top-button").style.display = "block";
    } else {
        document.getElementById("back-to-top-button").style.display = "none";
    }
}

/* When the user clicks "Back to top" scroll to the top */
function backToTop() {
    document.body.scrollTop = 0; //Safari
    document.documentElement.scrollTop = 0;
}

/* Submit the search query and still route to /search */
function submitSearch() {
    document.getElementById("search-container").submit();
}

/* ---------------------------------------------------------------------------------------------------------------*/

/* ---- Login/Register Overlays ---- */
function registerVisible() {
    document.getElementById("register-overlay").style.display = "flex";
}

function registerInvisible() {
    document.getElementById("register-overlay").style.display = "none";
}

function loginVisible() {
    document.getElementById("login-overlay").style.display = "flex";
}

function loginInvisible() {
    document.getElementById("login-overlay").style.display = "none";
}

/* If a user clicks into the search field, set the tab active for that time. */
function setSearchTabActive() {
    document.getElementById("search-container").style.backgroundColor = "#F5F5F5";
}

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

/* Grab the size of the content-container within the body and decide, where to place the footer.
 * If the content doesn't fill the screen it's fixed to the bottom, place after content otherwise. */

let headerHeight = Math.max(
    document.getElementById('header-container').scrollHeight,
    document.getElementById('header-container').clientHeight,
    document.getElementById('header-container').offsetHeight);
console.log("Header Height: "+ headerHeight);

let contentHeight = Math.max(
    document.getElementById('content-container').scrollHeight,
    document.getElementById('content-container').clientHeight,
    document.getElementById('content-container').offsetHeight);
console.log("Content Height: "+ contentHeight);

let viewportHeight = Math.max(
    document.body.scrollHeight,
    document.body.clientHeight,
    document.body.offsetHeight,
    document.documentElement.scrollHeight,
    document.documentElement.offsetHeight,
    document.documentElement.clientHeight);
console.log("Viewport Height: " + viewportHeight);


/*
---- Navigation link functionality (setting stuff active)----
---- JS attempt: ----

$(document).ready(function () {
    let navigationContainer = document.getElementById("navigation-items");
    console.log(navigationContainer);

//Get all navigation links
    let navigationLinks = navigationContainer.getElementsByClassName("navigation-link");

    console.log(navigationLinks);
    console.log(navigationLinks.length);

//Go through all links and add "active" class to current link
    for (let i = 0; i < navigationLinks.length; i++) {
        console.log("Within for Loop");
        navigationLinks[i].addEventListener("click", function () {
            let currentlyActive = document.getElementsByClassName("active");
            currentlyActive[0].className = currentlyActive[0].className.replace(" active", "");
            console.log("Deleted!");
            this.className += " active";
            console.log(navigationLinks);
        });
    }
});
*/

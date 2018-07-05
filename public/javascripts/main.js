/* Always for the document to be ready!!! */

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
    document.getElementById("search-container").style.backgroundColor = "#F5F5F5";
    document.getElementById("search-container").style.border = "1px solid black";
    document.getElementById("search-container").style.borderBottom = "1.25px solid #F5F5F5";
    document.getElementById("search-container").style.transitionDuration = "0.4s";
});

/* When the focus is lost, reset everything back to normal. */
$('.search-box').on('blur', function (e) {
    /* Only unfocus when your are not the search page. */
    if (window.location.pathname !== "/search") {
        document.getElementById("search-container").style.backgroundColor = "#666666";
        document.getElementById("search-container").style.border = "1px solid #505050";
        document.getElementById("search-container").style.borderBottom = "1.25px solid black";
        document.getElementById("search-container").style.transitionDuration = "unset";
    }
});

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

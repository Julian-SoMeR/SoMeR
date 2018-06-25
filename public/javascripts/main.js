/* ---- Navigation link functionality (setting stuff active)---- */

var navigationContainer = document.getElementById("navigation-items");
console.log(navigationContainer);

//Get all navigation links
var navigationLinks = navigationContainer.getElementsByClassName("navigation-link");

console.log(navigationLinks);
console.log(navigationLinks.length);
//Go through all links and add "active" class to current link
for (var i = 0; i < navigationLinks.length; i++) {
    console.log("Within for Loop")
    navigationLinks[i].addEventListener("click", function () {
        var currentlyActive = document.getElementsByClassName("active");
        currentlyActive[0].className = currentlyActive[0].className.replace(" active", "");
        console.log("Deleted!")
        this.className += " active";
        console.log(navigationLinks)
    });
}


/* ---- Login/Register Overlays ---- */
function registerVisible() {
    document.getElementById("register-overlay").style.display = "block";
}

function registerInvisible() {
    document.getElementById("register-overlay").style.display = "none";
}

function loginVisible() {
    document.getElementById("login-overlay").style.display = "block";
}

function loginInvisible() {
    document.getElementById("login-overlay").style.display = "none";
}
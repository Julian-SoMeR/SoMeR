// Delete a platform after confirming a dialog
function deletePlatform(urlToDelete, platformName) {
    let dialogResult = confirm("Deleting this platform also deletes all the data connected to it!\n" +
        "Do you want to proceed deleting '" + platformName + "' and all of its data?");
    if (dialogResult) {
        $.ajax({
            url: urlToDelete,
            type: 'DELETE',
            success: function (results) {
                alert("Platform '" + platformName + "' has been successfully deleted!");
                location.reload();
            }
        });
    }
}

// Check whether any input field changed and track it.
let changesAppeared = false;
$(document).ready(function () {
    $('.change-detection').each(function (index) {
        $(this).on("change keyup paste", function () {
            changesAppeared = true;
        });
    });
});

// Warn the user if he wants to leave a page via any link without saving
$(document).ready(function () {
    $(".discard-warning").each(function (index) {
        $(this).on("click", function (e) {
            if (changesAppeared) {
                let dialogResult = confirm("You have unsaved changes. Leaving the page\n " +
                    "will result in discarding these changes.\n\n " +
                    "Continue anyway?");
                if (!dialogResult) {
                    e.preventDefault();
                    return false;
                } else {
                    return true;
                }
            }
        });
    });
});

// Set value of submit button to predefined string that enables routing decisions in the savePlatform() method
function submitWithValue(submitValue) {
    if (submitValue === "platformcreate-saveredirectfunctions") {
        let dialogResult = confirm("You can only view functions after the platform is saved.\n" +
            "Save and proceed to the 'Functions' tab?");
        if (dialogResult) {
            let submitButton = document.getElementById("form-submit-button");
            submitButton.value = submitValue;
            submitButton.click();
        }
    } else if (submitValue === "platformcreate-saveredirectimpacts") {
        let dialogResult = confirm("You can only view impacts after the platform is saved.\n" +
            "Save and proceed to the 'Impacts' tab?");
        if (dialogResult) {
            let submitButton = document.getElementById("form-submit-button");
            submitButton.value = submitValue;
            submitButton.click();
        }
    } else if (submitValue === "platformcreate-saveonly") {
        let submitButton = document.getElementById("form-submit-button");
        submitButton.value = submitValue;
        submitButton.click();
    }
}

/*Save and redirect to functions page
function saveRedirectToFunctions() {
    let platformForm = document.getElementById("create-new-platform-form");
    let formSubmitted = false;
    let dialogResult = confirm("You can only view functions after the platform is saved.\n" +
        "Save and proceed to the 'Functions' tab?");
    if(dialogResult) {
        platformForm.submit();
        formSubmitted = true;
        console.log("1");
        let saveRouting = jsRoutes.controllers.PlatformsController.platforms(1);
        $.ajax({
            url: saveRouting.url,
            type: saveRouting.type,
            success: function (results) {
                console.log("2");
                //location.reload();
            }

        })
        $.ajax(jsRoutes.controllers.PlatformsController.saveGeneralPlatformInformation())
            .done(console.log("2"))
            .fail(console.log("3"));
    }
    console.log("4");
}
*/

/* ---- Platform Search Filter ---- */
function filterPlatforms() {
    let input, filter, table, tr, td, i;

    input = document.getElementById("platform-filter");
    filter = input.value.toUpperCase();
    table = document.getElementById("platform-table");
    tr = table.getElementsByTagName("tr");
    // Hide all rows that do not match the filter
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

//Reset function for platform filter
function resetPlatformFilter() {
    input = document.getElementById("platform-filter");
    input.value = "";
    filterPlatforms();
}


/*
$(document).ready(function () {

});
*/
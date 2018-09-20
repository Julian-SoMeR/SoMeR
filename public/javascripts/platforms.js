/* JS for the platforms site. */

// Delete a platform after confirming a dialog
function deletePlatform(urlToDelete, platformName) {
    let dialogResult = confirm("Are you sure you want to delete '" +
        platformName + "'?");
    if (dialogResult) {
        $.ajax({
            url: urlToDelete,
            type: 'DELETE',
            success: function (results) {
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

// Set value of submit button to predefined string that enables routing decisions.
function submitWithValue(submitValue) {
    let submitButton = document.getElementById("form-submit-button");
    submitButton.value = submitValue;
    submitButton.click();
}

// Set value of submit button to predefined category string that enables routing decisions.
function submitWithCategory(submitValue) {
    let submitButton = document.getElementById("form-submit-button");
    let categoryUrl = document.getElementById("category-url").value;
    submitValue = submitValue + categoryUrl;
    submitButton.value = submitValue;
    console.log(submitValue);
    submitButton.click();
}

/* ---- Platform Search Filter ---- */
function filterPlatforms() {
    let input, filter, table, tr, td, i;

    input = document.getElementById("platform-filter");
    input.style.display = "block";
    input.focus();
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
    let input = document.getElementById("platform-filter");
    input.value = "";
    filterPlatforms();
}

/* Enable Enter on platform filter input. */
let platformFilter = document.getElementById("platform-filter");
platformFilter.addEventListener("keyup", function (event) {
    event.preventDefault();
    if (event.keyCode === 13) {
        document.getElementById("filter-button").click();
    }
});
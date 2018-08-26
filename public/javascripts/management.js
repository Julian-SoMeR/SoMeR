function showInput(informationId) {
    let selectedTableRowLinkString = "table-row-link-" + informationId;
    let deleteEditIconContainerString = "delete-edit-icon-container-" + informationId;
    let selectedTableRowLink = document.getElementById(selectedTableRowLinkString);
    let deleteEditIconContainer = document.getElementById(deleteEditIconContainerString);
    let selectedTableRowValue = selectedTableRowLink.getAttribute("value");

    let selectedTableRowInputString = "table-row-input-" + informationId;
    let saveCancelIconContainerString = "save-cancel-icon-container-" + informationId;
    let selectedTableRowInput = document.getElementById(selectedTableRowInputString);
    let saveCancelIconContainer = document.getElementById(saveCancelIconContainerString);
    selectedTableRowInput.value = selectedTableRowValue;

    selectedTableRowLink.style.display = "none";
    deleteEditIconContainer.style.display = "none";
    selectedTableRowInput.style.display = "block";
    saveCancelIconContainer.style.display = "flex";
}

function hideInput(informationId) {
    let selectedTableRowLinkString = "table-row-link-" + informationId;
    let deleteEditIconContainerString = "delete-edit-icon-container-" + informationId;
    let selectedTableRowLink = document.getElementById(selectedTableRowLinkString);
    let deleteEditIconContainer = document.getElementById(deleteEditIconContainerString);
    let selectedTableRowValue = selectedTableRowLink.getAttribute("value");

    let selectedTableRowInputString = "table-row-input-" + informationId;
    let saveCancelIconContainerString = "save-cancel-icon-container-" + informationId;
    let selectedTableRowInput = document.getElementById(selectedTableRowInputString);
    let saveCancelIconContainer = document.getElementById(saveCancelIconContainerString);
    selectedTableRowInput.value = selectedTableRowValue;

    selectedTableRowLink.style.display = "block";
    deleteEditIconContainer.style.display = "flex";
    selectedTableRowInput.style.display = "none";
    saveCancelIconContainer.style.display = "none";
}

function showNewInformationInput() {
    let managementCreateNewInput = document.getElementById("create-new-input");
    let createNewSaveCancelContainer = document.getElementById("create-new-save-cancel-icon-container");

    managementCreateNewInput.style.display = "block";
    managementCreateNewInput.focus();
    createNewSaveCancelContainer.style.display = "flex";
}

function hideCreateNewInput() {
    let managementCreateNewInput = document.getElementById("create-new-input");
    let createNewSaveCancelContainer = document.getElementById("create-new-save-cancel-icon-container");

    managementCreateNewInput.style.display = "none";
    createNewSaveCancelContainer.style.display = "none";
}

// Submit the form with ajax, to avoid reloading the window.
function submitForm() {
    jsRoutes.controllers.ManagementController.saveInformation().ajax({
        data: $('#management-form').serialize(),
        success: function (data) {
            sessionStorage.setItem("reloading", "true");
            location.reload();
        },
        error: function (err) {
            alert("Saving failed!\n Please try again.");
        }
    });
}

// Delete an information after confirming a dialog
function deleteInformation(urlToDelete, informationName) {
    let dialogResult = confirm("Are you sure you want to delete '" +
        informationName + "'?");
    if (dialogResult) {
        $.ajax({
            url: urlToDelete,
            type: 'DELETE',
            success: function (results) {
                sessionStorage.setItem("delete_success_reloading", "true");
                location.reload();
            },
            error: function (err) {
                sessionStorage.setItem("delete_error_reloading", "true");
                location.reload();
            }
        });
    }
}

// Delete an function after confirming a dialog
function deleteFunction(urlToDelete, functionName) {
    let dialogResult = confirm("Are you sure you want to delete '" +
        functionName + "'?");
    if (dialogResult) {
        $.ajax({
            url: urlToDelete,
            type: 'DELETE',
            success: function (results) {
                sessionStorage.setItem("delete_success_reloading", "true");
                location.reload();
            },
            error: function (err) {
                sessionStorage.setItem("delete_error_reloading", "true");
                location.reload();
            }
        });
    }
}

// Delete an function after confirming a dialog
function deleteFunctionCategory(urlToDelete, categoryName) {
    let dialogResult = confirm("Are you sure you want to delete '" +
        categoryName + "'?");
    if (dialogResult) {
        $.ajax({
            url: urlToDelete,
            type: 'DELETE',
            success: function (results) {
                sessionStorage.setItem("delete_success_reloading", "true");
                location.reload();
            },
            error: function (err) {
                sessionStorage.setItem("delete_error_reloading", "true");
                location.reload();
            }
        });
    }
}

// Submit the form with ajax, to avoid reloading the window.
function submitNewInformation() {
    jsRoutes.controllers.ManagementController.createNewInformation().ajax({
        data: $('#management-form').serialize(),
        success: function (data) {
            sessionStorage.setItem("save_success_reloading", "true");
            location.reload();
        },
        error: function (err) {
            sessionStorage.setItem("error_reloading", "true");
        }
    });
}

$(document).ready(function () {
    window.onload = function () {
        let saveSuccessReloading = sessionStorage.getItem("save_success_reloading");
        let deleteSuccessReloading = sessionStorage.getItem("delete_success_reloading");

        if (saveSuccessReloading) {
            sessionStorage.removeItem("save_success_reloading");
            console.log("SAVE");
        }

        if (deleteSuccessReloading) {
            sessionStorage.removeItem("delete_success_reloading");
            console.log("DELETE");
        }

        if (deleteSuccessReloading) {
            sessionStorage.removeItem("error_reloading");
            console.log("SAVE ERROR");
        }
    }
});

/* ---- Management Information Search Filter ---- */
function filterInformations() {
    let input, filter, table, tr, td, i;

    input = document.getElementById("information-filter");
    input.style.display = "block";
    input.focus();
    filter = input.value.toUpperCase();
    table = document.getElementById("management-platform-information-table");
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
function resetInformationFilter() {
    let input = document.getElementById("information-filter");
    input.value = "";
    filterInformations();
}

/* Enable Enter on platform filter input. */
let platformFilter = document.getElementById("information-filter");
platformFilter.addEventListener("keyup", function (event) {
    event.preventDefault();
    if (event.keyCode === 13) {
        document.getElementById("filter-button").click();
    }
});


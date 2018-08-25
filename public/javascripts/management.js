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

// Submit the form with ajax, to avoid reloading the window.
function submitForm() {
    jsRoutes.controllers.ManagementController.saveInformation().ajax({
        data: $('#management-form').serialize(),
        success: function (data) {
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
                location.reload();
            }
        });
    }
}



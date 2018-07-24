// This uses ajax to delete a platform
function deletePlatform(urlToDelete) {
    $.ajax({
        url: urlToDelete,
        type: 'DELETE',
        success: function (results) {
            location.reload();
        }
    });
}

// Filter for the platform table
function filterPlatforms() {
    console.log("In function");
    // Declare variables
    let input, filter, table, tr, td, i;
    input = document.getElementById("platform-filter");
    filter = input.value.toUpperCase();
    table = document.getElementById("platform-table");
    tr = table.getElementsByTagName("tr");

    // Loop through all table rows, and hide those who don't match the search query
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

/*
$(document).ready(function () {

});
*/
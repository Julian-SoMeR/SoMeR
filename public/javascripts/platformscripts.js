function deletePlatform(urlToDelete) {
    $.ajax({
        url: urlToDelete,
        type: 'DELETE',
        success: function (results) {
            location.reload();
        }
    });
}
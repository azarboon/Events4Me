$(document).ready(function () {
    $.ajax({
        async: "true",
        type: "GET",
        url: "/userevents",
        dataType: "json",
        success: function(data){
            console.log(data);
        }

    })
});
$(document).ready(function () {
    $("#join-button").click(function() {
        var urlArray= window.location.href.split("/");
        urlArray = urlArray[5];
        $.ajax({
            async: "true",
            type: "GET",
            url: "/join/event/" + urlArray,
            success: function() {
                console.log("Event joined");


            }


        });
    });

});
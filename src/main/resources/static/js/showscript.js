$(document).ready(function () {
    $("#join-button").click(function() {
        var urlArray= window.location.href.split("/");
        urlArray = urlArray[5][0];
        $.ajax({
            async: "true",
            type: "POST",
            url: "/join/event/" + urlArray,
            success: function(data) {
                console.log(data);


            }


        });
    });

});
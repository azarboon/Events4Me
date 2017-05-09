$(document).ready(function () {
    changePic();
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

function changePic() {
    var image = document.getElementById("eventImage")
    if($("#event-category-input").val() === "SPORT"){
        image.setAttribute("src","http://avalimousine.com/wp-content/uploads/2015/02/2013-january-february-1859-magazine-best-of-oregon-portland-best-sporting-event-timbers-game-fc-dallas-winner.jpg?quality=100.3015021919180");
    }else if($("#event-category-input").val() === "BUSINESS"){
        image.setAttribute("src","https://www.makingdifferent.com/wp-content/uploads/2015/04/business-event-planning.jpg");
    } else if($("#event-category-input").val() === "PARTY"){
        image.setAttribute("src","http://www.eventlounge.be/wp-content/gallery/end-year-party/private-party-event-12.jpg");
    }
};

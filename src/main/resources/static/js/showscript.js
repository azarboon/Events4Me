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
    } else if($("#event-category-input").val() === "MOVIES"){
        image.setAttribute("src","http://listsurge.com/wp-content/uploads/2015/08/t-hill-s-top-movies-of-2011-so-far-.jpg");
    } else if($("#event-category-input").val() === "NATURE"){
        image.setAttribute("src","https://www.nature.org/cs/groups/webcontent/@web/@magazine/documents/media/spirit-bear-926-x-518.jpg");
    } else if($("#event-category-input").val() === "DANCE"){
        image.setAttribute("src","http://images.clipartpanda.com/dancer-clipart-6a09711db88a77919169ef8dd649441c.jpg");
    } else if($("#event-category-input").val() === "ART"){
        image.setAttribute("src","https://s-media-cache-ak0.pinimg.com/736x/94/e5/c8/94e5c8f8a38382c6750f26a2467ad670.jpg");
    }
};

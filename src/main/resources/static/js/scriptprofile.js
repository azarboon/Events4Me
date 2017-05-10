$(document).ready(function () {
    if ($("#attending").children().length === 0) {
        $("#attending").hide();

        var p = document.createElement('p');
        p.setAttribute("id", "notattending");
        p.style = "text-align: center";
        p.innerHTML = "You are not attending any events currently";
        document.getElementById('notattending').appendChild(p);



    }
    $.ajax({
        type: "GET",
        url: "/show/timesettings",
        dataType: "json",
        success: function(data) {
            for(var i =0;i < data.length;i++) {

                var td = document.createElement("td");

                td.innerHTML="From: " + data[i].start.hour + ":" + data[i].start.minute + " " + "Till: "  + data[i].end.hour + ":" + data[i].end.minute;
                document.getElementById('timeschedule').appendChild(td);
            }
        }
    });

});
$(document).ready(function () {
    if ($("#attending").children().length === 0) {
        $("#attending").hide();

        var p = document.createElement('p');
        p.setAttribute("id", "notattending");
        p.style = "text-align: center";
        p.innerHTML = "You are not attending any events currently";
        document.getElementById('notattending').appendChild(p);


    }

});
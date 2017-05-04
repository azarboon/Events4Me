$(document).ready(function () {
    $.ajax({
        async: "true",
        type: "GET",
        url: "/event/listFuture",
        dataType: "json",
        success: function(data) {
            console.log(data);
            var ul = document.createElement('ul');
            ul.setAttribute("id", "list");
            document.getElementById('eventList').appendChild(ul);

            data.forEach(function (singleEvent) {
                var li = document.createElement("li");

                ul.appendChild(li);
                var div = document.createElement("div");
                li.appendChild(div);
                div.innerHTML = singleEvent.name;
                var mins = singleEvent.dateTime.minute;
                if (singleEvent.dateTime.minute < 10) {
                    var mins = "0" + singleEvent.dateTime.minute;
                }
                div.innerHTML += " " + singleEvent.dateTime.dayOfMonth + "." + singleEvent.dateTime.monthValue;
                div.innerHTML += "<br/>" + singleEvent.dateTime.dayOfWeek + " Starting:" + singleEvent.dateTime.hour + ":" + mins;
                var form = document.createElement("form");
                form.setAttribute("action","/event/show/" + singleEvent.eventId)
                var buttonView = document.createElement("button");
                buttonView.setAttribute("type","submit");
                buttonView.setAttribute("class","btn btn-default");
                buttonView.innerHTML = "View";

                var form2 = document.createElement("form");
                form2.setAttribute("action","/product/edit/" + singleEvent.eventId)

                var buttonEdit = document.createElement("button");
                buttonEdit.setAttribute("type","submit");
                buttonEdit.setAttribute("class","btn btn-default");
                buttonEdit.innerHTML = "Edit";



                form2.appendChild(buttonEdit);
                form.appendChild(buttonView);
                div.appendChild(form);
                div.appendChild(form2);




            });
        }
    })
});
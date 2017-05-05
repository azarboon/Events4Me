$(document).ready(function () {
    $("#submitUser").hide();

    $("#validate").click(function(){

        if (validate() == true) {
            $("#submitUser").show();
            $("#validate").hide();

        }


    });


});
function validate() {
    var user = document.getElementById('username');
    var first = document.getElementById('firstname');
    var last = document.getElementById('lastname');
    var pass1 = document.getElementById('password1');
    var email = document.getElementById('email');
    var country = document.getElementById('country');

    if (user.value.length <= 0 || first.value.length <= 0 || last.value.length <= 0 || pass1.value.length <= 0 || email.value.length <= 0) {
        alert("Form must be filled out");
        return false;
    }
    return true;
}


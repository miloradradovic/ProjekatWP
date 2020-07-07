let user = null;
$(document).ready(function(){

    $.ajax({
        url: 'getUserByEmail',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.user = data;
            fillTheData();
        }, error: function(data){
            if(data === "400 Bad Request"){
                alert("Something went wrong!");
            }else{
                window.location.href = "../../../forbidden.html";
            }
        }
    })

    function fillTheData(){
        $("#email_input")
            .attr("value", window.user.oldEmail)
        $("#password1_input")
            .attr("value", window.user.password)
        $("#name_input")
            .attr("value", window.user.name)
        $("#surname_input")
            .attr("value", window.user.surname)
    }

    $("#edit_button").click(function(){
        if($("#password1_input").valid() && $("#password2_input").valid() && $("#name_input").valid() && $("#surname_input").valid()){
            let oldEmail = window.user.oldEmail;
            let newEmail = $("#email_input").val();
            let password = $("#password1_input").val();
            let name = $("#name_input").val();
            let surname = $("#surname_input").val();
            $.ajax({
                url: 'editProfile',
                type: 'post',
                data: JSON.stringify({
                    oldEmail: oldEmail,
                    email: newEmail,
                    password: password,
                    organizationName: window.user.organizationName,
                    name: name,
                    surname: surname,
                    userType: window.user.userType
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("Profile successfully updated!");
                        window.location.href = "editProfile.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })

        }
    })
})
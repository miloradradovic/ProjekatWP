let user = null;
$(document).ready(function(){

    $.ajax({
        url: 'getUserByEmail',
        type: 'post',
        data: sessionStorage.getItem("useredit"),
        dataType: 'json',
        success: function(data){
            window.user = data;
            console.log(window.user);
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
            .attr("value", window.user.email)
        $("#password_input")
            .attr("value", window.user.password)
        $("#name_input")
            .attr("value", window.user.name)
        $("#surname_input")
            .attr("value", window.user.surname)
        $("#org_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "org_input")
                .attr("value", window.user.organizationName)
        )
        if(window.user.userType === "Administrator"){
            $("#role_td").append(
                $("<select>")
                    .attr("id", "select_role")
                    .append($("<option>")
                        .attr("value", "Administrator")
                        .attr("label", "Administrator"))
                    .append($("<option>")
                        .attr("value", "User")
                        .attr("label", "User"))
            )
        }else{
            $("#role_td").append(
                $("<select>")
                    .attr("id", "select_role")
                    .append($("<option>")
                        .attr("value", "User")
                        .attr("label", "User"))
                    .append($("<option>")
                        .attr("value", "Administrator")
                        .attr("label", "Administrator"))
            )
        }
    }

    $("#edit_button").click(function(){
        if($("#password_input").valid() && $("#name_input").valid() && $("#surname_input").valid()){
            let email = window.user.email;
            let password = $("#password_input").val();
            console.log("PASSWORD "+password);
            let name = $("#name_input").val();
            let surname = $("#surname_input").val();
            let org = window.user.organizationName;
            let role = $("#select_role").children("option:selected").val();
            $.ajax({
                url: 'editUser',
                type: 'post',
                data: JSON.stringify({
                    email: email,
                    password: password,
                    organizationName: org,
                    name: name,
                    surname: surname,
                    userType: role
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("User successfully edited!");
                        sessionStorage.removeItem("useredit");
                        window.location.href = "../viewUsers/viewUsers.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })

        }
    })

    $("#delete_button").click(function(){
        $.ajax({
            url: 'deleteUser',
            type: 'post',
            data: window.user.email,
            complete: function (response) {
                if (response.status === 200) {
                    alert("User successfully deleted!");
                    sessionStorage.removeItem("useredit");
                    window.location.href = "../viewUsers/viewUsers.html";
                }else if(response.status === 400){
                    alert("Something went wrong!");
                }else{
                    window.location.href = "../../../forbidden.html";
                }
            }
        })
    })

})
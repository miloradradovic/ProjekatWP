let orgs = [];
$(document).ready(function(){

    $.ajax({
        url: 'getOrganizations',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.orgs = data;
            fillInputs();
        }, error(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function fillInputs(){

        window.orgs.forEach(element =>{
            $("#select_organization")
                .append(
                    $("<option>")
                        .attr("value", element.orgName)
                        .attr("label", element.orgName)
                )
        })

        $("#select_role")
            .append(
                $("<option>")
                    .attr("value", "Administrator")
                    .attr("label", "Administrator"),
                $("<option>")
                    .attr("value", "User")
                    .attr("label", "User")
            )

    }

    $("#addUserbutton").click(function(){

        if($("#email_input").valid() && $("#password_input").valid() && $("#name_input").valid() && $("#surname_input").valid()){
            let org_name = $("#select_organization").children("option:selected").val();
            let email = $("#email_input").val();
            let password = $("#password_input").val();
            let name = $("#name_input").val();
            let surname = $("#surname_input").val();
            let role = $("#select_role").children("option:selected").val();

            $.ajax({
                url: 'addUser',
                type: 'post',
                data: JSON.stringify({
                    email: email,
                    password: password,
                    name: name,
                    surname: surname,
                    organizationName: org_name,
                    userType: role
                }),
                dataType: 'json',
                complete: function(response){
                    if(response.status === 200){
                        alert("User successfully added!");
                        window.location.href = "../viewUsers/viewUsers.html";
                    }else if(response.status === 403){
                        window.location.href = "../../../forbidden.html";
                    }else{
                        alert("Something went wrong!");
                    }
                }
            })
        }
    })

})
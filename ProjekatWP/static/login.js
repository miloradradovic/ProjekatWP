$(document).ready(function(){


    $.ajax({
        url: 'getCurrentUser',
        type: 'get',
        dataType: 'json',
        complete: function(response){
            if(response.responseText === "1"){
                window.location.href = "User/VMs/viewVMs/viewVMs.html";
            }else if(response.responseText === "2"){
                window.location.href = "Administrator/VMs/viewVMs/viewVMs.html";
            }else if(response.responseText === "3"){
                window.location.href = "SuperAdministrator/VMs/viewVMs/viewVMs.html";
            }
        }
    })

    $("#button_login").click(function(){
        login();
    })

    $(document).on('keypress',function(e) {
        if(e.which === 13) {
            login();
        }
    });

    function login(){
        let email = $("#email_input").val();
        let password = $("#pass_input").val();

        if($("#email_input").valid() && $("#pass_input").valid()) {
            $.ajax({
                url: 'login',
                type: 'post',
                data: JSON.stringify({email: email, password: password}),
                complete: function (response) {
                    if (response.status === 200) {
                        if (response.responseText === "superadmin") {
                            window.location.href = "SuperAdministrator/VMs/viewVMs/viewVMs.html";
                        } else if (response.responseText === "admin") {
                            window.location.href = "Administrator/VMs/viewVMs/viewVMs.html";
                        } else if (response.responseText === "user") {
                            window.location.href = "User/VMs/viewVMs/viewVMs.html";
                        }
                    } else if (response.status === 400) {
                        alert("Something went wrong!");
                    }
                }
            })
        }
    }
})
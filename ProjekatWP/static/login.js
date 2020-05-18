$(document).ready(function(){

    $("#login_form").submit(function(){
        let email = $("#email_input").val();
        let password = $("#pass_input").val();

        $.ajax({
            url: 'login',
            type: 'post',
            data: JSON.stringify({email: email, password: password}),
            complete: function(response) {
                if (response.status === 200) {
                    if(response.responseText === "superadmin"){
                        window.location.href = "SuperAdministrator/VMs/viewVMs/viewVMs.html";
                    }else if(response.responseText === "admin"){
                        window.location.href = "Administrator/VMs/viewVMs/viewVMs.html";
                    }else if(response.responseText === "user"){
                        window.location.href = "User/VMs/viewVMs/viewVMs.html";
                    }
                } else if (response.status === 400) {
                    alert("User does not exist!");
                }
            }
        })
    })
})
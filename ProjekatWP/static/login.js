$(document).ready(function(){

    $("#button_login").click(function(){
        let email = $("#email_input").val();
        let password = $("#pass_input").val();
        if(email === ""){
            $("#email_input").removeClass("valid_input");
            $("#email_input").addClass("invalid_input");
        }else{
            $("#email_input").removeClass("invalid_input");
            $("#email_input").addClass("valid_input");
        }
        if(password === "" || password.length < 8){
            $("#pass_input").removeClass("valid_input");
            $("#pass_input").addClass("invalid_input");
        }else{
            $("#pass_input").removeClass("invalid_input");
            $("#pass_input").addClass("valid_input");
        }
        if(email !== "" && password !== ""  && password.length >= 8){
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
        }
    })

})
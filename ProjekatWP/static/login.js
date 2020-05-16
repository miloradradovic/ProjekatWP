$(document).ready(function(){

    $("#button_login").click(function(){
        let email = $("#email_input").val();
        let password = $("#pass_input").val();
        if(email === "" || password === ""){
            alert("Both fields must not be empty!")
        }else{
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
                        alert("CRKNI");
                    }
                }
            })
        }
    })

})
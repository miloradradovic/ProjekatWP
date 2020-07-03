$(document).ready(function(){


    $.ajax({
        url: 'logout',
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

})
$(document).ready(function(){


    $.ajax({
        url: 'logout',
        type: 'get',
        dataType: 'json',
        complete: function(data){
            console.log("usao");
            window.location.href = "login.html";
        }
    })

})
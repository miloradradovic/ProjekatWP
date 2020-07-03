$(document).ready(function(){


    $.ajax({
        url: 'logout',
        type: 'get',
        dataType: 'json',
        complete: function(response){
            if(response.responseText === "200 OK"){
                window.location.href = "index.html";
            }else{
                alert("Something went wrong")
                window.location.href = "index.html";
            }
        }
    })

})
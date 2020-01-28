$(document).ready(function(){
    
    function myFunction(){
        var s = localStorage.getItem("USER")
        $.ajax({
            type: "GET",
            url: "/preload",
            data: 0,
            contentType:"application/json",
            dataType: "json",
            error: function(data){
               alert("Niste ulogovani!");
               window.location.href = "/index.html"; 
            }
        })
    }
    
})
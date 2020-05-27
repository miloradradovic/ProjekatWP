let users = []
$(document).ready(function(){

    $.ajax({
        url: 'getUsers',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.users = data;
            fillTable();
        },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function fillTable(){
        $("#tableUsers tbody").children().remove();
        window.users.forEach(element => {
            $("#tableUsers tbody").append($("<tr>")
                .click(function(){
                    sessionStorage.setItem("useredit", element.email);
                    window.location.href = "../../Users/editUser/editUser.html";
                })
                .attr("title", "Click to edit the User")
                .attr("id", element.email)
                .append($("<td>")
                    .text(element.email))
                .append($("<td>")
                    .text(element.name))
                .append($("<td>")
                    .text(element.surname))
                .append($("<td>")
                    .text(element.organizationName))
            )
        })
    }

})
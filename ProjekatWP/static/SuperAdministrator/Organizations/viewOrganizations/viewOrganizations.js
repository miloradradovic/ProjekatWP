let organizations = [];
$(document).ready(function() {


    $.ajax({
        url: 'getOrganizations',
        type: 'get',
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            window.organizations = data;
            updateTable();
            },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function updateTable(){
        $("#tableO tbody").children().remove();
        window.organizations.forEach(element => {
            $("#tableO tbody").append($("<tr>")
                .click(function(){
                    sessionStorage.setItem("organizationedit", element.orgName);
                    window.location.href = "../editOrganization/editOrganization.html";
                })
                .attr("title", "Click to edit the organization")
                .attr("id", element.orgName)
                .append($("<td>")
                    .text(element.orgName))
                .append($("<td>")
                    .text(element.description))
                .append($("<td>")
                    .append($("<img>")
                        .attr("src", element.logo)))
            )
        })
    }

    $("#addorganization").click(function(){
        window.location.href = "../addOrganization/addOrganization.html";
    })

    

})
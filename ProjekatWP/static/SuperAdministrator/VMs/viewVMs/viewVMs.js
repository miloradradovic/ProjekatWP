let vms = [];
$(document).ready(function() {

    $.ajax({
        url: 'getCurrentUser',
        type: 'get',
        dataType: 'json',
        complete: function(response){
            if(response.status === 200){
                $.ajax({
                    url: 'getVMs',
                    type: 'get',
                    contentType: "application/json",
                    dataType: "json",
                    success: function(data){
                        window.vms = data;
                        updateTable();
                    },error: function(data){
                        alert("UNAUTHORIZED!");
                        window.location.href = "../../../login.html";
                    }
                })
            }else if(response.status === 403){
                alert("UNAUTHORIZED!!!");
                window.location.href = "../../../login.html";
            }
        }
    })

    function updateTable(){
        window.vms.forEach(element => {
            $("#tableVMs").append($("<tr>")
                .click(function(){
                    sessionStorage.setItem("vmedit", element.resourceName);
                    window.location.href = "../editVM/editVM.html";
                })
                .attr("title", "Click to edit the VM")
                .attr("id", element.resourceName)
                .append($("<td>")
                    .text(element.resourceName))
                .append($("<td>")
                    .text(element.numberOfCores))
                .append($("<td>")
                    .text(element.RAM))
                .append($("<td>")
                    .text(element.GPU))
                .append($("<td>")
                    .text(element.organizationName))
            )
        })
    }

    $("#addvm").click(function(){
        window.location.href = "../addVM/addVM.html";
    })

})
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
                        alert("Error!");
                    }
                })
            }else if(response.status === 403){
                alert("UNAUTHORIZED!!!");
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
                .append($("<td>")
                    .append($("<a>")
                        .attr("href", "../editVM/editVM.html")
                        .attr("id", element.resourceName)
                        .text("Edit")
                        .click(function(){
                            sessionStorage.setItem("vmedit", element.resourceName);
                        })))
                .append($("<td>")
                    .append($("<a>")
                        .attr("href", "")
                        .attr("id", element.resourceName)
                        .text("Delete")
                        .click(function(){
                            $.ajax
                            ({
                                type: "post",
                                url: "deleteVM",
                                data: element.resourceName,
                                complete: function(element)
                                {
                                    $('#' + element.resourceName).remove();
                                    alert("VM deleted successfully!");
                                }
                            })
                        })))
            )
        })
    }

})
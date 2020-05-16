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
                        console.log(window.vms)
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
            console.log(element);
            $("#tableVMs").append($("<tr>")
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
                        .attr("href", "")
                        .attr("id", element.resourceName)
                        .attr("onclick", "editVM(element)")
                        .text("Edit")))
                .append($("<td>")
                    .append($("<a>")
                        .attr("href", "")
                        .attr("id", element.resourceName)
                        .text("Delete")
                        .click(function(){
                            deleteVM(element)
                        })))
            )
        })
    }

    function deleteVM(data){

        $.ajax
        ({
            type: "post",
            url: "deleteVM",
            data: data.resourceName,
            complete: function(data)
            {
                $('#' + data.resourceName).remove();
                alert("VM deleted successfully!");
            }
        })
    }

    function editVM(data){
        localStorage.setItem("vmedit", data);
        window.location.href = "../editVM/editVM.html";
    }

})
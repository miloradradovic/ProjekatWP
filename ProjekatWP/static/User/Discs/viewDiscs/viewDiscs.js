let discs = [];
$(document).ready(function() {

    $.ajax({
        url: 'getDiscs',
        type: 'get',
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            window.discs = data;
            console.log(window.discs);
            updateTable();
            },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function updateTable(){
        $("#tableDiscs tbody").children().remove();
        window.discs.forEach(element => {
            $("#tableDiscs tbody").append($("<tr>")
                .click(function(){
                    sessionStorage.setItem("discedit", element.resourceName);
                    window.location.href = "../editDisc/editDisc.html";
                })
                .attr("title", "Click to edit the disc")
                .attr("id", element.resourceName)
                .append($("<td>")
                    .text(element.resourceName))
                .append($("<td>")
                    .text(element.capacity))
                .append($("<td>")
                    .text(element.vmName))
            )
        })
    }


})
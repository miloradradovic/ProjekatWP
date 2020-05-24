let vms = [];
$(document).ready(function() {

    $("#searchfilterdiv").hide();

    $.ajax({
        url: 'getVMs',
        type: 'get',
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            window.vms = data;
            updateTable();
            },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function updateTable(){
        $("#tableVMs tbody").children().remove();;
        window.vms.forEach(element => {
            $("#tableVMs tbody").append($("<tr>")
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

    $("#showsearch").click(function(){
        if($(this).attr("name") === "show"){
            $(this).attr("name", "hide");
            $(this).html("Hide search/filter form");
            $("#searchfilterdiv").show();
        }else{
            $(this).attr("name", "show");
            $(this).html("Show search/filter form");
            $("#searchfilterdiv").hide();
        }
    })

    $("#search_filter_button").click(function(){
        let vmName = $("#vm_name_search_input").val();
        let coresFrom = $("#num_of_cores_from").val();
        if(coresFrom === ""){
            coresFrom = -1;
        }
        let coresTo = $("#num_of_cores_to").val();
        if(coresTo === ""){
            coresTo = -1;
        }
        let ramFrom = $("#ram_from").val();
        if(ramFrom === ""){
            ramFrom = -1;
        }
        let ramTo = $("#ram_to").val();
        if(ramTo === ""){
            ramTo = -1;
        }
        let gpuFrom = $("#gpu_from").val();
        if(gpuFrom === ""){
            gpuFrom = -1;
        }
        let gpuTo = $("#gpu_to").val();
        if(gpuTo === ""){
            gpuTo = -1;
        }
        $.ajax({
            url: 'searchVM',
            type: 'post',
            data: JSON.stringify({
                vmName: vmName,
                coresFrom: coresFrom,
                coresTo : coresTo,
                ramFrom : ramFrom,
                ramTo : ramTo,
                gpuFrom: gpuFrom,
                gpuTo : gpuTo
            }),
            success: function(data){
                window.vms = data;
                updateTable();
            },error: function(data){
                if(data === "400 bad request"){
                    alert("Something went wrong!");
                }else{
                    window.location.href = "../../../forbidden.html";
                }
            }
        })
    })

})
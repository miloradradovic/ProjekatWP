let disc = null;
let vms = [];
$(document).ready(function(){

    $.ajax({
        url: 'getDiscByName',
        type: 'post',
        data: sessionStorage.getItem("discedit"),
        dataType: 'json',
        success: function(data){
            window.disc = data;
            fillInputs();
        }, error: function(data){
            if(data === "400 bad request"){
                alert("Something went wrong!");
            }else{
                window.location.href = "../../../forbidden.html";
            }
        }
    })

    function fillInputs(){

        console.log(window.disc);


        $("#name_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "disc_name_input")
                .attr("name", "disc_name_input")
                .attr("value", window.disc.resourceName)
        )

        $("#org_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "organization_input")
                .attr("value", window.disc.organizationName)
        )

        $("#cap_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "capacity_input")
                .attr("value", window.disc.capacity)
        )

        $("#disc_type_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "capacity_input")
                .attr("value", window.disc.type)
        )

        $("#vm_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "capacity_input")
                .attr("value", window.disc.vmName)
        )
        

    }




})
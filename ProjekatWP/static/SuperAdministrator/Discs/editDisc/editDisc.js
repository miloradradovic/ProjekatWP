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
            getAvailableVMs(window.disc.organizationName);
        }, error: function(data){
            if(data === "400 Bad Request"){
                alert("Something went wrong!");
            }else{
                window.location.href = "../../../forbidden.html";
            }
        }
    })

    function fillInputs(){

        $("#name_td").append(
            $("<input>")
                .attr("type", "text")
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
                .attr("id", "capacity_input")
                .attr("value", window.disc.capacity)
        )
        
        if(window.disc.type === "HDD"){
            $("#select_disc_type").append(
                $("<option>")
                .attr("label", "HDD")
                .attr("value", "HDD")
            )
            $("#select_disc_type").append(
                $("<option>")
                .attr("label", "SSD")
                .attr("value", "SSD")
            )
        }else{
            $("#select_disc_type").append(
                $("<option>")
                .attr("label", "SSD")
                .attr("value", "SSD")
            )
            $("#select_disc_type").append(
                $("<option>")
                .attr("label", "HDD")
                .attr("value", "HDD")
            )
        }


    }

    function getAvailableVMs(orgname){
        $.ajax({
            url: 'getAvailableVMs',
            type: 'post',
            data: orgname,
            dataType: 'json',
            success: function(data){
                window.vms = data;
                if(window.disc.vmName === ""){
                    $("#select_vm").append(
                        $("<option>")
                            .attr("value", "")
                            .attr("label", "")
                    )
                    window.vms.forEach(element => {

                            $("#select_vm").append(
                                $("<option>")
                                    .attr("label", element.resourceName)
                                    .attr("value", element.resourceName)
                            )

                    })

                }else {
                    window.vms.forEach(element => {
                        if(element.resourceName === window.disc.vmName){
                            $("#select_vm").append(
                                $("<option>")
                                    .attr("label", element.resourceName)
                                    .attr("value", element.resourceName)
                            )
                        }
                    })
                    window.vms.forEach(element => {
                        if(element.resourceName !== window.disc.vmName){
                            $("#select_vm").append(
                                $("<option>")
                                    .attr("label", element.resourceName)
                                    .attr("value", element.resourceName)
                            )
                        }
                    })
                    $("#select_vm").append(
                        $("<option>")
                            .attr("value", "")
                            .attr("label", "")
                    )
                }
                fillInputs();
            }, error: function(data){
                if(data === "400 Bad Request"){
                    alert("Something went wrong!");
                }else{
                    window.location.href = "../../../forbidden.html";
                }
            }
        })
    }

    $("#edit_button").click(function() {
        if($("#disc_name_input").valid()) {
            let oldResourceName = window.disc.oldResourceName;
            let newResourceName = $("#disc_name_input").val();
            let organizationName = $("#organization_input").val();
            let selected_disc_type = $("#select_disc_type").children("option:selected").val();
            let capacity = $("#capacity_input").val();
            let vm = $("#select_vm").children("option:selected").val();
            
            $.ajax({
                url: 'editDisc',
                type: 'post',
                data: JSON.stringify({
                    oldResourceName: oldResourceName,
                    resourceName: newResourceName,
                    organizationName: organizationName,
                    type: selected_disc_type,
                    capacity: capacity,
                    vmName: vm
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("Disc successfully edited!");
                        sessionStorage.removeItem("discedit");
                        window.location.href = "../viewDiscs/viewDiscs.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })
        }
    })


    $("#delete_button").click(function(){
        $.ajax({
            url: 'deleteDisc',
            type: 'post',
            data: sessionStorage.getItem("discedit"),
            dataType: 'json',
            complete: function(response){
                if(response.status === 200){
                    sessionStorage.removeItem("discedit");
                    alert("Disc successfully deleted!")
                    window.location.href = "../viewDiscs/viewDiscs.html";
                }else if(response.status === 400){
                    alert("Something went wrong!");
                }
                else{
                    window.location.href = "../../../forbidden.html";
                }
            }
        })
    })

})
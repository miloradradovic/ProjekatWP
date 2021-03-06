let orgs = [];
let vms = [];
$(document).ready(function(){

    $.ajax({
        url: 'getOrganizations',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.orgs = data;
            if(window.orgs.length === 0){
                alert("Please add the organization first!");
                window.location.href = "../viewDiscs/viewDiscs.html";
            }else{
                fillInputs();
            }
        }, error(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function fillInputs(){

        window.orgs.forEach(element =>{
            $("#select_organization")
                .append(
                    $("<option>")
                        .attr("value", element.orgName)
                        .attr("label", element.orgName)
                )
        })
        getAvailableVMs(window.orgs[0].orgName)


    }

    $("#select_organization").change(function(){
        let selected = $(this).children("option:selected").val();
        $("#select_vm").empty();
        getAvailableVMs(selected);
    })

    function getAvailableVMs(orgname){
        $.ajax({
            url: 'getAvailableVMs',
            type: 'post',
            data: orgname,
            dataType: 'json',
            success: function(data){
                window.vms = data;
                $("#select_vm").append(
                     $("<option>")
                         .attr("label", "")
                         .attr("value", "")
                )
                window.vms.forEach(element => {
                    $("#select_vm").append(
                        $("<option>")
                            .attr("label", element.resourceName)
                            .attr("value", element.resourceName)
                    )
                })
            }, error: function(data){
                if(data === "400 Bad Request"){
                    alert("Something went wrong!");
                }else{
                    window.location.href = "../../../forbidden.html";
                }
            }
        })
    }

    $("#addDiscbutton").click(function(){

        if($("#disc_name_input").valid() && $("#select_disc_type").valid() && $("#capacity_input").valid()){
            let org_name = $("#select_organization").children("option:selected").val();
            let disc_name = $("#disc_name_input").val();
            let selected_disc_type = $("#select_disc_type").children("option:selected").val();
            let capacity = $("#capacity_input").val();
            let vm = $("#select_vm").children("option:selected").val();

            $.ajax({
                url: 'addDisc',
                type: 'post',
                data: JSON.stringify({
                    resourceName: disc_name,
                    organizationName: org_name,
                    type: selected_disc_type,
                    capacity: capacity,
                    vmName: vm
                }),
                dataType: 'json',
                complete: function(response){
                    if(response.status === 200){
                        alert("Disc successfully added!");
                        window.location.href = "../viewDiscs/viewDiscs.html";
                    }else if(response.status === 403){
                        window.location.href = "../../../forbidden.html";
                    }else{
                        alert("Something went wrong!");
                    }
                }
            })
        }


    })

})
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
                $.ajax({
                    url: 'getVMs',
                    type: 'get',
                    dataType: 'json',
                    success: function(data){
                        window.vms = data;
                        if(window.vms.length === 0){
                            alert("Please add at least one VM first!")
                            window.location.href = "../viewDiscs/viewDiscs.html";
                        }else{
                            fillInputs();
                        }
                    }, error: function(data){
                        window.location.href = "../../../forbidden.html";
                    }
                })
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
        window.vms.forEach(element =>{
            $("#select_vm")
                .append(
                    $("<option>")
                        .attr("value", element.resourceName)
                        .attr("label", element.resourceName)
                )
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
let orgs = [];
let vms = [];
$(document).ready(function(){


    $.ajax({
        url: 'getOrganization',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.org = data;
            getAvailableVMs(data);
        }, error: function(data){
            window.location.href = "../../../forbidden.html";
            console.log("bla");
        }
    })

    function fillInputs(){

        $("#organization_input").attr("value", window.org.orgName);

        
    }


    function getAvailableVMs(orgname){
        $.ajax({
            url: 'getAvailableVMs',
            type: 'post',
            data: orgname,
            dataType: 'json',
            success: function(data){
                window.vms = data;
                window.vms.forEach(element => {
                    $("#select_vm").append(
                        $("<option>")
                            .attr("label", element.resourceName)
                            .attr("value", element.resourceName)
                    )
                })
                fillInputs();
            }, error: function(data){
                if(data === "400 bad request"){
                    alert("Something went wrong!");
                }else{
                    window.location.href = "../../../forbidden.html";
                    console.log("blablabla");
                }
            }
        })
    }

    $("#addDiscbutton").click(function(){

        if($("#disc_name_input").valid() && $("#select_disc_type").valid() && $("#capacity_input").valid()){
            let org_name = $("#organization_input").attr("value");
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
                        console.log("bla4");
                    }else{
                        alert("Something went wrong!");
                    }
                }
            })
        }


    })

})
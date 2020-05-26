let categories = [];
let discs = [];
let org = null;
$(document).ready(function(){

    $.ajax({
        url: 'getCategories',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.categories = data;
            if(window.categories.length === 0){
                alert("Please add at least one category first!");
                window.location.href = "../viewVMs/viewVMs.html";
            }else{
                $.ajax({
                    url: 'getDiscs',
                    type: 'get',
                    dataType: 'json',
                    success: function(data){
                        window.discs = data;
                        $.ajax({
                            url: 'getOrganization',
                            type: 'get',
                            dataType: 'json',
                            success: function(data){
                                window.org = data;
                                fillInputs()
                            }, error: function(data){
                                window.location.href = "../../../forbidden.html";
                            }
                        })
                    },
                    error: function(data){
                        window.location.href = "../../../forbidden.html";
                    }
                })
            }
        },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function fillInputs(){

        $("#select_discs").attr("title", "Hold down the CTRL button and click to select");

        $("#organization_input").attr("value", window.org.orgName);

        window.categories.forEach(element =>{
            $("#select_category")
                .append(
                    $("<option>")
                        .attr("value", element.categoryName)
                        .attr("label", element.categoryName)
                )
        })

        $("#num_of_cores_input").attr("value", window.categories[0].numberOfCores);
        $("#ram_input").attr("value", window.categories[0].RAM);
        $("#gpu_input").attr("value", window.categories[0].GPU);

        window.discs.forEach(element => {
            $("#select_discs").append(
                $("<option>")
                    .attr("label", element.resourceName)
                    .attr("value", element.resourceName)
            )
        })

    }

    $("#select_category").change(function(){
        let catname = $(this).children("option:selected").val()
        window.categories.forEach(element =>{
            if(element.categoryName === catname){
                $("#num_of_cores_input")
                    .attr("value", element.numberOfCores)
                $("#ram_input")
                    .attr("value", element.RAM);
                $("#gpu_input")
                    .attr("value", element.GPU);
            }
        })
    })

    $("#addVMbutton").click(function(){

        if($("#vm_name_input").valid()){
            let org_name = $("#organization_input").attr("value");
            let vm_name = $("#vm_name_input").val();
            let selected_category = $("#select_category").children("option:selected").val();
            let num_of_cores = $("#num_of_cores_input").val();
            let ram = $("#ram_input").val();
            let gpu = $("#gpu_input").val();
            let discs = $("#select_discs").val();

            $.ajax({
                url: 'addVM',
                type: 'post',
                data: JSON.stringify({
                    resourceName: vm_name,
                    organizationName: org_name,
                    categoryName: selected_category,
                    numberOfCores: num_of_cores,
                    RAM: ram,
                    GPU: gpu,
                    connectedDiscs: discs
                }),
                dataType: 'json',
                complete: function(response){
                    if(response.status === 200){
                        alert("VM successfully added!");
                        window.location.href = "../viewVMs/viewVMs.html";
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
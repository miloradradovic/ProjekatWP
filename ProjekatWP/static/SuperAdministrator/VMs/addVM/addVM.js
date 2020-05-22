let orgs = [];
let categories = [];
let discs = [];
$(document).ready(function(){

    $.ajax({
        url: 'getOrganizations',
        type: 'get',
        dataType: 'json',
        success: function(data){
            window.orgs = data;
            if(window.orgs.length === 0){
                alert("Please add the organization first!");
                window.location.href = "../viewVMs/viewVMs.html";
            }else{
                $.ajax({
                    url: 'getCategories',
                    type: 'get',
                    dataType: 'json',
                    success: function(data){
                        window.categories = data;
                        if(window.categories.length === 0){
                            alert("Please add at least one category first!")
                            window.location.href = "../viewVMs/viewVMs.html";
                        }else{
                            fillInputs();
                        }
                    }, error: function(data){
                        alert("UNAUTHORIZED!!!");
                        window.location.href = "../../../login.html";
                    }
                })
            }
        }, error(data){
            alert("UNAUTHORIZED!!!");
            window.location.href = "../../../login.html";
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
                .change(function(){
                    let selected = $(this).children("option:selected").val();
                    $.ajax({
                        url: 'getAvailableDiscs',
                        type: 'post',
                        data: selected,
                        dataType: 'json',
                        success: function(data){
                            window.discs = data;
                            window.discs.forEach(element => {
                                $("#select_discs").append(
                                    $("<option>")
                                        .attr("label", element.resourceName)
                                        .attr("value", element.resourceName)
                                )
                            })
                        }, error: function(data){
                            alert("UNAUTHORIZED!!!");
                            window.location.href = "../../../login.html";
                        }
                    })
                })

        })
        window.categories.forEach(element =>{
            $("#select_category")
                .append(
                    $("<option>")
                        .attr("value", element.categoryName)
                        .attr("label", element.categoryName)
                )
                .change(function(){
                    $("#num_of_cores_input")
                        .attr("value", element.numberOfCores)
                    $("#ram_input")
                        .attr("value", element.RAM);
                    $("#gpu_input")
                        .attr("value", element.GPU);
                })
        })

        $("#num_of_cores_input")
            .attr("value", window.categories[0].numberOfCores)
        $("#ram_input")
            .attr("value", window.categories[0].RAM);
        $("#gpu_input")
            .attr("value", window.categories[0].GPU);

    }

})
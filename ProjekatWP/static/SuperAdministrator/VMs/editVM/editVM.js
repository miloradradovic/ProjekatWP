let vm = null;
let categories = [];
$(document).ready(function(){

    $.ajax({
        url: 'getVMByName',
        type: 'post',
        data: sessionStorage.getItem("vmedit"),
        dataType: 'json',
        success: function(data){
            window.vm = data;
            console.log(window.vm);
            fillInputs();
        }, error: function(data){
            alert("UNAUTHORIZED!!!");
        }
    })

    $("#select_new_category").change(function(){
        window.categories.forEach(element =>{
            if(element.categoryName === $(this).val()){
                $("#category_input").attr("value", element.categoryName);
                $("#num_of_cores_input").attr("value", element.numberOfCores);
                $("#ram_input").attr("value", element.RAM);
                $("#gpu_input").attr("value", element.GPU);
            }
        })
    })

    function fillInputs(){
        $("#vm_name_input").attr("value", window.vm.resourceName);
        $("#organization_input").attr("value", window.vm.organizationName);
        $("#category_input").attr("value", window.vm.categoryName);
        getCategories();
        $("#num_of_cores_input").attr("value", window.vm.numberOfCores);
        $("#ram_input").attr("value", window.vm.RAM);
        $("#gpu_input").attr("value", window.vm.GPU);
        window.vm.connectedDiscs.forEach(element => {
            $("#connected_discs").append(
                $("<option>")
                    .attr("value", element)
                    .attr("label", element)
            )
        })
        window.vm.activityFROM.forEach(element => {
            $("#table_of_activites_on").append($("<tr>")
                .append($("<td>")
                    .text(element)
                )
            )
        })
        window.vm.activityTO.forEach(element => {
            $("#table_of_activites_off").append($("<tr>")
                .append($("<td>")
                    .text(element)
                )
            )
        })

    }

    function getCategories(){
        $.ajax({
            url: 'getCategories',
            type: 'get',
            dataType: 'json',
            success: function(data){
                window.categories = data;
                console.log(window.categories);
                window.categories.forEach(element => {
                    console.log(element);
                    $("#select_new_category").append(
                        $("<option>")
                            .attr("value", element.categoryName)
                            .attr("label", element.categoryName)
                    )
                })
            }, error: function(data){
                alert("UNAUTHORIZED!!!");
            }
        })
    }

    $("#turn_onoff").click(function(){
        if($("#turn_onoff").text() === "Turn on"){
            $("#turn_onoff").html("Turn off");
            let today = new Date();
            let date = today.getDate() + '-' + (today.getMonth()+1) + '-' + today.getFullYear() + ' ' + today.getHours() + ':' + today.getMinutes();
            let dateString = date.toString();
            $("#table_of_activites_on").append($("<tr>")
                .append($("<td>")
                    .text(dateString)
                )
            )
        }else{
            $("#turn_onoff").html("Turn on");
            let today = new Date();
            let date = today.getDate() + '-' + (today.getMonth()+1) + '-' + today.getFullYear() + ' ' + today.getHours() + ':' + today.getMinutes();
            let dateString = date.toString();
            $("#table_of_activities_off").append($("<tr>")
                .append($("<td>")
                    .text(dateString)
                )
            )
        }
    })

    $("#edit_button").click(function(){
        let oldResourceName = vm.oldResourceName;
        //TODO
    })


})
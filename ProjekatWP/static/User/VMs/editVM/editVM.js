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
            console.log("USPJESNO");
            alert("UPRAVO ULAZIM U FILL INPUTS");
            fillInputs();
        }, error: function(data){
            if(data === "400 bad request"){
                alert("Something went wrong!");
            }else{
                console.log(data);
                window.location.href = "../../../forbidden.html";
            }
        }
    })

    function fillInputs(){
        $("#vm_td").append(
            $("<input>")
                .attr("readonly", 'true')
                .attr("type", "text")
                .attr("id", "vm_name_input")
                .attr("name", "vm_name_input")
                .attr("value", window.vm.resourceName)
        )
        $("#org_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("readonly", "true")
                .attr("id", "organization_input")
                .attr("value", window.vm.organizationName)
        )
        $("#cat_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("id", "category_input")
                .attr("readonly", 'true')
                .attr("value", window.vm.categoryName)
        )

        $("#cores_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("readonly", "true")
                .attr("id", "num_of_cores_input")
                .attr("value", window.vm.numberOfCores)
        )

        $("#ram_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("readonly", "true")
                .attr("id", "ram_input")
                .attr("value", window.vm.RAM)
        )

        $("#gpu_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("readonly", "true")
                .attr("id", "gpu_input")
                .attr("value", window.vm.GPU)
        )

        $("#discs_td").append(
            $("<select>")
                .attr("id", "connectedDiscs")
        )

        window.vm.connectedDiscs.forEach(element => {
            console.log("DISK "+ element);
            $("#connectedDiscs").append(
                $("<option>")
                    .attr("value", element)
                    .attr("label", element)
            )
        })

        if(window.vm.activityFROM.length > window.vm.activityTO.length){

            window.vm.activityTO.forEach(element => {
                let index = window.vm.activityTO.indexOf(element);
                let elementFROM = window.vm.activityFROM[index];
                $("#table_of_activities").append($("<tr>")
                    .append($("<td>")
                        .text(elementFROM))
                    .append($("<td>")
                        .text(element)))
            })
            let last_element = window.vm.activityFROM[window.vm.activityFROM.length - 1];
            $("#table_of_activities").append($("<tr>")
                .attr("id", "last")
                .append($("<td>")
                    .text(last_element)))
        }else{

            window.vm.activityFROM.forEach(element =>{
                let index = window.vm.activityFROM.indexOf(element);
                let elementTO = window.vm.activityTO[index];
                $("#table_of_activities").append($("<tr>")
                    .attr("id", "last")
                    .append($("<td>")
                        .text(element))
                    .append($("<td>")
                        .text(elementTO)))
            })
        }


    }

})
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
            $("#table_of_activities_on").append($("<tr>")
                .append($("<td>")
                    .text(element)
                )
            )
        })
        window.vm.activityTO.forEach(element => {
            $("#table_of_activities_off").append($("<tr>")
                .append($("<td>")
                    .text(element)
                )
            )
        })
        if(window.vm.activityFROM.length > window.vm.activityTO.length){
            $("#table_of_activities_off").append($("<tr>")
                .append($("<td>")
                    .attr("id", "button")
                    .append($("<input>")
                        .attr("type", "button")
                        .attr("id", "turn_off")
                        .attr("value", "Turn off")
                        .click(function(){
                            turnonoff("off");
                        }))))
        }else{
            $("#table_of_activities_on").append($("<tr>")
                .append($("<td>")
                    .attr("id", "button")
                    .append($("<input>")
                        .attr("type", "button")
                        .attr("id", "turn_on")
                        .attr("value", "Turn on")
                        .click(function(){
                            turnonoff("on");
                        }))))
        }

    }

    function getCategories(){
        $.ajax({
            url: 'getCategories',
            type: 'get',
            dataType: 'json',
            success: function(data){
                window.categories = data;
                window.categories.forEach(element => {
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

    function turnonoff(turning){
        if(turning === "on"){
            $("#button").remove();
            let today = new Date();

            let day;
            if(today.getDate() < 10){
                day = '0' + today.getDate();
            }else{
                day = today.getDate();
            }

            let month;
            if(today.getMonth() < 10){
                month = '0' + (today.getMonth()+1);
            }else{
                month = today.getMonth()+1;
            }

            let year = today.getFullYear();

            let hour;
            if(today.getHours() < 10){
                hour = '0' + today.getHours();
            }else{
                hour = today.getHours();
            }

            let minutes;
            if(today.getMinutes() < 10){
                minutes = '0' + today.getMinutes();
            }else{
                minutes = today.getMinutes()
            }

            let date = day + '-' + month + '-' + year + ' ' + hour + ':' + minutes;
            let dateString = date.toString();
            $("#table_of_activities_on").append($("<tr>")
                .append($("<td>")
                    .text(dateString)
                )
            )
            $("#table_of_activities_off").append($("<tr>")
                .append($("<td>")
                    .attr("id", "button")
                    .append($("<input>")
                        .attr("type", "button")
                        .attr("id", "turn_off")
                        .attr("value", "Turn off")
                        .click(function(){
                            turnonoff("off");
                        }))))
        }else{
            $("#button").remove();
            let today = new Date();

            let day;
            if(today.getDate() < 10){
                day = '0' + today.getDate();
            }else{
                day = today.getDate();
            }

            let month;
            if(today.getMonth() < 10){
                month = '0' + (today.getMonth()+1);
            }else{
                month = today.getMonth()+1;
            }

            let year = today.getFullYear();

            let hour;
            if(today.getHours() < 10){
                hour = '0' + today.getHours();
            }else{
                hour = today.getHours();
            }

            let minutes;
            if(today.getMinutes() < 10){
                minutes = '0' + today.getMinutes();
            }else{
                minutes = today.getMinutes()
            }

            let date = day + '-' + month + '-' + year + ' ' + hour + ':' + minutes;
            let dateString = date.toString();
            $("#table_of_activities_off").append($("<tr>")
                .append($("<td>")
                    .text(dateString)
                )
            )
            $("#table_of_activities_on").append($("<tr>")
                .append($("<td>")
                    .attr("id", "button")
                    .append($("<input>")
                        .attr("type", "button")
                        .attr("id", "turn_on")
                        .attr("value", "Turn on")
                        .click(function(){
                            turnonoff("on");
                        }))))
        }
    }

    $("#edit_button").click(function() {
        if($("#vm_name_input").valid()) {
            let oldResourceName = window.vm.oldResourceName;
            let newResourceName = $("#vm_name_input").val();
            let organizationName = $("#organization_input").val();
            let categoryName = $("#category_input").val();
            let numberOfCores = $("#num_of_cores_input").val();
            let RAM = $("#ram_input").val();
            let GPU = $("#gpu_input").val();
            let connectedDiscs = window.vm.connectedDiscs;
            let activitiesFROM = [];
            $("#table_of_activities_on").find("tr").each(function () {
                let tds = $(this).find("td")
                if (tds.eq(0).text() !== "" && tds.eq(0).id !== "button") {
                    activitiesFROM.push(tds.eq(0).text());
                }
            })

            let activitiesTO = [];
            $("#table_of_activities_off").find("tr").each(function () {
                let tds = $(this).find("td")
                if (tds.eq(0).text() !== "" && tds.eq(0).id !== "button") {
                    activitiesTO.push(tds.eq(0).text());
                }
            })

            if (activitiesFROM.length > activitiesTO.length) {
                activitiesTO.push("");
            }

            $.ajax({
                url: 'editVM',
                type: 'post',
                data: JSON.stringify({
                    oldResourceName: oldResourceName,
                    resourceName: newResourceName,
                    organizationName: organizationName,
                    categoryName: categoryName,
                    numberOfCores: numberOfCores,
                    RAM: RAM,
                    GPU: GPU,
                    connectedDiscs: connectedDiscs,
                    activityFROM: activitiesFROM,
                    activityTO: activitiesTO
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("VM successfully edited!");
                        sessionStorage.removeItem("vmedit");
                        window.location.href = "../viewVMs/viewVMs.html";
                    }
                }
            })
        }
    })
})
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
            if(data === "400 bad request"){
                alert("Something went wrong!");
            }else{
                window.location.href = "../../../forbidden.html";
            }
        }
    })

    function fillInputs(){
        $("#vm_td").append(
            $("<input>")
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
            $("<select>")
                .attr("id", "select_new_category")
                .change(function(){
                    console.log("USAO U METODU");
                    window.categories.forEach(element =>{
                        console.log("SELEKTOVAO: "+$(this).children("option:selected").val())
                        console.log("TRENUTNI: "+element.categoryName);
                        if(element.categoryName === $(this).children("option:selected").val()){
                            $("#num_of_cores_input").attr("value", element.numberOfCores);
                            $("#ram_input").attr("value", element.RAM);
                            $("#gpu_input").attr("value", element.GPU);
                        }
                    })
                })
                .append($("<option>")
                    .attr("value", window.vm.categoryName)
                    .attr("label", window.vm.categoryName))
        )
        getCategories();

        $("#cores_td").append(
            $("<input>")
                .attr("readonly", "true")
                .attr("id", "num_of_cores_input")
                .attr("value", window.vm.numberOfCores)
        )

        $("#ram_td").append(
            $("<input>")
                .attr("readonly", "true")
                .attr("id", "ram_input")
                .attr("value", window.vm.RAM)
        )

        $("#gpu_td").append(
            $("<input>")
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
            $("#turn_onoff").attr("name", "off");
            $("#turn_onoff").html("Turn off")
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
            $("#turn_onoff").attr("name", "on");
            $("#turn_onoff").html("Turn on")
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

    function getCategories(){
        $.ajax({
            url: 'getCategories',
            type: 'get',
            dataType: 'json',
            success: function(data){
                window.categories = data;
                window.categories.forEach(element => {
                    if(element.categoryName !== window.vm.categoryName){
                        $("#select_new_category").append(
                            $("<option>")
                                .attr("value", element.categoryName)
                                .attr("label", element.categoryName)
                        )
                    }
                })
            }, error: function(data){
                window.location.href = "../../../forbidden.html";
            }
        })
    }

    function generateCurrentDate(){
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
        return dateString;
    }

    $("#edit_button").click(function() {
        if($("#vm_name_input").valid()) {
            let oldResourceName = window.vm.oldResourceName;
            let newResourceName = $("#vm_name_input").val();
            let organizationName = $("#organization_input").val();
            let categoryName = $("#select_new_category").children("option:selected").val();
            let numberOfCores = $("#num_of_cores_input").val();
            let RAM = $("#ram_input").val();
            let GPU = $("#gpu_input").val();
            let connectedDiscs = window.vm.connectedDiscs;
            let activitiesFROM = [];
            let activitiesTO = [];
            $("#table_of_activities").find("tr").each(function () {
                let tds = $(this).find("td")
                if (tds.eq(0).text() !== "") {
                    activitiesFROM.push(tds.eq(0).text());
                }
                if(tds.eq(1).text() !== ""){
                    activitiesTO.push(tds.eq(1).text())
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
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })
        }
    })

    $("#turn_onoff").click(function(){
        if($(this).attr("name") === "on"){
            $("#turn_onoff").html("Turn off")
            $("#turn_onoff").attr("name", "off");
            $("#last").removeAttr("id");
            let today = generateCurrentDate();

            $("#table_of_activities").append($("<tr>")
                .attr("id", "last")
                .append($("<td>")
                    .text(today)
                )
            )
        }else{
            $("#turn_onoff").html("Turn on")
            $("#turn_onoff").attr("name", "on");
            let today = generateCurrentDate();

            $("#last").append(
                $("<td>")
                    .text(today)
            )

        }
    });

    $("#delete_button").click(function(){
        $.ajax({
            url: 'deleteVM',
            type: 'post',
            data: sessionStorage.getItem("vmedit"),
            dataType: 'json',
            complete: function(response){
                if(response.status === 200){
                    sessionStorage.removeItem("vmedit");
                    window.location.href = "../viewVMs/viewVMs.html";
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
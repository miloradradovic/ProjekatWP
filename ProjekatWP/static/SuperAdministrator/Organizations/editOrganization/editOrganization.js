let organization = null;
$(document).ready(function(){

    $.ajax({
        url: 'getOrganizationByName',
        type: 'post',
        data: sessionStorage.getItem("organizationedit"),
        dataType: 'json',
        success: function(data){
            window.organization = data;
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


        $("#org_name_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("id", "org_name_input")
                .attr("name", "org_name_input")
                .attr("value", window.organization.orgName)
        )

        $("#description_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("id", "description_input")
                .attr("value", window.organization.description)
        )

        $("#logo_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("id", "logo_input")
                .attr("value", window.organization.logo)
        )


    }


    $("#edit_button").click(function() {
        if($("#org_name_input").valid() && $("#description_input").valid() && $("#logo_input").valid()){
            let oldOrgName = window.organization.oldOrgName;
            let orgName = $("#org_name_input").val();
            let description = $("#description_input").val();
            let logo = $("#logo_input").val();
            $.ajax({
                url: 'editOrganization',
                type: 'post',
                data: JSON.stringify({
                    oldOrgName: oldOrgName,
                    orgName: orgName,
                    description: description,
                    logo: logo,
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("Organization successfully edited!");
                        sessionStorage.removeItem("organizationedit");
                        window.location.href = "../viewOrganizations/viewOrganizations.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })
        }
    })


})
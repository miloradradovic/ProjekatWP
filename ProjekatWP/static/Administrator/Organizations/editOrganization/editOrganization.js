let organization = null;
$(document).ready(function(){

    $.ajax({
        url: 'getOrganization',
        type: 'get',
        data: sessionStorage.getItem("organizationedit"),
        dataType: 'json',
        success: function(data){
            window.organization = data;
            fillInputs();
        }, error: function(data){
            if(data === "400 Bad Request"){
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

        $("#photo").attr("src", window.organization.logo)


    }


    $("#edit_button").click(function() {
        if($("#org_name_input").valid() && $("#description_input").valid() && $("#logo_input").valid()){
            let oldOrgName = window.organization.oldOrgName;
            let orgName = $("#org_name_input").val();
            let description = $("#description_input").val();
            let logo = $("#photo").attr("src")
            if($("#defaultLogo").is(":checked")) {
                logo = ""
            }
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
                        window.location.href = "editOrganization.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })
        }
    })

    $("#logo_input").change(function(event){
        // if (this.files && this.files[0]) {
        //     $("#photo").attr("src", URL.createObjectURL(this.files[0]))
        //     console.log(URL.createObjectURL(this.files[0]))
        // }
        var reader = new FileReader();
        reader.onload = function(){
            $("#photo").attr("src", reader.result)
        }
        reader.readAsDataURL(event.target.files[0]);

    })

    $("#photo").dblclick(function(){
        $("#photo").attr("src", "")
        $("#logo_input").attr("value", "")
    })


})
$(document).ready(function(){

    $("#addOrganizationButton").click(function(){

        if($("#org_name_input").valid() && $("#description_input").valid()){
            let org_name = $("#org_name_input").val();
            let description = $("#description_input").val();
            let logo = $("#photo").attr("src")
            if($("defaultLogo").is(":checked")) {
                logo = ""
            }
            $.ajax({
                url: 'addOrganization',
                type: 'post',
                data: JSON.stringify({
                    orgName: org_name,
                    description: description,
                    logo: logo,
                }),
                dataType: 'json',
                complete: function(response){
                    if(response.status === 200){
                        alert("Organization successfully added!");
                        window.location.href = "../viewOrganizations/viewOrganizations.html";
                    }else if(response.status === 403){
                        window.location.href = "../../../forbidden.html";
                    }else{
                        alert("Something went wrong!");
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
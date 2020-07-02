$(document).ready(function(){

    $("#addOrganizationButton").click(function(){

        if($("#org_name_input").valid() && $("#description_input").valid()){
            let org_name = $("#org_name_input").val();
            let description = $("#description_input").val();
            let logo = $("#logo_input").val();
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
        console.log("USAO")
        let file = event.target.files[0]
        if(!file){
            return false
        }
        if(!file.type.match('image.*')){
            return false
        }
        let reader = new FileReader()
        let that = this
        reader.onload = function(e){
            $("#photo")
               .attr("src", event.target.result)
            console.log("reader onload")
            console.log($("#photo"))
        }
        reader.readAsDataURL(file)
        console.log("ZAVRSIO")
    })
    


})
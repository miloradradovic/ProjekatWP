$(document).ready(function(){

    $("#addCategoryButton").click(function(){

        if($("#category_name_input").valid() && $("#number_of_cores_input").valid() && $("#ram_input").valid() && $("#gpu_input").valid()){
            let category_name = $("#category_name_input").val();
            let number_of_cores = $("#number_of_cores_input").val();
            let ram = $("#ram_input").val();
            let gpu = $("#gpu_input").val();
            $.ajax({
                url: 'addCategory',
                type: 'post',
                data: JSON.stringify({
                    categoryName: category_name,
                    numberOfCores: number_of_cores,
                    RAM: ram,
                    GPU: gpu
                }),
                dataType: 'json',
                complete: function(response){
                    if(response.status === 200){
                        alert("Category successfully added!");
                        window.location.href = "../viewCategories/viewCategories.html";
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
let category = null;
$(document).ready(function(){

    $.ajax({
        url: 'getCategoryByName',
        type: 'post',
        data: sessionStorage.getItem("categoryedit"),
        dataType: 'json',
        success: function(data){
            window.category = data;
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


        $("#category_name_td").append(
            $("<input>")
                .attr("type", "text")
                .attr("id", "category_name_input")
                .attr("name", "category_name_input")
                .attr("value", window.category.oldCategoryName)
        )

        $("#number_of_cores_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("id", "number_of_cores_input")
                .attr("value", window.category.numberOfCores)
        )

        $("#ram_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("id", "ram_input")
                .attr("value", window.category.RAM)
        )
        
        $("#gpu_td").append(
            $("<input>")
                .attr("type", "number")
                .attr("id", "gpu_input")
                .attr("value", window.category.GPU)
        )


    }


    $("#edit_button").click(function() {
        if($("#category_name_input").valid() && $("#number_of_cores_input").valid() && $("#ram_input").valid() && $("#gpu_input").valid()){
            let oldCategoryName = window.category.oldCategoryName;
            let categoryName = $("#category_name_input").val();
            let numberOfCores = $("#number_of_cores_input").val();
            let ram = $("#ram_input").val();
            let gpu = $("#gpu_input").val();
            console.log(window.category.oldCategoryName);
            $.ajax({
                url: 'editCategory',
                type: 'post',
                data: JSON.stringify({
                    oldCategoryName: oldCategoryName,
                    categoryName: categoryName,
                    numberOfCores: numberOfCores,
                    RAM: ram,
                    GPU: gpu,
                }),
                complete: function (response) {
                    if (response.status === 200) {
                        alert("Category successfully edited!");
                        sessionStorage.removeItem("categoryedit");
                        window.location.href = "../viewCategories/viewCategories.html";
                    }else if(response.status === 400){
                        alert("Something went wrong!");
                    }else{
                        window.location.href = "../../../forbidden.html";
                    }
                }
            })
        }
    })


    $("#delete_button").click(function(){
        $.ajax({
            url: 'deleteCategory',
            type: 'post',
            data: sessionStorage.getItem("categoryedit"),
            dataType: 'json',
            complete: function(response){
                if(response.status === 200){
                    alert("Category successfully deleted!");
                    sessionStorage.removeItem("categoryedit");
                    window.location.href = "../viewCategories/viewCategories.html";
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
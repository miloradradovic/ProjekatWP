let categories = [];
$(document).ready(function() {


    $.ajax({
        url: 'getCategories',
        type: 'get',
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            window.categories = data;
            updateTable();
            },
        error: function(data){
            window.location.href = "../../../forbidden.html";
        }
    })

    function updateTable(){
        $("#tableC tbody").children().remove();
        window.categories.forEach(element => {
            $("#tableC tbody").append($("<tr>")
                .click(function(){
                    sessionStorage.setItem("categoryedit", element.categoryName);
                    window.location.href = "../editCategory/editCategory.html";
                })
                .attr("title", "Click to edit the category")
                .attr("id", element.categoryName)
                .append($("<td>")
                    .text(element.categoryName))
                .append($("<td>")
                    .text(element.numberOfCores))
                .append($("<td>")
                    .text(element.RAM))
                .append($("<td>")
                    .text(element.GPU))
            )
        })
    }

    $("#addcategory").click(function(){
        window.location.href = "../addCategory/addCategory.html";
    })

    

})
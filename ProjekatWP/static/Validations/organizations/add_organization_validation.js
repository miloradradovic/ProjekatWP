$(document).ready(function() {

    $("#addCategoryForm").validate({
        rules: {
            org_name_input: {
                required: true
            },
            description_input: {
                required: true
            }
        },
        messages: {
            org_name_input: {
                required: "Organization name field must not be empty!"
            },
            description_input:{
                required: "Description field must not be empty!"
            }
        }
    });
})
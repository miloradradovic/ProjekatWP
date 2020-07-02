$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            category_name_input: {
                required: true
            }
        },
        messages: {
            category_name_input: {
                required: "Category name field must not be empty!"
            }
        }
    });
})
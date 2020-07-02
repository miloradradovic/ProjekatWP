$(document).ready(function() {

    $("#edit_form").validate({
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
            description_input: {
                required: "Description name field must not be empty!"
            }
        }
    });
})
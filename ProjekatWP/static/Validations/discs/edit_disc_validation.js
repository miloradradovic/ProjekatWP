$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            disc_name_input: {
                required: true
            }
        },
        messages: {
            disc_name_input: {
                required: "Disc name field must not be empty!"
            }
        }
    });
})
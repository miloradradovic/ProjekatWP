$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            disc_name_input: {
                required: true
            },
            capacity_input: {
                required: true,
                min: 1
            }
        },
        messages: {
            disc_name_input: {
                required: "Disc name field must not be empty!"
            },
            capacity_input: {
                required: "Disc capacity can't be empty!",
                min: "Disc capacity must be greater than 0!"
            }
        }
    });
})
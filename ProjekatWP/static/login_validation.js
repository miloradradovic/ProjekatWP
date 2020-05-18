$(document).ready(function() {

    $("#login_form").validate({
        rules: {
            email_input: {
                required: true
            },
            pass_input: {
                required: true,
                minlength: 8
            }
        },
        messages: {
            email_input: {
                required: "Email field must not be empty!",
            },
            pass_input: {
                required: "Password field must not be empty!",
                minlength: "Password length must be greater than 7!"
            }
        }
    });
})
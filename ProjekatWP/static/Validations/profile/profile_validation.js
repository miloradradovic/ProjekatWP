$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            email_input: {
                required: true,
                email: true,

            },
            password1_input: {
                required: true,
                minlength: 8
            },
            password2_input: {
                required: true,
                equalTo: "#password1_input"
            },
            name_input: {
                required: true,
                pattern: "[A-Z][a-z]+"
            },
            surname_input: {
                required: true,
                pattern: "[A-Z][a-z]+"
            }
        },
        messages: {
            email_input: {
                required: "Email field must not be empty!",
                email: "Invalid email!",

            },
            password1_input: {
                required: "Password field must not be empty!",
                minlength: "Password length must be greater than or equal to 8!"
            },
            password2_input: {
                required: "Please confirm password change!",
                equalTo: "Passwords must match!"
            },
            name_input: {
                required: "Name field must not be empty!",
                pattern: "Invalid name!"
            },
            surname_input: {
                required: "Surname field must not be empty!",
                pattern: "Invalid surname!"
            }
        }
    });
})
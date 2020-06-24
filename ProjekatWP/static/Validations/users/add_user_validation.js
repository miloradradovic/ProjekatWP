$(document).ready(function() {

    $("#addUserForm").validate({
        rules: {
            email_input: {
                required: true,
                email: true
            },
            password_input: {
                required: true,
                minlength: 8
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
                email: "Invalid email!"
            },
            password_input: {
                required: "Password field must not be empty!",
                minlength: "Password length must be greater than or equal to 8!"
            },
            name_input: {
                required: "Name input must not be empty!",
                pattern: "Invalid name!"
            },
            surname_input: {
                required: "Surname input must not be empty!",
                pattern: "Invalid surname!"
            }
        }
    });
})
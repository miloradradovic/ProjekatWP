$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            vm_name_input: {
                required: true
            }
        },
        messages: {
            vm_name_input: {
                required: "VM name field must not be empty!"
            }
        }
    });
})
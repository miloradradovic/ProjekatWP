$(document).ready(function() {

    $("#edit_form").validate({
        rules: {
            category_name_input: {
                required: true
            },
            number_of_cores_input: {
                required: true
            },
            ram_input: {
                required: true
            },
            gpu_input: {
                required: true
            }
        },
        messages: {
            category_name_input: {
                required: "Category name field must not be empty!"
            },
            number_of_cores_input:{
                required: "Number of cores field must not be empty!"
            },
            ram_input: {
                required: "RAM field must not be empty!"
            },
            gpu_input: {
                required: "GPU field must not be empty!"
            }
        }
    });
})
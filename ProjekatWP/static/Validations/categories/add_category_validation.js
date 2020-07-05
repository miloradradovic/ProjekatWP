$(document).ready(function() {

    $("#addCategoryForm").validate({
        rules: {
            category_name_input: {
                required: true
            },
            number_of_cores_input: {
                required: true,
                min: 1
            },
            ram_input: {
                required: true,
                min: 1
            },
            gpu_input: {
                required: true,
                min: 1
            }
        },
        messages: {
            category_name_input: {
                required: "Category name field must not be empty!"
            },
            number_of_cores_input:{
                required: "Number of cores field must not be empty!",
                min: "Value must be greater than zero!"
            },
            ram_input: {
                required: "RAM field must not be empty!",
                min: "Value must be greater than zero!"
            },
            gpu_input: {
                required: "GPU field must not be empty!",
                min: "Value must be greater than zero!"
            }
        }
    });
})
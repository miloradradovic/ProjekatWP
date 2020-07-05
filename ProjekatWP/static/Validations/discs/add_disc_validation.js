$(document).ready(function() {

    $("#addDiscForm").validate({
        rules: {
            disc_name_input: {
                required: true
            },
            select_disc_type: {
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
            select_disc_type:{
                required: "You have to select a disc type!"
            },
            capacity_input: {
                required: "Capacity field must not be empty!",
                min: "Capacity must be greater than 0!"
            }
        }
    });
})
$(function() {
    $('#forms-header span:first-child').addClass('selected').css('animation', 'mode_active .5s ease-in-out forwards');
    $('#form-register').hide();
    $('#form-login').css('animation', 'form_show .5s ease-in-out forwards');

    // sign-in
    $('#forms-header span:first-child').click(function() {
        $('#forms-header span:last-child').removeClass('selected').css('animation', 'mode_reverse .5s ease-in-out forwards');
        $('#forms-header span:first-child').addClass('selected').css('animation', 'mode_active .5s ease-in-out forwards');
        $('#form-register').css('animation', 'form_hide .65s ease-in-out forwards');
        $('#form-register').hide();
        $('#form-login').show();
        $('#form-login').css('animation', 'form_show .65s ease-in-out forwards');
    });

    // sign-up
    $('#forms-header span:last-child').click(function() {
        $('#forms-header span:first-child').removeClass('selected').css('animation', 'mode_reverse .5s ease-in-out forwards');
        $('#forms-header span:last-child').addClass('selected').css('animation', 'mode_active .5s ease-in-out forwards');
        $('#form-login').hide();
        $('#form-login').css('animation', 'form_hide .65s ease-in-out forwards');
        $('#form-register').show();
        $('#form-register').css('animation', 'form_show .65s ease-in-out forwards');
    });

    // Function to enable or disable the submit button
    function toggleSubmitButton(formId) {
        let inputs = $(formId + ' input[type="text"], ' + formId + ' input[type="password"]');
        let submitButton = $(formId + ' input[type="submit"]');
        let allFilled = true;

        inputs.each(function() {
            if ($(this).val().trim() === "") {
                allFilled = false;
                return false; // break the loop
            }
        });

        if (allFilled) {
            submitButton.prop('disabled', false);
        } else {
            submitButton.prop('disabled', true);
        }
    }

    // Initial check on page load
    toggleSubmitButton('#form-login');
    toggleSubmitButton('#form-register');

    // Check on input change for login form
    $('#login-username, #login-password').on('input', function() {
        toggleSubmitButton('#form-login');
    });

    // Check on input change for register form
    $('#register-username, #register-email, #register-password').on('input', function() {
        toggleSubmitButton('#form-register');
    });

    // Function to enable or disable the submit button
    function toggleSubmitButton(formId) {
        let inputs = $(formId + ' input[type="text"], ' + formId + ' input[type="password"]');
        let submitButton = $(formId + ' input[type="submit"]');
        let allFilled = true;

        inputs.each(function() {
            if ($(this).val().trim() === "") {
                allFilled = false;
                return false; // break the loop
            }
        });

        if (allFilled) {
            submitButton.prop('disabled', false);
        } else {
            submitButton.prop('disabled', true);
        }
    }

    // Initial check on page load
    toggleSubmitButton('#form-login');
    toggleSubmitButton('#form-register');

    // Check on input change for login form
    $('#login-username, #login-password').on('input', function() {
        toggleSubmitButton('#form-login');
    });

    // AJAX for sign-up
    $('#form-register').submit(function(event) {
        event.preventDefault(); // Prevent the form from submitting via the browser
        $.ajax({
            url: 'register',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                if (response.success) {
                    alert('User added successfully!');
                    window.location.href = 'main.jsp';
                } else {
                    alert(response.message);
                }
            },
            error: function() {
                alert('Error occurred while signing up.');
            }
        });
    });
});

$(function () {
    // style
    $('#dropdown-menu').hide();
    $('#hello').hover(
        function() {
            $(this).removeClass('hello-out').addClass('hello-in');
            $('#dropdown-menu').slideDown(200);
        },
        function(e) {
            $(this).removeClass('hello-in').addClass('hello-out');
            $('#dropdown-menu').slideUp(200);
        }
    );

    $('ul > li').hover(
        function() {
            $(this).removeClass('li-out').addClass('li-in');
        },
        function(e) {
            $(this).removeClass('li-in').addClass('li-out');
        }
    );

    // back to homepage
    $('#header-left').click(function () {
        window.location.href = 'redirectToHome';
    });

    // servlet
    const originalData = {
        username: $('#username').val(),
        dob: $('#dob').val(),
        phoneNum: $('#phoneNum').val(),
        email: $('#email').val()
    };

    $('#updateBioForm').submit(function(e) {
        e.preventDefault();

        const newData = {
            username: $('#username').val(),
            dob: $('#dob').val(),
            phoneNum: $('#phoneNum').val(),
            email: $('#email').val()
        };

        if (JSON.stringify(originalData) === JSON.stringify(newData)) {
            alert('No changes made.');
        } else {
            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(response) {
                    alert('Profile updated successfully.');
                    location.reload();
                },
                error: function() {
                    alert('An error occurred while updating the profile.');
                }
            });
        }
    });

    $('#imageInput').change(function() {
        if (this.files && this.files[0]) {
            var formData = new FormData($('#addImageForm')[0]);

            $.ajax({
                type: 'POST',
                url: $('#addImageForm').attr('action'),
                data: formData,
                contentType: false,
                processData: false,
                success: function(response) {
                    alert('Profile image updated successfully.');
                    const image = document.getElementById('profileImage');

                    if (!image.src.includes('?')) {
                        image.src = `${image.src}?${Date.now()}`;
                    } else {
                        image.src = image.src.slice(0, image.src.indexOf('?') + 1) + Date.now();
                    }

                    window.location.href = window.location.href;
                },
                error: function() {
                    alert('An error occurred while uploading the image.');
                }
            });
        }
    });

    if (typeof successMessage !== 'undefined' && successMessage !== null) {
        alert(successMessage);
    }

    $('#cart').click(function () {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });
});

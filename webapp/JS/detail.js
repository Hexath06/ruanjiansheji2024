$(function () {
    // style
    $('#dropdown-menu').hide();
    $('#hello').hover(function() {
        $('#dropdown-menu').slideDown(200);
    }, function() {
        $('#dropdown-menu').slideUp(200);
    });

    // Animated button effects
    $('#addToCart').hover(function() {
        $(this).addClass('addToCart-in').removeClass('addToCart-out');
    }, function() {
        $(this).addClass('addToCart-out').removeClass('addToCart-in');
    });

    $('#buynow').hover(function() {
        $(this).addClass('buynow-in').removeClass('buynow-out');
    }, function() {
        $(this).addClass('buynow-out').removeClass('buynow-in');
    });

    $('#decrease-quantity, #increase-quantity').hover(function() {
        $(this).addClass('quantity-button-in').removeClass('quantity-button-out');
    }, function() {
        $(this).addClass('quantity-button-out').removeClass('quantity-button-in');
    });

    $('#buy-quantity').focus(function() {
        $(this).parent().addClass('set-quantity-in').removeClass('set-quantity-out');
    }).blur(function() {
        $(this).parent().addClass('set-quantity-out').removeClass('set-quantity-in');
    });

    $('ul > li').hover(function() {
        $(this).addClass('li-in').removeClass('li-out');
    }, function() {
        $(this).addClass('li-out').removeClass('li-in');
    });

    $('#hello').hover(function() {
        $(this).addClass('hello-in').removeClass('hello-out');
    }, function() {
        $(this).addClass('hello-out').removeClass('hello-in');
    });

    // back to homepage
    $('#header-left').click(function () {
        window.location.href = 'redirectToHome';
    });

    // logic
    let minQuantity = $('#set-quantity-left button').first();
    let addQuantity = $('#set-quantity-left button').last();
    let buyQuantity = $('#buy-quantity');
    let maxStock = parseInt($('#stock').text().trim(), 10); // Assuming retrievedProduct.quantity is an integer

    function updateButtons() {
        const quantity = parseInt(buyQuantity.val().trim(), 10);
        if (quantity <= 1) {
            minQuantity.prop('disabled', true).css('cursor', 'not-allowed');
        } else {
            minQuantity.prop('disabled', false).css('cursor', 'pointer');
        }
        if (quantity >= maxStock) {
            addQuantity.prop('disabled', true).css('cursor', 'not-allowed');
        } else {
            addQuantity.prop('disabled', false).css('cursor', 'pointer');
        }
    }

    minQuantity.click(function () {
        let newQuantity = parseInt(buyQuantity.val().trim(), 10) - 1;
        if (newQuantity >= 1) {
            buyQuantity.val(newQuantity);
            updateButtons();
        }
    });

    addQuantity.click(function () {
        let newQuantity = parseInt(buyQuantity.val().trim(), 10) + 1;
        if (newQuantity <= maxStock) {
            buyQuantity.val(newQuantity);
            updateButtons();
        }
    });

    // Initialize button states
    updateButtons();

    // servlet
    $('#addToCart').click(function() {
        let username = $('#active-username').text().trim();
        let productId = $('#product-id').val();
        let quantity = $('#buy-quantity').val(); // Get the quantity from the input field

        if (!username || !productId) {
            $('#error-message').text('Username and Product ID are required.').show();
            return;
        }

        // Make sure the quantity is a valid integer greater than 0
        if (!(/^\d+$/.test(quantity)) || parseInt(quantity) <= 0) {
            $('#error-message').text('Invalid quantity. Please enter a valid number greater than 0.').show();
            return;
        }

        $.ajax({
            url: 'cart',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                action: 'addToCart',
                username: username,
                productId: productId,
                quantity: quantity // Send the correct quantity to the server
            }),
            success: function(response) {
                console.log("Success response:", response); // Debugging line
                if (typeof response === "string") {
                    response = JSON.parse(response);
                }
                if (response && response.status === "success") {
                    alert(response.message);
                } else {
                    alert("Success but no message: " + JSON.stringify(response)); // Debugging line
                }
            },
            error: function(xhr) {
                console.log("Error occurred", xhr); // Debugging line
                let response;
                try {
                    response = JSON.parse(xhr.responseText);
                    console.log("Error response parsed:", response); // Debugging line
                    $('#error-message').text(response.message).show();
                    alert(response.message);
                } catch (e) {
                    console.log("Error parsing response:", e); // Debugging line
                    $('#error-message').text('An unexpected error occurred.').show();
                    alert('An unexpected error occurred.');
                }
            }
        });
    });

    $('#buyNowButton').click(function() {
        let username = $('#active-username').text().trim();
        let productId = $('#product-id').val();
        let quantity = $('#buy-quantity').val();

        if (!username || !productId) {
            $('#error-message').text('Username and Product ID are required.').show();
            return;
        }

        // Make sure the quantity is a valid integer greater than 0
        if (!(/^\d+$/.test(quantity)) || parseInt(quantity) <= 0) {
            $('#error-message').text('Invalid quantity. Please enter a valid number greater than 0.').show();
            return;
        }

        $.ajax({
            url: 'cart',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                action: 'addToCart',
                username: username,
                productId: productId,
                quantity: quantity
            }),
            success: function(response) {
                console.log("Success response:", response);
                if (typeof response === "string") {
                    response = JSON.parse(response);
                }
                if (response && response.status === "success") {
                    window.location.href = 'cart?action=showCart';
                } else {
                    alert("Success but no message: " + JSON.stringify(response));
                }
            },
            error: function(xhr) {
                console.log("Error occurred", xhr);
                let response;
                try {
                    response = JSON.parse(xhr.responseText);
                    console.log("Error response parsed:", response);
                    $('#error-message').text(response.message).show();
                    alert(response.message);
                } catch (e) {
                    console.log("Error parsing response:", e);
                    $('#error-message').text('An unexpected error occurred.').show();
                    alert('An unexpected error occurred.');
                }
            }
        });
    });

    $('#cart').click(function () {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });
});

$(function() {
    // style
    // Hover animations for list items
    $('ul > li').hover(
        function() {
            $(this).removeClass('li-out').addClass('li-in');
        },
        function() {
            $(this).removeClass('li-in').addClass('li-out');
        }
    );

    // Dropdown menu animations
    $('#dropdown-menu').hide();
    $('#hello').hover(
        function() {
            $(this).removeClass('hello-out').addClass('hello-in');
            $('#dropdown-menu').stop(true, true).slideDown(200);
        },
        function() {
            $(this).removeClass('hello-in').addClass('hello-out');
            $('#dropdown-menu').stop(true, true).slideUp(200);
        }
    );

    // Redirect to homepage
    $('#header-left').click(function() {
        window.location.href = 'redirectToHome';
    });

    // Cart functionality
    $('#cart').click(function() {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });

    // Redirect to product detail page
    $('.product-item').click(function() {
        let productId = $(this).find('.product-id').val().trim();

        if (productId) {
            window.location.href = 'detail?action=showDetail&productId=' + productId;
        } else {
            alert("Product ID is missing");
        }
    });

    // Submit search form
    $('#search-button').click(function() {
        $('#header-mid').submit();
    });

    // $('.addToCart').click(function () {
    //     let username = $('#active-username').text().trim();
    //     let productId = $(this).closest('a').find('.product-id').val().trim();
    //
    //     if (!username || !productId) {
    //         console.error("Username or productId is missing.");
    //         return;
    //     }
    //
    //     $.ajax({
    //         url: 'cart',
    //         type: 'POST',
    //         contentType: 'application/json',
    //         data: JSON.stringify({
    //             action: 'addToCart',
    //             username: username,
    //             productId: productId
    //         }),
    //         dataType: 'json',
    //         success: function(response) {
    //             alert(response.message);
    //         },
    //         error: function(xhr, status, error) {
    //             console.error("Error:", error);
    //             if (xhr.status === 400) {
    //                 let response = JSON.parse(xhr.responseText);
    //                 if (response.status === "error" && response.message === "Insufficient quantity.") {
    //                     alert("Failed to add product to cart: Insufficient quantity.");
    //                 }
    //             } else {
    //                 alert("Failed to add product to cart.");
    //             }
    //         }
    //     });
    // });

});

$(function() {
    // style
    $('ul > li').hover(
        function() {
            $(this).removeClass('li-out').addClass('li-in');
        },
        function(e) {
            $(this).removeClass('li-in').addClass('li-out');
        }
    );

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

    // back to homepage
    $('#header-left').click(function () {
        window.location.href = 'redirectToHome';
    });

    // servlet
    $('#cart').click(function () {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });

    $('#cart').click(function () {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });

    $('button').on('click', function() {
        $.ajax({
            url: 'cart',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                action: 'completeOrder',
                selectedItems: selectedItems // Pass the selected items
            }),
            success: function(response) {
                if (response.status === 'success') {
                    alert("Thank you for your purchase! Your order ID is: " + response.orderId);
                    window.location.href = 'receipt?orderId=' + response.orderId;
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error:', xhr.responseText);
                alert('AJAX Error: ' + error);
            }
        });
    });
});

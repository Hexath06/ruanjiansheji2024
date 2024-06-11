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
    $('#cart').click(function () {
        if (isLoggedIn) {
            window.location.href = 'cart?action=showCart';
        } else {
            alert("Please log in to view your cart.");
            window.location.href = 'login.jsp';
        }
    });
});
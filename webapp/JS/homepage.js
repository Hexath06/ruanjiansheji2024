$(function() {
    // style
    $('.category-image').hover(
        function() {
            $(this).removeClass('category-image-out').addClass('category-image-in');
        },
        function(e) {
            $(this).removeClass('category-image-in').addClass('category-image-out');
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

    // Initialize banner index
    let bannerIndex = 0;
    let timeoutId;

    // Show initial banner
    showBanner();
    $('.carousel-image').each(function () {
        $('#dot-container').append('<span class="dot">'+'</span>')
    });

    // Show next banner when clicking on the "next" button
    $('#next').click(function() {
        clearTimeout(timeoutId); // Clear previous timeout
        bannerIndex++;
        showBanner();
    });

    // Show previous banner when clicking on the "prev" button
    $('#prev').click(function() {
        clearTimeout(timeoutId); // Clear previous timeout
        bannerIndex--;
        showBanner();
    });

    // Show specific banner when clicking on a dot
    $('.dot').click(function() {
        clearTimeout(timeoutId); // Clear previous timeout
        bannerIndex = $(this).index(); // Get the index of the clicked dot
        showBanner();
    });

    function showBanner() {
        let obj = $(".carousel-image");
        let dots = $(".dot");

        // Hide all banners
        obj.hide();

        // Reset banner index if it exceeds the number of banners
        if (bannerIndex >= obj.length)
            bannerIndex = 0;

        // Reset banner index if it goes below 0
        if (bannerIndex < 0)
            bannerIndex = obj.length - 1;

        // Fade in the current banner
        obj.eq(bannerIndex).fadeIn();

        // Update indicator dot for the current banner
        dots.removeClass('active');
        dots.eq(bannerIndex).addClass('active');

        // Set timeout to change banner every 2 seconds
        timeoutId = setTimeout(function() {
            bannerIndex++;
            showBanner();
        }, 2000);
    }

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

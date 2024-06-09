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

    // logic
    // Select all items
    $('#select-all').click(function () {
        $('.item-checkbox').prop('checked', true);
        updateCartTotal(); // Add this line to update the total price
    });

    // Unselect all items
    $('#unselect-all').click(function () {
        $('.item-checkbox').prop('checked', false);
        updateCartTotal(); // Add this line to update the total price
    });

    $("#buy").click(function() {
        let selectedItems = [];
        $(".cart-item").each(function() {
            let checkbox = $(this).find(".item-checkbox");
            if (checkbox.is(":checked")) {
                let productId = $(this).find(".product-add").data("itemid");
                let price = parseFloat($(this).find(".product-price").text().trim());
                let quantity = parseInt($(this).find("input[type='text']").val().trim());
                selectedItems.push({
                    productId: productId,
                    price: price,
                    quantity: quantity
                });
            }
        });

        console.log("Selected Items:", selectedItems); // Debugging

        if (selectedItems.length === 0) {
            alert("Please select at least one item to purchase.");
            return;
        }

        // Add confirmation step
        let confirmPurchase = confirm("Are you sure you want to purchase the selected items?");
        if (!confirmPurchase) {
            return;
        }

        $.ajax({
            url: 'cart',
            type: 'POST',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify({
                action: 'purchase',
                selectedItems: selectedItems
            }),
            success: function(response) {
                console.log("AJAX Success Response:", response); // Log the response for debugging

                if (response.status === 'success') {
                    alert("Purchase completed!");
                    window.location.href = 'receipt.jsp?orderId=' + response.orderId;
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error:', xhr.responseText); // Log detailed error message
                alert('AJAX Error: ' + error); // Alert user about the error
            }
        });
    });

    // Increase quantity
    $('.product-add').click(function () {
        let $input = $(this).siblings('input[type="text"]');
        let currentVal = parseInt($input.val());
        let originalVal = parseInt($input.attr('data-original-val'));
        if (!isNaN(currentVal)) {
            let newQuantity = currentVal + 1;
            updateQuantity($(this).data('itemid'), newQuantity, $input, originalVal);
        }
    });

    // Decrease quantity
    $('.product-min').click(function () {
        let $input = $(this).siblings('input[type="text"]');
        let currentVal = parseInt($input.val());
        let originalVal = parseInt($input.attr('data-original-val'));
        if (!isNaN(currentVal) && currentVal > 1) {
            let newQuantity = currentVal - 1;
            updateQuantity($(this).data('itemid'), newQuantity, $input, originalVal);
        }
    });

    // Manual input change
    $('input[type="text"]').change(function () {
        let $input = $(this);
        let currentVal = parseInt($input.val());
        let originalVal = parseInt($input.attr('data-original-val'));
        if (!isNaN(currentVal) && currentVal > 0) {
            updateQuantity($input.siblings('.product-add').data('itemid'), currentVal, $input, originalVal);
        } else {
            $input.val(originalVal);
        }
    });

    // Update total price for each cart item
    function updateTotal($item) {
        let price = parseFloat($item.find('.product-price').text());
        let quantity = parseInt($item.find('input[type="text"]').val());
        let total = (price * quantity).toFixed(2);
        $item.find('.total').text(total);
        updateCartTotal();
    }

    // Update the overall cart total
    function updateCartTotal() {
        let total = 0;
        $('.item-checkbox:checked').each(function () {
            total += parseFloat($(this).closest('.cart-item').find('.total').text());
        });
        $('#all-total').text(total ? total.toFixed(2) : '');
    }

    // AJAX request to update quantity
    function updateQuantity(productId, quantity, $input, originalVal) {
        $.ajax({
            url: 'cart',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                action: 'updateQuantity',
                productId: productId,
                quantity: quantity
            }),
            success: function(response) {
                if (response.status === 'success') {
                    $input.val(quantity).attr('data-original-val', quantity);
                    updateTotal($input.closest('.cart-item'));
                } else if (response.status === 'error') {
                    alert(response.message);
                    $input.val(originalVal); // Revert to original value if the update fails
                }
            }
        });
    }

    // Handle checkbox change
    $('.item-checkbox').change(function () {
        updateCartTotal();
    });

    // Remove cart functionality
    $('#remove-cart').click(function () {
        if (confirm('Are you sure you want to empty the cart?')) {
            $.ajax({
                url: 'cart',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    action: 'removeCart'
                }),
                success: function(response) {
                    if (response.status === 'success') {
                        window.location.reload();
                    }
                }
            });
        }
    });

    // Initial total calculation
    updateCartTotal();
});

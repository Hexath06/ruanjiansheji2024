$(function () {
    calculateTotal();

    function calculateTotal() {
        let total = 0;
        $('.cart-item').each(function () {
            // Get the price and quantity
            let price = parseFloat($(this).find('.product-price').text());
            let quantity = parseInt($(this).find('.quantity').text());

            // Calculate the total for this item
            total += price * quantity;
        });

        // Update the total in the DOM
        $('#all-total').text(total.toFixed(2));
    }
})
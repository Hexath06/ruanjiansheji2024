$(function () {
    $.ajax({
        url: 'cart',
        type: 'GET',
        data: { action: 'showCart' },
        dataType: 'json',
        success: function(response) {
            if (response) {
                console.log("Cart Items:", response.cartItems);
                console.log("Product Name and Price List:", response.productNameAndPrice);

                let cartTableBody = $('#cartTable tbody');
                cartTableBody.empty(); // Clear existing table rows

                for (let i = 0; i < response.cartItems.length; i++) {
                    let item = response.cartItems[i];
                    let product = response.productNameAndPrice[i];

                    let row = '<tr>' +
                        '<td>' + item.productid + '</td>' +
                        '<td>' + product.productName + '</td>' +
                        '<td>' + product.productPrice + '</td>' +
                        '<td>' + item.quantity + '</td>' +
                        '<td>' + item.total + '</td>' +
                        '</tr>';
                    cartTableBody.append(row);
                }
            }
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
            alert("Error encountered while processing the request.");
        }
    });
})
package com.example.ecommerce.model
data class CartItem(
    val product: Product,
    val offer: Offer,
    var quantity: Int = 1
) {
    // Calculate total price for the cart item
    fun getTotalPrice(): Double {
        return offer.price * quantity
    }
}
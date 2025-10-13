package com.example.ecommerce.model

// The Cart singleton now stores a list of CartItem objects.
object Cart {
    val items = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        // You could add more complex logic here, like checking if the
        // same product from the same seller is already in the cart,
        // and if so, just increasing the quantity.
        items.add(item)
    }

    // ADD THIS FUNCTION
    fun removeItem(item: CartItem) {
        items.remove(item)
    }

    fun getCartItems(): List<CartItem> {
        return items
    }
}
package com.example.ecommerce.model

object Cart {
    val items = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        items.add(item)
    }
    fun removeItem(item: CartItem) {
        items.remove(item)
    }

    fun getCartItems(): List<CartItem> {
        return items
    }
}
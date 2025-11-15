package com.example.ecommerce.model

object Cart {
    private val items = mutableListOf<CartItem>()
    fun addItem(item: CartItem): Boolean {
        val existingItem = items.find {
            it.product.productId == item.product.productId &&
                    it.offer.sellerName == item.offer.sellerName
        }

        return if (existingItem != null) {
            existingItem.quantity += item.quantity
            false // Item already existed
        } else {
            items.add(item)
            true // New item added
        }
    }

     // Remove item completely from cart
    fun removeItem(item: CartItem) {
        items.remove(item)
    }

     // Update quantity of a specific item
    fun updateQuantity(item: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(item)
        } else {
            items.find {
                it.product.productId == item.product.productId &&
                        it.offer.sellerName == item.offer.sellerName
            }?.quantity = newQuantity
        }
    }

     // Increase quantity by 1
    fun incrementQuantity(item: CartItem) {
        items.find {
            it.product.productId == item.product.productId &&
                    it.offer.sellerName == item.offer.sellerName
        }?.let {
            it.quantity++
        }
    }

    // Decrease quantity by 1 (removes if quantity becomes 0)
    fun decrementQuantity(item: CartItem) {
        items.find {
            it.product.productId == item.product.productId &&
                    it.offer.sellerName == item.offer.sellerName
        }?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                removeItem(it)
            }
        }
    }

     // Get all cart items
    fun getCartItems(): List<CartItem> {
        return items.toList()
    }

     // Get total number of items (counting quantities)
    fun getTotalItemCount(): Int {
        return items.sumOf { it.quantity }
    }

     // Get total price of all items
    fun getTotalPrice(): Double {
        return items.sumOf { it.getTotalPrice() }
    }

    // Clear all items from cart
    fun clear() {
        items.clear()
    }

     // Check if cart is empty
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    // Get number of unique items
    fun getUniqueItemCount(): Int {
        return items.size
    }
}
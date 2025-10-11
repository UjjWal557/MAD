package com.example.ecommerce.model

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val sellers: List<Seller>
)

data class Seller(
    val sellerName: String,
    // Changed Int to Double to match the JSON data
    val price: Double,
    val rating: Double,
    val feedbackCount: Int,
    val deliveryPeriod: String,
    val isTrusted: Boolean
)
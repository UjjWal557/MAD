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
    val price: Double,
    val rating: Double,
    val feedbackCount: Int,
    val deliveryDays: Int,
    val isTrusted: Boolean
)
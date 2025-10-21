// In file: model/Product.kt

package com.example.ecommerce.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val products: List<Product>
)

data class Product(
    @SerializedName("product_id")
    val productId: String,

    @SerializedName("product_name")
    val productName: String,

    val brand: String,

    @SerializedName("base_image_url")
    val baseImageUrl: String,

    val description: String,
    val variants: List<Variant>
)

data class Variant(
    @SerializedName("variant_id")
    val variantId: String,

    val specifications: Map<String, String>,
    val offers: List<Offer>
)

data class Offer(
    @SerializedName("seller_name")
    val sellerName: String,

    val price: Double,
    val rating: Double,

    @SerializedName("rating_count")
    val ratingCount: Int,

    @SerializedName("delivery_in_days")
    val deliveryInDays: Int,

    @SerializedName("is_trusted_seller")
    val isTrustedSeller: Boolean
)
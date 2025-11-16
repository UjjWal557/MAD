package com.example.ecommerce

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommerce.adapter.OfferAdapter
import com.example.ecommerce.databinding.ActivityProductDetailBinding
import com.example.ecommerce.model.Cart
import com.example.ecommerce.model.CartItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductResponse
import com.example.ecommerce.model.Variant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var offerAdapter: OfferAdapter
    private var allProducts = listOf<Product>()
    private lateinit var currentProduct: Product
    private var selectedVariant: Variant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("PRODUCT_ID")
        if (productId == null) {
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        allProducts = loadProductsFromAssets()
        val product = allProducts.firstOrNull { it.productId == productId }

        if (product == null) {
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentProduct = product
        setupUI()
        setupOffersRecyclerView()
        displaySpecificationsAndOffers()
    }

    private fun setupUI() {
        binding.textViewProductName.text = currentProduct.productName
        binding.textViewDescription.text = currentProduct.description
        Glide.with(this)
            .load(currentProduct.baseImageUrl)
            .into(binding.imageViewProduct)
    }

    private fun setupOffersRecyclerView() {
        offerAdapter = OfferAdapter { selectedOffer ->
            val cartItem = CartItem(product = currentProduct, offer = selectedOffer)
            val added = Cart.addItem(cartItem)

            if (added) {
                Toast.makeText(
                    this,
                    "${currentProduct.productName} from ${selectedOffer.sellerName} added to cart 🛒",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Quantity increased in cart",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.recyclerViewOffers.apply {
            layoutManager = LinearLayoutManager(this@ProductDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    private fun displaySpecificationsAndOffers() {
                if (currentProduct.variants.isNotEmpty()) {
            selectedVariant = currentProduct.variants.first()

            // Display specifications
            val specsText = buildString {
                selectedVariant?.specifications?.forEach { (key, value) ->
                    append("• $key: $value\n")
                }
            }.trim()

            binding.textViewSpecifications.text = specsText.ifEmpty { "No specifications available" }

            // Collect all offers from all variants
            val allOffers = currentProduct.variants.flatMap { it.offers }.distinctBy { it.sellerName }
            offerAdapter.submitList(allOffers)
        } else {
            binding.textViewSpecifications.text = "No specifications available"
            offerAdapter.submitList(emptyList())
        }
    }

    private fun loadProductsFromAssets(): List<Product> {
        return try {
            val jsonString = assets.open("products.json").bufferedReader().use { it.readText() }
            val responseType = object : TypeToken<ProductResponse>() {}.type
            Gson().fromJson<ProductResponse>(jsonString, responseType).products
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Toast.makeText(this, "Error loading products", Toast.LENGTH_SHORT).show()
            emptyList()
        }
    }
}
package com.example.ecommerce

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var offerAdapter: OfferAdapter
    private var allProducts = listOf<Product>()
    private lateinit var currentProduct: Product
    private val selectedSpecs = mutableMapOf<String, String>()

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
        createSpecSelectors()
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
                    "This item is already in your cart",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.recyclerViewOffers.apply {
            layoutManager = LinearLayoutManager(this@ProductDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    private fun createSpecSelectors() {
        val specTypes = currentProduct.variants
            .flatMap { it.specifications.keys }
            .distinct()

        specTypes.forEach { specType ->
            val specValues = currentProduct.variants
                .mapNotNull { it.specifications[specType] }
                .distinct()

            addChipGroupForSpec(specType, specValues)
        }
    }

    private fun addChipGroupForSpec(specType: String, specValues: List<String>) {
        // Create a TextView label
        val specLabel = TextView(this).apply {
            text = "Select $specType:"
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16.dpToPx()
            }
            setTextAppearance(android.R.style.TextAppearance_Material_Title)
        }

        // Create a ChipGroup
        val chipGroup = ChipGroup(this).apply {
            isSingleSelection = true
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Create chips
        specValues.forEach { specValue ->
            val chip = Chip(this).apply {
                text = specValue
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedSpecs[specType] = specValue
                        updateOffers()
                    }
                }
            }
            chipGroup.addView(chip)
        }

        binding.specsContainer.addView(specLabel)
        binding.specsContainer.addView(chipGroup)
    }

    private fun updateOffers() {
        val selectedVariant = currentProduct.variants.find { variant ->
            selectedSpecs.all { (key, value) ->
                variant.specifications[key] == value
            }
        }

        if (selectedVariant != null) {
            offerAdapter.submitList(selectedVariant.offers)
        } else {
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

    // Extension function for dp to px conversion
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
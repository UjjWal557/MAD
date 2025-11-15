package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.adapter.ProductAdapter
import com.example.ecommerce.databinding.ActivityHomeBinding
import com.example.ecommerce.model.Cart
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private var originalProductList = listOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupBottomNavigation()

        binding.deal.setOnClickListener {
            Toast.makeText(this, getString(R.string.deals_not_available), Toast.LENGTH_SHORT).show()
        }

        loadProducts()
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT_ID", product.productId)
            }
            startActivity(intent)
        }

        binding.recyclerViewProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_home

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        updateCartBadge()
    }

    private fun updateCartBadge() {
        val cartItemCount = Cart.getTotalItemCount()
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.navigation_cart)
        if (cartItemCount > 0) {
            badge.isVisible = true
            badge.number = cartItemCount
        } else {
            badge.isVisible = false
        }
    }

    private fun filterProducts(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            originalProductList
        } else {
            val searchQuery = query.lowercase(Locale.ROOT)
            originalProductList.filter { product ->
                product.productName.lowercase(Locale.ROOT).contains(searchQuery) ||
                        product.brand.lowercase(Locale.ROOT).contains(searchQuery) ||
                        product.description.lowercase(Locale.ROOT).contains(searchQuery)
            }
        }
        productAdapter.submitList(filteredList)
    }

    private fun loadProducts() {
        originalProductList = loadProductsFromAssets()
        if (originalProductList.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_loading_products), Toast.LENGTH_LONG).show()
        } else {
            productAdapter.submitList(originalProductList)
        }
    }

    private fun loadProductsFromAssets(): List<Product> {
        return try {
            val jsonString = assets.open("products.json").bufferedReader().use { it.readText() }
            val responseType = object : TypeToken<ProductResponse>() {}.type
            Gson().fromJson<ProductResponse>(jsonString, responseType).products
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            emptyList()
        }
    }
}
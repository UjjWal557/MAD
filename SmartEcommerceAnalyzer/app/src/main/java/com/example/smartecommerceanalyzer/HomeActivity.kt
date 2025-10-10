package com.example.smartecommerceanalyzer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartecommerceanalyzer.adapter.ProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.smartecommerceanalyzer.model.Product
import java.io.IOException

class HomeActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Apply window insets for the edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Find views by their ID ---
        productsRecyclerView = findViewById(R.id.recyclerViewProducts)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // --- Setup UI and Load Data ---
        setupRecyclerView()
        loadProductData()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        // Initialize the adapter with click listeners
        productAdapter = ProductAdapter { product, action ->
            when (action) {
                ProductAdapter.Action.VIEW_DETAILS -> {
                    // This action is handled within the adapter to show/hide details
                }
                ProductAdapter.Action.ADD_TO_CART -> {
                    Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Apply layout manager and adapter to the RecyclerView
        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = productAdapter
        }
    }

    private fun loadProductData() {
        try {
            // Read the JSON file from the assets folder
            val jsonString = application.assets.open("products.json").bufferedReader().use { it.readText() }
            val listProductType = object : TypeToken<List<Product>>() {}.type
            val products: List<Product> = Gson().fromJson(jsonString, listProductType)

            // Submit the list of products to the adapter
            productAdapter.submitList(products)
        } catch (ioException: IOException) {
            Log.e("HomeActivity", "Error reading products.json", ioException)
            Toast.makeText(this, "Failed to load product data", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true // Already on the home screen
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
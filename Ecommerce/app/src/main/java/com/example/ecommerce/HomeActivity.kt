package com.example.ecommerce

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


// File: HomeActivity.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView // Make sure to import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var productAdapter: ProductAdapter

    // Keep a copy of the original, full list of products
    private var originalProductList = listOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        productsRecyclerView = findViewById(R.id.recyclerViewProducts)
        searchView = findViewById(R.id.searchView)

        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        // Load the original list from JSON
        originalProductList = loadProductsFromAssets()

        // Initialize the adapter with the full list
        productAdapter = ProductAdapter(originalProductList)
        productsRecyclerView.adapter = productAdapter
        productsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    // --- ADD THIS ENTIRE FUNCTION ---
    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // This method is called when the user presses the search button
            override fun onQueryTextSubmit(query: String?): Boolean {
                // You can optionally handle this, but live filtering is often better
                return false
            }

            // This method is called every time the user types a character
            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }

    // --- ADD THIS ENTIRE FUNCTION ---
    private fun filterProducts(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            // If the search query is empty, show the original full list
            originalProductList
        } else {
            // Otherwise, filter the original list
            originalProductList.filter { product ->
                // Check if the product name contains the search query (case-insensitive)
                product.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
        // Update the adapter with the new filtered list
        productAdapter.filterList(filteredList)
    }

    private fun loadProductsFromAssets(): List<Product> {
        // ... (This function remains the same as before)
    }
}
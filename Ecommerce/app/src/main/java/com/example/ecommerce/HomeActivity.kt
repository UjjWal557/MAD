package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.adapter.ProductAdapter
import com.example.ecommerce.databinding.ActivityHomeBinding
import com.example.ecommerce.model.Product
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

        setupEdgeToEdge()
        setupRecyclerView()
        setupSearchView()
        setupBottomNavigation() // Add this call

        originalProductList = loadProductsFromAssets()
        productAdapter.submitList(originalProductList)
    }

    private fun setupRecyclerView() {
        // Instantiate the refactored adapter
        productAdapter = ProductAdapter()

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

    // Add this new function for navigation
    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on the home screen, do nothing
                    true
                }
                R.id.navigation_cart -> {
                    // Navigate to CartActivity
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun filterProducts(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            originalProductList
        } else {
            val searchQuery = query.lowercase(Locale.ROOT)
            originalProductList.filter { product ->
                product.name.lowercase(Locale.ROOT).contains(searchQuery)
            }
        }
        productAdapter.submitList(filteredList)
    }

    private fun loadProductsFromAssets(): List<Product> {
        return try {
            val jsonString = assets.open("products.json").bufferedReader().use { it.readText() }
            val listProductType = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(jsonString, listProductType)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            emptyList()
        }
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
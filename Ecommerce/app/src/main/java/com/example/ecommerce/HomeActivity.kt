package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

        setupRecyclerView()
        setupSearchView()
        setupBottomNavigation()

        binding.deal.setOnClickListener {
            Toast.makeText(this,"New deals not available",Toast.LENGTH_SHORT).show()
        }

        originalProductList = loadProductsFromAssets()
        productAdapter.submitList(originalProductList)
    }

    private fun setupRecyclerView() {
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

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.navigation_profile ->{
                    startActivity(Intent(this, ProfileActivity::class.java))
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
}
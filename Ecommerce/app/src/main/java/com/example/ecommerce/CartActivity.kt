package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.adapter.CartAdapter
import com.example.ecommerce.databinding.ActivityCartBinding
import com.example.ecommerce.model.Cart

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        setupBottomNavigation()
        binding.deal.setOnClickListener {
            Toast.makeText(this,"New deals not available",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadCartItems()
    }
    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_cart

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_cart -> {
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

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter { cartItem ->
            Cart.removeItem(cartItem)
            Toast.makeText(this, "${cartItem.product.name} removed from cart", Toast.LENGTH_SHORT).show()
            loadCartItems()
        }
        binding.recyclerViewCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun loadCartItems() {
        val cartItems = Cart.getCartItems()

        if (cartItems.isEmpty()) {
            binding.recyclerViewCart.visibility = View.GONE
            binding.textViewEmptyCart.visibility = View.VISIBLE
        } else {
            binding.recyclerViewCart.visibility = View.VISIBLE
            binding.textViewEmptyCart.visibility = View.GONE
            cartAdapter.submitList(cartItems.toList())
        }
    }
}
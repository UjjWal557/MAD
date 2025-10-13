package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemProductBinding
import com.example.ecommerce.model.Cart
import com.example.ecommerce.model.CartItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.Seller

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.textViewProductName.text = product.name
            binding.textViewDescription.text = product.description
            Glide.with(itemView.context)
                .load(product.imageUrl)
                .into(binding.imageViewProduct)

            // Setup nested RecyclerView for sellers
            setupSellersRecyclerView(product)
        }

        private fun setupSellersRecyclerView(product: Product) {
            val sellerAdapter = SellerAdapter(product.sellers) { selectedSeller ->
                // This callback runs when "Add to Cart" is clicked on a seller
                val cartItem = CartItem(product = product, seller = selectedSeller)
                Cart.addItem(cartItem)

                Toast.makeText(
                    itemView.context,
                    "${product.name} from ${selectedSeller.sellerName} added to cart 🛒",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.recyclerViewSellers.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = sellerAdapter
            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.model.Product
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ItemProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val onProductClicked: (Product, Action) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    enum class Action {
        VIEW_DETAILS, ADD_TO_CART
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onProductClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClicked: (Product, Action) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Find the seller with the lowest price
            val bestSeller = product.sellers.minByOrNull { it.price }

            binding.textViewProductName.text = product.name

            // Format currency for India (₹)
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

            bestSeller?.let {
                binding.textViewBestPrice.text = "Best Price: ${format.format(it.price)}"
                binding.textViewSellerInfo.text = "from ${it.sellerName} (${it.rating}⭐)"
            }

            // Load image from URL using Glide
            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                .into(binding.imageViewProduct)

            // Setup the detailed, collapsible view
            binding.textViewDescription.text = product.description
            val sellersText = product.sellers.joinToString("\n") { seller ->
                val trustedMark = if (seller.isTrusted) "✓ Trusted" else ""
                "- ${seller.sellerName}: ${format.format(seller.price)} (${seller.rating}⭐, ${seller.feedbackCount} reviews) $trustedMark"
            }
            binding.textViewSellersList.text = sellersText

            // Handle expand/collapse button click
            binding.buttonViewDetails.setOnClickListener {
                toggleDetailsVisibility()
            }

            binding.buttonAddToCart.setOnClickListener {
                onProductClicked(product, Action.ADD_TO_CART)
            }
        }

        private fun toggleDetailsVisibility() {
            val isDetailsVisible = binding.groupProductDetails.visibility == View.VISIBLE
            binding.groupProductDetails.visibility = if (isDetailsVisible) View.GONE else View.VISIBLE
            binding.buttonViewDetails.text = if (isDetailsVisible) "View Details" else "Hide Details"
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
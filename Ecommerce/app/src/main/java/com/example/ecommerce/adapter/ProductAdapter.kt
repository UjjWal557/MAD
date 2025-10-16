package com.example.ecommerce.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ItemProductBinding
import com.example.ecommerce.model.Cart
import com.example.ecommerce.model.CartItem
import com.example.ecommerce.model.Product

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
                .placeholder(R.drawable.ic_placeholder) // Optional: Shows a loading image
                .error(R.drawable.ic_error)           // Optional: Shows an error image on failure
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("GlideError", "Image load failed for URL: ${product.imageUrl}", e)
                        return false // Return false to allow the error drawable to be shown
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.imageViewProduct)

            setupSellersRecyclerView(product)
        }

        private fun setupSellersRecyclerView(product: Product) {

            val sellerAdapter = SellerAdapter { selectedSeller ->
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
            sellerAdapter.submitList(product.sellers)
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
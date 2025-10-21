package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCartBinding
import com.example.ecommerce.model.CartItem

class CartAdapter(
    private val onRemoveClicked: (CartItem) -> Unit,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.productName.text = cartItem.product.productName
            binding.sellerName.text = "Sold by: ${cartItem.offer.sellerName}"
            binding.productPrice.text = "₹${"%,.0f".format(cartItem.offer.price)}"
            binding.quantityText.text = cartItem.quantity.toString()

            // Calculate and show total price for this item
            val totalPrice = cartItem.getTotalPrice()
            binding.totalPrice.text = "Total: ₹${"%,.0f".format(totalPrice)}"

            Glide.with(itemView.context)
                .load(cartItem.product.baseImageUrl)
                .into(binding.productImage)

            // Quantity controls
            binding.buttonDecrease.setOnClickListener {
                if (cartItem.quantity > 1) {
                    onQuantityChanged(cartItem, cartItem.quantity - 1)
                } else {
                    onRemoveClicked(cartItem)
                }
            }

            binding.buttonIncrease.setOnClickListener {
                onQuantityChanged(cartItem, cartItem.quantity + 1)
            }

            binding.buttonRemove.setOnClickListener {
                onRemoveClicked(cartItem)
            }
        }
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.product.productId == newItem.product.productId &&
                oldItem.offer.sellerName == newItem.offer.sellerName
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem && oldItem.quantity == newItem.quantity
    }
}
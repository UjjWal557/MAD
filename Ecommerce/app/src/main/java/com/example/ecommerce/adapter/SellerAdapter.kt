package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ItemSellerBinding
import com.example.ecommerce.model.Seller

class SellerAdapter(
    private val onAddToCartClicked: (Seller) -> Unit
) : ListAdapter<Seller, SellerAdapter.SellerViewHolder>(SellerDiffCallback()) {

    inner class SellerViewHolder(private val binding: ItemSellerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(seller: Seller) {
            binding.textViewSellerName.text = seller.sellerName
            binding.textViewPrice.text = "₹${"%,.0f".format(seller.price)}"
            binding.textViewRating.text = "${seller.rating} (${seller.feedbackCount})"
            binding.textViewDelivery.text = "Delivery in ${seller.deliveryDays} days"

            val isTrusted = seller.rating > 4.3 && seller.feedbackCount > 1000

            binding.textViewTrusted.visibility = View.VISIBLE

            if (isTrusted) {
                binding.textViewTrusted.text = "Trusted Seller"
                binding.textViewTrusted.setTextColor(ContextCompat.getColor(itemView.context, R.color.trusted))
                binding.textViewTrusted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trusted, 0, 0, 0)
            } else {
                binding.textViewTrusted.text = "Not Trusted"
                binding.textViewTrusted.setTextColor(ContextCompat.getColor(itemView.context, R.color.not_trusted))
                binding.textViewTrusted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_untrusted, 0, 0, 0)
            }
            binding.buttonAddToCart.setOnClickListener {
                onAddToCartClicked(seller)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
        val binding = ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SellerDiffCallback : DiffUtil.ItemCallback<Seller>() {
        override fun areItemsTheSame(oldItem: Seller, newItem: Seller): Boolean {
            return oldItem.sellerName == newItem.sellerName
        }

        override fun areContentsTheSame(oldItem: Seller, newItem: Seller): Boolean {
            return oldItem == newItem
        }
    }
}
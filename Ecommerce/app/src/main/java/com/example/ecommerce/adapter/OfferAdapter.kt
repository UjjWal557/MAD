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
import com.example.ecommerce.model.Offer

class OfferAdapter(
    private val onAddToCartClicked: (Offer) -> Unit
) : ListAdapter<Offer, OfferAdapter.OfferViewHolder>(OfferDiffCallback()) {

    inner class OfferViewHolder(private val binding: ItemSellerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            val context = binding.root.context

            binding.textViewSellerName.text = offer.sellerName
            binding.textViewPrice.text = context.getString(R.string.price_format, "%,.0f".format(offer.price))
            binding.textViewRating.text = context.getString(R.string.rating_format, offer.rating, offer.ratingCount)
            binding.textViewDelivery.text = context.getString(R.string.delivery_days, offer.deliveryInDays)

            // Determine if seller is trusted
            val isTrusted = offer.isTrustedSeller && offer.rating > 4.3 && offer.ratingCount > 1000

            // Show trusted/untrusted seller badgee
            binding.textView.visibility = View.VISIBLE
            if (isTrusted) {
                binding.textView.text = context.getString(R.string.trusted_seller)
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.trusted))

                // Set drawable with tint
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_trusted)
                binding.textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                binding.textView.compoundDrawablesRelative[0]?.setTint(
                    ContextCompat.getColor(context, R.color.trusted)
                )
            } else {
                binding.textView.text = context.getString(R.string.untrusted_seller)
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.not_trusted))

                // Set drawable with tint
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_untrusted)
                binding.textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                binding.textView.compoundDrawablesRelative[0]?.setTint(
                    ContextCompat.getColor(context, R.color.not_trusted)
                )
            }

            binding.buttonAddToCart.setOnClickListener {
                onAddToCartClicked(offer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.sellerName == newItem.sellerName
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }
}
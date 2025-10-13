package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.model.Seller

class SellerAdapter(
    private val sellers: List<Seller>,
    // This is a callback function. It will be triggered when a cart button is clicked.
    // It passes the selected Seller back to the parent adapter.
    private val onAddToCartClicked: (Seller) -> Unit
) : RecyclerView.Adapter<SellerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sellerName: TextView = view.findViewById(R.id.sellerName)
        val sellerPrice: TextView = view.findViewById(R.id.sellerPrice)
        val sellerRating: TextView = view.findViewById(R.id.sellerRating)
        val addToCartButton: Button = view.findViewById(R.id.buttonAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seller, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seller = sellers[position]
        holder.sellerName.text = seller.sellerName
        holder.sellerPrice.text = "₹${seller.price}"
        holder.sellerRating.text = "⭐ ${seller.rating} (${seller.feedbackCount})"

        // When the button is clicked, invoke the callback with the current seller
        holder.addToCartButton.setOnClickListener {
            onAddToCartClicked(seller)
        }
    }

    override fun getItemCount() = sellers.size
}
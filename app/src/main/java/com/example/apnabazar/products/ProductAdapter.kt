package com.example.apnabazar.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.ProductEdge

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    lateinit var productEdge : List<Storefront.ProductEdge>
    lateinit var ctx : Context

    fun setProductData(
        productEdge: List<Storefront.ProductEdge>,
        ctx : Context
    ){
        this.productEdge = productEdge
        this.ctx = ctx
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(com.example.apnabazar.R.id.productName)
        val img = view.findViewById<ImageView>(com.example.apnabazar.R.id.productImg)
        val price = view.findViewById<TextView>(com.example.apnabazar.R.id.pricing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.apnabazar.R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.productEdge[position].node


        holder.title.text = data.title
        holder.price.text = data.priceRange.maxVariantPrice.amount

        if(this.productEdge[position].node.images.edges.size > 0){
            val url = this.productEdge[position].node.images.edges[0].node.url
            Glide.with(ctx).load(url).centerCrop().into(holder.img)
        }

    }

    override fun getItemCount(): Int {
        return productEdge.size
    }
}
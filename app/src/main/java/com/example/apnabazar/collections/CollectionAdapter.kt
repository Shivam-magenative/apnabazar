package com.example.apnabazar.collections

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shopify.buy3.Storefront
import kotlin.math.log

class CollectionAdapter : RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){
    lateinit var collectionEdges: List<Storefront.CollectionEdge>
    lateinit var ctx: Context

    fun setData(collectionEdges: List<Storefront.CollectionEdge>, ctx: Context) {
        this.collectionEdges = collectionEdges
        this.ctx = ctx
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val categoryText = view.findViewById<TextView>(com.example.apnabazar.R.id.categoryText)
        val categoryImg = view.findViewById<ImageView>(com.example.apnabazar.R.id.categoryImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.apnabazar.R.layout.collection_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = collectionEdges[position].node
        holder.categoryText.text = data.title
        if (data.image != null){

            val url = data.image.url
            Glide.with(ctx).load(url).centerCrop().into(holder.categoryImg)

        }

    }
    override fun getItemCount(): Int {
        return collectionEdges.size
    }
}
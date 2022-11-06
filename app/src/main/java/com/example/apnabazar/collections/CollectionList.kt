package com.example.apnabazar.collections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apnabazar.R
import com.example.apnabazar.ViewModel.CollectionVIewModel
import com.example.apnabazar.ViewModel.ViewModelFactory
import com.example.apnabazar.backend.Repositories
import com.shopify.buy3.Storefront

class CollectionList : AppCompatActivity() {
    private var model : CollectionVIewModel?=null
    lateinit var factory: ViewModelFactory
    lateinit var categoryList : RecyclerView
    private var adapter: CollectionAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_list)
        categoryList = findViewById(R.id.collectionList)
        categoryList.layoutManager = LinearLayoutManager(applicationContext)

//        if (model!=null){
//            model = ViewModelProvider(this, factory!!)[CollectionVIewModel::class.java]
//            model!!.context = this
//            model!!.Response().observe(this, Observer<List<Storefront.CollectionEdge>>{
//                this.setRecyclerData(it)
//            })
//        }else{
            var repositories=Repositories()
            repositories.context=this
            model= CollectionVIewModel(repositories)
            model!!.context = this
            model!!.Response().observe(this, Observer<List<Storefront.CollectionEdge>>{
                this.setRecyclerData(it)
            })

//        }
    }

    private fun setRecyclerData(collections: List<Storefront.CollectionEdge>?) {
        if(collections!!.size > 0){
            adapter= CollectionAdapter()
            adapter?.setData(collections, this)
            categoryList.adapter = adapter

            adapter!!.notifyDataSetChanged()
        }else
        {
            Toast.makeText(this, "No collection found...",Toast.LENGTH_LONG).show()
        }
    }
}
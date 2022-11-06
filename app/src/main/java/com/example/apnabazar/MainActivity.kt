package com.example.apnabazar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apnabazar.ViewModel.CollectionVIewModel
import com.example.apnabazar.ViewModel.ProductViewModel
import com.example.apnabazar.ViewModel.ViewModelFactory
import com.example.apnabazar.backend.GraphQLResponse
import com.example.apnabazar.backend.Repositories
import com.example.apnabazar.backend.Status
import com.example.apnabazar.collections.CollectionList
import com.example.apnabazar.products.ProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.Error

class MainActivity : AppCompatActivity() {
    private var productModel : ProductViewModel ?= null
   // private var factory: ViewModelFactory?=null
    lateinit var productEdge : List<Storefront.ProductEdge>
    lateinit var productList : RecyclerView
    lateinit var adapter: ProductAdapter
    lateinit var nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        img = findViewById(R.id.productImg)
//        title = findViewById(R.id.productTitle)
//        price = findViewById(R.id.productPrice)

        nav = findViewById(R.id.bottomNav)
          productList = findViewById(R.id.recyclerView)

//        val layoutManager = LinearLayoutManager(applicationContext)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL


        nav.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.menu_home -> {

                }
                R.id.menu_category ->{
                    startActivity(Intent(this, CollectionList::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                }
            }
        }

        productList.layoutManager = GridLayoutManager(applicationContext, 2)


        val repositories= Repositories()
        repositories.context=this
        productModel= ProductViewModel(repositories)
        productModel!!.context = this
        productModel!!.Response().observe(this, Observer{
            this.consumeResponse(it)
        })

    }

    private fun consumeResponse(reponse: GraphQLResponse) {
        when (reponse.status) {
            Status.SUCCESS -> {
                val result =
                    (reponse.data as GraphCallResult.Success<Storefront.QueryRoot>).response
                if (result.hasErrors) {
                    val errors = result.errors
                    val iterator = errors.iterator()
                    val errormessage = StringBuilder()
                    var error: Error? = null
                    while (iterator.hasNext()) {
                        error = iterator.next()
                        errormessage.append(error.message())
                    }
                    Toast.makeText(this, "" + errormessage, Toast.LENGTH_SHORT).show()
                }else{
                    try {
                        productEdge = result.data!!.products.edges

                        if (productEdge.size > 0) {
                            adapter = ProductAdapter()
                            adapter.setProductData(productEdge, this)
                            productList.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }

                    }catch (e : Exception){
                        e.printStackTrace()
                    }

                }
            }
            Status.ERROR -> Toast.makeText(this, reponse.error!!.error.message, Toast.LENGTH_SHORT)
                .show()
            else -> {}
        }
    }
}



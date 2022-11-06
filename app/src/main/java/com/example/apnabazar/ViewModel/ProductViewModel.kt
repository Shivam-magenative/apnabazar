package com.example.apnabazar.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apnabazar.backend.*
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.Error

class ProductViewModel(val repository: Repositories) : ViewModel(){
//    private var categoryID = "426489348378"
    var keys: Storefront.ProductSortKeys? = Storefront.ProductSortKeys.TITLE
    var cursor = "nocursor"
    var sortKeys: Storefront.ProductCollectionSortKeys? = Storefront.ProductCollectionSortKeys.TITLE
    var isDirection = false
    var number = 10
    val message = MutableLiveData<String>()
    lateinit var context: Context
    private val responseLiveData = MutableLiveData<GraphQLResponse>()
//
//    fun getCategoryID() : String{
//        return categoryID
//    }
    fun Response() : MutableLiveData<GraphQLResponse> {
        getAllProducts()
        return responseLiveData
    }


    private fun getAllProducts() {
        try {
            doGraphQLQueryGraph(
                repository,
                Query.getProducts( cursor, keys, isDirection,number),
                customResponse = object : CustomResponse {
                    override fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {
                        invoke(result)
                    }
                },
                context = context
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private operator fun invoke(result: GraphCallResult<Storefront.QueryRoot>): Unit {
        if (result is GraphCallResult.Success<*>) {
            responseLiveData.value = GraphQLResponse.success(result as GraphCallResult.Success<*>)
        } else {
            responseLiveData.value = GraphQLResponse.error(result as GraphCallResult.Failure)
        }
        return Unit
    }

}
package com.example.apnabazar.backend

import android.content.Context
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun doGraphQLQueryGraph(
    repository: Repositories,
    query: Storefront.QueryRootQuery,
    customResponse: CustomResponse,
    context: Context
) {
    CoroutineScope(Dispatchers.Main).launch {

    }
    var call = repository.graphClient.queryGraph(query)
    call.enqueue { result: GraphCallResult<Storefront.QueryRoot> ->
        CoroutineScope(Dispatchers.Main).launch {
            customResponse.onSuccessQuery(result)

        }
    }
}
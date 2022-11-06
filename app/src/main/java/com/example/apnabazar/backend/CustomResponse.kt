package com.example.apnabazar.backend

import com.google.gson.JsonElement
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront

interface CustomResponse {

    fun onSuccessMutate(result: GraphCallResult<Storefront.Mutation>) {}
    fun onSuccessQuery(result: GraphCallResult<Storefront.QueryRoot>) {}
}
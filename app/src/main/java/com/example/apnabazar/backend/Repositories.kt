package com.example.apnabazar.backend

import android.content.Context
import com.shopify.buy3.GraphClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class Repositories {

    lateinit var context : Context

    val graphClient: GraphClient
        get() {
            return GraphClient.build(
                context,
                Urls().shopdomain,
                Urls().apikey,

            )
        }
}
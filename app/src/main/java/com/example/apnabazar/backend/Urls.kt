package com.example.apnabazar.backend

import android.util.Log
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class Urls {

    val shopdomain: String
        get() {
            var domain = "anubhav-library.myshopify.com" //magenative-store.myshopify.com
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
//                    if (repository.getPreviewData().size > 0) {
//                        domain = repository.getPreviewData().get(0).shopurl!!
//                    }
                    domain
                }
                val future = executor.submit(callable)
                domain = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return domain
        }


    val mid: String
        get() {
            var domain = "11092" // 3937
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
//                    if (repository.getPreviewData().size > 0) {
//                        domain = repository.getPreviewData().get(0).mid!!
//                    }
                    domain
                }
                val future = executor.submit(callable)
                domain = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return domain
        }
    val apikey: String
        get() {
            var key = "dd8d1ad84279ea342ca6f09e714a4c63"
            try {
                val executor = Executors.newSingleThreadExecutor()
                val callable = Callable {
//                    if (repository.getPreviewData().size > 0) {
//                        key = repository.getPreviewData().get(0).apikey!!
//                    }
                    key
                }
                val future = executor.submit(callable)
                key = future.get()
                executor.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return key
        }

}
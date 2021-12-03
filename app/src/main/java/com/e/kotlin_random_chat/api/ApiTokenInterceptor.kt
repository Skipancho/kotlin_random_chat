package com.e.kotlin_random_chat.api

import com.e.kotlin_random_chat.common.Prefs
import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {
            Prefs.token?.let { header("Authorization", it) }
            method(original.method(), original.body())
        }.build()

        return chain.proceed(request)
    }
}
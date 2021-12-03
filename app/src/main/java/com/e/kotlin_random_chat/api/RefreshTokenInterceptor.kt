package com.e.kotlin_random_chat.api

import com.e.kotlin_random_chat.common.Prefs
import com.e.kotlin_random_chat.common.clearTasksAndStartNewActivity
import com.e.kotlin_random_chat.domain.auth.Auth
import okhttp3.Interceptor
import okhttp3.Response

class RefreshTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            Prefs.refreshToken?.let { header("Authorization",it) }
            method(original.method(), original.body())
        }.build()

        val response = chain.proceed(request)

        if (response.code() == 401){
            Auth.signout()
            //clearTasksAndStartNewActivity<SigninActivity>()
        }

        return response
    }

}
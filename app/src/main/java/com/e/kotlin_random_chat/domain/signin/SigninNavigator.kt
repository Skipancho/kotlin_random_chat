package com.e.kotlin_random_chat.domain.signin

import com.e.kotlin_random_chat.api.response.ApiResponse
import com.e.kotlin_random_chat.api.response.SigninResponse

interface SigninNavigator {
    fun startRandomChatActivity(response : ApiResponse<SigninResponse>)
}
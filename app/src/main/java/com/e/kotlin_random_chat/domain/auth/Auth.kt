package com.e.kotlin_random_chat.domain.auth

import com.e.kotlin_random_chat.common.Prefs

object Auth {

    fun signin(token : String, refreshToke : String, nickName : String){
        Prefs.token = token
        Prefs.refreshToken = refreshToke
        Prefs.nickname = nickName
    }

    fun signout(){
        Prefs.token = null
        Prefs.refreshToken = null
        Prefs.nickname = null
    }

    fun refreshToken(token: String){
        Prefs.token = token
    }
}
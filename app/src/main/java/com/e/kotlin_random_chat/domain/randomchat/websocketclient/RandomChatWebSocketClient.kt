package com.e.kotlin_random_chat.domain.randomchat.websocketclient

import android.util.Log
import com.e.kotlin_random_chat.App
import com.e.kotlin_random_chat.common.Prefs
import com.e.kotlin_random_chat.domain.randomchat.messagelistener.Message
import com.e.kotlin_random_chat.domain.randomchat.messagelistener.RandomChatMessageListener
import com.google.gson.Gson
import okhttp3.*

class RandomChatWebSocketClient(
    private val messageListener: RandomChatMessageListener
) : WebSocketListener(){

    private val okHttpClient = OkHttpClient()
    private val gson = Gson()

    init {
        val accessToken = "$ACCESS_TOKEN ${Prefs.token}"
        val request = Request.Builder()
            .addHeader(SEC_PROTOCOL, accessToken)
            .url(App.WEBSOCKET_ENDPOINT)
            .build()

        okHttpClient.newWebSocket(request, this)
        okHttpClient.dispatcher().executorService().shutdown()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("RandomChatWebSocket", "message: $text")
        val message = runCatching {
            gson.fromJson(text, Message::class.java)
        }

        message.onSuccess { messageListener.onMessage(it) }
        message.onFailure { messageListener.onMessageError(it) }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        messageListener.onStart()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        messageListener.onClosed()
    }

    companion object{
        const val SEC_PROTOCOL = "sec-websocket-protocol"
        const val ACCESS_TOKEN = "access_token"
    }
}
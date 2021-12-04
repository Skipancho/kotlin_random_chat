package com.e.kotlin_random_chat.domain.randomchat.messagelistener

interface RandomChatMessageListener {

    //메시지를 정상적으로 받았을 때 호출될 함수
    fun onMessage(message : Message)

    //메시지 처리 오류시 호출될 함수
    fun onMessageError(t : Throwable)

    //네트워크 오류시 호출될 함수
    fun onNetworkError(t : Throwable)

    //웹소켓 세션이 시작될 때 호출될 함수
    fun onStart()

    //웹소켓 접속이 끊겼을 때 호출될 함수
    fun onClosed()
}
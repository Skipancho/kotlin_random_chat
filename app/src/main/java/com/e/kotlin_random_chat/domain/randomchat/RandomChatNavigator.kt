package com.e.kotlin_random_chat.domain.randomchat

interface RandomChatNavigator {
    fun onMessage(messageModel: MessageModel)
}
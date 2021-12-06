package com.e.kotlin_random_chat.domain.randomchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.kotlin_random_chat.R
import com.e.kotlin_random_chat.databinding.ActivityRandomChatBinding
import splitties.toast.toast
import java.lang.ref.WeakReference

class RandomChatActivity : AppCompatActivity(), RandomChatNavigator {

    val viewModel by lazy{
        ViewModelProvider(this)
            .get(RandomChatViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    val binding by lazy {
        DataBindingUtil.setContentView<ActivityRandomChatBinding>(
                this,
                R.layout.activity_random_chat
            )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupConversationAdapter()
    }

    private fun setupConversationAdapter(){
        binding.conversation.layoutManager = LinearLayoutManager(this)
        binding.conversation.adapter =
            ConversationAdapter(this).also {
                it.addMessages(viewModel.messages)
            }
    }

    override fun onMessage(messageModel: MessageModel) {
        val adapter = binding.conversation.adapter as? ConversationAdapter

        adapter?.let {
            adapter.addMessage(messageModel)

            val lastPosition = adapter.itemCount - 1
            binding.conversation.smoothScrollToPosition(lastPosition)
        }
    }

    private var lastTimeBackPressed : Long  = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis()-lastTimeBackPressed < 1500){
            viewModel.onClosed()
            return
        }
        toast("뒤로가기를 한 번 더 눌러 채팅방을 나갑니다.")
        lastTimeBackPressed = System.currentTimeMillis()
    }
}
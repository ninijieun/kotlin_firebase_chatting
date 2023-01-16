package com.example.kotlin_firebase_chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_firebase_chatting.Model.UserItem
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val adapter = GroupieAdapter()
        recycleview_list.adapter = adapter

        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())



    }
}
package com.example.kotlin_firebase_chatting.adapter

import com.example.kotlin_firebase_chatting.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_left_you.view.*

class ChatLeftYou(val msg:String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.leftMsg.text = msg
    }

    override fun getLayout(): Int {
        return R.layout.chat_left_you
    }
}
package com.example.kotlin_firebase_chatting.adapter

import com.example.kotlin_firebase_chatting.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_right_me.view.*

class ChatRightMe(val msg:String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.rightMsg.text = msg
    }

    override fun getLayout(): Int {
       return R.layout.chat_right_me
    }
}
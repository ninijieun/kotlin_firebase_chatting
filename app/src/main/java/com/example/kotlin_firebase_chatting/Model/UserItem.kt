package com.example.kotlin_firebase_chatting.Model

import com.example.kotlin_firebase_chatting.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserItem : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {




    }

    override fun getLayout(): Int {
        return R.layout.message_list_row
    }


}
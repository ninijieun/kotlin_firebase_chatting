package com.example.kotlin_firebase_chatting.adapter

import com.example.kotlin_firebase_chatting.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.message_list_row.view.*

class UserItem(val name:String, val uid:String) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.name.text=name
    }

    override fun getLayout(): Int {
        return R.layout.message_list_row
    }


}
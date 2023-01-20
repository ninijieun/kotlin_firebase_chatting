package com.example.kotlin_firebase_chatting.Model

import java.util.Date

class User (val uid :String,
            val username:String,
            val statusMsg:String,
            val joinDate: Long){
    constructor() : this("","","",System.currentTimeMillis())
}
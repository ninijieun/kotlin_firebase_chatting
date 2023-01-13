package com.example.kotlin_firebase_chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val TAG:String=MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        join_button.setOnClickListener {
            val email = email_area.text.toString()
            val pwd = password_area.text.toString()

            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG,"createUserWithEmail:success")

                        //데이터베이스에 저장
                        val database = Firebase.database
                        val myRef = database.getReference("message")

                        val intent = Intent(this,ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        Log.w(TAG,"createUserWithEmail:failure", task.exception)
                        print(pwd)
                    }
                }
        }



        login_button_main.setOnClickListener {
            val intent = Intent(this,loginActivity::class.java)
            startActivity(intent)
        }
    }


}
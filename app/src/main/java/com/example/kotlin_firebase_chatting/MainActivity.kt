package com.example.kotlin_firebase_chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.login_Button
import kotlinx.android.synthetic.main.activity_main.login_email
import kotlinx.android.synthetic.main.activity_main.login_pwd

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val TAG:String=MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        try{
            login_Button.setOnClickListener {
                val email = login_email.text.toString()
                val pwd = login_pwd.text.toString()

                auth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")

                            val intent = Intent(this, FriendsListActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)

                        }
                    }
            }
        }catch (e:Exception){
            Log.d(TAG,e.toString())
        }

        join_button_main.setOnClickListener {
            val intent = Intent(this,JoinActivity::class.java)
            startActivity(intent)
        }

    }


}
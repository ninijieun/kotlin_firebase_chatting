package com.example.kotlin_firebase_chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kotlin_firebase_chatting.adapter.UserItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupieAdapter
import kotlinx.android.synthetic.main.activity_friends_list.*

class FriendsListActivity : AppCompatActivity() {

    val db = Firebase.firestore //firebase 데이터베이스
    val auth = Firebase.auth //firebase 인증
    val connectUser = auth.uid

    private val TAG:String= MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        val adapter = GroupieAdapter()

        //데이터베이스 읽기
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //접속 사용자일 경우, 나의 프로필에 세팅.
                    val userUid = document.get("uid").toString()
                    val userName = document.get("username").toString()

                    if(connectUser.equals(userUid)){
                        my_username.setText(userName)
                        my_statusMsg.setText(document.get("statusMsg").toString())
                    }else{
                        adapter.add(UserItem(userName,userUid ))
                    }

                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                recycleview_list.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        adapter.setOnItemClickListener { item, view ->

            val uid:String = (item as UserItem).uid
            val name:String = (item as UserItem).name

            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("yourUid",uid)
            intent.putExtra("yourName",name)
            startActivity(intent)
        }

        myChatList.setOnClickListener {
            val intent = Intent(this,MyRoomActivity::class.java)
            startActivity(intent)
        }

        status_button.setOnClickListener {
            var chg = mutableMapOf<String,Any>()
            chg["statusMsg"] = my_statusMsg.text.toString()
            db.collection("users").document(connectUser.toString()).update(chg)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"상태메세지 업데이트 완료",Toast.LENGTH_SHORT).show()
                    }
                }
        }




    }
}
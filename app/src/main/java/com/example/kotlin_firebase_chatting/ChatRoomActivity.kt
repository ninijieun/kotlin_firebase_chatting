package com.example.kotlin_firebase_chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.kotlin_firebase_chatting.Model.ChatModel
import com.example.kotlin_firebase_chatting.Model.ChatNewModel
import com.example.kotlin_firebase_chatting.adapter.ChatLeftYou
import com.example.kotlin_firebase_chatting.adapter.ChatRightMe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupieAdapter
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val TAG:String=ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        auth = Firebase.auth

        val myUid = auth.uid
        val youUid = intent.getStringExtra("yourUid").toString()
        val youName = intent.getStringExtra("yourName")

        val adapter = GroupieAdapter()

//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())

        recyclerView_chat.adapter = adapter

        val db = Firebase.firestore

        //firebase store 데이터베이스 불러오기
//        db.collection("message")
//            .orderBy("time")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.e("TAG", document.toString())
//
//                    //만약 내가 보낸 메세지일때,
//                    val sendUid = document.get("myUid")
//                    val msg = document.get("message").toString()
//
//                    if (sendUid!!.equals(myUid)) {
//                        adapter.add(ChatRightMe(msg))
//                    } else {
//                        adapter.add(ChatLeftYou(msg))
//                    }
//                }
//            }
//            .addOnFailureListener { }

        val database = Firebase.database
        val myRef = database.getReference("message")
        val readRef = database.getReference("message").child(myUid.toString()).child(youUid)

        val childEventListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("TAG","p0 : "+snapshot)

                val model = snapshot.getValue(ChatNewModel::class.java)
                val msg = model?.message.toString()
                val who = model?.who

                if(who == "me"){
                    adapter.add(ChatRightMe( msg))
                }else{
                    adapter.add(ChatLeftYou(msg))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        readRef.addChildEventListener(childEventListener)

        button.setOnClickListener {
            //real database쓰기
            val message = editTextTextPersonName.text.toString()
            editTextTextPersonName.setText("")
            //보낸사람 data
            val sendChat = ChatNewModel(myUid.toString(), youUid, message, System.currentTimeMillis(),"me")
            myRef.child(myUid.toString()).child(youUid).push().setValue(sendChat)

            //받는사람 data
            val getChat = ChatNewModel(youUid, myUid.toString(), message, System.currentTimeMillis(),"you")
            myRef.child(youUid).child(myUid.toString()).push().setValue(getChat)
        }
//         firebase database store 쓰기
        //            val message = editTextTextPersonName.text.toString()
//            editTextTextPersonName.setText("")
//
//            val chat = ChatModel(myUid.toString(),youUid , message, System.currentTimeMillis())
//
//            //DB저장
//            db.collection("message")
//                .add(chat)
//                .addOnSuccessListener {
//                    Log.d("TAG","메세지 저장 성공")
//                }
//                .addOnFailureListener {
//                    Log.d("TAG","메세지 저장 실패")
//                }
//        }
//
//    }
    }
}
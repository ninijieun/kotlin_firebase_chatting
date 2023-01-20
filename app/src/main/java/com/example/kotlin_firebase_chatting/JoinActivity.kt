package com.example.kotlin_firebase_chatting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_firebase_chatting.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {

    // 비밀번호 정규식
    private val PASSWORD_PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$")

    private lateinit var auth: FirebaseAuth //firebase연동
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        auth = Firebase.auth

        //회워가입.
        join_button.setOnClickListener {
            val email = email_area.text.toString()
            val pwd = password_area.text.toString()

            if(!joinValidation()){
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val uid = auth.uid.toString()
                        val user = User(uid, userName_area.text.toString(),"",System.currentTimeMillis())

                        //데이터베이스 firestore 넣음
                        val db = Firebase.firestore.collection("users")
                        db.document(auth.uid.toString())
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "데이터베이스 성공")
                            }
                            .addOnFailureListener {
                                Log.w(TAG, "데이터베이스 실패", task.exception)
                            }
                        val intent = Intent(this, FriendsListActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }

    fun joinValidation(): Boolean {
        //사용자 이름
        val username = userName_area.text.toString()
        if(username.length==0){
            Toast.makeText(this,"사용자 이름을 입력하세요.",Toast.LENGTH_SHORT).show()
            userName_area.requestFocus()
            return false
        }

        //이메일
        val email = email_area.text.toString()
        if(email.length==0){
            Toast.makeText(this,"Email을 입력하세요.",Toast.LENGTH_SHORT).show()
            email_area.requestFocus()
            return false
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show()
            email_area.requestFocus()
            return false
        }

        //패스워드
        val pwd = password_area.text.toString()
        if(pwd.length == 0){
            Toast.makeText(this,"패스워드를 입력하세요.",Toast.LENGTH_SHORT).show()
            password_area.requestFocus()
            return false
        }else if(!PASSWORD_PATTERN.matcher(pwd).matches()){
            Toast.makeText(this,"패스워드 형식이 아닙니다.(6-16자리이내)",Toast.LENGTH_SHORT).show()
            password_area.requestFocus()
            return false
        }
        return true
    }
}